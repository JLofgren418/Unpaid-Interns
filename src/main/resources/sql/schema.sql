-- MySQL dump 10.13  Distrib 8.0.33, for Linux (x86_64)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	8.0.33-0ubuntu0.22.04.1

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
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course` (
  `id` bigint NOT NULL,
  `attributes` varchar(255) NOT NULL,
  `campus` varchar(255) NOT NULL,
  `catalog_number` varchar(255) NOT NULL,
  `classid` varchar(255) NOT NULL,
  `comments` varchar(255) NOT NULL,
  `consent` varchar(255) NOT NULL,
  `course_code` varchar(255) NOT NULL,
  `course_title` varchar(255) NOT NULL,
  `credits` varchar(255) NOT NULL,
  `cross_listings` varchar(255) NOT NULL,
  `dept_code` varchar(255) NOT NULL,
  `enrollment` varchar(255) NOT NULL,
  `expected_enrollment` double NOT NULL,
  `graded` varchar(255) NOT NULL,
  `link` varchar(255) NOT NULL,
  `max_enrollment` varchar(255) NOT NULL,
  `meeting_days` varchar(255) NOT NULL,
  `meeting_time` varchar(255) NOT NULL,
  `method` varchar(255) NOT NULL,
  `min_credits` varchar(255) NOT NULL,
  `notes` varchar(255) NOT NULL,
  `partner` varchar(255) NOT NULL,
  `printable` varchar(255) NOT NULL,
  `prior_enrollment` varchar(255) NOT NULL,
  `projected_enrollment` varchar(255) NOT NULL,
  `room_attributes` varchar(255) NOT NULL,
  `room_cap` varchar(255) NOT NULL,
  `section_number` varchar(255) NOT NULL,
  `section_type` varchar(255) NOT NULL,
  `session` varchar(255) NOT NULL,
  `sisid` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `subject_code` varchar(255) NOT NULL,
  `term` varchar(255) NOT NULL,
  `term_code` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `wait_cap` varchar(255) NOT NULL,
  `instructor_id` bigint NOT NULL,
  `room_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqk2yq2yk124dhlsilomy36qr9` (`instructor_id`),
  KEY `FKj29qwwp1brsjqsn8rle1k3seu` (`room_id`),
  CONSTRAINT `FKj29qwwp1brsjqsn8rle1k3seu` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`),
  CONSTRAINT `FKqk2yq2yk124dhlsilomy36qr9` FOREIGN KEY (`instructor_id`) REFERENCES `instructor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_change`
--

DROP TABLE IF EXISTS `course_change`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course_change` (
  `id` bigint NOT NULL,
  `change_course` varchar(1024) DEFAULT NULL,
  `date_time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_change`
--

LOCK TABLES `course_change` WRITE;
/*!40000 ALTER TABLE `course_change` DISABLE KEYS */;
/*!40000 ALTER TABLE `course_change` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `idgenerator`
--

DROP TABLE IF EXISTS `idgenerator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `idgenerator` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `idgenerator`
--

LOCK TABLES `idgenerator` WRITE;
/*!40000 ALTER TABLE `idgenerator` DISABLE KEYS */;
INSERT INTO `idgenerator` VALUES (1000);
/*!40000 ALTER TABLE `idgenerator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `instructor`
--

DROP TABLE IF EXISTS `instructor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `instructor` (
  `id` bigint NOT NULL,
  `availability` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `instructor`
--

LOCK TABLES `instructor` WRITE;
/*!40000 ALTER TABLE `instructor` DISABLE KEYS */;
/*!40000 ALTER TABLE `instructor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room` (
  `id` bigint NOT NULL,
  `building` varchar(255) NOT NULL,
  `capacity` int NOT NULL,
  `number` int NOT NULL,
  `room_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (-1,'Does not Meet',0,-1,'Totally Online'),(0,'None',0,0,'None'),(1,'Peter Kiewit Institute',12,108,'Lecture'),(2,'Peter Kiewit Institute',16,150,'Lecture'),(3,'Peter Kiewit Institute',42,153,'Lecture'),(4,'Peter Kiewit Institute',40,155,'Lecture'),(5,'Peter Kiewit Institute',24,157,'Lecture'),(6,'Peter Kiewit Institute',42,160,'Lecture'),(7,'Peter Kiewit Institute',30,161,'Lecture'),(8,'Peter Kiewit Institute',56,164,'Lecture'),(9,'Peter Kiewit Institute',16,250,'Laboratory'),(10,'Peter Kiewit Institute',58,252,'Lecture'),(11,'Peter Kiewit Institute',40,256,'Lecture'),(12,'Peter Kiewit Institute',16,259,'Lecture'),(13,'Peter Kiewit Institute',40,260,'Lecture'),(15,'Peter Kiewit Institute',50,263,'Lecture'),(16,'Peter Kiewit Institute',30,269,'Lecture'),(17,'Peter Kiewit Institute',16,270,'Lecture'),(18,'Peter Kiewit Institute',32,274,'Lecture'),(19,'Peter Kiewit Institute',40,276,'Laboratory'),(20,'Peter Kiewit Institute',40,278,'Laboratory'),(21,'Peter Kiewit Institute',30,279,'Lecture'),(22,'Peter Kiewit Institute',16,350,'Lecture'),(23,'Peter Kiewit Institute',40,361,'Laboratory'),(24,'Peter Kiewit Institute',10,391,'Lecture'),(25,'Roskens Hall-A',80,102,'Lecture'),(26,'Scott Technolohy Center-A',80,10,'Lecture');
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-25 13:11:32
