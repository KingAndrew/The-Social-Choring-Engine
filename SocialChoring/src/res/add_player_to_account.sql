-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: comments before and after the routine body will not be stored by the server
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE `add_new_player_to_account`(account_id BIGINT(44), player_first_name VARCHAR(44))
BEGIN

  INSERT INTO PLAYER (parent_id, first_name) VALUES (account_id, player_first_name);
END
