-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: comments before and after the routine body will not be stored by the server
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE `get_account_for_user`(IN p_user_name VARCHAR(44), OUT p_account_id BIGINT(44))
BEGIN
  SELECT account_id FROM USERS WHERE USER_NAME=p_user_name INTO p_account_id; 
END
