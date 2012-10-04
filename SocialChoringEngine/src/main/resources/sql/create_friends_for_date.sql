-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: comments before and after the routine body will not be stored by the server
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE `create_friends_for_date`(IN p_date DATE, IN p_player_one_id BIGINT(44), 

                                                               IN p_player_two_id BIGINT(44), OUT success BOOLEAN)
BEGIN
	DECLARE playerId BIGINT(44);
  	SET success = FALSE;
  	SELECT id FROM player WHERE id = p_player_one_id INTO playerId;
  	IF playerId = p_player_one_id THEN
  		SELECT id FROM player WHERE id = p_player_two_id INTO playerId;
  		IF playerId = p_player_two_id THEN
  			INSERT INTO FRIENDS_FOR_DATE(player_one, player_two, begin_date) VALUES (p_player_one_id, p_player_two_id, p_date);
			IF ROW_COUNT() > 0 THEN
				SET success = TRUE;
  			END IF;
  		END IF;
 	END IF;  	
END
