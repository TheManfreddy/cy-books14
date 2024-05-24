-- Insertion des données dans la table user
INSERT INTO `user` (`mail`, `name`, `first_name`, `birth_date`, `address`, `phone_number`, `number_borrow`) VALUES
                                                                                                                ('ash@pokemon.com', 'Ash', 'Ketchum', '1996-02-27', 'Pallet Town', '0678901234', 4),
                                                                                                                ('eren@attack.com', 'Eren', 'Yeager', '2009-09-09', 'Shiganshina', '0670123456', 1),
                                                                                                                ('goku@dbz.com', 'Goku', 'Son', '1984-12-03', 'Earth', '0679012345', 1),
                                                                                                                ('kirby@popstar.com', 'Kirby', 'Dream', '1992-04-27', 'Dream Land', '0678678901', 0),
                                                                                                                ('link@hyrule.com', 'Link', 'Hyrule', '1986-02-21', 'Hyrule', '0678345678', 1),
                                                                                                                ('luffy@onepiece.com', 'Luffy', 'Monkey D.', '1997-07-22', 'East Blue', '0674567890', 1),
                                                                                                                ('luigi@nintendo.com', 'Luigi', 'Mario', '1983-07-14', 'Mushroom Kingdom', '0678234567', 1),
                                                                                                                ('mario@nintendo.com', 'Mario', 'Mario', '1981-07-09', 'Mushroom Kingdom', '0678123456', 1),
                                                                                                                ('naruto@konoha.com', 'Naruto', 'Uzumaki', '1999-09-21', 'Konoha', '0671234567', 1),
                                                                                                                ('nezuko@demon.com', 'Nezuko', 'Kamado', '2016-02-15', 'Mount Kumotori', '0679012345', 1),
                                                                                                                ('pikachu@pokemon.com', 'Pikachu', 'Pokemon', '1996-02-27', 'Pallet Town', '0678789012', 0),
                                                                                                                ('sakura@konoha.com', 'Sakura', 'Haruno', '1999-09-21', 'Konoha', '0673456789', 0),
                                                                                                                ('samus@metroid.com', 'Samus', 'Aran', '1986-08-06', 'Galactic Federation', '0678567890', 1),
                                                                                                                ('sanji@onepiece.com', 'Sanji', 'Vinsmoke', '1997-07-22', 'East Blue', '0676789012', 2),
                                                                                                                ('sasuke@konoha.com', 'Sasuke', 'Uchiha', '1999-09-21', 'Konoha', '0672345678', 0),
                                                                                                                ('tanjiro@demon.com', 'Tanjiro', 'Kamado', '2016-02-15', 'Mount Kumotori', '0678901234', 1),
                                                                                                                ('usopp@onepiece.com', 'Usopp', 'SniperKing', '1997-07-22', 'East Blue', '0677890123', 0),
                                                                                                                ('vegeta@dbz.com', 'Vegeta', 'Saiyan', '1988-02-26', 'Planet Vegeta', '0670123456', 1),
                                                                                                                ('zelda@hyrule.com', 'Zelda', 'Hyrule', '1986-02-21', 'Hyrule', '0678456789', 1),
                                                                                                                ('zoro@onepiece.com', 'Zoro', 'Roronoa', '1997-07-22', 'East Blue', '0675678901', 1);

INSERT INTO `borrow` (`idBorrow`, `isbn`, `idUser`, `duration`, `start_date`, `end_date`, `status`) VALUES
                                                                                                        (1, '9782366130164', 'ash@pokemon.com', 25, '2024-04-28', '2024-05-28', 0),
                                                                                                        (2, '9782723478991', 'goku@dbz.com', 23, '2024-05-14', '2024-06-13', 1),
                                                                                                        (3, '9782723478991', 'ash@pokemon.com', 0, '2024-05-23', '2024-06-22', 0),
                                                                                                        (4, '9782723478991', 'mario@nintendo.com', 6, '2024-05-17', '2024-06-16', 0),
                                                                                                        (5, '9782723484138', 'nezuko@demon.com', 0, '2024-05-23', '2024-06-22', 0),
                                                                                                        (6, '9782723470353', 'link@hyrule.com', 26, '2024-04-27', '2024-05-27', 0),
                                                                                                        (8, '9782723470353', 'sanji@onepiece.com', 31, '2024-05-18', '2024-05-17', 1),
                                                                                                        (9, '9782350303970', 'vegeta@dbz.com', 20, '2024-05-03', '2024-06-02', 0),
                                                                                                        (10, '9782350303970', 'zoro@onepiece.com', 31, '2024-04-23', '2024-05-23', 0),
                                                                                                        (12, '9782350303970', 'tanjiro@demon.com', 20, '2024-04-07', '2024-05-07', 1),
                                                                                                        (13, '9782350303970', 'sanji@onepiece.com', 23, '2024-04-29', '2024-05-29', 0),
                                                                                                        (14, '9782363120083', 'luffy@onepiece.com', 26, '2024-04-27', '2024-05-27', 0),
                                                                                                        (15, '9782363120083', 'samus@metroid.com', 9, '2024-05-14', '2024-06-13', 0),
                                                                                                        (16, '9782363120083', 'ash@pokemon.com', 15, '2024-01-23', '2024-02-22', 1),
                                                                                                        (17, '9782363120083', 'naruto@konoha.com', 34, '2024-02-23', '2024-03-24', 1),
                                                                                                        (19, '9782070624546', 'luigi@nintendo.com', 22, '2024-05-01', '2024-05-31', 0),
                                                                                                        (21, '9782754811811', 'ash@pokemon.com', 19, '2024-05-05', '2024-06-04', 0),
                                                                                                        (22, '9782754811811', 'eren@attack.com', 17, '2024-05-06', '2024-06-05', 0),
                                                                                                        (23, '9782754811811', 'zelda@hyrule.com', 28, '2024-04-25', '2024-05-25', 0);
INSERT INTO library VALUES('login1','mdp1');
ALTER TABLE library MODIFY password VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin;
ALTER TABLE library MODIFY login VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin;