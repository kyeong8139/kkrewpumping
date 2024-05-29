DROP SCHEMA IF EXISTS `kkrew` ;

CREATE SCHEMA IF NOT EXISTS `kkrew` char set utf8;
USE `kkrew` ;

-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kkrew`.`user` (
  `id` VARCHAR(45) NOT NULL PRIMARY KEY,
  `nickname` VARCHAR(45) NOT NULL,
  `password` CHAR(64),
  `introduction` VARCHAR(280),
  `image_url` VARCHAR(2083),
  `is_kakao_user` TINYINT,
  `exp` INT NOT NULL DEFAULT 10
);


-- -----------------------------------------------------
-- Table `kkrew`.`video`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kkrew`.`video` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `title` VARCHAR(100) NOT NULL,
  `purpose` VARCHAR(45) NOT NULL,
  `image_url` VARCHAR(2083) NOT NULL,
  `video_url` VARCHAR(2083) NOT NULL,
  `view_count` INT DEFAULT 0,
  `writer` VARCHAR(45),
  `description` VARCHAR(400),
  CONSTRAINT `v_user_id` FOREIGN KEY (`writer`) REFERENCES `kkrew`.`user` (`id`) ON DELETE SET NULL
);


-- -----------------------------------------------------
-- Table `kkrew`.`supplies`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kkrew`.`supplies` (
  `supplies` VARCHAR(20) NOT NULL PRIMARY KEY
);

-- -----------------------------------------------------
-- Table `kkrew`.`video_supplies`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kkrew`.`video_supplies` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `video_id` INT NOT NULL,
  `supplies` VARCHAR(45) NOT NULL,
  CONSTRAINT `vs_video_id` FOREIGN KEY (`video_id`) REFERENCES `kkrew`.`video` (`id`) ON DELETE CASCADE, 
  CONSTRAINT `vs_supplies` FOREIGN KEY (`supplies`) REFERENCES `kkrew`.`supplies` (`supplies`)
);


-- -----------------------------------------------------
-- Table `kkrew`.`challenge`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kkrew`.`challenge` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `writer` VARCHAR(45),
  `title` VARCHAR(100) NOT NULL,
  `purpose` VARCHAR(100) NOT NULL,
  `period` INT NOT NULL,
  `time` INT NOT NULL,
  `level` INT NOT NULL,
  `image_url` VARCHAR(2083),
  `video_count` INT NOT NULL,
  `participants_count` INT DEFAULT 0,
  `current_participants_count` INT DEFAULT 0,
  `success_participants_count` INT DEFAULT 0,
	CONSTRAINT `c_user_id` FOREIGN KEY (`writer`) REFERENCES `kkrew`.`user` (`id`) ON DELETE SET NULL
);


-- -----------------------------------------------------
-- Table `kkrew`.`challenge_supplies`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kkrew`.`challenge_supplies` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `challenge_id` INT NOT NULL,
  `supplies` VARCHAR(45) NOT NULL,
  CONSTRAINT `cs_challenge_id` FOREIGN KEY (`challenge_id`) REFERENCES `kkrew`.`challenge` (`id`) ON DELETE CASCADE,
  CONSTRAINT `cs_supplies` FOREIGN KEY (`supplies`) REFERENCES `kkrew`.`supplies` (`supplies`)
);


-- -----------------------------------------------------
-- Table `kkrew`.`challenge_video`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kkrew`.`challenge_video` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `challenge_id` INT NOT NULL,
  `video_id` INT NOT NULL,
  `order` INT NOT NULL,
  CONSTRAINT `cv_challenge_id` FOREIGN KEY (`challenge_id`) REFERENCES `kkrew`.`challenge` (`id`) ON DELETE CASCADE
);

-- -----------------------------------------------------
-- Table `kkrew`.`review`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kkrew`.`review` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `challenge_id` INT NOT NULL,
  `writer` VARCHAR(45),
  `content` VARCHAR(280) NOT NULL,
  CONSTRAINT `r_challenge_id` FOREIGN KEY (`challenge_id`) REFERENCES `kkrew`.`challenge` (`id`),
  CONSTRAINT `r_user_id` FOREIGN KEY (`writer`) REFERENCES `kkrew`.`user` (`id`) ON DELETE SET NULL
);

-- -----------------------------------------------------
-- Table `kkrew`.`user_challenge`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kkrew`.`user_challenge` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `user_id` VARCHAR(45) NOT NULL,
  `challenge_id` INT NOT NULL,
  `crew_id` INT DEFAULT -1,
  `start_date` DATE,
  `end_date` DATE,
  `video_count` INT,
  `current_completion` INT DEFAULT 0,
  `completion` TINYINT DEFAULT 0,
  `update_timeStamp` TIMESTAMP NOT NULL	DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT `uc_challenge_id` FOREIGN KEY (`challenge_id`) REFERENCES `kkrew`.`challenge` (`id`),
  CONSTRAINT `uc_user_id` FOREIGN KEY (`user_id`) REFERENCES `kkrew`.`user` (`id`) ON DELETE CASCADE
);

-- -----------------------------------------------------
-- Table `kkrew`.`crew`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kkrew`.`crew` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(45) NOT NULL,
  `end_date` DATE NOT NULL,
  `challenge_id` INT NOT NULL,
  CONSTRAINT `c_challenge_id` FOREIGN KEY (`challenge_id`) REFERENCES `kkrew`.`challenge` (`id`)
);

-- -----------------------------------------------------
-- Table `kkrew`.`crew_user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kkrew`.`crew_user` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `crew_id` INT NOT NULL,
  `user_id` VARCHAR(45) NOT NULL,
  `user_challenge_id` INT,
  CONSTRAINT `cu_crew_id` FOREIGN KEY (`crew_id`) REFERENCES `kkrew`.`crew` (`id`),
  CONSTRAINT `cu_user_id` FOREIGN KEY (`user_id`) REFERENCES `kkrew`.`user` (`id`) ON DELETE CASCADE
);

-- -----------------------------------------------------
-- Table `kkrew`.`message`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kkrew`.`message` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `from_user` VARCHAR(45) NOT NULL DEFAULT "kkrewPunmping",
  `from_datetime` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `to_user` VARCHAR(45) NOT NULL,
  `content` VARCHAR(280) NOT NULL,
  CONSTRAINT `m_to_user_id` FOREIGN KEY (`to_user`) REFERENCES `kkrew`.`user` (`id`) ON DELETE CASCADE
);