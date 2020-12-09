-- MySQL dump 10.13  Distrib 8.0.22, for Linux (x86_64)
--
-- Host: localhost    Database: restaurant
-- ------------------------------------------------------
-- Server version	8.0.22-0ubuntu0.20.04.3

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `client` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `courriel` varchar(255) DEFAULT NULL,
  `date_de_naissance` date NOT NULL,
  `nom` varchar(255) NOT NULL,
  `prenom` varchar(255) NOT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_jj2yd1ng9w67p0gf583tfuo2u` (`courriel`),
  UNIQUE KEY `UK_1rxa00yetw8yyqg6eus42k6ig` (`telephone`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES (1,NULL,'1998-09-19','Jelidi','Mohammed','28249424'),(2,NULL,'1998-10-03','Snoussi','Hamza','28248616'),(3,NULL,'1997-03-03','Ben Salah','Brahim','98248616');
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `met`
--

DROP TABLE IF EXISTS `met`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `met` (
  `nom` varchar(255) NOT NULL,
  `prix` float NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`nom`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `met`
--

LOCK TABLES `met` WRITE;
/*!40000 ALTER TABLE `met` DISABLE KEYS */;
INSERT INTO `met` VALUES ('Crepe',3.5,'Dessert'),('Omelette',2.5,'Plat'),('Pizza',5,'Plat'),('Soupe',4,'Entree');
/*!40000 ALTER TABLE `met` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordered_with`
--

DROP TABLE IF EXISTS `ordered_with`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ordered_with` (
  `met` varchar(255) NOT NULL,
  `ticket` int NOT NULL,
  KEY `FKgw6fvqmiqbkllcidvq69unbxx` (`ticket`),
  KEY `FKf655kl1cmrhehf6kfmp3m47jt` (`met`),
  CONSTRAINT `FKf655kl1cmrhehf6kfmp3m47jt` FOREIGN KEY (`met`) REFERENCES `met` (`nom`),
  CONSTRAINT `FKgw6fvqmiqbkllcidvq69unbxx` FOREIGN KEY (`ticket`) REFERENCES `ticket` (`numero`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordered_with`
--

LOCK TABLES `ordered_with` WRITE;
/*!40000 ALTER TABLE `ordered_with` DISABLE KEYS */;
INSERT INTO `ordered_with` VALUES ('Pizza',97),('Pizza',93),('Pizza',87),('Pizza',92),('Pizza',115),('Pizza',91),('Pizza',98),('Pizza',99),('Pizza',103),('Pizza',100),('Crepe',92),('Crepe',79),('Crepe',78),('Crepe',87),('Crepe',93),('Crepe',95),('Crepe',95),('Crepe',115),('Crepe',91),('Crepe',90),('Crepe',90),('Crepe',90),('Crepe',90),('Crepe',103),('Crepe',103),('Crepe',101),('Crepe',104),('Crepe',105),('Crepe',106),('Crepe',107),('Crepe',108),('Crepe',76),('Omelette',116),('Omelette',101),('Omelette',91),('Omelette',90),('Omelette',103),('Omelette',114),('Omelette',77),('Omelette',109),('Omelette',94),('Soupe',116),('Soupe',114),('Soupe',90),('Soupe',91),('Soupe',101),('Soupe',77),('Soupe',109),('Soupe',94);
/*!40000 ALTER TABLE `ordered_with` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurant_table`
--

DROP TABLE IF EXISTS `restaurant_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `restaurant_table` (
  `numero` int NOT NULL AUTO_INCREMENT,
  `nb_couvert` int DEFAULT NULL,
  `supplement` float NOT NULL,
  `type` varchar(255) NOT NULL,
  PRIMARY KEY (`numero`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurant_table`
--

LOCK TABLES `restaurant_table` WRITE;
/*!40000 ALTER TABLE `restaurant_table` DISABLE KEYS */;
INSERT INTO `restaurant_table` VALUES (2,6,20,'grande terrasse'),(3,6,20,'grande terrasse'),(4,6,20,'grande terrasse'),(5,3,10,'petite terrasse');
/*!40000 ALTER TABLE `restaurant_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ticket` (
  `numero` int NOT NULL AUTO_INCREMENT,
  `addition` float NOT NULL,
  `date` date NOT NULL,
  `nb_couvert` int DEFAULT NULL,
  `client_id` bigint NOT NULL,
  `table_numero` int NOT NULL,
  PRIMARY KEY (`numero`),
  KEY `FKj2rjr6m31hp7m00tm1hdxckov` (`client_id`),
  KEY `FKsfn4jpwhjp1kian7nnqkquuik` (`table_numero`),
  CONSTRAINT `FKj2rjr6m31hp7m00tm1hdxckov` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`),
  CONSTRAINT `FKsfn4jpwhjp1kian7nnqkquuik` FOREIGN KEY (`table_numero`) REFERENCES `restaurant_table` (`numero`)
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
INSERT INTO `ticket` VALUES (48,9,'2020-12-09',1,1,5),(49,9,'2020-12-09',1,1,5),(50,19,'2020-12-19',9,2,2),(51,9,'2020-12-09',1,1,5),(52,9,'2020-12-09',1,1,5),(53,9,'2020-12-09',1,1,5),(54,9,'2020-12-09',1,1,5),(55,9,'2020-12-09',1,1,5),(56,9,'2020-12-09',1,1,5),(57,9,'2020-12-09',1,1,5),(58,9,'2020-12-09',1,1,5),(59,9,'2020-12-09',1,1,5),(60,9,'2020-12-09',1,1,5),(61,9,'2020-12-09',1,1,5),(62,9,'2020-12-09',1,1,5),(63,9,'2020-12-09',1,1,5),(64,9,'2020-12-09',1,1,5),(65,9,'2020-12-09',1,1,5),(66,9,'2020-12-09',1,1,5),(67,9,'2020-12-09',1,1,5),(68,9,'2020-12-09',1,1,5),(69,9,'2020-12-09',1,1,5),(70,9,'2020-12-09',1,1,5),(71,9,'2020-12-09',1,1,5),(72,9,'2020-12-09',1,1,5),(73,9,'2020-12-09',1,1,5),(74,9,'2020-12-09',1,1,5),(75,19,'2020-12-19',9,2,5),(76,19,'2020-12-19',9,2,5),(77,16,'2020-12-20',10,2,4),(78,9,'2020-12-09',1,1,5),(79,9,'2020-12-09',1,1,5),(80,9,'2020-12-09',1,1,5),(81,9,'2020-12-09',1,1,5),(82,9,'2020-12-09',1,1,5),(83,9,'2020-12-09',1,1,5),(84,9,'2020-12-09',1,1,5),(85,9,'2020-12-09',1,1,5),(86,9,'2020-12-09',1,1,5),(87,9,'2020-12-09',1,1,5),(88,9,'2020-12-09',1,1,5),(89,9,'2020-12-09',1,1,5),(90,19,'2020-12-19',9,2,5),(91,19,'2020-12-19',9,2,5),(92,9,'2020-12-09',1,1,5),(93,9,'2020-12-09',1,1,5),(94,9,'2020-12-09',1,3,4),(95,9,'2020-12-09',1,1,5),(96,9,'2020-12-09',1,1,5),(97,9,'2020-12-09',1,1,5),(98,9,'2020-12-09',1,2,5),(99,9,'2020-12-09',1,2,5),(100,9,'2020-12-09',1,3,2),(101,19,'2020-12-19',9,2,5),(102,19,'2020-12-19',9,2,5),(103,19,'2020-12-19',9,2,5),(104,19,'2020-12-19',9,2,5),(105,19,'2020-12-19',9,2,5),(106,19,'2020-12-19',9,2,5),(107,19,'2020-12-19',9,2,5),(108,19,'2020-12-19',9,2,5),(109,16,'2020-12-20',10,3,3),(110,19,'2020-12-19',9,2,5),(111,19,'2020-12-19',9,2,5),(112,19,'2020-12-19',9,2,5),(113,19,'2020-12-19',9,2,5),(114,19,'2020-12-19',9,2,5),(115,16,'2020-12-20',10,2,4),(116,16,'2020-12-20',10,2,4);
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('foulen','fouleni'),('medjelidi','123456');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-12-09 11:04:33
