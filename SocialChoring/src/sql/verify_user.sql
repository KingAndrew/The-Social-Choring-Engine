-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: comments before and after the routine body will not be stored by the server
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE `verify_user`( IN p_user_name VARCHAR(44), OUT success BOOLEAN)
BEGIN
  DECLARE session_life INT DEFAULT 5*60; -- SECOND
  DECLARE session_start DATETIME;
  SET success = TRUE;
  SELECT session_start FROM USERS WHERE user_name = p_user_name AND disabled = FALSE INTO session_start;
  IF session_start IS NOT NULL THEN
    IF TIMESTAMPDIFF(SECOND, session_start, NOW()) > session_life THEN
        SET success = FALSE;
    ELSE
         SET success = TRUE;
  	END IF;
  ELSE
    SET success = FALSE;
  END IF;
END
