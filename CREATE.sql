CREATE TABLE STORAGE (
sID NUMBER(2) not null, sCleaningProduct VARCHAR2(25) not null, sLinenProduct VARCHAR2(25) not null,
PRIMARY KEY (sID)
);

CREATE TABLE EMPLOYEE (
eAFM NUMBER(9) not null, eFirstname VARCHAR2(25) not null, eLastname VARCHAR2(25) not null, eHireDate DATE not null, eType VARCHAR2(1) not null, managesStorageID NUMBER(1),
PRIMARY KEY (eAFM), 
FOREIGN KEY (managesStorageID) REFERENCES STORAGE(sID),
CONSTRAINT eType check (eType='E' OR eType='A') 
);

CREATE TABLE CLIENT (
cAFM NUMBER(9) not null, cFirstname VARCHAR2(25) not null, cLastname VARCHAR2(25) not null, cPhone NUMBER(10) not null, cCountry VARCHAR2(25) not null, 
cCity VARCHAR2(25) not null, cStreet VARCHAR2(25) not null, cNum NUMBER(3) not null, cTK NUMBER(5) not null, 
PRIMARY KEY (cAFM)
);


CREATE TABLE LOGIN (
lAFM NUMBER(9) not null,  lPassword VARCHAR2(25) not null, lType VARCHAR2(8) not null, employeeAFM NUMBER(9) not null,
PRIMARY KEY (lAFM),
FOREIGN KEY (employeeAFM) REFERENCES EMPLOYEE(eAFM),
CONSTRAINT lType check (lType='employee' OR lType='admin'),
CONSTRAINT lAFM check (lAFM = employeeAFM) 
);


/*
CREATE TABLE LOGIN (
lAFM NUMBER(9) not null,  lPassword VARCHAR2(25) not null, lType VARCHAR2(8) not null,
PRIMARY KEY (lAFM)
);

CREATE TABLE LOGIN (
lAFM VARCHAR2(9) not null,  lPassword VARCHAR2(25) not null, lType VARCHAR2(8) not null,
PRIMARY KEY (lAFM)
);

INSERT INTO LOGIN (lAFM, lPassword, lType)

VALUES (123456789, 'chris123', 'admin');

INSERT INTO LOGIN VALUES 
(123456799, 'maria123', 'employee');
*/


CREATE TABLE ROOM (
rID NUMBER(2) not null, rType VARCHAR2(25) not null, rBeds NUMBER(2),  rPrice VARCHAR2(10), rStatus VARCHAR2(25),
PRIMARY KEY (rID)
);


CREATE TABLE REGISTER (
eAFM NUMBER(9) not null, cAFM NUMBER(9) not null, cRegisterDate DATE ,
PRIMARY KEY (eAFM, cAFM),
FOREIGN KEY (eAFM) REFERENCES EMPLOYEE(eAFM),
FOREIGN KEY (cAFM) REFERENCES CLIENT(cAFM)
);



CREATE TABLE BOOKING (
bID VARCHAR2(5),  clientAFM NUMBER(9),
bFirstname VARCHAR2(25), bLastname VARCHAR2(25), bPhone NUMBER(10),
bCheckIN DATE, bPersons NUMBER(1),
roomID NUMBER (2), bRoomType VARCHAR2(12), bRoomBeds NUMBER (1),
bPrice NUMBER(5), bNumOfDays NUMBER(2), bTotalAmount NUMBER(7),  bCheckOUT DATE, 
PRIMARY KEY (bID),
FOREIGN KEY (roomID) REFERENCES ROOM(rID),
FOREIGN KEY (clientAFM) REFERENCES CLIENT(cAFM)
);

/*
CREATE TABLE PAYMENT (
pID NUMBER(3) not null, pAFM NUMBER(9),
pFirstname VARCHAR2(25) not null, pLastname VARCHAR2(25) not null, pPhone NUMBER(10) not null,
pCheckIN DATE, pCheckOUT DATE not null, pPersons NUMBER(1), 
pNumOfDays NUMBER(2),pRoomID NUMBER(2) not null, pPricePerDay NUMBER(5) not null, 
pTotalAmount NUMBER(7), bookingID VARCHAR2(5) not null,
PRIMARY KEY (pID),
FOREIGN KEY (bookingID) REFERENCES BOOKING(bID)
);
*/

/*
INSERT INTO PAYMENT (pID,pAFM,pFirstname,pLastname,pPhone,pCheckIN,pCheckOUT,pPersons,pNumOfDays,pRoomID,pPricePerDay,pTotalAmount,bookingID)
VALUES (1, 123111111, 'Sakis', 'Makritis', 698123320, TO_DATE('19/03/2022'), TO_DATE('29/03/2022'), 4, 10, 01, 30, 300, '1');

INSERT INTO PAYMENT 
VALUES (2, 123111112, 'Katerina', 'Nikou', 698123321, TO_DATE('25/04/2022'), TO_DATE('30/04/2022'), 2, 5, 06, 40, 200, '2');


DROP TABLE PAYMENT;


SELECT PAYMENT.pID, PAYMENT.pCheckIN, PAYMENT.pCheckOUT, PAYMENT.pTotalAmount,  PAYMENT.pRoomID, CLIENT.cFirstname, CLIENT.cLastname, CLIENT.cPhone
FROM PAYMENT
FULL OUTER JOIN CLIENT ON PAYMENT.pAFM=CLIENT.cAFM
ORDER BY PAYMENT.pID;

SELECT * FROM PAYMENT;
*/

--pType VARCHAR2(4) not null // ** CASH or VISA **
--pDescription CHAR(50) // ** Metaferthike giati den ths arese to dwmatio ** 
/*
CREATE TABLE RESERVE (
cAFM NUMBER(9) not null, bID VARCHAR2(5) not null,
PRIMARY KEY (cAFM, bID),
FOREIGN KEY (cAFM) REFERENCES CLIENT(cAFM),
FOREIGN KEY (bID) REFERENCES BOOKING(bID)
);
*/
