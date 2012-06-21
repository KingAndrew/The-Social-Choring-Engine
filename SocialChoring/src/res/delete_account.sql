-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: comments before and after the routine body will not be stored by the server
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_account`(IN account_id BIGINT(44), OUT success BOOLEAN)
    DETERMINISTIC
BEGIN 
	SET success = FALSE;
-- delete player's chore abserved
	DELETE FROM player_chore_observed WHERE chore_plan_id IN (SELECT id from player_chore_plan WHERE player_id IN (SELECT id FROM player WHERE parent_id=account_id));
-- delete player's chore plan
	DELETE FROM player_chore_plan WHERE player_id IN (SELECT id FROM player WHERE parent_id=account_id);
-- delete friends
  DELETE FROM friends_for_date WHERE player_one IN (SELECT id FROM player WHERE parent_id=account_id) OR player_two IN (SELECT id FROM player WHERE parent_id=account_id);
-- delete player
  DELETE FROM player WHERE parent_id=account_id;
-- delete parent
  DELETE FROM PARENT WHERE id=account_id;

  SET success = TRUE;
END
