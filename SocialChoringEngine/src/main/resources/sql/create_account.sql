-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: comments before and after the routine body will not be stored by the server
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE `create_account`(IN p_user_name VARCHAR(44), IN p_parent_first_name VARCHAR(40), IN p_parent_last_name VARCHAR(40), IN p_parent_email VARCHAR(50), IN p_player_first_name VARCHAR(40), OUT p_account_id BIGINT(44))
BEGIN
  INSERT INTO PARENT (first_name, last_name, email) VALUES (p_parent_first_name, p_parent_last_name, p_parent_email);
    
  SET p_account_id = LAST_INSERT_ID();

  INSERT INTO PLAYER (parent_id, first_name) VALUES (p_account_id, p_player_first_name);
  
  UPDATE USERS SET account_id = p_account_id WHERE user_name = p_user_name;
 
END
