DROP TABLE if exists library;
DROP TABLE if exists borrow;
DROP TABLE if exists user;

CREATE TABLE user(mail VARCHAR(50) PRIMARY KEY,
					name VARCHAR(50),
					first_name VARCHAR(50),
					date_birth DATE,
					adress VARCHAR(100),
					number INT,
					number_borrow INT);
					
CREATE TABLE borrow(idLoan INT AUTO_INCREMENT PRIMARY KEY,
					isbn INT,
					idUser VARCHAR(50),
					duration INT,
					start_date DATE,
					end_date DATE,
					FOREIGN KEY (idUser) REFERENCES User(mail));
					
CREATE TABLE library(login VARCHAR(50) PRIMARY KEY,
						password VARCHAR(50));
					