-- MySQL dump 10.13  Distrib 8.0.40, for Linux (x86_64)
--
-- Host: localhost    Database: book
-- ------------------------------------------------------
-- Server version	8.0.40
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
    `createdAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `updatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
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
    (
        '1',
        'Bp sen xanh',
        'son tung',
        0,
        '2024-12-19 12:06:49',
        '2024-12-19 12:06:49'
    ),
    (
        '168a8d64-d333-4eb8-8375-fee7569f0162',
        'stringaaaaa    a',
        'string',
        1,
        '2024-12-19 12:06:49',
        '2024-12-19 12:06:49'
    ),
    (
        '3',
        'Tu?i th? d? d?i - T?p 1',
        'Phng Qun',
        1,
        '2024-12-19 12:06:49',
        '2024-12-19 12:06:49'
    ),
    (
        '3dab2b83-9d75-43f4-831e-c4235df4f4de',
        'string',
        'string',
        1,
        '2024-12-19 12:06:49',
        '2024-12-19 12:06:49'
    ),
    (
        '4',
        'Astrix - Astrix v ci v?c',
        'Ren Goscinny',
        1,
        '2024-12-19 12:06:49',
        '2024-12-19 12:06:49'
    ),
    (
        '43cafc61-03f6-4a85-b8b7-dfeafadc4bf3',
        'string',
        'string',
        1,
        '2024-12-19 12:06:49',
        '2024-12-19 12:06:49'
    ),
    (
        '4af3f0f5-74dc-4b63-85a1-b425aae503b2',
        'string',
        'string',
        1,
        '2024-12-19 12:06:49',
        '2024-12-19 12:06:49'
    ),
    (
        '4bc9f185-7bb7-43f0-b024-fcc4cc155e3e',
        'string',
        'string',
        1,
        '2024-12-19 12:06:49',
        '2024-12-19 12:06:49'
    ),
    (
        '5',
        'Momo, Hong t? b xm Cc Lam',
        'Yal Hassan',
        1,
        '2024-12-19 12:06:49',
        '2024-12-19 12:06:49'
    ),
    (
        '6',
        'Khi \'trai\' ??p h?n h - T?p 2 (T?ng Set 2 Bookmark Ghp Hnh)',
        'Nana Aokawa',
        1,
        '2024-12-19 12:06:49',
        '2024-12-19 12:06:49'
    ),
    (
        '625d7793-acf7-4654-90b6-956a81573df7',
        'string',
        'string',
        1,
        '2024-12-19 12:06:49',
        '2024-12-19 12:06:49'
    ),
    (
        '6b5f2f84-a16a-42ee-9c2d-0b89afc82aca',
        'string',
        'string',
        1,
        '2024-12-19 12:06:49',
        '2024-12-19 12:06:49'
    ),
    (
        '7',
        'D? mn phiu l?u k',
        'T Hoi',
        1,
        '2024-12-19 12:06:49',
        '2024-12-19 12:06:49'
    ),
    (
        '7a131517-4415-475f-a6e9-8f0d3ca21746',
        'update content',
        'update content',
        NULL,
        '2024-12-19 12:06:49',
        '2024-12-19 12:06:49'
    ),
    (
        '8',
        'Doraemon Movie Story mu - Nobita v b?n giao h??ng ??a C?u',
        'Fujiko F Fujio',
        1,
        '2024-12-19 12:06:49',
        '2024-12-19 12:06:49'
    ),
    (
        '87d70b1d-f45d-46b6-a907-8c9f946ce747',
        'string',
        'string',
        1,
        '2024-12-19 12:06:49',
        '2024-12-19 12:06:49'
    ),
    (
        'aef9a350-9568-4553-85d5-86628f7bb31d',
        'string aaaaaaaaa',
        'string   aaaaa',
        1,
        '2024-12-19 12:06:49',
        '2024-12-19 12:06:49'
    ),
    (
        'b6c5d2a6-0bb4-4089-ac84-05bbad6a6f99',
        'stringaaaaa    a',
        'string',
        1,
        '2024-12-19 12:06:49',
        '2024-12-19 12:06:49'
    ),
    (
        'c1e311b1-8d48-4895-b8f9-cae0827d5460',
        'string',
        'string',
        1,
        '2024-12-19 12:06:49',
        '2024-12-19 12:06:49'
    ),
    (
        'fa3cd5ef-95a3-48c5-8c20-d85c1dea3252',
        'string',
        'string',
        1,
        '2024-12-19 12:06:49',
        '2024-12-19 12:06:49'
    );

/*!40000 ALTER TABLE `book` ENABLE KEYS */;

UNLOCK TABLES;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;

/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;

/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-01-23 23:35:45
