CREATE TABLE User(mail VARCHAR(50) PRIMARY KEY,
					name VARCHAR(50),
					first_name VARCHAR(50),
					adress VARCHAR(100),
					number INT,
					number_borrow INT);
CREATE TABLE Book(ISBN INT PRIMARY KEY,
					title VARCHAR(50),
					kind VARCHAR(50),
					author VARCHAR(50),
					time_borrow INT);
CREATE TABLE Loan(idLoan INT PRIMARY KEY,
					idBook INT,
					idUser VARCHAR(50),
					duration INT,
					date DATE,
					late INT,
					FOREIGN KEY (idBook) REFERENCES Book(ISBN),
					FOREIGN KEY (idUser) REFERENCES User(mail));
					