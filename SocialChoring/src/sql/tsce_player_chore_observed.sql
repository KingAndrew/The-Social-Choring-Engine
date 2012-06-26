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
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='This is how a PLAYER did on a certain day.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player_chore_observed`
--

LOCK TABLES `player_chore_observed` WRITE;
/*!40000 ALTER TABLE `player_chore_observed` DISABLE KEYS */;
INSERT INTO `player_chore_observed` VALUES (1,1,'0000-00-00','5','1','5','0','1',1,1,3),(2,20,'2012-01-23','6','5','5','1','2',1,1,3),(3,2,'2012-01-23','15','7','5','2','4',1,1,2),(4,2,'2012-01-23','6','7','5','2','4',0,1,1),(5,2,'2012-01-23','9','7','5','2','4',1,1,3),(6,31,'2012-01-23','12','8','5','0','1',1,1,2),(7,32,'2012-01-23','9','8','5','2','4',0,1,1),(8,33,'2012-01-23','16','8','15','0','3',1,1,2),(9,34,'2012-01-23','24','8','15','5','10',0,1,1),(10,35,'2012-01-23','10','8','5','1','2',1,1,3),(11,36,'2012-01-23','12','8','15','6','7',0,1,1),(12,37,'2012-01-23','12','8','15','7','8',0,0,0),(13,38,'2012-01-23','19','8','10','1','2',0,1,1),(14,39,'2012-01-23','15','8','5','1','2',0,1,1),(15,40,'2012-01-23','12','8','5','0','1',1,1,2),(16,41,'2012-01-23','10','8','5','0','2',1,1,3),(17,41,'2012-01-23','10','8','5','0','2',1,1,3);
/*!40000 ALTER TABLE `player_chore_observed` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-06-15 17:36:59
