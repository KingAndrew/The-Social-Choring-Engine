-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: comments before and after the routine body will not be stored by the server
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE `get_players_for_account`(account_id BIGINT(44))
BEGIN
  SELECT * FROM PLAYER WHERE parent_id=account_id;
END
