
DROP TABLE EMPLOYEE;
DROP TABLE BOOKING;
DROP TABLE ROOM;
DROP TABLE CLIENT;
DROP TABLE STORAGE;



/*
CREATE TABLE EMPLOYEE (
eAFM NUMBER(9) not null, eFirstname VARCHAR2(25) not null, eLastname VARCHAR2(25) not null, 
eCheckIN DATE not null, eCheckOUT DATE not null,
PRIMARY KEY (eAFM)
);

INSERT INTO EMPLOYEE (eAFM, eFirstname, eLastname, eCheckIN, eCheckOUT)

VALUES (123456789, 'Christos', 'Mandravelis', TO_DATE('10/05/2022'), TO_DATE('16/05/2022'));

SELECT * FROM EMPLOYEE;
DROP TABLE EMPLOYEE;
commit;

INSERT INTO EMPLOYEE(eAFM, eFirstname, eLastname, eCheckIN, eCheckOUT) VALUES(123111111, 'Christos', 'Mandravelis', TO_DATE('Thu Jan 01 00:00:00 EET 1970','DD/MM/YYYY'),  TO_DATE('Thu Jan 01 18:22:49 EET 1970','DD/MM/YYYY'));

*/
