-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: comments before and after the routine body will not be stored by the server
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE `create_chore_plan_for_player`(IN p_player_id BIGINT(44), IN chore_id BIGINT(44), OUT chore_plan_id BIGINT(44))
BEGIN

-- copy all the MASTER_CHORE to PLAYER_CHORE_PLAN for the player.
  DECLARE _id BIGINT(44);
  SET chore_plan_id= -1;
  SELECT id FROM MASTER_CHORE WHERE id = chore_id INTO _id;
  IF _id = chore_id THEN
      SELECT id FROM PLAYER WHERE id = p_player_id INTO _id;
      IF _id = p_player_id THEN
          INSERT INTO 
            PLAYER_CHORE_PLAN(player_id, name, description, ideal_time, max_time, min_time) 
          SELECT 
            p_player_id, MC.name, MC.description, MC.ideal_time, MC.max_time, MC.min_time 
          FROM
            MASTER_CHORE MC
          WHERE ID = chore_id;
          SET chore_plan_id = LAST_INSERT_ID();
       END IF;  	
 	END IF;  	
END
