CREATE SCHEMA `customers` ;
CREATE TABLE `customers`.`customer` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `firstName` VARCHAR(45) NULL,
  `lastName` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `city` VARCHAR(45) NULL,
  `state` VARCHAR(45) NULL,
  `birthday` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));

INSERT INTO `customers`.`customer` (`id`, `firstName`, `lastName`, `email`, `city`, `birthday`) VALUES ('1', 'Osama', 'Oransa', 'osa.ora@acme.com', 'Cairo','No', '2000-01-09');
INSERT INTO `customers`.`customer` (`id`, `firstName`, `lastName`, `email`, `city`, `birthday`) VALUES ('2', 'Sameh', 'Ahmed', 'sa.or@acme.com', 'Dubai','Yes', '2001-02-20');
commit;

CREATE TABLE `customers`.`customer_accounts` (
  `customer_id` INT NOT NULL,
  `account_no` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`customer_id`, `account_no`));

ALTER TABLE `customers`.`customer_accounts` 
ADD COLUMN `type` VARCHAR(45) NULL AFTER `account_no`;

INSERT INTO `customers`.`customer_accounts` (`customer_id`, `account_no`, `type`) VALUES ('1', '123456-1', '100');
INSERT INTO `customers`.`customer_accounts` (`customer_id`, `account_no`, `type`) VALUES ('1', '123456-2', '101');
INSERT INTO `customers`.`customer_accounts` (`customer_id`, `account_no`, `type`) VALUES ('2', '2323445-1', '100');
commit;


GRANT ALL PRIVILEGES ON *.* TO 'customers'@'localhost' IDENTIFIED BY 'customers';

