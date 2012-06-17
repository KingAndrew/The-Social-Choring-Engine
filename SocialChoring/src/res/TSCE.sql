-- phpMyAdmin SQL Dump
-- version 3.3.7deb5build0.10.10.1
-- http://www.phpmyadmin.net
--
-- Host: localhost:3306
-- Generation Time: Jun 15, 2012 at 02:12 AM
-- Server version: 5.1.49
-- PHP Version: 5.3.3-1ubuntu9.3

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `TSCE`
--

-- --------------------------------------------------------

--
-- Table structure for table `FRIENDS_FOR_DATE`
--

CREATE TABLE IF NOT EXISTS `FRIENDS_FOR_DATE` (
  `id` bigint(44) NOT NULL AUTO_INCREMENT,
  `begin_date` date NOT NULL COMMENT 'the date at which this friendship started.',
  `player_one` bigint(44) NOT NULL COMMENT 'foreign key to PLAYER for the owner',
  `player_two` bigint(44) NOT NULL COMMENT 'foreign key to PLAYER for the friends',
  PRIMARY KEY (`id`),
  KEY `player_owner` (`player_one`),
  KEY `player_friend` (`player_two`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='Each night the winners need to be calculated.  To do this th' AUTO_INCREMENT=7 ;

--
-- Dumping data for table `FRIENDS_FOR_DATE`
--

INSERT INTO `FRIENDS_FOR_DATE` (`id`, `begin_date`, `player_one`, `player_two`) VALUES
(1, '2012-01-21', 1, 8),
(2, '2012-01-23', 1, 4),
(3, '2012-01-23', 1, 6),
(4, '2012-01-23', 6, 8),
(5, '2012-01-23', 6, 9),
(6, '2012-01-23', 9, 8);

-- --------------------------------------------------------

--
-- Table structure for table `GAME`
--

CREATE TABLE IF NOT EXISTS `GAME` (
  `id` bigint(44) NOT NULL AUTO_INCREMENT,
  `company` varchar(30) NOT NULL,
  `name_of_game` varchar(15) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `g_id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='This is a multi-tenant system.  The tenants will be Companys' AUTO_INCREMENT=1 ;

--
-- Dumping data for table `GAME`
--


-- --------------------------------------------------------

--
-- Table structure for table `MASTER_CHORE`
--

CREATE TABLE IF NOT EXISTS `MASTER_CHORE` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `description` varchar(50) NOT NULL COMMENT 'Description of the chore',
  `ideal_time` mediumtext NOT NULL,
  `max_time` mediumtext NOT NULL,
  `min_time` mediumtext NOT NULL,
  `parent_chore` int(11) DEFAULT NULL COMMENT 'null unless this is a sub core.  ',
  PRIMARY KEY (`id`),
  UNIQUE KEY `mc_name_UNIQUE` (`name`),
  UNIQUE KEY `mc_id_UNIQUE` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='This is where all the Chores enter existance.' AUTO_INCREMENT=12 ;

--
-- Dumping data for table `MASTER_CHORE`
--

INSERT INTO `MASTER_CHORE` (`id`, `name`, `description`, `ideal_time`, `max_time`, `min_time`, `parent_chore`) VALUES
(1, 'Wake up', 'Alarm turned off and out of bed', '1', '5', '0', NULL),
(2, 'Make Bed', 'Straighten, tuck in sheets', '4', '5', '2', NULL),
(3, 'Make Breakfast', 'Prepare food to eat', '3', '15', '0', NULL),
(4, 'Eat Breakfast', 'Consume your food.', '10', '15', '5', NULL),
(5, 'Brush teeth', 'Tootpaste on brush on teet Move brush. Rinse', '2', '5', '1', NULL),
(6, 'Shower', 'Wash hair and body', '7', '15', '6', NULL),
(7, 'Bath', 'Wash hair and body', '8', '15', '7', NULL),
(8, 'Dry hair', 'Towel or blow dry hair', '2', '10', '1', NULL),
(9, 'Dry body', 'Towel dry body', '2', '5', '1', NULL),
(10, 'Put on underwear', 'Put underwear on body', '1', '5', '0', NULL),
(11, 'Puit on clothes', 'Put on the clothes peope will see', '2', '5', '0', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `PARENT`
--

CREATE TABLE IF NOT EXISTS `PARENT` (
  `id` bigint(44) NOT NULL AUTO_INCREMENT,
  `email` varchar(80) NOT NULL,
  `first_name` varchar(40) NOT NULL,
  `last_name` varchar(40) NOT NULL,
  `address_id` bigint(44) DEFAULT NULL COMMENT 'a foriegn key that points to ADDRESS',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `PARENT`
--

INSERT INTO `PARENT` (`id`, `email`, `first_name`, `last_name`, `address_id`) VALUES
(1, 'andrew.boyd@bbtech.net', 'Andrew', 'Boyd', NULL),
(6, 'david.nickel@davidnickel.com', 'David', 'Nickel', NULL),
(7, '------.a@bbtech.net', 'Scenario', 'A', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `PASSWORD`
--

CREATE TABLE IF NOT EXISTS `PASSWORD` (
  `id` bigint(44) NOT NULL AUTO_INCREMENT,
  `owner_id` bigint(44) NOT NULL COMMENT 'A foriegn key to either the PARENT or the PLAYER',
  `password` varchar(255) NOT NULL,
  `password_hint` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `PASSWORD`
--


-- --------------------------------------------------------

--
-- Table structure for table `PLAYER`
--

CREATE TABLE IF NOT EXISTS `PLAYER` (
  `id` bigint(44) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(40) NOT NULL COMMENT 'I would like to make the first name unique for each parent_id.',
  `earnings` float NOT NULL DEFAULT '0' COMMENT 'The amount earned from winning.',
  `champion_count` bigint(20) NOT NULL DEFAULT '0' COMMENT 'When a player earns more than any of his friends for the day that player gets one additional award_count.',
  `parent_id` bigint(44) NOT NULL,
  `date_added` datetime DEFAULT NULL,
  `date_validated` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL COMMENT 'Version of the table',
  PRIMARY KEY (`id`),
  UNIQUE KEY `first_name_UNIQUE` (`first_name`),
  KEY `fk_PLAYER_Parent1` (`parent_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=16 ;

--
-- Dumping data for table `PLAYER`
--

INSERT INTO `PLAYER` (`id`, `first_name`, `earnings`, `champion_count`, `parent_id`, `date_added`, `date_validated`, `version`) VALUES
(1, 'Victor', 6, 0, 1, NULL, NULL, NULL),
(4, 'Hoss', 3, 0, 1, NULL, NULL, NULL),
(6, 'Megan', 20, 1, 6, NULL, NULL, NULL),
(7, 'Adam', 0, 0, 1, NULL, NULL, NULL),
(8, 'Chris', 0, 0, 6, NULL, NULL, NULL),
(9, 'Dave', 0, 0, 6, NULL, NULL, NULL),
(15, 'player01', 0, 0, 7, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `PLAYER_CHORE_OBSERVED`
--

CREATE TABLE IF NOT EXISTS `PLAYER_CHORE_OBSERVED` (
  `id` bigint(44) NOT NULL AUTO_INCREMENT,
  `chore_plan_id` bigint(44) NOT NULL,
  `date_observed` date NOT NULL,
  `time_ended` mediumtext,
  `time_started` mediumtext NOT NULL COMMENT 'when the chore was started.',
  `max_time` mediumtext NOT NULL,
  `min_time` mediumtext NOT NULL,
  `ideal_time` mediumtext NOT NULL,
  `was_in_time` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'calculated by observed_time <= max_time',
  `did_complete` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'needs to be true whenever ended_time is !NULL and ended_time > min_time',
  `earnings` int(11) NOT NULL DEFAULT '0' COMMENT 'what the player earned for this chore. calculated by if(did_complete){ +1 if(min_time >= observed_time <=max_time) {+1 if(min_time >= observed_time <= ideal_time) +1}}}',
  PRIMARY KEY (`id`),
  KEY `fk_PLAYER_CHORE_OBSERVED_CHORE_PLAN1` (`chore_plan_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='This is how a PLAYER did on a certain day.' AUTO_INCREMENT=18 ;

--
-- Dumping data for table `PLAYER_CHORE_OBSERVED`
--

INSERT INTO `PLAYER_CHORE_OBSERVED` (`id`, `chore_plan_id`, `date_observed`, `time_ended`, `time_started`, `max_time`, `min_time`, `ideal_time`, `was_in_time`, `did_complete`, `earnings`) VALUES
(1, 1, '0000-00-00', '5', '1', '5', '0', '1', 1, 1, 3),
(2, 20, '2012-01-23', '6', '5', '5', '1', '2', 1, 1, 3),
(3, 2, '2012-01-23', '15', '7', '5', '2', '4', 1, 1, 2),
(4, 2, '2012-01-23', '6', '7', '5', '2', '4', 0, 1, 1),
(5, 2, '2012-01-23', '9', '7', '5', '2', '4', 1, 1, 3),
(6, 31, '2012-01-23', '12', '8', '5', '0', '1', 1, 1, 2),
(7, 32, '2012-01-23', '9', '8', '5', '2', '4', 0, 1, 1),
(8, 33, '2012-01-23', '16', '8', '15', '0', '3', 1, 1, 2),
(9, 34, '2012-01-23', '24', '8', '15', '5', '10', 0, 1, 1),
(10, 35, '2012-01-23', '10', '8', '5', '1', '2', 1, 1, 3),
(11, 36, '2012-01-23', '12', '8', '15', '6', '7', 0, 1, 1),
(12, 37, '2012-01-23', '12', '8', '15', '7', '8', 0, 0, 0),
(13, 38, '2012-01-23', '19', '8', '10', '1', '2', 0, 1, 1),
(14, 39, '2012-01-23', '15', '8', '5', '1', '2', 0, 1, 1),
(15, 40, '2012-01-23', '12', '8', '5', '0', '1', 1, 1, 2),
(16, 41, '2012-01-23', '10', '8', '5', '0', '2', 1, 1, 3),
(17, 41, '2012-01-23', '10', '8', '5', '0', '2', 1, 1, 3);

-- --------------------------------------------------------

--
-- Table structure for table `PLAYER_CHORE_PLAN`
--

CREATE TABLE IF NOT EXISTS `PLAYER_CHORE_PLAN` (
  `id` bigint(44) NOT NULL AUTO_INCREMENT,
  `player_id` bigint(44) NOT NULL,
  `name` varchar(20) NOT NULL,
  `description` varchar(45) NOT NULL,
  `how_often` enum('EVERY_DAY','WEEK_DAYS','WEEK_ENDS','ONCE_AWEEK','ONCE_AMONTH','ONCE_AYEAR') NOT NULL DEFAULT 'WEEK_DAYS',
  `when` enum('MORNING','DAY','EVENING') NOT NULL DEFAULT 'MORNING',
  `ideal_time` mediumtext NOT NULL,
  `min_time` mediumtext NOT NULL,
  `max_time` mediumtext NOT NULL,
  PRIMARY KEY (`id`),
  KEY `p_id` (`player_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='PLAYER_CHORE_PLAN is a resolution table for the many to many' AUTO_INCREMENT=102 ;

--
-- Dumping data for table `PLAYER_CHORE_PLAN`
--

INSERT INTO `PLAYER_CHORE_PLAN` (`id`, `player_id`, `name`, `description`, `how_often`, `when`, `ideal_time`, `min_time`, `max_time`) VALUES
(1, 1, 'Wake up', 'Alarm turned off and out of bed', 'WEEK_DAYS', 'MORNING', '1', '0', '5'),
(2, 1, 'Make Bed', 'Straighten, tuck in sheets', 'WEEK_DAYS', 'MORNING', '4', '2', '5'),
(3, 1, 'Make Breakfast', 'Prepare food to eat', 'WEEK_DAYS', 'MORNING', '3', '0', '15'),
(4, 1, 'Eat Breakfast', 'Consume your food.', 'WEEK_DAYS', 'MORNING', '10', '5', '15'),
(5, 1, 'Brush teeth', 'Tootpaste on brush on teet Move brush. Rinse', 'WEEK_DAYS', 'MORNING', '2', '1', '5'),
(6, 1, 'Shower', 'Wash hair and body', 'WEEK_DAYS', 'MORNING', '7', '6', '15'),
(7, 1, 'Bath', 'Wash hair and body', 'WEEK_DAYS', 'MORNING', '8', '7', '15'),
(8, 1, 'Dry hair', 'Towel or blow dry hair', 'WEEK_DAYS', 'MORNING', '2', '1', '10'),
(9, 1, 'Dry body', 'Towel dry body', 'WEEK_DAYS', 'MORNING', '2', '1', '5'),
(10, 1, 'Put on underwear', 'Put underwear on body', 'WEEK_DAYS', 'MORNING', '1', '0', '5'),
(11, 1, 'Puit on clothes', 'Put on the clothes peope will see', 'WEEK_DAYS', 'MORNING', '2', '0', '5'),
(16, 4, 'Wake up', 'Alarm turned off and out of bed', 'WEEK_DAYS', 'MORNING', '1', '0', '5'),
(17, 4, 'Make Bed', 'Straighten, tuck in sheets', 'WEEK_DAYS', 'MORNING', '4', '2', '5'),
(18, 4, 'Make Breakfast', 'Prepare food to eat', 'WEEK_DAYS', 'MORNING', '3', '0', '15'),
(19, 4, 'Eat Breakfast', 'Consume your food.', 'WEEK_DAYS', 'MORNING', '10', '5', '15'),
(20, 4, 'Brush teeth', 'Tootpaste on brush on teet Move brush. Rinse', 'WEEK_DAYS', 'MORNING', '2', '1', '5'),
(21, 4, 'Shower', 'Wash hair and body', 'WEEK_DAYS', 'MORNING', '7', '6', '15'),
(22, 4, 'Bath', 'Wash hair and body', 'WEEK_DAYS', 'MORNING', '8', '7', '15'),
(23, 4, 'Dry hair', 'Towel or blow dry hair', 'WEEK_DAYS', 'MORNING', '2', '1', '10'),
(24, 4, 'Dry body', 'Towel dry body', 'WEEK_DAYS', 'MORNING', '2', '1', '5'),
(25, 4, 'Put on underwear', 'Put underwear on body', 'WEEK_DAYS', 'MORNING', '1', '0', '5'),
(26, 4, 'Puit on clothes', 'Put on the clothes peope will see', 'WEEK_DAYS', 'MORNING', '2', '0', '5'),
(31, 6, 'Wake up', 'Alarm turned off and out of bed', 'WEEK_DAYS', 'MORNING', '1', '0', '5'),
(32, 6, 'Make Bed', 'Straighten, tuck in sheets', 'WEEK_DAYS', 'MORNING', '4', '2', '5'),
(33, 6, 'Make Breakfast', 'Prepare food to eat', 'WEEK_DAYS', 'MORNING', '3', '0', '15'),
(34, 6, 'Eat Breakfast', 'Consume your food.', 'WEEK_DAYS', 'MORNING', '10', '5', '15'),
(35, 6, 'Brush teeth', 'Tootpaste on brush on teet Move brush. Rinse', 'WEEK_DAYS', 'MORNING', '2', '1', '5'),
(36, 6, 'Shower', 'Wash hair and body', 'WEEK_DAYS', 'MORNING', '7', '6', '15'),
(37, 6, 'Bath', 'Wash hair and body', 'WEEK_DAYS', 'MORNING', '8', '7', '15'),
(38, 6, 'Dry hair', 'Towel or blow dry hair', 'WEEK_DAYS', 'MORNING', '2', '1', '10'),
(39, 6, 'Dry body', 'Towel dry body', 'WEEK_DAYS', 'MORNING', '2', '1', '5'),
(40, 6, 'Put on underwear', 'Put underwear on body', 'WEEK_DAYS', 'MORNING', '1', '0', '5'),
(41, 6, 'Puit on clothes', 'Put on the clothes peope will see', 'WEEK_DAYS', 'MORNING', '2', '0', '5'),
(46, 7, 'Wake up', 'Alarm turned off and out of bed', 'WEEK_DAYS', 'MORNING', '1', '0', '5'),
(47, 7, 'Make Bed', 'Straighten, tuck in sheets', 'WEEK_DAYS', 'MORNING', '4', '2', '5'),
(48, 7, 'Make Breakfast', 'Prepare food to eat', 'WEEK_DAYS', 'MORNING', '3', '0', '15'),
(49, 7, 'Eat Breakfast', 'Consume your food.', 'WEEK_DAYS', 'MORNING', '10', '5', '15'),
(50, 7, 'Brush teeth', 'Tootpaste on brush on teet Move brush. Rinse', 'WEEK_DAYS', 'MORNING', '2', '1', '5'),
(51, 7, 'Shower', 'Wash hair and body', 'WEEK_DAYS', 'MORNING', '7', '6', '15'),
(52, 7, 'Bath', 'Wash hair and body', 'WEEK_DAYS', 'MORNING', '8', '7', '15'),
(53, 7, 'Dry hair', 'Towel or blow dry hair', 'WEEK_DAYS', 'MORNING', '2', '1', '10'),
(54, 7, 'Dry body', 'Towel dry body', 'WEEK_DAYS', 'MORNING', '2', '1', '5'),
(55, 7, 'Put on underwear', 'Put underwear on body', 'WEEK_DAYS', 'MORNING', '1', '0', '5'),
(56, 7, 'Puit on clothes', 'Put on the clothes peope will see', 'WEEK_DAYS', 'MORNING', '2', '0', '5'),
(61, 8, 'Wake up', 'Alarm turned off and out of bed', 'WEEK_DAYS', 'MORNING', '1', '0', '5'),
(62, 8, 'Make Bed', 'Straighten, tuck in sheets', 'WEEK_DAYS', 'MORNING', '4', '2', '5'),
(63, 8, 'Make Breakfast', 'Prepare food to eat', 'WEEK_DAYS', 'MORNING', '3', '0', '15'),
(64, 8, 'Eat Breakfast', 'Consume your food.', 'WEEK_DAYS', 'MORNING', '10', '5', '15'),
(65, 8, 'Brush teeth', 'Tootpaste on brush on teet Move brush. Rinse', 'WEEK_DAYS', 'MORNING', '2', '1', '5'),
(66, 8, 'Shower', 'Wash hair and body', 'WEEK_DAYS', 'MORNING', '7', '6', '15'),
(67, 8, 'Bath', 'Wash hair and body', 'WEEK_DAYS', 'MORNING', '8', '7', '15'),
(68, 8, 'Dry hair', 'Towel or blow dry hair', 'WEEK_DAYS', 'MORNING', '2', '1', '10'),
(69, 8, 'Dry body', 'Towel dry body', 'WEEK_DAYS', 'MORNING', '2', '1', '5'),
(70, 8, 'Put on underwear', 'Put underwear on body', 'WEEK_DAYS', 'MORNING', '1', '0', '5'),
(71, 8, 'Puit on clothes', 'Put on the clothes peope will see', 'WEEK_DAYS', 'MORNING', '2', '0', '5'),
(76, 9, 'Wake up', 'Alarm turned off and out of bed', 'WEEK_DAYS', 'MORNING', '1', '0', '5'),
(77, 9, 'Make Bed', 'Straighten, tuck in sheets', 'WEEK_DAYS', 'MORNING', '4', '2', '5'),
(78, 9, 'Make Breakfast', 'Prepare food to eat', 'WEEK_DAYS', 'MORNING', '3', '0', '15'),
(79, 9, 'Eat Breakfast', 'Consume your food.', 'WEEK_DAYS', 'MORNING', '10', '5', '15'),
(80, 9, 'Brush teeth', 'Tootpaste on brush on teet Move brush. Rinse', 'WEEK_DAYS', 'MORNING', '2', '1', '5'),
(81, 9, 'Shower', 'Wash hair and body', 'WEEK_DAYS', 'MORNING', '7', '6', '15'),
(82, 9, 'Bath', 'Wash hair and body', 'WEEK_DAYS', 'MORNING', '8', '7', '15'),
(83, 9, 'Dry hair', 'Towel or blow dry hair', 'WEEK_DAYS', 'MORNING', '2', '1', '10'),
(84, 9, 'Dry body', 'Towel dry body', 'WEEK_DAYS', 'MORNING', '2', '1', '5'),
(85, 9, 'Put on underwear', 'Put underwear on body', 'WEEK_DAYS', 'MORNING', '1', '0', '5'),
(86, 9, 'Puit on clothes', 'Put on the clothes peope will see', 'WEEK_DAYS', 'MORNING', '2', '0', '5'),
(91, 9, 'Wake up', 'Alarm turned off and out of bed', 'WEEK_DAYS', 'MORNING', '1', '0', '5'),
(92, 9, 'Make Bed', 'Straighten, tuck in sheets', 'WEEK_DAYS', 'MORNING', '4', '2', '5'),
(93, 9, 'Make Breakfast', 'Prepare food to eat', 'WEEK_DAYS', 'MORNING', '3', '0', '15'),
(94, 9, 'Eat Breakfast', 'Consume your food.', 'WEEK_DAYS', 'MORNING', '10', '5', '15'),
(95, 9, 'Brush teeth', 'Tootpaste on brush on teet Move brush. Rinse', 'WEEK_DAYS', 'MORNING', '2', '1', '5'),
(96, 9, 'Shower', 'Wash hair and body', 'WEEK_DAYS', 'MORNING', '7', '6', '15'),
(97, 9, 'Bath', 'Wash hair and body', 'WEEK_DAYS', 'MORNING', '8', '7', '15'),
(98, 9, 'Dry hair', 'Towel or blow dry hair', 'WEEK_DAYS', 'MORNING', '2', '1', '10'),
(99, 9, 'Dry body', 'Towel dry body', 'WEEK_DAYS', 'MORNING', '2', '1', '5'),
(100, 9, 'Put on underwear', 'Put underwear on body', 'WEEK_DAYS', 'MORNING', '1', '0', '5'),
(101, 9, 'Puit on clothes', 'Put on the clothes peope will see', 'WEEK_DAYS', 'MORNING', '2', '0', '5');

-- --------------------------------------------------------

--
-- Table structure for table `PLAYER_DAY_PLAN`
--

CREATE TABLE IF NOT EXISTS `PLAYER_DAY_PLAN` (
  `id` bigint(44) NOT NULL AUTO_INCREMENT,
  `player_id` bigint(44) NOT NULL,
  `morning_start` time DEFAULT NULL,
  `morning_end` time DEFAULT NULL,
  `day_start` time DEFAULT NULL,
  `day_end` time DEFAULT NULL,
  `evening_start` time DEFAULT NULL,
  `evening_end` time DEFAULT NULL,
  `night_start` time DEFAULT NULL,
  `night_end` time DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_PLAYER_DAY_PLAN_PLAYER1` (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='PLAYER_DAY_PLAN tracks how the day is divided between mornin' AUTO_INCREMENT=1 ;

--
-- Dumping data for table `PLAYER_DAY_PLAN`
--


-- --------------------------------------------------------

--
-- Table structure for table `PLAYER_GAMES`
--

CREATE TABLE IF NOT EXISTS `PLAYER_GAMES` (
  `id` bigint(44) NOT NULL AUTO_INCREMENT,
  `game_id` bigint(44) NOT NULL,
  `player_id` bigint(44) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_PLAYER_GAMES_GAME1` (`game_id`),
  KEY `fk_PLAYER_GAMES_PLAYER1` (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='This is a resolution table between PLAYER and GAME' AUTO_INCREMENT=1 ;

--
-- Dumping data for table `PLAYER_GAMES`
--


--
-- Constraints for dumped tables
--

--
-- Constraints for table `FRIENDS_FOR_DATE`
--
ALTER TABLE `FRIENDS_FOR_DATE`
  ADD CONSTRAINT `player_owner` FOREIGN KEY (`player_one`) REFERENCES `PLAYER` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `PLAYER`
--
ALTER TABLE `PLAYER`
  ADD CONSTRAINT `fk_PLAYER_Parent1` FOREIGN KEY (`parent_id`) REFERENCES `PARENT` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `PLAYER_CHORE_OBSERVED`
--
ALTER TABLE `PLAYER_CHORE_OBSERVED`
  ADD CONSTRAINT `fk_PLAYER_CHORE_OBSERVED_CHORE_PLAN1` FOREIGN KEY (`chore_plan_id`) REFERENCES `PLAYER_CHORE_PLAN` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `PLAYER_CHORE_PLAN`
--
ALTER TABLE `PLAYER_CHORE_PLAN`
  ADD CONSTRAINT `player_id` FOREIGN KEY (`player_id`) REFERENCES `PLAYER` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `PLAYER_DAY_PLAN`
--
ALTER TABLE `PLAYER_DAY_PLAN`
  ADD CONSTRAINT `fk_PLAYER_DAY_PLAN_PLAYER1` FOREIGN KEY (`player_id`) REFERENCES `PLAYER` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `PLAYER_GAMES`
--
ALTER TABLE `PLAYER_GAMES`
  ADD CONSTRAINT `fk_PLAYER_GAMES_GAME1` FOREIGN KEY (`game_id`) REFERENCES `GAME` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_PLAYER_GAMES_PLAYER1` FOREIGN KEY (`player_id`) REFERENCES `PLAYER` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
