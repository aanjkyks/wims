drop database if exists database_test;
create database database_test;
use database_test;

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

# --Create table Users

DROP TABLE IF EXISTS `Users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Users` (
	`id` int(10) unsigned NOT NULL AUTO_INCREMENT,
	`name` varchar(45) NOT NULL,
	`password` varchar(100) NOT NULL,
	PRIMARY KEY (`id`)
	) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

# --Add data to table Users
LOCK TABLES `Users` WRITE;
/*!40000 ALTER TABLE `Users` DISABLE KEYS */;
INSERT INTO `Users` VALUES (1,'TestUser1', '123'),(2,'TestUser2','123'),(3,'TestUser3','123');
/*!40000 ALTER TABLE `Users` ENABLE KEYS */;
UNLOCK TABLES;


# --Create table Tags
DROP TABLE IF EXISTS `Tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Tags` (
 	`id` int(10) unsigned NOT NULL AUTO_INCREMENT,
 	`name` varchar(100) NOT NULL,
 	`userID` int(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

# --add data to table Tags
LOCK TABLES `Tags` WRITE;
/*!40000 ALTER TABLE `Tags` DISABLE KEYS */;
#INSERT INTO `Tags` VALUES (1,'test'),(2,'vacation'),(3,'job'),(4,'home');
/*!40000 ALTER TABLE `Tags` ENABLE KEYS */;
UNLOCK TABLES;


# --Create table Notes
DROP TABLE IF EXISTS `Notes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Notes` (
	`id` int(10) unsigned NOT NULL AUTO_INCREMENT,
	`name` varchar(45) NOT NULL,
	 `noteDate` DATE NOT NULL,
 	`userID` int(10) unsigned NOT NULL,
 	`tagID` int(10) unsigned NOT NULL,
 	`text` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
    KEY `FK_Users` (`userID`),
    KEY `FK_Tags` (`tagID`),
  CONSTRAINT `FK_Users` FOREIGN KEY (`userID`) REFERENCES `Users` (`id`),
  CONSTRAINT `FK_Tags` FOREIGN KEY (`tagID`) REFERENCES `Tags` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

# --Add data to table Notes
LOCK TABLES `Notes` WRITE;
/*!40000 ALTER TABLE `Notes` DISABLE KEYS */;
#INSERT INTO `Notes` VALUES (1,'first note','2008-7-04',1,1),(2,'second note','2010-2-01',2,2),(3,'third note','2012-8-05',3,3);
/*!40000 ALTER TABLE `Notes` ENABLE KEYS */;
UNLOCK TABLES;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
