--------------------------数据库创建SQL--------------------------

------- create user ----
CREATE USER 'crawler'@'%' IDENTIFIED BY 'crawler@12345';

------- create database ----
create database crawler_db;
create database crawler_db default charset UTF8mb4 collate utf8_general_ci;

GRANT all privileges ON crawler_db.* TO 'crawler'@'%';

FLUSH PRIVILEGES;


