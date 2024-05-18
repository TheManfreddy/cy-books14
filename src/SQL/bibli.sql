DROP TABLE if exists library;
DROP TABLE if exists borrow;
DROP TABLE if exists user;

CREATE TABLE user(mail VARCHAR(50) PRIMARY KEY,
					name VARCHAR(50),
					first_name VARCHAR(50),
				  	birth_date VARCHAR(10),
					address VARCHAR(100),
					phone_number VARCHAR(10),
					number_borrow INT);
					
CREATE TABLE borrow(idBorrow INT AUTO_INCREMENT PRIMARY KEY,
					isbn VARCHAR(50),-- changement de int en varchar car ISBN peut contenir des tirets ex : 2-7298-9646-5
					idUser VARCHAR(50),
					duration INT,
					start_date DATE,
					end_date DATE,
					status INT,
					FOREIGN KEY (idUser) REFERENCES User(mail));
					
CREATE TABLE library(login VARCHAR(50) PRIMARY KEY,
						password VARCHAR(50));
					