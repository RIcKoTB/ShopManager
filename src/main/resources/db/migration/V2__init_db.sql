START TRANSACTION;

CREATE DATABASE
  IF NOT EXISTS accounting_of_goods1
  CHARACTER SET utf8mb4
        COLLATE utf8mb4_unicode_ci;

CREATE TABLE accounting_of_goods1.category
(
    id   INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255),
    PRIMARY KEY (id)
)ENGINE = INNODB;

CREATE TABLE role(
                                          id   INT NOT NULL AUTO_INCREMENT,
                                          name VARCHAR(255),
                                          PRIMARY KEY (id)
)ENGINE = INNODB;

CREATE TABLE users(
                                           id   INT NOT NULL AUTO_INCREMENT,
                                           login VARCHAR(255),
                                           password VARCHAR(255),
                                           role_id INT NOT NULL,
                                           PRIMARY KEY (id),
                                           FOREIGN KEY (role_id) REFERENCES role(id)
)ENGINE = INNODB;

CREATE TABLE basket(
                                            id   INT NOT NULL AUTO_INCREMENT,
                                            user_id INT NOT NULL ,
                                            goods_list TEXT,
                                            data_create DATE,
                                            PRIMARY KEY (id),
                                            FOREIGN KEY (user_id) REFERENCES users(id)
)ENGINE = INNODB;

CREATE TABLE departments(
                                                 id   INT NOT NULL AUTO_INCREMENT,
                                                 city VARCHAR(255),
                                                 street VARCHAR(255),
                                                 photo BLOB,
                                                 PRIMARY KEY (id)
)ENGINE = INNODB;

CREATE TABLE goods(
                                           id   INT NOT NULL AUTO_INCREMENT,
                                           name VARCHAR(255),
                                           photo BLOB,
                                           category_id INT NOT NULL,
                                           price DOUBLE,
                                           PRIMARY KEY (id),
                                           FOREIGN KEY (category_id) REFERENCES category(id)

)ENGINE = INNODB;

CREATE TABLE distributors(
                                                  id   INT NOT NULL AUTO_INCREMENT,
                                                  full_name VARCHAR(255),
                                                  goods_id INT NOT NULL,
                                                  company VARCHAR(255),
                                                  logo BLOB,
                                                  PRIMARY KEY (id),
                                                  FOREIGN KEY (goods_id) REFERENCES goods(id)

)ENGINE = INNODB;

CREATE TABLE session(
                                             id   INT NOT NULL AUTO_INCREMENT,
                                             user_id INT NOT NULL,
                                             data DATETIME NOT NULL,
                                             PRIMARY KEY (id),
                                             FOREIGN KEY (user_id) REFERENCES users(id)

)ENGINE = INNODB;

CREATE TABLE store(
                                           id   INT NOT NULL AUTO_INCREMENT,
                                           name VARCHAR(255),
                                           good_id INT NOT NULL,
                                           count INT,
                                           department_id INT NOT NULL,
                                           distributor_id INT NOT NULL,
                                           PRIMARY KEY (id),
                                           FOREIGN KEY (good_id) REFERENCES goods(id),
                                           FOREIGN KEY (department_id) REFERENCES departments(id),
                                           FOREIGN KEY (distributor_id) REFERENCES distributors(id)
)ENGINE = INNODB;

CREATE TABLE history_buy(
                                                 id   INT NOT NULL AUTO_INCREMENT,
                                                 user_id INT NOT NULL,
                                                 basket_id INT NOT NULL,
                                                 data DATE,
                                                 PRIMARY KEY (id),
                                                 FOREIGN KEY (user_id) REFERENCES users(id),
                                                 FOREIGN KEY (basket_id) REFERENCES basket(id)
)ENGINE = INNODB;

CREATE TABLE history_distributor(
                                                         id   INT NOT NULL AUTO_INCREMENT,
                                                         distributor_id INT NOT NULL,
                                                         department_id INT NOT NULL,
                                                         data DATE,
                                                         PRIMARY KEY (id),
                                                         FOREIGN KEY (distributor_id) REFERENCES distributors(id),
                                                         FOREIGN KEY (department_id) REFERENCES departments(id)
)ENGINE = INNODB;
COMMIT;