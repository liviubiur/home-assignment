CREATE DATABASE testdatabase;
  \connect testdatabase

 CREATE TABLE product (
     id     SERIAL PRIMARY KEY,
     name   varchar(40),
     price  DOUBLE PRECISION,
     created_at TIMESTAMP
 );