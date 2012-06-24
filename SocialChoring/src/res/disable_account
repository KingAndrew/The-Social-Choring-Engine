-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: comments before and after the routine body will not be stored by the server
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE `create_account`(IN p_parent_first_name VARCHAR(40), IN p_parent_last_name VARCHAR(40), IN p_parent_email VARCHAR(50), IN p_player_first_name VARCHAR(40), OUT account_id BIGINT(44))
BEGIN
  INSERT INTO PARENT (first_name, last_name, email) VALUES (p_parent_first_name, p_parent_last_name, p_parent_email);
    
  SET account_id = LAST_INSERT_ID();

  INSERT INTO PLAYER (parent_id, first_name) VALUES (account_id, p_player_first_name);
 
END
