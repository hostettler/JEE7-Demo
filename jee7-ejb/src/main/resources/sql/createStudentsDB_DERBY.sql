CONNECT 'jdbc:derby://localhost:1527/StudentsDB;user=DEV;create=true';

DROP TABLE STUDENTS;
DROP TABLE PICTURES;
DROP TABLE GRADES;
DROP TABLE BADGES;

CREATE TABLE STUDENTS (
ID BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1000, INCREMENT BY 1)  NOT NULL,
FIRST_NAME VARCHAR(35) NOT NULL, 
PHONE_NUMBER VARCHAR(14), 
BIRTH_DATE DATE NOT NULL, 
LAST_NAME VARCHAR(35), 
NUMBER VARCHAR(255), 
CITY VARCHAR(255), 
STREET VARCHAR(255), 
POSTAL_CODE VARCHAR(255), 
PRIMARY KEY (ID)
);


CREATE TABLE PICTURES (
ID BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1000, INCREMENT BY 1) NOT NULL, 
PICTURE BLOB(2147483647),
STUDENT_ID BIGINT, 
PRIMARY KEY (ID));

CREATE TABLE GRADES (
ID BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1000, INCREMENT BY 1) NOT NULL, 
DISCIPLINE INTEGER NOT NULL, 
GRADE INTEGER, 
STUDENT_ID BIGINT, 
PRIMARY KEY (ID));

CREATE TABLE BADGES (
ID BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1000, INCREMENT BY 1) NOT NULL, 
SECURITY_LEVEL INTEGER NOT NULL,
STUDENT_ID BIGINT, 
PRIMARY KEY (ID));


ALTER TABLE PICTURES ADD CONSTRAINT FK_PICTURES_STUDENT_ID FOREIGN KEY (STUDENT_ID) REFERENCES STUDENTS (ID);
ALTER TABLE GRADES ADD CONSTRAINT FK_GRADES_STUDENTS FOREIGN KEY (STUDENT_ID) REFERENCES STUDENTS (ID);
ALTER TABLE BADGES ADD CONSTRAINT FK_BADGES_STUDENTS FOREIGN KEY (STUDENT_ID) REFERENCES STUDENTS (ID);



INSERT INTO STUDENTS VALUES(0, 'John', '000-000-000', '1965-12-10',  'Doe', '22', 'Downtown', 'wisteria street', '34343');

INSERT INTO GRADES VALUES(0, 0, 50, 0);
INSERT INTO GRADES VALUES(1, 1, 20, 0);
INSERT INTO GRADES VALUES(2, 2, 70, 0);
INSERT INTO GRADES VALUES(3, 3, 45, 0);

INSERT INTO GRADES VALUES(4, 0, 12, 1);
INSERT INTO GRADES VALUES(5, 1, 78, 1);
INSERT INTO GRADES VALUES(6, 2, 43, 1);
INSERT INTO GRADES VALUES(7, 3, 23, 1);

INSERT INTO BADGES VALUES(0,0,0);
INSERT INTO PICTURES VALUES(0,null,0);



INSERT INTO STUDENTS VALUES(1, 'Jane', '000-000-000', '1985-12-10',  'Doe', '22', 'Downtown', 'wisteria street', '34343');

INSERT INTO GRADES VALUES(4, 0, 12, 1);
INSERT INTO GRADES VALUES(5, 1, 78, 1);
INSERT INTO GRADES VALUES(6, 2, 43, 1);
INSERT INTO GRADES VALUES(7, 3, 23, 1);

INSERT INTO BADGES VALUES(1,0,1);
INSERT INTO PICTURES VALUES(1,null,1);


QUIT;