
INSERT INTO user (mail, name, first_name, birth_date, address, phone_number, number_borrow) VALUES
                                                                                                ('mario@nintendo.com', 'Mario', 'Mario', '1980-07-09', 'Mushroom Kingdom, World 1-1', '123-456-7890', 0),
                                                                                                ('snow.white@fairytale.com', 'Blanche', 'Neige', '1937-02-04', 'Cottage, Enchanted Forest', '123-456-7891', 0),
                                                                                                ('zoro@onepiece.com', 'Roronoa', 'Zoro', '1997-08-04', 'Going Merry, East Blue', '123-456-7892', 3),
                                                                                                ('dora@explorer.com', 'Dora', 'Marquez', '2000-08-14', 'Backpack Land', '123-456-7893', 2),
                                                                                                ('homer@springfield.com', 'Homer', 'Simpson', '1956-05-12', '742 Evergreen Terrace, Springfield', '123-456-7894', 3),
                                                                                                ('mickey@disney.com', 'Mickey', 'Mouse', '1928-11-18', 'Disneyland, USA', '123-456-7895', 2),
                                                                                                ('minnie@disney.com', 'Minnie', 'Mouse', '1928-11-18', 'Disneyland, USA', '123-456-7896', 1),
                                                                                                ('sponge.bob@bikini.com', 'SpongeBob', 'SquarePants', '1999-05-01', '124 Conch Street, Bikini Bottom', '123-456-7897', 3),
                                                                                                ('patrick@bikini.com', 'Patrick', 'Star', '1999-05-01', '120 Conch Street, Bikini Bottom', '123-456-7898', 2),
                                                                                                ('elsa@arendelle.com', 'Elsa', 'Arendelle', '2013-11-27', 'Arendelle Castle', '123-456-7899', 2),
                                                                                                ('anna@arendelle.com', 'Anna', 'Arendelle', '2013-11-27', 'Arendelle Castle', '123-456-7800', 3),
                                                                                                ('batman@gotham.com', 'Bruce', 'Wayne', '1939-05-01', 'Wayne Manor, Gotham', '123-456-7801', 0),
                                                                                                ('superman@krypton.com', 'Clark', 'Kent', '1938-06-01', 'Smallville, Kansas', '123-456-7802', 0),
                                                                                                ('wonder.woman@amazon.com', 'Diana', 'Prince', '1941-12-01', 'Themyscira', '123-456-7803', 0),
                                                                                                ('harry.potter@hogwarts.com', 'Harry', 'Potter', '1980-07-31', '4 Privet Drive, Little Whinging', '123-456-7804', 0),
                                                                                                ('hermione@hogwarts.com', 'Hermione', 'Granger', '1979-09-19', 'Hampstead, London', '123-456-7805', 0),
                                                                                                ('ron@hogwarts.com', 'Ron', 'Weasley', '1980-03-01', 'The Burrow, Ottery St Catchpole', '123-456-7806', 0),
                                                                                                ('olaf@arendelle.com', 'Olaf', 'Snowman', '2013-11-27', 'Arendelle Castle', '123-456-7807', 1),
                                                                                                ('mulan@china.com', 'Fa', 'Mulan', '1998-06-19', 'Ancient China', '123-456-7808', 0),
                                                                                                ('tiana@neworleans.com', 'Tiana', 'Williams', '2009-12-11', 'New Orleans', '123-456-7809', 0),
                                                                                                ('simba@pridelands.com', 'Simba', 'Lion', '1994-06-24', 'Pride Lands, Africa', '123-456-7810', 0),
                                                                                                ('nemo@ocean.com', 'Nemo', 'Fish', '2003-05-30', 'Great Barrier Reef', '123-456-7811', 0),
                                                                                                ('buzz@toyworld.com', 'Buzz', 'Lightyear', '1995-11-22', 'Andys Room', '123-456-7812', 0),
                                                                                                ('woody@toyworld.com', 'Woody', 'Pride', '1995-11-22', 'Andys Room', '123-456-7813', 0),
                                                                                                ('shrek@swamp.com', 'Shrek', 'Ogre', '2001-05-18', 'Swamp, Far Far Away', '123-456-7814', 0),
                                                                                                ('fiona@swamp.com', 'Fiona', 'Ogre', '2001-05-18', 'Swamp, Far Far Away', '123-456-7815', 0),
                                                                                                ('donkey@swamp.com', 'Donkey', 'Ass', '2001-05-18', 'Swamp, Far Far Away', '123-456-7816', 0),
                                                                                                ('moana@motunui.com', 'Moana', 'Waialiki', '2016-11-23', 'Motunui Island', '123-456-7817', 0),
                                                                                                ('ralph@wreckit.com', 'Wreck-It', 'Ralph', '2012-11-02', 'Litwaks Arcade', '123-456-7818', 0),
                                                                                                ('vanellope@sugar.com', 'Vanellope', 'von Schweetz', '2012-11-02', 'Sugar Rush', '123-456-7819', 0);


INSERT INTO INSERT INTO borrow (isbn, idUser, duration, start_date, end_date, status) VALUES
    ('9780439554930', 'zoro@onepiece.com', 0, '2024-05-01', '2024-05-31', 0),
    ('9780786849102', 'zoro@onepiece.com', 0, '2024-05-05', '2024-06-04', 0),
    ('9780786819884', 'zoro@onepiece.com', 0, '2024-05-10', '2024-06-09', 1),
    ('9780786838656', 'dora@explorer.com', 0, '2024-05-01', '2024-05-31', 1),
    ('9780544952669', 'dora@explorer.com', 0, '2024-05-10', '2024-06-09', 0),
    ('9780142407332', 'homer@springfield.com', 0, '2024-05-05', '2024-06-04', 1),
    ('9780736438659', 'homer@springfield.com', 0, '2024-05-10', '2024-06-09', 1),
    ('9780545162074', 'homer@springfield.com', 0, '2024-05-15', '2024-06-14', 0),
    ('9780394852947', 'mickey@disney.com', 0, '2024-05-05', '2024-06-04', 0),
    ('9781442468351', 'mickey@disney.com', 0, '2024-05-10', '2024-06-09', 1),
    ('9780736438659', 'minnie@disney.com', 0, '2024-05-10', '2024-06-09', 0),
    ('9781442468351', 'sponge.bob@bikini.com', 0, '2024-05-01', '2024-05-31', 1),
    ('9780142407332', 'sponge.bob@bikini.com', 0, '2024-05-10', '2024-06-09', 1),
    ('9780786849102', 'sponge.bob@bikini.com', 0, '2024-05-15', '2024-06-14', 0),
    ('9780736438659', 'patrick@bikini.com', 0, '2024-05-10', '2024-06-09', 0),
    ('9781442468351', 'patrick@bikini.com', 0, '2024-05-15', '2024-06-14', 0),
    ('9781442468351', 'elsa@arendelle.com', 0, '2024-05-15', '2024-06-14', 1),
    ('9780142407332', 'anna@arendelle.com', 0, '2024-05-10', '2024-06-09', 0),
    ('9780786819884', 'anna@arendelle.com', 0, '2024-05-15', '2024-06-14', 0),
    ('9780736438659', 'anna@arendelle.com', 0, '2024-05-20', '2024-06-19', 1);


INSERT INTO library VALUES('login1','mdp1');
ALTER TABLE library MODIFY password VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin;
ALTER TABLE library MODIFY login VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin;