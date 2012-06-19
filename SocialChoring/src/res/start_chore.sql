-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: comments before and after the routine body will not be stored by the server
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE `start_chore`(IN p_chore_plan_id BIGINT(44), IN p_time_started LONG,
                                       IN p_date_observed DATE, OUT p_chore_observed_id BIGINT(44))
BEGIN
  DECLARE _id BIGINT(44);
  SET p_chore_observed_id= -1;
  SELECT id FROM PLAYER_CHORE_PLAN WHERE id = p_chore_plan_id INTO _id;
  IF _id = p_chore_plan_id THEN
      INSERT INTO 
        PLAYER_CHORE_OBSERVED(chore_plan_id, date_observed, time_started,
                              max_time, min_time, ideal_time) 
      SELECT 
        p_chore_plan_id,
        p_date_observed,
        p_time_started, 
        PCO.max_time, 
        PCO.min_time,
        PCO.ideal_time
      FROM
        PLAYER_CHORE_PLAN PCO
      WHERE
        PCO.id = p_chore_plan_id;
      Set p_chore_observed_id = LAST_INSERT_ID();
 	END IF;  	
END
