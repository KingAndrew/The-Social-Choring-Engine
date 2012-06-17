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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-06-15 17:37:08
