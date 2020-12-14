SET @@collation_server = utf8_general_ci;
SET NAMES utf8;

DROP USER IF EXISTS 'productuser'@'%';
DROP USER IF EXISTS 'productuser'@'localhost';
CREATE USER 'productuser'@'%' IDENTIFIED BY 'productpass';
CREATE USER 'productuser'@'localhost' IDENTIFIED BY 'productpass';

GRANT ALL PRIVILEGES ON product . * TO 'productuser'@'%';
GRANT ALL PRIVILEGES ON product . * TO 'productuser'@'localhost';

FLUSH PRIVILEGES;

DROP DATABASE IF EXISTS `product`;

CREATE DATABASE `product`
DEFAULT CHARACTER SET utf8
DEFAULT COLLATE utf8_general_ci;

USE `product`;

CREATE TABLE `product` (
	`id` Integer NOT NULL AUTO_INCREMENT,
	`sku` Varchar(12) NOT NULL,
	`name` Varchar(50) NOT NULL,
	`description` Varchar(500) NOT NULL,
	`manufacturer` Varchar(50) NOT NULL,
	`category` Varchar(30) NOT NULL,
	`costPrice` Decimal(5,2) NOT NULL,
	`sellPrice` Decimal(5,2) NOT NULL,
	`active` Boolean,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
