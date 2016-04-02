CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password_hash` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `league` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `player` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_of_birth` date DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `season` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `end_date` date DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `team` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `city` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `scoresheet` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `game_date` date DEFAULT NULL,
  `game_number` int(11) DEFAULT NULL,
  `home_team_id` bigint(20) DEFAULT NULL,
  `season_id` bigint(20) DEFAULT NULL,
  `visiting_team_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_a173g3i57wx8v5lboop7b15k8` (`home_team_id`),
  KEY `FK_bm63fiavvr9o0tt54r4aq5y3k` (`season_id`),
  KEY `FK_li0eeamms775i1fxyhbo6kt48` (`visiting_team_id`),
  CONSTRAINT `FK_a173g3i57wx8v5lboop7b15k8` FOREIGN KEY (`home_team_id`) REFERENCES `team` (`id`),
  CONSTRAINT `FK_bm63fiavvr9o0tt54r4aq5y3k` FOREIGN KEY (`season_id`) REFERENCES `season` (`id`),
  CONSTRAINT `FK_li0eeamms775i1fxyhbo6kt48` FOREIGN KEY (`visiting_team_id`) REFERENCES `team` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `scoresheet_pitcher` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `balks` int(11) DEFAULT NULL,
  `earned_runs` int(11) DEFAULT NULL,
  `hits` int(11) DEFAULT NULL,
  `homeruns` int(11) DEFAULT NULL,
  `innings_pitched` int(11) DEFAULT NULL,
  `loss` bit(1) DEFAULT NULL,
  `runs` int(11) DEFAULT NULL,
  `save` bit(1) DEFAULT NULL,
  `strikeouts` int(11) DEFAULT NULL,
  `walks` int(11) DEFAULT NULL,
  `win` bit(1) DEFAULT NULL,
  `h_scoresheet_player_id` bigint(20) DEFAULT NULL,
  `player_id` bigint(20) DEFAULT NULL,
  `v_scoresheet_player_id` bigint(20) DEFAULT NULL,
  `pitcher_order` int(11) DEFAULT NULL,
  `partial_innings_pitched` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_t0w7by96f9x3q3f87bgf3j910` (`h_scoresheet_player_id`),
  KEY `FK_6xahd2wskqlbp01k0e8weqel` (`player_id`),
  KEY `FK_cfeayno63kpewk0gbh0bwrk9j` (`v_scoresheet_player_id`),
  CONSTRAINT `FK_6xahd2wskqlbp01k0e8weqel` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`),
  CONSTRAINT `FK_cfeayno63kpewk0gbh0bwrk9j` FOREIGN KEY (`v_scoresheet_player_id`) REFERENCES `scoresheet` (`id`),
  CONSTRAINT `FK_t0w7by96f9x3q3f87bgf3j910` FOREIGN KEY (`h_scoresheet_player_id`) REFERENCES `scoresheet` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `scoresheet_player` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lineup_order` int(11) DEFAULT NULL,
  `lineup_order_index` int(11) DEFAULT NULL,
  `player_id` bigint(20) DEFAULT NULL,
  `v_scoresheet_player_id` bigint(20) DEFAULT NULL,
  `h_scoresheet_player_id` bigint(20) DEFAULT NULL,
  `at_bats` int(11) DEFAULT NULL,
  `runs` int(11) DEFAULT NULL,
  `hits` int(11) DEFAULT NULL,
  `rbi` int(11) DEFAULT NULL,
  `doubles` int(11) DEFAULT NULL,
  `homeruns` int(11) DEFAULT NULL,
  `stolen_bases` int(11) DEFAULT NULL,
  `triples` int(11) DEFAULT NULL,
  `errors` int(11) DEFAULT NULL,
  `passed_balls` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_hkc0r905ce73e0av49okst15t` (`player_id`),
  KEY `FK_a92t62owo7rb1tf70h12y66wn` (`v_scoresheet_player_id`),
  KEY `FK_iur2r4a12a12akfuxkhce1ktd` (`h_scoresheet_player_id`),
  CONSTRAINT `FK_a92t62owo7rb1tf70h12y66wn` FOREIGN KEY (`v_scoresheet_player_id`) REFERENCES `scoresheet` (`id`),
  CONSTRAINT `FK_hkc0r905ce73e0av49okst15t` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`),
  CONSTRAINT `FK_iur2r4a12a12akfuxkhce1ktd` FOREIGN KEY (`h_scoresheet_player_id`) REFERENCES `scoresheet` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `team_league` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `end_date` date DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `league_id` bigint(20) DEFAULT NULL,
  `team_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_tdh3n45nhmd7t4jd8rbdit9e5` (`league_id`),
  KEY `FK_ro6sox8crjfdxo2xnylsgs0ny` (`team_id`),
  CONSTRAINT `FK_ro6sox8crjfdxo2xnylsgs0ny` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`),
  CONSTRAINT `FK_tdh3n45nhmd7t4jd8rbdit9e5` FOREIGN KEY (`league_id`) REFERENCES `league` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `team_player` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `end_date` date DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `player_id` bigint(20) DEFAULT NULL,
  `team_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_3i69b9o5ye4ekk9r2wqujc1g5` (`player_id`),
  KEY `FK_g4ltvwh9h24d6xcyh1aao3eye` (`team_id`),
  CONSTRAINT `FK_3i69b9o5ye4ekk9r2wqujc1g5` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`),
  CONSTRAINT `FK_g4ltvwh9h24d6xcyh1aao3eye` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `team_player_pos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `num_games` int(11) DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  `team_player_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_smvd0ryc9vxorniuyg2xwt7y6` (`team_player_id`),
  CONSTRAINT `FK_smvd0ryc9vxorniuyg2xwt7y6` FOREIGN KEY (`team_player_id`) REFERENCES `team_player` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

