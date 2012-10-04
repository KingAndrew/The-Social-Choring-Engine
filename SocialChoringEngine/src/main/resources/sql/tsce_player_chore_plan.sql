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
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8 COMMENT='PLAYER_CHORE_PLAN is a resolution table for the many to many';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player_chore_plan`
--

LOCK TABLES `player_chore_plan` WRITE;
/*!40000 ALTER TABLE `player_chore_plan` DISABLE KEYS */;
INSERT INTO `player_chore_plan` VALUES (1,1,'Wake up','Alarm turned off and out of bed','WEEK_DAYS','MORNING','1','0','5'),(2,1,'Make Bed','Straighten, tuck in sheets','WEEK_DAYS','MORNING','4','2','5'),(3,1,'Make Breakfast','Prepare food to eat','WEEK_DAYS','MORNING','3','0','15'),(4,1,'Eat Breakfast','Consume your food.','WEEK_DAYS','MORNING','10','5','15'),(5,1,'Brush teeth','Tootpaste on brush on teet Move brush. Rinse','WEEK_DAYS','MORNING','2','1','5'),(6,1,'Shower','Wash hair and body','WEEK_DAYS','MORNING','7','6','15'),(7,1,'Bath','Wash hair and body','WEEK_DAYS','MORNING','8','7','15'),(8,1,'Dry hair','Towel or blow dry hair','WEEK_DAYS','MORNING','2','1','10'),(9,1,'Dry body','Towel dry body','WEEK_DAYS','MORNING','2','1','5'),(10,1,'Put on underwear','Put underwear on body','WEEK_DAYS','MORNING','1','0','5'),(11,1,'Puit on clothes','Put on the clothes peope will see','WEEK_DAYS','MORNING','2','0','5'),(16,4,'Wake up','Alarm turned off and out of bed','WEEK_DAYS','MORNING','1','0','5'),(17,4,'Make Bed','Straighten, tuck in sheets','WEEK_DAYS','MORNING','4','2','5'),(18,4,'Make Breakfast','Prepare food to eat','WEEK_DAYS','MORNING','3','0','15'),(19,4,'Eat Breakfast','Consume your food.','WEEK_DAYS','MORNING','10','5','15'),(20,4,'Brush teeth','Tootpaste on brush on teet Move brush. Rinse','WEEK_DAYS','MORNING','2','1','5'),(21,4,'Shower','Wash hair and body','WEEK_DAYS','MORNING','7','6','15'),(22,4,'Bath','Wash hair and body','WEEK_DAYS','MORNING','8','7','15'),(23,4,'Dry hair','Towel or blow dry hair','WEEK_DAYS','MORNING','2','1','10'),(24,4,'Dry body','Towel dry body','WEEK_DAYS','MORNING','2','1','5'),(25,4,'Put on underwear','Put underwear on body','WEEK_DAYS','MORNING','1','0','5'),(26,4,'Puit on clothes','Put on the clothes peope will see','WEEK_DAYS','MORNING','2','0','5'),(31,6,'Wake up','Alarm turned off and out of bed','WEEK_DAYS','MORNING','1','0','5'),(32,6,'Make Bed','Straighten, tuck in sheets','WEEK_DAYS','MORNING','4','2','5'),(33,6,'Make Breakfast','Prepare food to eat','WEEK_DAYS','MORNING','3','0','15'),(34,6,'Eat Breakfast','Consume your food.','WEEK_DAYS','MORNING','10','5','15'),(35,6,'Brush teeth','Tootpaste on brush on teet Move brush. Rinse','WEEK_DAYS','MORNING','2','1','5'),(36,6,'Shower','Wash hair and body','WEEK_DAYS','MORNING','7','6','15'),(37,6,'Bath','Wash hair and body','WEEK_DAYS','MORNING','8','7','15'),(38,6,'Dry hair','Towel or blow dry hair','WEEK_DAYS','MORNING','2','1','10'),(39,6,'Dry body','Towel dry body','WEEK_DAYS','MORNING','2','1','5'),(40,6,'Put on underwear','Put underwear on body','WEEK_DAYS','MORNING','1','0','5'),(41,6,'Puit on clothes','Put on the clothes peope will see','WEEK_DAYS','MORNING','2','0','5'),(46,7,'Wake up','Alarm turned off and out of bed','WEEK_DAYS','MORNING','1','0','5'),(47,7,'Make Bed','Straighten, tuck in sheets','WEEK_DAYS','MORNING','4','2','5'),(48,7,'Make Breakfast','Prepare food to eat','WEEK_DAYS','MORNING','3','0','15'),(49,7,'Eat Breakfast','Consume your food.','WEEK_DAYS','MORNING','10','5','15'),(50,7,'Brush teeth','Tootpaste on brush on teet Move brush. Rinse','WEEK_DAYS','MORNING','2','1','5'),(51,7,'Shower','Wash hair and body','WEEK_DAYS','MORNING','7','6','15'),(52,7,'Bath','Wash hair and body','WEEK_DAYS','MORNING','8','7','15'),(53,7,'Dry hair','Towel or blow dry hair','WEEK_DAYS','MORNING','2','1','10'),(54,7,'Dry body','Towel dry body','WEEK_DAYS','MORNING','2','1','5'),(55,7,'Put on underwear','Put underwear on body','WEEK_DAYS','MORNING','1','0','5'),(56,7,'Puit on clothes','Put on the clothes peope will see','WEEK_DAYS','MORNING','2','0','5'),(61,8,'Wake up','Alarm turned off and out of bed','WEEK_DAYS','MORNING','1','0','5'),(62,8,'Make Bed','Straighten, tuck in sheets','WEEK_DAYS','MORNING','4','2','5'),(63,8,'Make Breakfast','Prepare food to eat','WEEK_DAYS','MORNING','3','0','15'),(64,8,'Eat Breakfast','Consume your food.','WEEK_DAYS','MORNING','10','5','15'),(65,8,'Brush teeth','Tootpaste on brush on teet Move brush. Rinse','WEEK_DAYS','MORNING','2','1','5'),(66,8,'Shower','Wash hair and body','WEEK_DAYS','MORNING','7','6','15'),(67,8,'Bath','Wash hair and body','WEEK_DAYS','MORNING','8','7','15'),(68,8,'Dry hair','Towel or blow dry hair','WEEK_DAYS','MORNING','2','1','10'),(69,8,'Dry body','Towel dry body','WEEK_DAYS','MORNING','2','1','5'),(70,8,'Put on underwear','Put underwear on body','WEEK_DAYS','MORNING','1','0','5'),(71,8,'Puit on clothes','Put on the clothes peope will see','WEEK_DAYS','MORNING','2','0','5'),(76,9,'Wake up','Alarm turned off and out of bed','WEEK_DAYS','MORNING','1','0','5'),(77,9,'Make Bed','Straighten, tuck in sheets','WEEK_DAYS','MORNING','4','2','5'),(78,9,'Make Breakfast','Prepare food to eat','WEEK_DAYS','MORNING','3','0','15'),(79,9,'Eat Breakfast','Consume your food.','WEEK_DAYS','MORNING','10','5','15'),(80,9,'Brush teeth','Tootpaste on brush on teet Move brush. Rinse','WEEK_DAYS','MORNING','2','1','5'),(81,9,'Shower','Wash hair and body','WEEK_DAYS','MORNING','7','6','15'),(82,9,'Bath','Wash hair and body','WEEK_DAYS','MORNING','8','7','15'),(83,9,'Dry hair','Towel or blow dry hair','WEEK_DAYS','MORNING','2','1','10'),(84,9,'Dry body','Towel dry body','WEEK_DAYS','MORNING','2','1','5'),(85,9,'Put on underwear','Put underwear on body','WEEK_DAYS','MORNING','1','0','5'),(86,9,'Puit on clothes','Put on the clothes peope will see','WEEK_DAYS','MORNING','2','0','5'),(91,9,'Wake up','Alarm turned off and out of bed','WEEK_DAYS','MORNING','1','0','5'),(92,9,'Make Bed','Straighten, tuck in sheets','WEEK_DAYS','MORNING','4','2','5'),(93,9,'Make Breakfast','Prepare food to eat','WEEK_DAYS','MORNING','3','0','15'),(94,9,'Eat Breakfast','Consume your food.','WEEK_DAYS','MORNING','10','5','15'),(95,9,'Brush teeth','Tootpaste on brush on teet Move brush. Rinse','WEEK_DAYS','MORNING','2','1','5'),(96,9,'Shower','Wash hair and body','WEEK_DAYS','MORNING','7','6','15'),(97,9,'Bath','Wash hair and body','WEEK_DAYS','MORNING','8','7','15'),(98,9,'Dry hair','Towel or blow dry hair','WEEK_DAYS','MORNING','2','1','10'),(99,9,'Dry body','Towel dry body','WEEK_DAYS','MORNING','2','1','5'),(100,9,'Put on underwear','Put underwear on body','WEEK_DAYS','MORNING','1','0','5'),(101,9,'Puit on clothes','Put on the clothes peope will see','WEEK_DAYS','MORNING','2','0','5');
/*!40000 ALTER TABLE `player_chore_plan` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-06-15 17:37:09
