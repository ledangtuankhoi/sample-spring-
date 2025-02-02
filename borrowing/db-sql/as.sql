-- MySQL dump 10.13  Distrib 8.2.0, for Win64 (x86_64)
--
-- Host: localhost    Database: demo
-- ------------------------------------------------------
-- Server version	8.2.0
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
-- Table structure for table `book`
--
DROP TABLE IF EXISTS `book`;

/*!40101 SET @saved_cs_client     = @@character_set_client */;

/*!50503 SET character_set_client = utf8mb4 */;

CREATE TABLE `book` (
    `id` varchar(255) NOT NULL,
    `name` varchar(255) NOT NULL,
    `author` varchar(255) DEFAULT NULL,
    `isReady` tinyint (1) DEFAULT '0',
    PRIMARY KEY (`id`, `name`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--
LOCK TABLES `book` WRITE;

/*!40000 ALTER TABLE `book` DISABLE KEYS */;

INSERT INTO
    `book`
VALUES
    ('1', 'Búp sen xanh', 'son tung', 0),
    (
        '2',
        'Astérix - Astérix ? Th? V?n H?i',
        ' René Goscinny',
        0
    ),
    ('3', 'Tu?i th? d? d?i - T?p 1', 'Phùng Quán', 1),
    (
        '4',
        'Astérix - Astérix và cái v?c',
        'René Goscinny',
        1
    ),
    (
        '5',
        'Momo, Hoàng t? bé xóm Cúc Lam',
        'Yaël Hassan',
        1
    ),
    (
        '6',
        'Khi \'trai\' ??p h?n hò - T?p 2 (T?ng Set 2 Bookmark Ghép Hình)',
        'Nana Aokawa',
        1
    ),
    ('7', 'D? mèn phiêu l?u ký', 'Tô Hoài', 1),
    (
        '8',
        'Doraemon Movie Story màu - Nobita và b?n giao h??ng ??a C?u',
        'Fujiko F Fujio',
        1
    );

/*!40000 ALTER TABLE `book` ENABLE KEYS */;

UNLOCK TABLES;

--
-- Table structure for table `employee`
--
DROP TABLE IF EXISTS `employee`;

/*!40101 SET @saved_cs_client     = @@character_set_client */;

/*!50503 SET character_set_client = utf8mb4 */;

CREATE TABLE `employee` (
    `id` varchar(255) NOT NULL,
    `firstName` varchar(255) NOT NULL,
    `lastName` varchar(255) DEFAULT NULL,
    `KIN` varchar(255) DEFAULT NULL,
    `isDiscipilined` tinyint (1) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--
LOCK TABLES `employee` WRITE;

/*!40000 ALTER TABLE `employee` DISABLE KEYS */;

INSERT INTO
    `employee`
VALUES
    ('1', 'A', 'Le van', 'AAA', 0),
    ('2', 'B', 'Nguyen van', 'BBB', 0);

/*!40000 ALTER TABLE `employee` ENABLE KEYS */;

UNLOCK TABLES;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;

/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;

/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-17 16:31:05
