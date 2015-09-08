CREATE DATABASE  IF NOT EXISTS `RestaurantReservation_db` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `RestaurantReservation_db`;
-- MySQL dump 10.13  Distrib 5.6.24, for osx10.8 (x86_64)
--
-- Host: 127.0.0.1    Database: RestaurantReservation_db
-- ------------------------------------------------------
-- Server version	5.6.26

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
-- Table structure for table `OwnersDetails`
--

DROP TABLE IF EXISTS `OwnersDetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OwnersDetails` (
  `RestaurantId` int(11) NOT NULL,
  `FirstName` varchar(45) NOT NULL,
  `LastName` varchar(45) NOT NULL,
  `Email` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  `ContactNumber` varchar(45) NOT NULL,
  PRIMARY KEY (`Email`),
  KEY `RestaurantId_idx` (`RestaurantId`),
  CONSTRAINT `RestaurantId_fk_login` FOREIGN KEY (`RestaurantId`) REFERENCES `RestaurantProfile` (`RestaurantId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OwnersDetails`
--

LOCK TABLES `OwnersDetails` WRITE;
/*!40000 ALTER TABLE `OwnersDetails` DISABLE KEYS */;
INSERT INTO `OwnersDetails` VALUES (1002,'Jordan','McAlister','jmcalister@gmail.com','jida','4056783451');
/*!40000 ALTER TABLE `OwnersDetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ReservationDetails`
--

DROP TABLE IF EXISTS `ReservationDetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ReservationDetails` (
  `ConfirmationNumber` int(11) NOT NULL AUTO_INCREMENT,
  `FIrstName` varchar(45) NOT NULL,
  `LastName` varchar(45) NOT NULL,
  `ContactNumber` varchar(30) NOT NULL,
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  `PartySize` int(11) NOT NULL,
  `Status` varchar(30) NOT NULL,
  `Visited` char(30) DEFAULT NULL,
  `AssignedTableNumber` int(11) DEFAULT NULL,
  `RestaurantId` int(11) NOT NULL,
  PRIMARY KEY (`ConfirmationNumber`,`RestaurantId`),
  KEY `AssignedTableNumber_idx` (`AssignedTableNumber`),
  KEY `RestaurantId_idx` (`RestaurantId`),
  CONSTRAINT `restaurantId_fk` FOREIGN KEY (`RestaurantId`) REFERENCES `RestaurantProfile` (`RestaurantId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ReservationDetails`
--

LOCK TABLES `ReservationDetails` WRITE;
/*!40000 ALTER TABLE `ReservationDetails` DISABLE KEYS */;
INSERT INTO `ReservationDetails` VALUES (1,'Zenia','Arora','4057805416','2015-08-30','20:00:00',4,'Confirmed','Yes',10,1002),(2,'Chandler','Bing','4024535416','2015-08-30','21:00:00',3,'Confirmed','Yes',5,1001),(3,'Monica','Gellar','7653452222','2015-08-16','21:00:00',8,'Cancel','Yes',4,1003),(5,'Zenia','Arora','4445556666','2015-09-06','19:00:00',5,'Confirmed','N',9,1001),(6,'Rachell','Green','405789999','2015-09-07','19:00:00',5,'Waiting','No',0,1002),(7,'Joe','Bond','4567834561','2015-09-06','18:45:00',2,'Cancelled','No',6,1002),(8,'Pistol','Pete','4057807800','2015-10-09','21:00:00',5,'Waiting','No',0,1002);
/*!40000 ALTER TABLE `ReservationDetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RestaurantProfile`
--

DROP TABLE IF EXISTS `RestaurantProfile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RestaurantProfile` (
  `RestaurantId` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(30) DEFAULT NULL,
  `ContactNumber` varchar(30) NOT NULL,
  `Email` varchar(45) DEFAULT NULL,
  `Address` varchar(50) NOT NULL,
  `OperationalDays` varchar(50) NOT NULL,
  `OpeningHours` time NOT NULL,
  `Closinghours` time NOT NULL,
  `AutoAssign` char(20) NOT NULL,
  PRIMARY KEY (`RestaurantId`)
) ENGINE=InnoDB AUTO_INCREMENT=1004 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RestaurantProfile`
--

LOCK TABLES `RestaurantProfile` WRITE;
/*!40000 ALTER TABLE `RestaurantProfile` DISABLE KEYS */;
INSERT INTO `RestaurantProfile` VALUES (1001,'Buffalo wild wings','8765437890',' Buffalowings@abc.com',' 456 Main St','Sun, Mon, Tues, Wed, Thurs, Fri, Sat','11:00:00','01:00:00','Y'),(1002,'Palominos','40567555544',' Palominos@abc.com',' 123 Scott St','Sun, Mon, Tues, Wed, Thurs, Fri, Sat','11:00:00','21:00:00','Y'),(1003,'Texas House','4056783214',' texashouse@abc.com',' N.Hampton St','Sun, Mon, Tues, Wed, Thurs, Fri, Sat','11:30:00','22:00:00','Y');
/*!40000 ALTER TABLE `RestaurantProfile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SeatingArrangements`
--

DROP TABLE IF EXISTS `SeatingArrangements`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SeatingArrangements` (
  `RestaurantId` int(11) NOT NULL,
  `TableNumber` int(11) NOT NULL,
  `SeatsAvailable` int(11) NOT NULL,
  PRIMARY KEY (`TableNumber`,`RestaurantId`),
  KEY `RestaurantId_idx` (`RestaurantId`),
  CONSTRAINT `RestaurantId` FOREIGN KEY (`RestaurantId`) REFERENCES `RestaurantProfile` (`RestaurantId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SeatingArrangements`
--

LOCK TABLES `SeatingArrangements` WRITE;
/*!40000 ALTER TABLE `SeatingArrangements` DISABLE KEYS */;
INSERT INTO `SeatingArrangements` VALUES (1003,1,6),(1001,2,4),(1002,3,8),(1003,3,6),(1002,4,4),(1001,6,2),(1002,6,3),(1002,7,5),(1003,8,4),(1001,9,5),(1002,10,4),(1002,13,8);
/*!40000 ALTER TABLE `SeatingArrangements` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-09-08 13:56:10
