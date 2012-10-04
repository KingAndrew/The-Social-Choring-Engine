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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='Each night the winners need to be calculated.  To do this th';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friends_for_date`
--

LOCK TABLES `friends_for_date` WRITE;
/*!40000 ALTER TABLE `friends_for_date` DISABLE KEYS */;
INSERT INTO `friends_for_date` VALUES (1,'2012-01-21',1,8),(2,'2012-01-23',1,4),(3,'2012-01-23',1,6),(4,'2012-01-23',6,8),(5,'2012-01-23',6,9),(6,'2012-01-23',9,8);
/*!40000 ALTER TABLE `friends_for_date` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-06-15 17:37:00
