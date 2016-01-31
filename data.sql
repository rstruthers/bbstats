INSERT INTO user (email, password_hash, role)
VALUES ('demo@localhost', '$2a$10$ebyC4Z5WtCXXc.HGDc1Yoe6CLFzcntFmfse6/pTj7CeDY5I05w16C', 'ADMIN');

INSERT INTO `bbstats`.`season`
(`id`,
`name`,
`start_date`,
`end_date`)
VALUES
(1,
'1961',
str_to_date('4/10/1961','%m/%d/%Y'),
str_to_date('10/1/1961','%m/%d/%Y'));

INSERT INTO `bbstats`.`league`
(`id`,
`name`)
VALUES
(1,'American League');

INSERT INTO `bbstats`.`team`
(`id`,
`city`,
`name`,
`state`,
`league_id`)
VALUES
(1,
'Baltimore',
'Orioles',
'Maryland',
1);

INSERT INTO `bbstats`.`season_team`
(`season_id`,
`team_id`)
VALUES
(1,
1);
--
-- INSERT INTO `bbstats`.`player`
-- (`id`,
-- `date_of_birth`,
-- `name`,
-- `position`)
-- VALUES
-- (1,
-- '1930-07-30',
-- 'Gus Triandos', 
-- 'CATCHER');
-- 
-- 
-- INSERT INTO `bbstats`.`team_player`
-- (`start_date`,
-- `player_id`,
-- `team_id`)
-- VALUES
-- (str_to_date('4/10/1961','%m/%d/%Y'),
-- 1,
-- 1);
