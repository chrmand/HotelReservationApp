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

/*
,
ADD CONSTRAINT SYSDATE_or_future CHECK(
cRegisterDate IS NOT NULL
AND TO_CHAR(cRegisterDate, 'DD-MM-YYYY') > '19/03/2022') 
*/


CREATE TABLE BOOKING (
bID VARCHAR2(5) not null, bCheckIN DATE not null, bCheckOUT DATE not null, bPersons NUMBER(1), bNumOfDays NUMBER(2), roomID NUMBER (2) not null,
clientAFM NUMBER(9) not null,
PRIMARY KEY (bID),
FOREIGN KEY (roomID) REFERENCES ROOM(rID),
FOREIGN KEY (clientAFM) REFERENCES CLIENT(cAFM)
);


CREATE TABLE PAYMENT (
pType VARCHAR2(4) not null, pPrice NUMBER(4), pDescription CHAR(50), bookingID VARCHAR2(5) not null,
PRIMARY KEY (pType),
FOREIGN KEY (bookingID) REFERENCES BOOKING(bID)
);

/*
CREATE TABLE RESERVE (
cAFM NUMBER(9) not null, bID VARCHAR2(5) not null,
PRIMARY KEY (cAFM, bID),
FOREIGN KEY (cAFM) REFERENCES CLIENT(cAFM),
FOREIGN KEY (bID) REFERENCES BOOKING(bID)
);
*/
