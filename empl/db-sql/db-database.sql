-- Active: 1734519446611@@127.0.0.1@55555@demo
--create table Book
DROP TABLE book;

CREATE TABLE book (
    id VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    author VARCHAR(255),
    isReady BOOLEAN DEFAULT false,
    PRIMARY KEY (id, name),
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO
    book (id, name, author, `isReady`)
VALUES
    ("1", "Búp sen xanh", "son tung", 0);

INSERT INTO
    book (id, name, author, `isReady`)
VALUES
    (
        "2",
        "Astérix - Astérix ở Thế Vận Hội",
        " René Goscinny",
        0
    );

SELECT
    *
FROM
    book;

--create table Employee
DROP TABLE employee;

CREATE TABLE employee (
    id VARCHAR(255) NOT NULL,
    firstName VARCHAR(255) NOT NULL,
    lastName VARCHAR(255),
    KIN VARCHAR(255),
    isDiscipilined BOOLEAN,
    PRIMARY KEY (id),
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO
    employee (
        id,
        `firstName`,
        `lastName`,
        `KIN`,
        `isDiscipilined`
    )
VALUES
    ("1", "A", "Le van", "AAA", 0);

INSERT INTO
    employee (
        id,
        `firstName`,
        `lastName`,
        `KIN`,
        `isDiscipilined`
    )
VALUES
    ("2", "B", "Nguyen van", "BBB", 0);

SELECT
    *
FROM
    employee;

use demo;

DROP TABLE borrorwing;

CREATE TABLE borrorwing (
    id INT NOT NULL AUTO_INCREMENT,
    bookId VARCHAR(255) NOT NULL,
    employeeId VARCHAR(255) NOT NULL,
    borrowingDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    returnBorrowing TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM ('BORROWED', 'CANCELED', 'RETURNED') NOT NULL,
    PRIMARY KEY (id),
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    -- CONSTRAINT book_id Foreign Key (bookId) REFERENCES book(id),
    -- CONSTRAINT employee_id Foreign Key (employeeId) REFERENCES employee(id)
);

ALTER TABLE borrorwing
DROP INDEX employee_id;

ALTER TABLE borrorwing
DROP CONSTRAINT employee_id;

ALTER TABLE borrorwing
DROP CONSTRAINT book_id AFTER TABLE borrorwing
DROP COLUMN id;

ALTER Table borrorwing
ADD COLUMN id INT NOT NULL AUTO_INCREMENT;

ALTER Table borrorwing ADD PRIMARY KEY (id);

DELETE FROM borrorwing
WHERE
    id < 5;

SELECT
    *
FROM
    borrorwing;

-- INSERT INTO TABLE
DELETE FROM book;

DELETE FROM borrorwing;

AFTER TABLE book
DROP COLUMN book_id;

ALTER TABLE book
DROP COLUMN is_ready;

INSERT INTO
    book (id, name, author, `isReady`)
VALUES
    ("1", "Búp sen xanh", "son tung", 0);

INSERT INTO
    book (id, name, author, `isReady`)
VALUES
    (
        "2",
        "Astérix - Astérix ở Thế Vận Hội",
        " René Goscinny",
        0
    );

SELECT
    *
FROM
    book;

SHOW COLUMNS
FROM
    book;

-- // Get borrowed books for employee GET /api /v1/employees/{employeeId}/books
SELECT
    *
FROM
    book b
WHERE
    b.id = "1";

SELECT
    *
FROM
    employee emp
WHERE
    emp.id = "1";

SELECT
    *
FROM
    borrorwing br
WHERE
    br.`employeeId` = "1";

SELECT
    *
FROM
    borrorwing br
WHERE
    br.`bookId` = "1"
    AND br.status like 'BORROWED'
ORDER BY
    br.`borrowingDate` DESC
LIMIT
    1;

UPDATE borrorwing br
SET
    br.`employeeId` = "12312"
WHERE
    br.`bookId` = "1"
    AND br.status like 'BORROWED'
ORDER BY
    br.`borrowingDate` DESC
LIMIT
    1;

SELECT
    *
FROM
    borrorwing;

-- -- 7. Công ty Việt Tiến đã cung cấp những mặt hàng nào
--  SELECT mh.TenHang, ncc.MaCongTy, ncc.TenCongTy  FROM nhacungcap AS ncc INNER JOIN  mathang AS mh ON ncc.MaCongTy = mh.MaCongTy WHERE ncc.TenCongTy = "Công ty may mặc Việt Tiến";
-- SELECT br.`bookId` FROM borrorwing br  JOIN book b ON br.`bookId` = b.id;
SELECT
    b.*
FROM
    book b
    LEFT JOIN borrorwing br ON br.`bookId` = b.id
WHERE
    br.`employeeId` = "1";

-- // Get book borrowing by employee GET /api /v1/borrowing/{employeeId}
INSERT INTO
    book (id, name, author, `isReady`)
VALUES
    ("1", "Búp sen xanh", "son tung", 0);

INSERT INTO
    book (id, name, author, `isReady`)
VALUES
    ("3", "Tuổi thơ dữ dội - Tập 1", "Phùng Quán", 1),
    (
        "4",
        "Astérix - Astérix và cái vạc",
        "René Goscinny",
        1
    ),
    (
        "5",
        "Momo, Hoàng tử bé xóm Cúc Lam",
        "Yaël Hassan",
        1
    ),
    (
        "6",
        "Khi 'trai' đẹp hẹn hò - Tập 2 (Tặng Set 2 Bookmark Ghép Hình)",
        "Nana Aokawa",
        1
    ),
    ("7", "Dế mèn phiêu lưu ký", "Tô Hoài", 1),
    (
        "8",
        "Doraemon Movie Story màu - Nobita và bản giao hưởng Địa Cầu",
        "Fujiko F Fujio",
        1
    );

SELECT
    *
FROM
    book;

-- Lược sử nước Việt bằng tranh - Viet Nam – A Brief History in Pictures
--   Huyền Trang
select
    be1_0.id,
    be1_0.bookId,
    be1_0.borrowingDate,
    be1_0.employeeId,
    be1_0.returnBorrowing,
    be1_0.status
from
    borrorwing be1_0
where
    be1_0.employeeId = 2
select
    be1_0.id,
    be1_0.author,
    be1_0.isReady,
    be1_0.name
from
    book be1_0
    left join borrorwing be2_0 on be2_0.bookId = be1_0.id
where
    be2_0.employeeId = 2
ALTER TABLE book
DROP COLUMN is_ready;

ALTER TABLE borrowing
DROP COLUMN createdAt,
DROP COLUMN updatedAt;

ALTER TABLE borrowing ADD createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

ALTER TABLE borrowing
DROP COLUMN borrowingDate;

ALTER TABLE borrowing ADD borrowingDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE borrowing
DROP COLUMN returnBorrowing;

ALTER TABLE borrowing ADD returnBorrowing TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE borrowing ADD updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

-- mysql-8.2.0-winx64\bin\mysqldump.exe -uroot -P55555 demo>./demo/databaseDemo.sql
