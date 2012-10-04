-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: comments before and after the routine body will not be stored by the server
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE `login`( IN user_name VARCHAR(44), IN user_id BIGINT(44), IN p_date_observed DATE, OUT success BOOLEAN)
BEGIN
  DECLARE parentId BIGINT(44);
  SET success = FALSE;
  SELECT id FROM parent WHERE id = account_id INTO parentId;
  IF parentId = account_id THEN
	INSERT INTO PLAYER (parent_id, first_name) VALUES (account_id, player_first_name);
	IF ROW_COUNT() > 0 THEN
		SET success = TRUE;
  	END IF;
  END IF;
END
