-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: comments before and after the routine body will not be stored by the server
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`root`@`localhost` PROCEDURE `get_friends_for_player`(IN p_player_id BIGINT(44), IN p_begin_date DATE)
BEGIN
  SELECT * FROM FRIENDS_FOR_DATE
                WHERE begin_date <= p_begin_date
                AND (p_player_id = player_one OR p_player_id = player_two);
END
