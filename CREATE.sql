----------------------------------------------------CREATE------------------------------------------------------------------
CREATE TABLE STORAGE (
sStorageName VARCHAR2(25) not null, sID VARCHAR2(3) not null, sCategory VARCHAR2(25) not null, 
sProductName VARCHAR2(25) not null, sQuantity NUMBER(5) not null,
PRIMARY KEY (sID)
);


CREATE TABLE EMPLOYEE (
eAFM NUMBER(9) not null, eFirstname VARCHAR2(25) not null, eLastname VARCHAR2(25) not null, eHireDate DATE not null, 
PRIMARY KEY (eAFM)
);


CREATE TABLE CLIENT (
cAFM NUMBER(9) not null, cFirstname VARCHAR2(25) not null, cLastname VARCHAR2(25) not null, cPhone NUMBER(10) not null, 
cCountry VARCHAR2(25) not null, cCity VARCHAR2(25) not null, cStreet VARCHAR2(25) not null, 
cNum NUMBER(3) not null, cTK NUMBER(5) not null, 
PRIMARY KEY (cAFM)
);


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


