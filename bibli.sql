CREATE TABLE User(mail VARCHAR(50) PRIMARY KEY,
					name VARCHAR(50),
					first_name VARCHAR(50),
					age INT,
					adress VARCHAR(100),
					number INT,
					number_borrow INT);
					
CREATE TABLE Book(ISBN INT PRIMARY KEY,
					title VARCHAR(50),
					kind VARCHAR(50),
					author VARCHAR(50),
					picture VARCHAR(100),
					stock INT,
					time_borrow INT DEFAULT 30);
					
CREATE TABLE Loan(idLoan INT AUTO_INCREMENT PRIMARY KEY,
					idBook INT,
					idUser VARCHAR(50),
					duration INT,
					start_date DATE,
					end_date DATE,
					late INT DEFAULT 0,
					FOREIGN KEY (idBook) REFERENCES Book(ISBN),
					FOREIGN KEY (idUser) REFERENCES User(mail));
					
CREATE TABLE Library(login VARCHAR(50) PRIMARY KEY,
						password VARCHAR(50));
					