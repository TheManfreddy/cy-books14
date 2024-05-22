
INSERT INTO user values('albertroger@gmail.com','Albert','Roger','1960-06-24','156 rue des merles chanteurs,Paris','0656667686',2);
INSERT INTO borrow (isbn,idUser,duration,start_date,end_date,status) VALUES ('1234','albertroger@gmail.com',34,'2024-05-12','2024-06-12',0);
INSERT INTO library VALUES('login1','mdp1');
ALTER TABLE library MODIFY password VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin;
ALTER TABLE library MODIFY login VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin;