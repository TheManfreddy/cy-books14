-- Insertion des données dans la table user
INSERT INTO user (mail, name, first_name, birth_date, address, phone_number, number_borrow) VALUES
                                                                                                ('mario@nintendo.com', 'Mario', 'Mario', '1981-07-09', 'Mushroom Kingdom', '0678123456', 0),
                                                                                                ('luigi@nintendo.com', 'Luigi', 'Mario', '1983-07-14', 'Mushroom Kingdom', '0678234567', 0),
                                                                                                ('link@hyrule.com', 'Link', '', '1986-02-21', 'Hyrule', '0678345678', 0),
                                                                                                ('zelda@hyrule.com', 'Zelda', '', '1986-02-21', 'Hyrule', '0678456789', 0),
                                                                                                ('samus@metroid.com', 'Samus', 'Aran', '1986-08-06', 'Galactic Federation', '0678567890', 0),
                                                                                                ('kirby@popstar.com', 'Kirby', '', '1992-04-27', 'Dream Land', '0678678901', 0),
                                                                                                ('pikachu@pokemon.com', 'Pikachu', '', '1996-02-27', 'Pallet Town', '0678789012', 0),
                                                                                                ('ash@pokemon.com', 'Ash', 'Ketchum', '1996-02-27', 'Pallet Town', '0678901234', 0),
                                                                                                ('goku@dbz.com', 'Goku', 'Son', '1984-12-03', 'Earth', '0679012345', 0),
                                                                                                ('vegeta@dbz.com', 'Vegeta', '', '1988-02-26', 'Planet Vegeta', '0670123456', 0),
                                                                                                ('naruto@konoha.com', 'Naruto', 'Uzumaki', '1999-09-21', 'Konoha', '0671234567', 0),
                                                                                                ('sasuke@konoha.com', 'Sasuke', 'Uchiha', '1999-09-21', 'Konoha', '0672345678', 0),
                                                                                                ('sakura@konoha.com', 'Sakura', 'Haruno', '1999-09-21', 'Konoha', '0673456789', 0),
                                                                                                ('luffy@onepiece.com', 'Luffy', 'Monkey D.', '1997-07-22', 'East Blue', '0674567890', 0),
                                                                                                ('zoro@onepiece.com', 'Zoro', 'Roronoa', '1997-07-22', 'East Blue', '0675678901', 0),
                                                                                                ('sanji@onepiece.com', 'Sanji', 'Vinsmoke', '1997-07-22', 'East Blue', '0676789012', 0),
                                                                                                ('usopp@onepiece.com', 'Usopp', '', '1997-07-22', 'East Blue', '0677890123', 0),
                                                                                                ('tanjiro@demon.com', 'Tanjiro', 'Kamado', '2016-02-15', 'Mount Kumotori', '0678901234', 0),
                                                                                                ('nezuko@demon.com', 'Nezuko', 'Kamado', '2016-02-15', 'Mount Kumotori', '0679012345', 0),
                                                                                                ('eren@attack.com', 'Eren', 'Yeager', '2009-09-09', 'Shiganshina', '0670123456', 0);


INSERT INTO library VALUES('login1','mdp1');
ALTER TABLE library MODIFY password VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin;
ALTER TABLE library MODIFY login VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin;