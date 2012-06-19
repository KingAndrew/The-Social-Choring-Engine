-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: comments before and after the routine body will not be stored by the server
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE `stop_chore`(IN p_chore_observed_id BIGINT(44), 
            IN p_time_stoped LONG, IN p_did_complete BOOLEAN, OUT success BOOLEAN)
BEGIN

-- update the chore observed
  UPDATE 
    PLAYER_CHORE_OBSERVED 
  SET
    time_stoped   = p_time_stoped,
    did_complete  = p_did_complete,
    was_in_time   = min_time <= (p_time_stoped - time_started) <= max_time,
    observed_time = p_time_stoped - time_started,
    earnings      = SUM(p_did_complete) + 
                    SUM(p_did_complete AND (min_time <= (p_time_stoped - time_started) <= max_time)) + 
                    SUM(p_did_complete AND (min_time <= (p_time_stoped - time_started) <= ideal_time))
   WHERE
    id = p_chore_observed_id;
    
    SET success = TRUE;
END
