-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema assignment2
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema assignment2
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `assignment2` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `assignment2` ;

-- -----------------------------------------------------
-- Table `assignment2`.`zone`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `assignment2`.`zone` (
  `id_zone` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`id_zone`),
  UNIQUE INDEX `UK_27s7q3vqft9a76yi9k7e7rroi` (`name` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 10
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `assignment2`.`address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `assignment2`.`address` (
  `id_address` INT NOT NULL AUTO_INCREMENT,
  `city` VARCHAR(255) NULL DEFAULT NULL,
  `country` VARCHAR(255) NULL DEFAULT NULL,
  `number` VARCHAR(255) NULL DEFAULT NULL,
  `postal_code` VARCHAR(255) NULL DEFAULT NULL,
  `street` VARCHAR(100) NULL DEFAULT NULL,
  `id_zone` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id_address`),
  INDEX `FK7vrfdb91u1l994gl3n50ulgfi` (`id_zone` ASC) VISIBLE,
  CONSTRAINT `FK7vrfdb91u1l994gl3n50ulgfi`
    FOREIGN KEY (`id_zone`)
    REFERENCES `assignment2`.`zone` (`id_zone`))
ENGINE = InnoDB
AUTO_INCREMENT = 18
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `assignment2`.`administrator`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `assignment2`.`administrator` (
  `id_user` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(100) NULL DEFAULT NULL,
  `first_name` VARCHAR(100) NULL DEFAULT NULL,
  `last_name` VARCHAR(100) NULL DEFAULT NULL,
  `password` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id_user`),
  UNIQUE INDEX `UK_jj3mmcc0vjobqibj67dvuwihk` (`email` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `assignment2`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `assignment2`.`category` (
  `id_category` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`id_category`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `assignment2`.`customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `assignment2`.`customer` (
  `id_user` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(100) NULL DEFAULT NULL,
  `first_name` VARCHAR(100) NULL DEFAULT NULL,
  `last_name` VARCHAR(100) NULL DEFAULT NULL,
  `password` VARCHAR(255) NULL DEFAULT NULL,
  `id_address` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id_user`),
  UNIQUE INDEX `UK_dwk6cx0afu8bs9o4t536v1j5v` (`email` ASC) VISIBLE,
  INDEX `FKrtg0ytybwvot9ne0txhl5jxy4` (`id_address` ASC) VISIBLE,
  CONSTRAINT `FKrtg0ytybwvot9ne0txhl5jxy4`
    FOREIGN KEY (`id_address`)
    REFERENCES `assignment2`.`address` (`id_address`))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `assignment2`.`restaurant`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `assignment2`.`restaurant` (
  `id_restaurant` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NULL DEFAULT NULL,
  `id_address` INT NULL DEFAULT NULL,
  `id_administrator` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id_restaurant`),
  UNIQUE INDEX `UK_i6u3x7opncroyhd755ejknses` (`name` ASC) VISIBLE,
  INDEX `FK3rorhysbvro2rpkjmnncye0ap` (`id_address` ASC) VISIBLE,
  INDEX `FKdu3genvcl4h37okfj6qojeqgh` (`id_administrator` ASC) VISIBLE,
  CONSTRAINT `FK3rorhysbvro2rpkjmnncye0ap`
    FOREIGN KEY (`id_address`)
    REFERENCES `assignment2`.`address` (`id_address`),
  CONSTRAINT `FKdu3genvcl4h37okfj6qojeqgh`
    FOREIGN KEY (`id_administrator`)
    REFERENCES `assignment2`.`administrator` (`id_user`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `assignment2`.`delivery_order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `assignment2`.`delivery_order` (
  `id_order` INT NOT NULL AUTO_INCREMENT,
  `date` DATE NOT NULL,
  `status` VARCHAR(255) NOT NULL,
  `total` INT NOT NULL,
  `id_customer` INT NULL DEFAULT NULL,
  `id_restaurant` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id_order`),
  INDEX `FKhyadg04t9s9cvw0mvoxnpi5rp` (`id_customer` ASC) VISIBLE,
  INDEX `FKo1kxv65ji22p72i8temjrdunu` (`id_restaurant` ASC) VISIBLE,
  CONSTRAINT `FKhyadg04t9s9cvw0mvoxnpi5rp`
    FOREIGN KEY (`id_customer`)
    REFERENCES `assignment2`.`customer` (`id_user`),
  CONSTRAINT `FKo1kxv65ji22p72i8temjrdunu`
    FOREIGN KEY (`id_restaurant`)
    REFERENCES `assignment2`.`restaurant` (`id_restaurant`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `assignment2`.`delivery_zone`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `assignment2`.`delivery_zone` (
  `id_restaurant` INT NOT NULL,
  `id_zone` INT NOT NULL,
  PRIMARY KEY (`id_restaurant`, `id_zone`),
  INDEX `FKsog0eh5addatbaf77mcvcgt10` (`id_zone` ASC) VISIBLE,
  CONSTRAINT `FK6rwm659w3ihm832q0c62obs6`
    FOREIGN KEY (`id_restaurant`)
    REFERENCES `assignment2`.`restaurant` (`id_restaurant`),
  CONSTRAINT `FKsog0eh5addatbaf77mcvcgt10`
    FOREIGN KEY (`id_zone`)
    REFERENCES `assignment2`.`zone` (`id_zone`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `assignment2`.`food`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `assignment2`.`food` (
  `id_food` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  `name` VARCHAR(100) NULL DEFAULT NULL,
  `price` INT NULL DEFAULT NULL,
  `id_category` INT NULL DEFAULT NULL,
  `id_restaurant` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id_food`),
  INDEX `FKo4igwdepmk6gaqrl8idiutweg` (`id_category` ASC) VISIBLE,
  INDEX `FKtfdkhaqot1oq93x2ddxswun5p` (`id_restaurant` ASC) VISIBLE,
  CONSTRAINT `FKo4igwdepmk6gaqrl8idiutweg`
    FOREIGN KEY (`id_category`)
    REFERENCES `assignment2`.`category` (`id_category`),
  CONSTRAINT `FKtfdkhaqot1oq93x2ddxswun5p`
    FOREIGN KEY (`id_restaurant`)
    REFERENCES `assignment2`.`restaurant` (`id_restaurant`))
ENGINE = InnoDB
AUTO_INCREMENT = 26
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `assignment2`.`food_order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `assignment2`.`food_order` (
  `id_order` INT NOT NULL,
  `id_food` INT NOT NULL,
  INDEX `FK2ta7urhn62yy1nifwraba08bt` (`id_food` ASC) VISIBLE,
  INDEX `FKhm3h7b2uwwke2sk9jjtr7wsds` (`id_order` ASC) VISIBLE,
  CONSTRAINT `FK2ta7urhn62yy1nifwraba08bt`
    FOREIGN KEY (`id_food`)
    REFERENCES `assignment2`.`food` (`id_food`),
  CONSTRAINT `FKhm3h7b2uwwke2sk9jjtr7wsds`
    FOREIGN KEY (`id_order`)
    REFERENCES `assignment2`.`delivery_order` (`id_order`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
