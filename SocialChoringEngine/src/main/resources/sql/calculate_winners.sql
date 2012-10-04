-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: comments before and after the routine body will not be stored by the server
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE `calculate_winners`(IN p_date_observed DATE, OUT success BOOLEAN)
    DETERMINISTIC
BEGIN 


-- Scoring: max 3, min 0 points per chore.
-- Calculating team multiplier
--   for each friend_for_date 
--       create a list of the top ten total of PLAYER_CHORE_OBSERVED.earnings for that date.
-- 
-- loop test
DECLARE v_finished INT;
DECLARE v_king              BIGINT(44);

-- players must have the team mininum size to compete for winner 
DECLARE c_min_team_size    INT DEFAULT 5;
DECLARE v_team_size        INT;
DECLARE v_friends          INT;
DECLARE v_other_kings       INT;
DECLARE v_other_non_kings   INT;

DECLARE v_loop_counter      INT;

-- used to calculate the reward
DECLARE c_base_mulitplier   FLOAT DEFAULT 0.10;

-- v_team_multiplier depends on the size of the team
DECLARE v_team_multiplier  FLOAT;

-- v_friend is a player_id
DECLARE v_friend            BIGINT(44);

-- declare vars to populate temp table
DECLARE v_player_id          BIGINT(44);
DECLARE v_rank               LONG;

DECLARE v_points_total       INT;
DECLARE v_king_points_total  INT;


DECLARE v_need_update       BOOLEAN DEFAULT  FALSE;
DECLARE v_did_win_own_team  BOOLEAN DEFAULT  TRUE;
DECLARE v_number_in_team    INT;



DECLARE earnings_curs CURSOR FOR
    SELECT player_id, points_total, did_win_own_team
	FROM   player_team;

DECLARE kings_curs CURSOR FOR
	SELECT player_id, points_total
	FROM   player_team
    WHERE  did_win_own_team = TRUE
	GROUP BY player_id 
    ORDER BY points_total DESC;


-- other non-king members in same team
DECLARE rewards_curs CURSOR FOR
	SELECT player_id AS friend, points_total 
  FROM player_team
  WHERE player_id <> v_king AND king_id = v_king
  GROUP BY friend 
  ORDER BY points_total DESC
  LIMIT 9; -- we reward the top ten. we already have the winner)

DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_finished = 1;

-- You might want to look at this article for nested cursors
-- http://rpbouman.blogspot.com/2005/10/nesting-mysql-cursor-loops.html

-- drop the table we are about to create.  
DROP TEMPORARY TABLE IF EXISTS player_team;

-- get all the players total points earned
CREATE TEMPORARY TABLE player_team  
 	(player_id BIGINT(44), points_total INT, did_win_own_team BOOLEAN, earnings FLOAT, king_id BIGINT(44));


INSERT INTO player_team (player_id, points_total, did_win_own_team, earnings, king_id)
	SELECT  PLA.id AS player_id,  
            sum(PCO.earnings) AS points_total, 
            TRUE  AS did_win_own_team,   -- everyone starts off winning their own team           
            sum(PCO.earnings) AS earnings,  -- if you aren't in a winning team this is all u get.  
            NULL AS king_id

     FROM PLAYER_CHORE_OBSERVED AS PCO,
          PLAYER_CHORE_PLAN AS PCP,
          PLAYER AS PLA

    WHERE PCO.date_observed = p_date_observed  -- "2012-01-23"
    AND PCP.id = PCO.chore_plan_id
    AND PLA.id = PCP.player_id
    GROUP BY PLA.id
    ORDER BY points_total DESC;

-- set loop control to not finished
SET v_finished = FALSE;
SET success = FALSE;

OPEN earnings_curs;
-- looping through all the players.
LOOP1: LOOP
  -- descend through player point_total setting v_did_win_own_team
  FETCH earnings_curs INTO  v_player_id, v_points_total, v_did_win_own_team;
  -- check for v_finished
	IF v_finished THEN 
     SET v_finished = false;
     LEAVE LOOP1;
  END IF;
    
    SELECT COUNT(*) 
        FROM FRIENDS_FOR_DATE AS FFD
        WHERE FFD.begin_date <= p_date_observed AND
                (v_player_id = player_one OR v_player_id = player_two)
        INTO v_friends;
    
-- you must have at least 5 friends in your team to get rewards
-- set did_win_own_team to false if points_total is less than v_points_total
	IF v_friends < (c_min_team_size -1) THEN 
    UPDATE player_team 
        SET  did_win_own_team = FALSE       
        WHERE player_id = v_player_id;
  ELSE
    UPDATE player_team 
        SET  did_win_own_team = FALSE       
        WHERE (points_total <  v_points_total
                AND player_id IN (SELECT 
                    CASE v_player_id 
                        WHEN player_one  THEN player_two
                        WHEN player_two  THEN player_one 
                    END AS friend
                    FROM FRIENDS_FOR_DATE AS FFD
                    WHERE FFD.begin_date <= p_date_observed));
  END IF;
END LOOP LOOP1;
CLOSE earnings_curs;

-- Loop through players who won their own team and calculate the rewards
-- for the player and their team.
OPEN kings_curs;
LOOP2: LOOP
  FETCH kings_curs into v_king, v_king_points_total;
  -- check for v_finished
  IF v_finished THEN
          SET v_finished = false;
          CLOSE kings_curs;
          LEAVE LOOP2;
  END IF;
  -- want to compare rankings with each player

    -- get team size
    SELECT COUNT(*) 
    FROM FRIENDS_FOR_DATE
    WHERE begin_date <= p_date_observed AND
            v_king = player_one OR v_king = player_two
    INTO v_team_size;

    -- update winner first
     UPDATE player_team
     SET earnings = v_king_points_total*(1+ c_base_mulitplier)*(v_team_size+1), 
         king_id = v_king
     WHERE player_id = v_king;

    -- update other team members to have a king
    UPDATE player_team
    SET king_id = v_king
    WHERE player_id IN (SELECT CASE v_king 
                    WHEN player_one  THEN player_two
                    WHEN player_two  THEN player_one END
                FROM FRIENDS_FOR_DATE AS FFD
                WHERE FFD.begin_date <= p_date_observed)
    AND king_id IS NULL;
END LOOP LOOP2;


-- winner is rewarded now reward the team
-- reward the team
OPEN kings_curs;
LOOP3: LOOP
  FETCH kings_curs into v_king, v_king_points_total;
  -- check for v_finished
  IF v_finished THEN
      SET v_finished = false;
      CLOSE kings_curs;
      LEAVE LOOP3;
  END IF;
  
-- other kings in same team
  SELECT count(*)
  FROM player_team
  WHERE king_id = player_id AND player_id <> v_king
  AND player_id IN (SELECT CASE v_king 
                    WHEN player_one  THEN player_two
                    WHEN player_two  THEN player_one END
                FROM FRIENDS_FOR_DATE AS FFD
                WHERE FFD.begin_date <= p_date_observed)
  INTO v_other_kings;
  

-- other non-king members in same team
  SELECT count(*) 
  FROM player_team
  WHERE player_id <> v_king AND king_id = v_king
  INTO v_other_non_kings;
  
-- team size;
  SET v_team_size = v_other_kings + v_other_non_kings + 1;
  SET v_loop_counter = 0;
  OPEN rewards_curs;
  LOOP4: LOOP
      FETCH rewards_curs INTO v_friend, v_points_total;
      IF v_finished THEN
        SET v_finished = false;
        CLOSE rewards_curs;
        LEAVE LOOP4;        
      END IF;
      
      UPDATE player_team 
      SET earnings = v_points_total*( 1+ c_base_mulitplier)*(v_other_non_kings - v_loop_counter)
      WHERE player_id = v_friend;
      SET v_loop_counter = v_loop_counter + 1;
  END LOOP LOOP4;  
END LOOP LOOP3;

-- now update PLAYER from tmp table
UPDATE player, player_team 
SET player.earnings = player.earnings + player_team.earnings
WHERE player.id = player_team.player_id;


UPDATE player, player_team 
SET champion_count = champion_count + 1
WHERE player.id = player_team.player_id AND did_win_own_team = TRUE;

SELECT * FROM player_team;

-- drop the temp table
DROP TEMPORARY TABLE IF EXISTS player_team;

SET success = TRUE;

-- select sum(PCO.earnings) from 
-- PLAYER PL,   -- a specific player
-- FRIENDS_FOR_DATE FFD, -- thia gives you the player's friends
-- PLAYER_CHORE_PLAN PCP, -- this gives you their planned activities
-- PLAYER_CHORE_OBSERVED PCO -- this gives you their observed
-- where PL.id = FFD.player_one   -- OR player_two = FFD.player_one ?
-- and FFD.player_one = PCP.player_id  -- OR player_two = PCP.player_id ?
-- and PCP.id = PCO.chore_plan_id
-- group by PCP.player_id

-- this will return all the friend's scores 
END
