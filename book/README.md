-import database to mysqldb
docker exec -i  one-book-mysqldb-1  mysql -uroot -ppassword book < book/db-sql/book.sql

-export database to mysqldb
docker exec -i  one-book-mysqldb-1  mysqldump -uroot -ppassword book > book/db-sql/book.sql