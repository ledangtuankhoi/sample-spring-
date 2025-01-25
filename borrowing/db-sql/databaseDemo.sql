-- Active: 1735032885229@@mysqltestdb-14534erfdvfdhtdf.mysql.database.azure.com@3306@demo
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
        'Búp sen xanh',
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
        'Phùng Quán',
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
        'Astérix - Astérix và cái v?c',
        'René Goscinny',
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
        'Momo, Hoàng t? bé xóm Cúc Lam',
        'Yaël Hassan',
        1,
        '2024-12-19 12:06:49',
        '2024-12-19 12:06:49'
    ),
    (
        '6',
        'Khi \'trai\' ??p h?n hò - T?p 2 (T?ng Set 2 Bookmark Ghép Hình)',
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
        'D? mèn phiêu l?u ký',
        'Tô Hoài',
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
        'Doraemon Movie Story màu - Nobita và b?n giao h??ng ??a C?u',
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

--
-- Table structure for table `borrowing`
--
DROP TABLE IF EXISTS `borrowing`;

/*!40101 SET @saved_cs_client     = @@character_set_client */;

/*!50503 SET character_set_client = utf8mb4 */;

CREATE TABLE `borrowing` (
    `id` varchar(255) NOT NULL,
    `bookId` varchar(255) NOT NULL,
    `employeeId` varchar(255) NOT NULL,
    `status` enum ('BORROWED', 'CANCELED', 'RETURNED') NOT NULL,
    `borrowingDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `returnBorrowing` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `createdAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `updatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `borrowing`
--
LOCK TABLES `borrowing` WRITE;

/*!40000 ALTER TABLE `borrowing` DISABLE KEYS */;

INSERT INTO
    `borrowing`
VALUES
    (
        '125e86b1-3860-4ef1-ad1f-8ba7b50bb9b7',
        '1',
        '2',
        'BORROWED',
        '2024-12-20 09:52:04',
        '2024-12-20 09:53:27',
        '2024-12-20 09:55:40',
        '2024-12-20 09:55:40'
    ),
    (
        '88f38aaa-b096-4e79-84e5-6761657e532e',
        '3',
        '2',
        'BORROWED',
        '2024-12-20 09:52:04',
        '2024-12-20 09:53:27',
        '2024-12-20 09:55:40',
        '2024-12-20 09:55:40'
    ),
    (
        'cc6ab229-fc50-4877-8269-86e4afa7aa36',
        '5',
        '2',
        'CANCELED',
        '2024-12-20 09:52:04',
        '2024-12-20 09:53:27',
        '2024-12-20 09:55:40',
        '2024-12-20 09:55:40'
    );

/*!40000 ALTER TABLE `borrowing` ENABLE KEYS */;

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
    `createdAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `updatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
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
    (
        '1',
        'A',
        'Le van',
        'AAA',
        0,
        '2024-12-20 09:55:24',
        '2024-12-20 09:55:24'
    ),
    (
        '2',
        'B',
        'Nguyen van',
        'BBB',
        0,
        '2024-12-20 09:55:24',
        '2024-12-20 09:55:24'
    );

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

-- Dump completed on 2024-12-20 16:56:51
