-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: zcompany_db
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account_status` varchar(255) NOT NULL,
  `date_of_birth` date NOT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `gender` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `marital_status` varchar(255) NOT NULL,
  `nationality` int NOT NULL,
  `nid_or_passport` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `profile_picture_url` varchar(255) DEFAULT NULL,
  `document_image_url` varchar(255) DEFAULT NULL,
  `password_reset_token` varchar(255) DEFAULT NULL,
  `password_reset_token_expiry` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'UNVERIFIED','2002-01-01','mucyoernest@gmail.com','John','MALE','Doe','SINGLE',0,NULL,'$2a$10$obiTUj5ixj7rSWmnkKDda.o4GzdOo1qsE5V8aUpSs.A/oE0F8Afaq',NULL,NULL,'ce8539d9-98de-45a3-ba72-61732aef08d5','2023-05-09 20:14:22'),(2,'UNVERIFIED','2003-05-08','string@exa.com','string','FEMALE','string','DIVORCED',190,'string','StringP1@','string',NULL,NULL,NULL),(4,'VERIFIED','2003-05-09','stg@ea.com','string','FEMALE','string','DIVORCED',30,'PAC200000011523','$2a$10$L2zM3mX.ugEEgKa0uNlhyuC3EpyBLzvzfdG3DQ1nO5VArvXfofL92','http://res.cloudinary.com/dks5kdawg/image/upload/v1683629230/gxmkhttpklnhbqkgstpw.png','http://res.cloudinary.com/dks5kdawg/image/upload/v1683635690/htavucau7bvlbniacrmi.jpg',NULL,NULL),(7,'UNVERIFIED','2003-05-09','string@we.fg','string','FEMALE','string','DIVORCED',0,NULL,'$2a$10$1a2obF6bX6HmqOhKH.yy9.DHHR5vfkpxDQWC3V5bufL/UansaZ6sK',NULL,'string','string','2023-05-09 18:48:22');
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

-- Dump completed on 2023-05-09 21:23:12
