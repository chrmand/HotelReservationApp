/*CREATE TABLE STORAGE (
sID NUMBER(2) not null, sCleaningProduct VARCHAR2(25) not null, sLinenProduct VARCHAR2(25) not null,
PRIMARY KEY (sID)
);*/

--Right--
CREATE TABLE STORAGE (
sStorageName VARCHAR2(25) not null, sID VARCHAR2(3) not null, sCategory VARCHAR2(25) not null, sProductName VARCHAR2(25) not null, sQuantity NUMBER(5) not null,
--UNIQUE (sStorageName),
PRIMARY KEY (sID)
);


---RIGHT---
CREATE TABLE EMPLOYEE (
eAFM NUMBER(9) not null, eFirstname VARCHAR2(25) not null, eLastname VARCHAR2(25) not null, eHireDate DATE not null, 
PRIMARY KEY (eAFM)
);


/*
CREATE TABLE EMPLOYEE (
eAFM NUMBER(9) not null, eFirstname VARCHAR2(25) not null, eLastname VARCHAR2(25) not null, eHireDate DATE not null, eType VARCHAR2(1) not null, managesStorageCategory VARCHAR2(25),
PRIMARY KEY (eAFM), 
FOREIGN KEY (managesStorageCategory) REFERENCES STORAGE(sCategory),
CONSTRAINT eType check (eType='E' OR eType='A') 
);
*/

CREATE TABLE CLIENT (
cAFM NUMBER(9) not null, cFirstname VARCHAR2(25) not null, cLastname VARCHAR2(25) not null, cPhone NUMBER(10) not null, cCountry VARCHAR2(25) not null, 
cCity VARCHAR2(25) not null, cStreet VARCHAR2(25) not null, cNum NUMBER(3) not null, cTK NUMBER(5) not null, 
PRIMARY KEY (cAFM)
);

/*
CREATE TABLE LOGIN (
lAFM NUMBER(9) not null,  lPassword VARCHAR2(25) not null, lType VARCHAR2(8) not null, employeeAFM NUMBER(9) not null,
PRIMARY KEY (lAFM),
FOREIGN KEY (employeeAFM) REFERENCES EMPLOYEE(eAFM),
CONSTRAINT lType check (lType='employee' OR lType='admin'),
CONSTRAINT lAFM check (lAFM = employeeAFM) 
);
*/


CREATE TABLE ROOM (
rID NUMBER(2) not null, rType VARCHAR2(25) not null, rBeds NUMBER(2),  rPrice VARCHAR2(10), rStatus VARCHAR2(25),
PRIMARY KEY (rID)
);

CREATE TABLE BOOKING (
bID VARCHAR2(5) not null,  clientAFM NUMBER(9) not null,
bFirstname VARCHAR2(25) not null, bLastname VARCHAR2(25) not null, bPhone NUMBER(10) not null,
bCheckIN DATE not null, bPersons NUMBER(1) not null,
roomID NUMBER (2), bRoomType VARCHAR2(12), bRoomBeds NUMBER (1),
bPrice NUMBER(5), bNumOfDays NUMBER(2), bTotalAmount NUMBER(7),  bCheckOUT DATE, 
PRIMARY KEY (bID),
FOREIGN KEY (roomID) REFERENCES ROOM(rID),
FOREIGN KEY (clientAFM) REFERENCES CLIENT(cAFM)
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

CREATE TABLE LOGIN (
lAFM NUMBER(9) not null,  lPassword VARCHAR2(25) not null,
PRIMARY KEY (lAFM)
);

INSERT INTO LOGIN (lAFM, lPassword, lType)

VALUES (123456789, 'chris123', 'admin');

INSERT INTO LOGIN VALUES 
(123456799, 'maria123', 'employee');

INSERT INTO LOGIN (lAFM, lPassword)

VALUES (123456789, 'chris123');
*/


