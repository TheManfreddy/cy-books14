DROP TABLE Library;
DROP TABLE Loan;
DROP TABLE Book;
DROP TABLE User;

CREATE TABLE User(mail VARCHAR(50) PRIMARY KEY,
					name VARCHAR(50),
					first_name VARCHAR(50),
					date_birth DATE,
					adress VARCHAR(100),
					number INT,
					number_borrow INT);
					
CREATE TABLE Book(ISBN INT PRIMARY KEY,
					title VARCHAR(50),
					kind VARCHAR(50),
					author VARCHAR(50),
					editor VARCHAR(50),
					picture VARCHAR(100),
					language VARCHAR(50),
					release_year VARCHAR(50),
					stock INT);
					
CREATE TABLE Loan(idLoan INT AUTO_INCREMENT PRIMARY KEY,
					idBook INT,
					idUser VARCHAR(50),
					duration INT,
					start_date DATE,
					end_date DATE,
					FOREIGN KEY (idBook) REFERENCES Book(ISBN),
					FOREIGN KEY (idUser) REFERENCES User(mail));
					
CREATE TABLE Library(login VARCHAR(50) PRIMARY KEY,
						password VARCHAR(50));
					