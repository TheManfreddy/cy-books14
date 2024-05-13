INSERT INTO User values('albertroger@gmail.com','Albert','Roger',50,'156 rue des merles chanteurs,Paris',0656667686,2);
INSERT INTO Book (ISBN, title, kind, author, picture, stock) values (1234,'title1','kind1','author1','picture1.jpg',4);
INSERT INTO Loan (idBook,idUser,duration,start_date,end_date) values (1234,'albertroger@gmail.com',34,'2024-05-12','2024-06-12');
INSERT INTO Library values('login1','mdp1');

UPDATE Loan
SET late = 1
WHERE duration > 30;