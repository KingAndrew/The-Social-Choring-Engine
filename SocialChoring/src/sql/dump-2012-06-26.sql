CREATE DATABASE  IF NOT EXISTS `tsce` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `tsce`;
-- MySQL dump 10.13  Distrib 5.5.16, for Win32 (x86)
--
-- Host: localhost    Database: tsce
-- ------------------------------------------------------
-- Server version	5.5.25

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `player_chore_observed`
--

DROP TABLE IF EXISTS `player_chore_observed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `player_chore_observed` (
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
  KEY `fk_PLAYER_CHORE_OBSERVED_CHORE_PLAN1` (`chore_plan_id`),
  CONSTRAINT `fk_PLAYER_CHORE_OBSERVED_CHORE_PLAN1` FOREIGN KEY (`chore_plan_id`) REFERENCES `player_chore_plan` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1465 DEFAULT CHARSET=utf8 COMMENT='This is how a PLAYER did on a certain day.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player_chore_observed`
--

LOCK TABLES `player_chore_observed` WRITE;
/*!40000 ALTER TABLE `player_chore_observed` DISABLE KEYS */;
INSERT INTO `player_chore_observed` VALUES (1,1,'0000-00-00','5','1','5','0','1',1,1,3),(2,20,'2012-01-23','6','5','5','1','2',1,1,3),(3,2,'2012-01-23','15','7','5','2','4',1,1,2),(4,2,'2012-01-23','6','7','5','2','4',0,1,1),(5,2,'2012-01-23','9','7','5','2','4',1,1,3),(6,31,'2012-01-23','12','8','5','0','1',1,1,2),(7,32,'2012-01-23','9','8','5','2','4',0,1,1),(8,33,'2012-01-23','16','8','15','0','3',1,1,2),(9,34,'2012-01-23','24','8','15','5','10',0,1,1),(10,35,'2012-01-23','10','8','5','1','2',1,1,3),(11,36,'2012-01-23','12','8','15','6','7',0,1,1),(12,37,'2012-01-23','12','8','15','7','8',0,0,0),(13,38,'2012-01-23','19','8','10','1','2',0,1,1),(14,39,'2012-01-23','15','8','5','1','2',0,1,1),(15,40,'2012-01-23','12','8','5','0','1',1,1,2),(16,41,'2012-01-23','10','8','5','0','2',1,1,3),(17,41,'2012-01-23','10','8','5','0','2',1,1,3);
/*!40000 ALTER TABLE `player_chore_observed` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friends_for_date`
--

DROP TABLE IF EXISTS `friends_for_date`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `friends_for_date` (
  `id` bigint(44) NOT NULL AUTO_INCREMENT,
  `begin_date` date NOT NULL COMMENT 'the date at which this friendship started.',
  `player_one` bigint(44) NOT NULL COMMENT 'foreign key to PLAYER for the owner',
  `player_two` bigint(44) NOT NULL COMMENT 'foreign key to PLAYER for the friends',
  PRIMARY KEY (`id`),
  KEY `player_owner` (`player_one`),
  KEY `player_friend` (`player_two`),
  CONSTRAINT `player_owner` FOREIGN KEY (`player_one`) REFERENCES `player` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=494 DEFAULT CHARSET=utf8 COMMENT='Each night the winners need to be calculated.  To do this th';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friends_for_date`
--

LOCK TABLES `friends_for_date` WRITE;
/*!40000 ALTER TABLE `friends_for_date` DISABLE KEYS */;
INSERT INTO `friends_for_date` VALUES (1,'2012-01-21',1,8),(2,'2012-01-23',1,4),(3,'2012-01-23',1,6),(4,'2012-01-23',6,8),(5,'2012-01-23',6,9),(6,'2012-01-23',9,8);
/*!40000 ALTER TABLE `friends_for_date` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `password`
--

DROP TABLE IF EXISTS `password`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `password` (
  `id` bigint(44) NOT NULL AUTO_INCREMENT,
  `owner_id` bigint(44) NOT NULL COMMENT 'A foriegn key to either the PARENT or the PLAYER',
  `password` varchar(255) NOT NULL,
  `password_hint` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `password`
--

LOCK TABLES `password` WRITE;
/*!40000 ALTER TABLE `password` DISABLE KEYS */;
/*!40000 ALTER TABLE `password` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` bigint(44) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(45) NOT NULL,
  `user_id` bigint(44) DEFAULT NULL,
  `account_id` bigint(44) DEFAULT NULL,
  `session_start` datetime DEFAULT NULL,
  `disabled` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (2,'kalparser@Twitter',29941670,169,'2012-06-26 16:47:13',0),(3,'kalparser@Twitter',12345,169,'2012-06-26 16:58:09',0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parent`
--

DROP TABLE IF EXISTS `parent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parent` (
  `id` bigint(44) NOT NULL AUTO_INCREMENT,
  `email` varchar(80) NOT NULL,
  `first_name` varchar(40) NOT NULL,
  `last_name` varchar(40) NOT NULL,
  `address_id` bigint(44) DEFAULT NULL COMMENT 'a foriegn key that points to ADDRESS',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=170 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parent`
--

LOCK TABLES `parent` WRITE;
/*!40000 ALTER TABLE `parent` DISABLE KEYS */;
INSERT INTO `parent` VALUES (1,'andrew.boyd@bbtech.net','Andrew','Boyd',NULL),(6,'david.nickel@davidnickel.com','David','Nickel',NULL),(7,'------.a@bbtech.net','Scenario','A',NULL);
/*!40000 ALTER TABLE `parent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `player_day_plan`
--

DROP TABLE IF EXISTS `player_day_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `player_day_plan` (
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
  KEY `fk_PLAYER_DAY_PLAN_PLAYER1` (`player_id`),
  CONSTRAINT `fk_PLAYER_DAY_PLAN_PLAYER1` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='PLAYER_DAY_PLAN tracks how the day is divided between mornin';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player_day_plan`
--

LOCK TABLES `player_day_plan` WRITE;
/*!40000 ALTER TABLE `player_day_plan` DISABLE KEYS */;
/*!40000 ALTER TABLE `player_day_plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `player`
--

DROP TABLE IF EXISTS `player`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `player` (
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
  KEY `fk_PLAYER_Parent1` (`parent_id`),
  CONSTRAINT `fk_PLAYER_Parent1` FOREIGN KEY (`parent_id`) REFERENCES `parent` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=282 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player`
--

LOCK TABLES `player` WRITE;
/*!40000 ALTER TABLE `player` DISABLE KEYS */;
INSERT INTO `player` VALUES (1,'Victor',6,0,1,NULL,NULL,NULL),(4,'Hoss',3,0,1,NULL,NULL,NULL),(6,'Megan',20,1,6,NULL,NULL,NULL),(7,'Adam',0,0,1,NULL,NULL,NULL),(8,'Chris',0,0,6,NULL,NULL,NULL),(9,'Dave',0,0,6,NULL,NULL,NULL),(15,'player01',0,0,7,NULL,NULL,NULL);
/*!40000 ALTER TABLE `player` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `game`
--

DROP TABLE IF EXISTS `game`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `game` (
  `id` bigint(44) NOT NULL AUTO_INCREMENT,
  `company` varchar(30) NOT NULL,
  `name_of_game` varchar(15) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `g_id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='This is a multi-tenant system.  The tenants will be Companys';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game`
--

LOCK TABLES `game` WRITE;
/*!40000 ALTER TABLE `game` DISABLE KEYS */;
/*!40000 ALTER TABLE `game` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `master_chore`
--

DROP TABLE IF EXISTS `master_chore`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `master_chore` (
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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='This is where all the Chores enter existance.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `master_chore`
--

LOCK TABLES `master_chore` WRITE;
/*!40000 ALTER TABLE `master_chore` DISABLE KEYS */;
INSERT INTO `master_chore` VALUES (1,'Wake up','Alarm turned off and out of bed','1','5','0',NULL),(2,'Make Bed','Straighten, tuck in sheets','4','5','2',NULL),(3,'Make Breakfast','Prepare food to eat','3','15','0',NULL),(4,'Eat Breakfast','Consume your food.','10','15','5',NULL),(5,'Brush teeth','Tootpaste on brush on teet Move brush. Rinse','2','5','1',NULL),(6,'Shower','Wash hair and body','7','15','6',NULL),(7,'Bath','Wash hair and body','8','15','7',NULL),(8,'Dry hair','Towel or blow dry hair','2','10','1',NULL),(9,'Dry body','Towel dry body','2','5','1',NULL),(10,'Put on underwear','Put underwear on body','1','5','0',NULL),(11,'Puit on clothes','Put on the clothes peope will see','2','5','0',NULL);
/*!40000 ALTER TABLE `master_chore` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `player_chore_plan`
--

DROP TABLE IF EXISTS `player_chore_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `player_chore_plan` (
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
  KEY `p_id` (`player_id`),
  CONSTRAINT `player_id` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1656 DEFAULT CHARSET=utf8 COMMENT='PLAYER_CHORE_PLAN is a resolution table for the many to many';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player_chore_plan`
--

LOCK TABLES `player_chore_plan` WRITE;
/*!40000 ALTER TABLE `player_chore_plan` DISABLE KEYS */;
INSERT INTO `player_chore_plan` VALUES (1,1,'Wake up','Alarm turned off and out of bed','WEEK_DAYS','MORNING','1','0','5'),(2,1,'Make Bed','Straighten, tuck in sheets','WEEK_DAYS','MORNING','4','2','5'),(3,1,'Make Breakfast','Prepare food to eat','WEEK_DAYS','MORNING','3','0','15'),(4,1,'Eat Breakfast','Consume your food.','WEEK_DAYS','MORNING','10','5','15'),(5,1,'Brush teeth','Tootpaste on brush on teet Move brush. Rinse','WEEK_DAYS','MORNING','2','1','5'),(6,1,'Shower','Wash hair and body','WEEK_DAYS','MORNING','7','6','15'),(7,1,'Bath','Wash hair and body','WEEK_DAYS','MORNING','8','7','15'),(8,1,'Dry hair','Towel or blow dry hair','WEEK_DAYS','MORNING','2','1','10'),(9,1,'Dry body','Towel dry body','WEEK_DAYS','MORNING','2','1','5'),(10,1,'Put on underwear','Put underwear on body','WEEK_DAYS','MORNING','1','0','5'),(11,1,'Puit on clothes','Put on the clothes peope will see','WEEK_DAYS','MORNING','2','0','5'),(16,4,'Wake up','Alarm turned off and out of bed','WEEK_DAYS','MORNING','1','0','5'),(17,4,'Make Bed','Straighten, tuck in sheets','WEEK_DAYS','MORNING','4','2','5'),(18,4,'Make Breakfast','Prepare food to eat','WEEK_DAYS','MORNING','3','0','15'),(19,4,'Eat Breakfast','Consume your food.','WEEK_DAYS','MORNING','10','5','15'),(20,4,'Brush teeth','Tootpaste on brush on teet Move brush. Rinse','WEEK_DAYS','MORNING','2','1','5'),(21,4,'Shower','Wash hair and body','WEEK_DAYS','MORNING','7','6','15'),(22,4,'Bath','Wash hair and body','WEEK_DAYS','MORNING','8','7','15'),(23,4,'Dry hair','Towel or blow dry hair','WEEK_DAYS','MORNING','2','1','10'),(24,4,'Dry body','Towel dry body','WEEK_DAYS','MORNING','2','1','5'),(25,4,'Put on underwear','Put underwear on body','WEEK_DAYS','MORNING','1','0','5'),(26,4,'Puit on clothes','Put on the clothes peope will see','WEEK_DAYS','MORNING','2','0','5'),(31,6,'Wake up','Alarm turned off and out of bed','WEEK_DAYS','MORNING','1','0','5'),(32,6,'Make Bed','Straighten, tuck in sheets','WEEK_DAYS','MORNING','4','2','5'),(33,6,'Make Breakfast','Prepare food to eat','WEEK_DAYS','MORNING','3','0','15'),(34,6,'Eat Breakfast','Consume your food.','WEEK_DAYS','MORNING','10','5','15'),(35,6,'Brush teeth','Tootpaste on brush on teet Move brush. Rinse','WEEK_DAYS','MORNING','2','1','5'),(36,6,'Shower','Wash hair and body','WEEK_DAYS','MORNING','7','6','15'),(37,6,'Bath','Wash hair and body','WEEK_DAYS','MORNING','8','7','15'),(38,6,'Dry hair','Towel or blow dry hair','WEEK_DAYS','MORNING','2','1','10'),(39,6,'Dry body','Towel dry body','WEEK_DAYS','MORNING','2','1','5'),(40,6,'Put on underwear','Put underwear on body','WEEK_DAYS','MORNING','1','0','5'),(41,6,'Puit on clothes','Put on the clothes peope will see','WEEK_DAYS','MORNING','2','0','5'),(46,7,'Wake up','Alarm turned off and out of bed','WEEK_DAYS','MORNING','1','0','5'),(47,7,'Make Bed','Straighten, tuck in sheets','WEEK_DAYS','MORNING','4','2','5'),(48,7,'Make Breakfast','Prepare food to eat','WEEK_DAYS','MORNING','3','0','15'),(49,7,'Eat Breakfast','Consume your food.','WEEK_DAYS','MORNING','10','5','15'),(50,7,'Brush teeth','Tootpaste on brush on teet Move brush. Rinse','WEEK_DAYS','MORNING','2','1','5'),(51,7,'Shower','Wash hair and body','WEEK_DAYS','MORNING','7','6','15'),(52,7,'Bath','Wash hair and body','WEEK_DAYS','MORNING','8','7','15'),(53,7,'Dry hair','Towel or blow dry hair','WEEK_DAYS','MORNING','2','1','10'),(54,7,'Dry body','Towel dry body','WEEK_DAYS','MORNING','2','1','5'),(55,7,'Put on underwear','Put underwear on body','WEEK_DAYS','MORNING','1','0','5'),(56,7,'Puit on clothes','Put on the clothes peope will see','WEEK_DAYS','MORNING','2','0','5'),(61,8,'Wake up','Alarm turned off and out of bed','WEEK_DAYS','MORNING','1','0','5'),(62,8,'Make Bed','Straighten, tuck in sheets','WEEK_DAYS','MORNING','4','2','5'),(63,8,'Make Breakfast','Prepare food to eat','WEEK_DAYS','MORNING','3','0','15'),(64,8,'Eat Breakfast','Consume your food.','WEEK_DAYS','MORNING','10','5','15'),(65,8,'Brush teeth','Tootpaste on brush on teet Move brush. Rinse','WEEK_DAYS','MORNING','2','1','5'),(66,8,'Shower','Wash hair and body','WEEK_DAYS','MORNING','7','6','15'),(67,8,'Bath','Wash hair and body','WEEK_DAYS','MORNING','8','7','15'),(68,8,'Dry hair','Towel or blow dry hair','WEEK_DAYS','MORNING','2','1','10'),(69,8,'Dry body','Towel dry body','WEEK_DAYS','MORNING','2','1','5'),(70,8,'Put on underwear','Put underwear on body','WEEK_DAYS','MORNING','1','0','5'),(71,8,'Puit on clothes','Put on the clothes peope will see','WEEK_DAYS','MORNING','2','0','5'),(76,9,'Wake up','Alarm turned off and out of bed','WEEK_DAYS','MORNING','1','0','5'),(77,9,'Make Bed','Straighten, tuck in sheets','WEEK_DAYS','MORNING','4','2','5'),(78,9,'Make Breakfast','Prepare food to eat','WEEK_DAYS','MORNING','3','0','15'),(79,9,'Eat Breakfast','Consume your food.','WEEK_DAYS','MORNING','10','5','15'),(80,9,'Brush teeth','Tootpaste on brush on teet Move brush. Rinse','WEEK_DAYS','MORNING','2','1','5'),(81,9,'Shower','Wash hair and body','WEEK_DAYS','MORNING','7','6','15'),(82,9,'Bath','Wash hair and body','WEEK_DAYS','MORNING','8','7','15'),(83,9,'Dry hair','Towel or blow dry hair','WEEK_DAYS','MORNING','2','1','10'),(84,9,'Dry body','Towel dry body','WEEK_DAYS','MORNING','2','1','5'),(85,9,'Put on underwear','Put underwear on body','WEEK_DAYS','MORNING','1','0','5'),(86,9,'Puit on clothes','Put on the clothes peope will see','WEEK_DAYS','MORNING','2','0','5'),(91,9,'Wake up','Alarm turned off and out of bed','WEEK_DAYS','MORNING','1','0','5'),(92,9,'Make Bed','Straighten, tuck in sheets','WEEK_DAYS','MORNING','4','2','5'),(93,9,'Make Breakfast','Prepare food to eat','WEEK_DAYS','MORNING','3','0','15'),(94,9,'Eat Breakfast','Consume your food.','WEEK_DAYS','MORNING','10','5','15'),(95,9,'Brush teeth','Tootpaste on brush on teet Move brush. Rinse','WEEK_DAYS','MORNING','2','1','5'),(96,9,'Shower','Wash hair and body','WEEK_DAYS','MORNING','7','6','15'),(97,9,'Bath','Wash hair and body','WEEK_DAYS','MORNING','8','7','15'),(98,9,'Dry hair','Towel or blow dry hair','WEEK_DAYS','MORNING','2','1','10'),(99,9,'Dry body','Towel dry body','WEEK_DAYS','MORNING','2','1','5'),(100,9,'Put on underwear','Put underwear on body','WEEK_DAYS','MORNING','1','0','5'),(101,9,'Puit on clothes','Put on the clothes peope will see','WEEK_DAYS','MORNING','2','0','5');
/*!40000 ALTER TABLE `player_chore_plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `player_games`
--

DROP TABLE IF EXISTS `player_games`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `player_games` (
  `id` bigint(44) NOT NULL AUTO_INCREMENT,
  `game_id` bigint(44) NOT NULL,
  `player_id` bigint(44) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_PLAYER_GAMES_GAME1` (`game_id`),
  KEY `fk_PLAYER_GAMES_PLAYER1` (`player_id`),
  CONSTRAINT `fk_PLAYER_GAMES_GAME1` FOREIGN KEY (`game_id`) REFERENCES `game` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_PLAYER_GAMES_PLAYER1` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='This is a resolution table between PLAYER and GAME';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player_games`
--

LOCK TABLES `player_games` WRITE;
/*!40000 ALTER TABLE `player_games` DISABLE KEYS */;
/*!40000 ALTER TABLE `player_games` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'tsce'
--
/*!50003 DROP PROCEDURE IF EXISTS `add_new_player_to_account` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `add_new_player_to_account`(IN account_id BIGINT(44), IN player_first_name VARCHAR(44), OUT success BOOLEAN)
BEGIN
  DECLARE parentId BIGINT(44);
  SET success = FALSE;
  SELECT id FROM parent WHERE id = account_id INTO parentId;
  IF parentId = account_id THEN
	INSERT INTO PLAYER (parent_id, first_name) VALUES (account_id, player_first_name);
	IF ROW_COUNT() > 0 THEN
		SET success = TRUE;
  	END IF;
  END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `calculate_winners` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `calculate_winners`(IN p_date_observed DATE, OUT success BOOLEAN)
    DETERMINISTIC
BEGIN 


-- Scoring: max 3, min 0 points per chore.
-- Calculating team multiplier
--   for each friend_for_date 
--       create a list of the top ten total of PLAYER_CHORE_OBSERVED.earnings for that date.
-- 
-- loop test
DECLARE v_finished INT;
DECLARE v_king              BIGINT(44);

-- players must have the team mininum size to compete for winner 
DECLARE c_min_team_size    INT DEFAULT 5;
DECLARE v_team_size        INT;
DECLARE v_friends          INT;
DECLARE v_other_kings       INT;
DECLARE v_other_non_kings   INT;

DECLARE v_loop_counter      INT;

-- used to calculate the reward
DECLARE c_base_mulitplier   FLOAT DEFAULT 0.10;

-- v_team_multiplier depends on the size of the team
DECLARE v_team_multiplier  FLOAT;

-- v_friend is a player_id
DECLARE v_friend            BIGINT(44);

-- declare vars to populate temp table
DECLARE v_player_id          BIGINT(44);
DECLARE v_rank               LONG;

DECLARE v_points_total       INT;
DECLARE v_king_points_total  INT;


DECLARE v_need_update       BOOLEAN DEFAULT  FALSE;
DECLARE v_did_win_own_team  BOOLEAN DEFAULT  TRUE;
DECLARE v_number_in_team    INT;



DECLARE earnings_curs CURSOR FOR
    SELECT player_id, points_total, did_win_own_team
	FROM   player_team;

DECLARE kings_curs CURSOR FOR
	SELECT player_id, points_total
	FROM   player_team
    WHERE  did_win_own_team = TRUE
	GROUP BY player_id 
    ORDER BY points_total DESC;


-- other non-king members in same team
DECLARE rewards_curs CURSOR FOR
	SELECT player_id AS friend, points_total 
  FROM player_team
  WHERE player_id <> v_king AND king_id = v_king
  GROUP BY friend 
  ORDER BY points_total DESC
  LIMIT 9; -- we reward the top ten. we already have the winner)

DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_finished = 1;

-- You might want to look at this article for nested cursors
-- http://rpbouman.blogspot.com/2005/10/nesting-mysql-cursor-loops.html

-- drop the table we are about to create.  
DROP TEMPORARY TABLE IF EXISTS player_team;

-- get all the players total points earned
CREATE TEMPORARY TABLE player_team  
 	(player_id BIGINT(44), points_total INT, did_win_own_team BOOLEAN, earnings FLOAT, king_id BIGINT(44));


INSERT INTO player_team (player_id, points_total, did_win_own_team, earnings, king_id)
	SELECT  PLA.id AS player_id,  
            sum(PCO.earnings) AS points_total, 
            TRUE  AS did_win_own_team,   -- everyone starts off winning their own team           
            sum(PCO.earnings) AS earnings,  -- if you aren't in a winning team this is all u get.  
            NULL AS king_id

     FROM PLAYER_CHORE_OBSERVED AS PCO,
          PLAYER_CHORE_PLAN AS PCP,
          PLAYER AS PLA

    WHERE PCO.date_observed = p_date_observed  -- "2012-01-23"
    AND PCP.id = PCO.chore_plan_id
    AND PLA.id = PCP.player_id
    GROUP BY PLA.id
    ORDER BY points_total DESC;

-- set loop control to not finished
SET v_finished = FALSE;
SET success = FALSE;

OPEN earnings_curs;
-- looping through all the players.
LOOP1: LOOP
  -- descend through player point_total setting v_did_win_own_team
  FETCH earnings_curs INTO  v_player_id, v_points_total, v_did_win_own_team;
  -- check for v_finished
	IF v_finished THEN 
     SET v_finished = false;
     LEAVE LOOP1;
  END IF;
    
    SELECT COUNT(*) 
        FROM FRIENDS_FOR_DATE AS FFD
        WHERE FFD.begin_date <= p_date_observed AND
                (v_player_id = player_one OR v_player_id = player_two)
        INTO v_friends;
    
-- you must have at least 5 friends in your team to get rewards
-- set did_win_own_team to false if points_total is less than v_points_total
	IF v_friends < (c_min_team_size -1) THEN 
    UPDATE player_team 
        SET  did_win_own_team = FALSE       
        WHERE player_id = v_player_id;
  ELSE
    UPDATE player_team 
        SET  did_win_own_team = FALSE       
        WHERE (points_total <  v_points_total
                AND player_id IN (SELECT 
                    CASE v_player_id 
                        WHEN player_one  THEN player_two
                        WHEN player_two  THEN player_one 
                    END AS friend
                    FROM FRIENDS_FOR_DATE AS FFD
                    WHERE FFD.begin_date <= p_date_observed));
  END IF;
END LOOP LOOP1;
CLOSE earnings_curs;

-- Loop through players who won their own team and calculate the rewards
-- for the player and their team.
OPEN kings_curs;
LOOP2: LOOP
  FETCH kings_curs into v_king, v_king_points_total;
  -- check for v_finished
  IF v_finished THEN
          SET v_finished = false;
          CLOSE kings_curs;
          LEAVE LOOP2;
  END IF;
  -- want to compare rankings with each player

    -- get team size
    SELECT COUNT(*) 
    FROM FRIENDS_FOR_DATE
    WHERE begin_date <= p_date_observed AND
            v_king = player_one OR v_king = player_two
    INTO v_team_size;

    -- update winner first
     UPDATE player_team
     SET earnings = v_king_points_total*(1+ c_base_mulitplier)*(v_team_size+1), 
         king_id = v_king
     WHERE player_id = v_king;

    -- update other team members to have a king
    UPDATE player_team
    SET king_id = v_king
    WHERE player_id IN (SELECT CASE v_king 
                    WHEN player_one  THEN player_two
                    WHEN player_two  THEN player_one END
                FROM FRIENDS_FOR_DATE AS FFD
                WHERE FFD.begin_date <= p_date_observed)
    AND king_id IS NULL;
END LOOP LOOP2;


-- winner is rewarded now reward the team
-- reward the team
OPEN kings_curs;
LOOP3: LOOP
  FETCH kings_curs into v_king, v_king_points_total;
  -- check for v_finished
  IF v_finished THEN
      SET v_finished = false;
      CLOSE kings_curs;
      LEAVE LOOP3;
  END IF;
  
-- other kings in same team
  SELECT count(*)
  FROM player_team
  WHERE king_id = player_id AND player_id <> v_king
  AND player_id IN (SELECT CASE v_king 
                    WHEN player_one  THEN player_two
                    WHEN player_two  THEN player_one END
                FROM FRIENDS_FOR_DATE AS FFD
                WHERE FFD.begin_date <= p_date_observed)
  INTO v_other_kings;
  

-- other non-king members in same team
  SELECT count(*) 
  FROM player_team
  WHERE player_id <> v_king AND king_id = v_king
  INTO v_other_non_kings;
  
-- team size;
  SET v_team_size = v_other_kings + v_other_non_kings + 1;
  SET v_loop_counter = 0;
  OPEN rewards_curs;
  LOOP4: LOOP
      FETCH rewards_curs INTO v_friend, v_points_total;
      IF v_finished THEN
        SET v_finished = false;
        CLOSE rewards_curs;
        LEAVE LOOP4;        
      END IF;
      
      UPDATE player_team 
      SET earnings = v_points_total*( 1+ c_base_mulitplier)*(v_other_non_kings - v_loop_counter)
      WHERE player_id = v_friend;
      SET v_loop_counter = v_loop_counter + 1;
  END LOOP LOOP4;  
END LOOP LOOP3;

-- now update PLAYER from tmp table
UPDATE player, player_team 
SET player.earnings = player.earnings + player_team.earnings
WHERE player.id = player_team.player_id;


UPDATE player, player_team 
SET champion_count = champion_count + 1
WHERE player.id = player_team.player_id AND did_win_own_team = TRUE;

SELECT * FROM player_team;

-- drop the temp table
DROP TEMPORARY TABLE IF EXISTS player_team;

SET success = TRUE;

-- select sum(PCO.earnings) from 
-- PLAYER PL,   -- a specific player
-- FRIENDS_FOR_DATE FFD, -- thia gives you the player's friends
-- PLAYER_CHORE_PLAN PCP, -- this gives you their planned activities
-- PLAYER_CHORE_OBSERVED PCO -- this gives you their observed
-- where PL.id = FFD.player_one   -- OR player_two = FFD.player_one ?
-- and FFD.player_one = PCP.player_id  -- OR player_two = PCP.player_id ?
-- and PCP.id = PCO.chore_plan_id
-- group by PCP.player_id

-- this will return all the friend's scores 
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `create_account` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `create_account`(IN p_user_name VARCHAR(44), IN p_parent_first_name VARCHAR(40), IN p_parent_last_name VARCHAR(40), IN p_parent_email VARCHAR(50), IN p_player_first_name VARCHAR(40), OUT p_account_id BIGINT(44))
BEGIN
  INSERT INTO PARENT (first_name, last_name, email) VALUES (p_parent_first_name, p_parent_last_name, p_parent_email);
    
  SET p_account_id = LAST_INSERT_ID();

  INSERT INTO PLAYER (parent_id, first_name) VALUES (p_account_id, p_player_first_name);
  
  UPDATE USERS SET account_id = p_account_id WHERE user_name = p_user_name;
 
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `create_chore_plan_for_player` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `create_chore_plan_for_player`(IN p_player_id BIGINT(44), IN chore_id BIGINT(44), OUT chore_plan_id BIGINT(44))
BEGIN

-- copy all the MASTER_CHORE to PLAYER_CHORE_PLAN for the player.
  DECLARE _id BIGINT(44);
  SET chore_plan_id= -1;
  SELECT id FROM MASTER_CHORE WHERE id = chore_id INTO _id;
  IF _id = chore_id THEN
      SELECT id FROM PLAYER WHERE id = p_player_id INTO _id;
      IF _id = p_player_id THEN
          INSERT INTO 
            PLAYER_CHORE_PLAN(player_id, name, description, ideal_time, max_time, min_time) 
          SELECT 
            p_player_id, MC.name, MC.description, MC.ideal_time, MC.max_time, MC.min_time 
          FROM
            MASTER_CHORE MC
          WHERE ID = chore_id;
          SET chore_plan_id = LAST_INSERT_ID();
       END IF;  	
 	END IF;  	
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `create_friends_for_date` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `create_friends_for_date`(IN p_date DATE, IN p_player_one_id BIGINT(44), 

                                                               IN p_player_two_id BIGINT(44), OUT success BOOLEAN)
BEGIN
	DECLARE playerId BIGINT(44);
  	SET success = FALSE;
  	SELECT id FROM player WHERE id = p_player_one_id INTO playerId;
  	IF playerId = p_player_one_id THEN
  		SELECT id FROM player WHERE id = p_player_two_id INTO playerId;
  		IF playerId = p_player_two_id THEN
  			INSERT INTO FRIENDS_FOR_DATE(player_one, player_two, begin_date) VALUES (p_player_one_id, p_player_two_id, p_date);
			IF ROW_COUNT() > 0 THEN
				SET success = TRUE;
  			END IF;
  		END IF;
 	END IF;  	
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `delete_account` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `delete_account`(IN account_id BIGINT(44), OUT success BOOLEAN)
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
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_friends_for_player` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `get_friends_for_player`(IN p_player_id BIGINT(44), IN p_begin_date DATE)
BEGIN
  SELECT * FROM FRIENDS_FOR_DATE
                WHERE begin_date <= p_begin_date
                AND (p_player_id = player_one OR p_player_id = player_two);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_players_for_account` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `get_players_for_account`(account_id BIGINT(44))
BEGIN
  SELECT * FROM PLAYER WHERE parent_id=account_id;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `login` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `login`( IN p_user_name VARCHAR(44), IN p_user_id BIGINT(44), IN p_session_started TIMESTAMP, OUT success BOOLEAN)
BEGIN
  DECLARE v_id BIGINT(44);
  SET success = FALSE;
  SELECT id FROM USERS WHERE user_name = user_name AND user_id = p_user_id AND disabled = FALSE INTO v_id;
  IF v_id IS NOT NULL THEN
    UPDATE USERS SET 
    session_start = p_session_started
    WHERE id = v_id;
    SET success = TRUE;
  ELSE
    INSERT INTO USERS(user_name, user_id, session_start) VALUES (p_user_name, p_user_id, p_session_started);
  END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `observe_chore_for_player` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`admin`@`%`*/ /*!50003 PROCEDURE `observe_chore_for_player`(IN p_chore_plan_id BIGINT(44), 

                                                                IN p_observed_time FLOAT, 

                                                                IN p_date_observed DATE,

                                                                IN p_did_complete BOOLEAN)
BEGIN

  DECLARE v_calculate_earnings INT DEFAULT 0;



  if p_did_complete THEN SET v_calculate_earnings = v_calculate_earnings + 1; END IF;

-- don't have max_time yet  if p_observed_time <= max_time THEN SET v_calculate_earnings = v_calculate_earnings + 1; END IF;

-- don't have max_time yet  if min_time <= p_observed_time <= ideal_time THEN SET v_calculate_earnings = v_calculate_earnings + 1; END IF;


-- 1. & 2. copy all the PLAYER_CHORE_PLAN times to PLAYER_CHORE_OBSERVED.

  INSERT INTO 

    PLAYER_CHORE_OBSERVED(chore_plan_id, date_observed,  observed_time, max_time, min_time,  ideal_time, was_in_time, did_complete, earnings) 

  SELECT 

    p_chore_plan_id,

    p_date_observed,

    p_observed_time,  

    PCO.max_time, 

    PCO.min_time,

    PCO.ideal_time,

    (p_observed_time <= PCO.max_time),

    p_did_complete,

    v_calculate_earnings

  FROM

    PLAYER_CHORE_PLAN PCO

  WHERE

    id=p_chore_plan_id;


END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `start_chore` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `start_chore`(IN p_chore_plan_id BIGINT(44), IN p_time_started LONG,
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
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `stop_chore` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `stop_chore`(IN p_chore_observed_id BIGINT(44), 
            IN p_time_stoped LONG, IN p_did_complete BOOLEAN, OUT success BOOLEAN)
BEGIN

-- update the chore observed
  UPDATE 
    PLAYER_CHORE_OBSERVED 
  SET
    time_ended    = p_time_stoped,
    did_complete  = p_did_complete,
    was_in_time   = p_did_complete AND (min_time <= (p_time_stoped - time_started)) AND ((p_time_stoped - time_started) <= max_time),
   -- observed_time = p_time_stoped - time_started,
    earnings      = 1 +  CAST((p_did_complete AND (min_time <= (p_time_stoped - time_started)) AND ((p_time_stoped - time_started) <= max_time)) AS SIGNED INTEGER) + 
                    CAST((p_did_complete AND (min_time <= (p_time_stoped - time_started)) AND ((p_time_stoped - time_started) <= ideal_time)) AS SIGNED INTEGER)
   WHERE
    id = p_chore_observed_id;
    
    SET success = TRUE;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `verify_user` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `verify_user`( IN p_user_name VARCHAR(44), OUT success BOOLEAN)
BEGIN
  DECLARE session_life INT DEFAULT 300; -- SECOND
  DECLARE v_session_start TIMESTAMP;
  SET success = TRUE;
  SELECT session_start FROM USERS WHERE user_name = p_user_name AND disabled = FALSE INTO v_session_start;
  IF v_session_start IS NOT NULL THEN
    IF TIMESTAMPDIFF(SECOND, v_session_start, NOW()) > session_life THEN
        SET success = FALSE;
    ELSE
         SET success = TRUE;
  	END IF;
  ELSE
    SET success = FALSE;
  END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-06-26 17:01:43
