-- Insertion des données dans la table user
INSERT INTO user (mail, name, first_name, birth_date, address, phone_number, number_borrow) VALUES
                                                                                                ('mario@nintendo.com', 'Mario', 'Mario', '1981-07-09', 'Mushroom Kingdom', '0678123456', 2),
                                                                                                ('luigi@nintendo.com', 'Luigi', 'Mario', '1983-07-14', 'Mushroom Kingdom', '0678234567', 1),
                                                                                                ('link@hyrule.com', 'Link', '', '1986-02-21', 'Hyrule', '0678345678', 1),
                                                                                                ('zelda@hyrule.com', 'Zelda', '', '1986-02-21', 'Hyrule', '0678456789', 2),
                                                                                                ('samus@metroid.com', 'Samus', 'Aran', '1986-08-06', 'Galactic Federation', '0678567890', 5),
                                                                                                ('kirby@popstar.com', 'Kirby', '', '1992-04-27', 'Dream Land', '0678678901', 3),
                                                                                                ('pikachu@pokemon.com', 'Pikachu', '', '1996-02-27', 'Pallet Town', '0678789012', 3),
                                                                                                ('ash@pokemon.com', 'Ash', 'Ketchum', '1996-02-27', 'Pallet Town', '0678901234', 2),
                                                                                                ('goku@dbz.com', 'Goku', 'Son', '1984-12-03', 'Earth', '0679012345', 3),
                                                                                                ('vegeta@dbz.com', 'Vegeta', '', '1988-02-26', 'Planet Vegeta', '0670123456', 1),
                                                                                                ('naruto@konoha.com', 'Naruto', 'Uzumaki', '1999-09-21', 'Konoha', '0671234567', 2),
                                                                                                ('sasuke@konoha.com', 'Sasuke', 'Uchiha', '1999-09-21', 'Konoha', '0672345678', 2),
                                                                                                ('sakura@konoha.com', 'Sakura', 'Haruno', '1999-09-21', 'Konoha', '0673456789', 1),
                                                                                                ('luffy@onepiece.com', 'Luffy', 'Monkey D.', '1997-07-22', 'East Blue', '0674567890', 1),
                                                                                                ('zoro@onepiece.com', 'Zoro', 'Roronoa', '1997-07-22', 'East Blue', '0675678901', 1),
                                                                                                ('sanji@onepiece.com', 'Sanji', 'Vinsmoke', '1997-07-22', 'East Blue', '0676789012', 2),
                                                                                                ('usopp@onepiece.com', 'Usopp', '', '1997-07-22', 'East Blue', '0677890123', 1),
                                                                                                ('tanjiro@demon.com', 'Tanjiro', 'Kamado', '2016-02-15', 'Mount Kumotori', '0678901234', 1),
                                                                                                ('nezuko@demon.com', 'Nezuko', 'Kamado', '2016-02-15', 'Mount Kumotori', '0679012345', 0),
                                                                                                ('eren@attack.com', 'Eren', 'Yeager', '2009-09-09', 'Shiganshina', '0670123456', 1);

-- Insertion des données dans la table borrow
INSERT INTO borrow (isbn, idUser, duration, start_date, end_date, status) VALUES
                                                                              ('9782723488525', 'mario@nintendo.com', 27, '2024-04-25', '2024-05-25', 0),
                                                                              ('9782723478991', 'mario@nintendo.com', 11, '2024-05-11', '2024-06-10', 0),
                                                                              ('9782723488525', 'luigi@nintendo.com', 37, '2024-03-12', '2024-04-11', 1),
                                                                              ('9782723460606', 'luigi@nintendo.com', 10, '2024-05-12', '2024-06-11', 0),
                                                                              ('9782723488525', 'link@hyrule.com', 34, '2024-04-18', '2024-05-18', 0),
                                                                              ('9782723460606', 'link@hyrule.com', 9, '2024-03-12', '2024-04-11', 1),
                                                                              ('9782723498234', 'zelda@hyrule.com', 36, '2024-04-16', '2024-05-16', 0),
                                                                              ('9782344004319', 'zelda@hyrule.com', 8, '2024-05-14', '2024-06-13', 0),
                                                                              ('9782723478991', 'samus@metroid.com', 27, '2024-04-20', '2024-05-20', 1),
                                                                              ('9782344006597', 'samus@metroid.com', 6, '2024-05-16', '2024-06-15', 0),
                                                                              ('9782723484138', 'samus@metroid.com', 33, '2024-04-19', '2024-05-19', 0),
                                                                              ('9782723495646', 'samus@metroid.com', 6, '2024-05-16', '2024-06-15', 0),
                                                                              ('9782723454193', 'samus@metroid.com', 23, '2024-04-29', '2024-05-29', 0),
                                                                              ('9782723470353', 'samus@metroid.com',4, '2024-05-18', '2024-06-17', 0),
                                                                              ('9782505017653', 'samus@metroid.com',17, '2024-05-01', '2024-05-31', 1),
                                                                              ('9782505065067', 'kirby@popstar.com', 35, '2024-04-17', '2024-05-17', 0),
                                                                              ('9782505015529', 'kirby@popstar.com', 26, '2024-04-26', '2024-05-26', 0),
                                                                              ('9782505004172', 'kirby@popstar.com', 6, '2024-04-10', '2024-04-16', 1),
                                                                              ('9782723460606', 'kirby@popstar.com', 26, '2024-04-26', '2024-05-26', 0),
                                                                              ('9782505004172', 'pikachu@pokemon.com', 15, '2024-05-03', '2024-06-02', 0),
                                                                              ('9782505007111', 'pikachu@pokemon.com', 25, '2024-04-27', '2024-05-27', 1),
                                                                              ('9782723478991', 'pikachu@pokemon.com', 35, '2024-04-17', '2024-05-17', 0),
                                                                              ('9782505015529', 'pikachu@pokemon.com', 25, '2024-04-27', '2024-05-27', 0),
                                                                              ('9782505063568', 'ash@pokemon.com', 25, '2024-04-27', '2024-04-27', 1),
                                                                              ('9782505007111', 'ash@pokemon.com', 24, '2024-04-28', '2024-05-28', 0),
                                                                              ('9782505006701', 'ash@pokemon.com', 4, '2024-05-18', '2024-06-17', 0),
                                                                              ('9782723460606', 'ash@pokemon.com', 34, '2024-03-18', '2024-04-21', 1),
                                                                              ('9782723460606', 'goku@dbz.com', 3, '2024-05-19', '2024-06-18', 0),
                                                                              ('9782723498234', 'goku@dbz.com', 23, '2024-04-29', '2024-05-29', 0),
                                                                              ('9782344004319', 'goku@dbz.com', 25, '2024-02-19', '2024-03-15', 1),
                                                                              ('9782723470353', 'goku@dbz.com', 23, '2024-04-29', '2024-05-29', 0),
                                                                              ('9782505015529', 'vegeta@dbz.com', 2, '2024-05-20', '2024-06-19', 0),
                                                                              ('9782723498234', 'vegeta@dbz.com', 25, '2024-03-17', '2024-04-16', 1),
                                                                              ('9782723495646', 'naruto@konoha.com', 45, '2024-04-07', '2024-05-07', 0),
                                                                              ('9782505004172', 'naruto@konoha.com', 21, '2024-05-01', '2024-05-31', 0),
                                                                              ('9782505061267', 'sasuke@konoha.com', 21, '2024-03-22', '2024-04-12', 1),
                                                                              ('9782505006701', 'sasuke@konoha.com', 13, '2024-05-09', '2024-06-08', 0),
                                                                              ('9782723478991', 'sakura@konoha.com', 38, '2024-04-14', '2024-05-14', 0),
                                                                              ('9782723498234', 'luffy@onepiece.com', 42, '2024-01-23', '2024-03-05', 1),
                                                                              ('9782505061267', 'luffy@onepiece.com', 35, '2024-04-17', '2024-05-17', 0),
                                                                              ('9782723495646', 'zoro@onepiece.com', 2, '2024-05-20', '2024-06-19', 0),
                                                                              ('9782505017653', 'zoro@onepiece.com', 18, '2024-05-04', '2024-06-03', 1),
                                                                              ('9782505063568', 'sanji@onepiece.com', 21, '2024-05-01', '2024-05-31', 0),
                                                                              ('9782344006597', 'sanji@onepiece.com', 17, '2024-05-05', '2024-06-04', 0),
                                                                              ('9782723478991', 'usopp@onepiece.com', 32, '2024-03-26', '2024-04-27', 1),
                                                                              ('9782505061267', 'usopp@onepiece.com', 16, '2024-05-06', '2024-06-05', 0),
                                                                              ('9782723470353', 'tanjiro@demon.com', 14, '2024-05-08', '2024-06-07', 0),
                                                                              ('9782505007111', 'nezuko@demon.com', 36, '2024-01-05', '2024-02-10', 1),
                                                                              ('9782505065067', 'eren@attack.com', 12, '2024-05-10', '2024-06-09', 0);


INSERT INTO library VALUES('login1','mdp1');
ALTER TABLE library MODIFY password VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin;
ALTER TABLE library MODIFY login VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin;