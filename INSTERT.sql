
----------------------------------------------------STORAGE INSERTS------------------------------------------------------------

INSERT INTO STORAGE (sID, sCleaningProduct, sLinenProduct)

VALUES (1, 'Gantia', 'Sentonia');

INSERT INTO STORAGE
VALUES (2, 'Aporypantika', 'Maksilaria');

INSERT INTO STORAGE
VALUES (3, 'Sakoules', 'Petsetes');


----------------------------------------------------EMPLOYEE INSERTS------------------------------------------------------------

INSERT INTO EMPLOYEE (eAFM, eFirstname, eLastname, eHireDate, eType, managesStorageID)

VALUES (123456789, 'Christos', 'Mandravelis', TO_DATE('19/03/2022'), 'A', 1);

INSERT INTO EMPLOYEE VALUES 
(123456799, 'Maria', 'Karavasili', TO_DATE('19/03/2022'), 'E', NULL);

INSERT INTO EMPLOYEE VALUES 
(223456789, 'Kostas', 'Kapas', TO_DATE('28/05/2022'), 'E', NULL);

INSERT INTO EMPLOYEE VALUES 
(223456719, 'Alexandra', 'Pasourh', TO_DATE('22/05/2022'), 'E', NULL);

INSERT INTO EMPLOYEE VALUES 
(223456700, 'Mixaela', 'Giasourh', TO_DATE('22/05/2022'), 'A', 1);

INSERT INTO EMPLOYEE VALUES 
(223456110, 'Lakis', 'Kamakaris', TO_DATE('22/06/2022'), 'E', NULL);


----------------------------------------------------CLIENT INSERTS------------------------------------------------------------
INSERT INTO CLIENT (cAFM, cFirstname, cLastname, cPhone, cCountry, cCity, cStreet, cNum, cTK)

VALUES (123111111, 'Sakis', 'Makritis', 698123320, 'Greece', 'Larisa', 'Kolokotronh', 5, 41001);

INSERT INTO CLIENT 
VALUES (123111112, 'Katerina', 'Nikou', 698123321, 'Greece', 'Kavala', 'Thasou', 12, 42002);

INSERT INTO CLIENT 
VALUES (123111113, 'Giannis', 'Emmanouhl', 698123322, 'Greece', 'Athina', 'Aridaia', 7, 43003);

INSERT INTO CLIENT 
VALUES (123111114, 'Kosmas', 'Margaritidis', 698123331, 'Greece', 'Thessalonikh', 'Ptolemaiou', 123, 44004);

INSERT INTO CLIENT 
VALUES (123111115, 'Alexia', 'Skalkh', 698123324, 'Greece', 'Lamia', 'Hpyrou', 65, 51009);

INSERT INTO CLIENT 
VALUES (123111116, 'Zoi', 'Grafidou', 698123325, 'Greece', 'Volos', 'Agias', 8, 52009);


----------------------------------------------------LOGIN INSERTS------------------------------------------------------------

INSERT INTO LOGIN (lAFM, lPassword, lType, employeeAFM)

VALUES (123456789, 'chris123', 'admin', 123456789);

INSERT INTO LOGIN VALUES 
(123456799, 'maria123', 'employee', 123456799);

INSERT INTO LOGIN VALUES 
(223456789, 'kostas123', 'employee', 223456789);

INSERT INTO LOGIN VALUES 
(223456110, 'lakis123', 'employee', 223456110);



----------------------------------------------------ROOM INSERTS----------------------------------------------------------------

INSERT INTO ROOM (rID, rType, rBeds, rPrice, rStatus)

VALUES (01, 'Monoklino' , 1, '30', 'Not Booked');

INSERT INTO ROOM VALUES (02, 'Monoklino', 1, '30', 'Not Booked');

INSERT INTO ROOM VALUES (03, 'Monoklino', 1, '30', 'Not Booked');

INSERT INTO ROOM VALUES (04, 'Monoklino', 1, '30', 'Not Booked');

INSERT INTO ROOM VALUES (05, 'Monoklino', 1, '30', 'Not Booked');

INSERT INTO ROOM VALUES (06, 'Diklino', 2, '40', 'Not Booked');

INSERT INTO ROOM VALUES (07, 'Diklino', 2, '40', 'Not Booked');

INSERT INTO ROOM VALUES (08, 'Diklino', 2, '40', 'Not Booked');

INSERT INTO ROOM VALUES (09, 'Diklino', 2, '40', 'Not Booked');

INSERT INTO ROOM VALUES (10, 'Diklino', 2, '40', 'Not Booked');

INSERT INTO ROOM VALUES (11, 'Triklino', 3, '50', 'Not Booked');

INSERT INTO ROOM VALUES (12, 'Triklino', 3, '50', 'Not Booked');

INSERT INTO ROOM VALUES (13, 'Triklino', 3, '50', 'Not Booked');

INSERT INTO ROOM VALUES (14, 'Triklino', 3, '50', 'Not Booked');

INSERT INTO ROOM VALUES (15, 'Triklino', 3, '50', 'Not Booked');

----------------------------------------------------BOOKING INSERTS------------------------------------------------------------

INSERT INTO BOOKING (bID, bCheckIN, bCheckOUT, bPersons, bNumOfDays, roomID, clientAFM)

VALUES ('1', TO_DATE('19/03/2022'), TO_DATE('29/03/2022'), 4, 10, 01, 123111111);

INSERT INTO BOOKING
VALUES ('2', TO_DATE('25/04/2022'), TO_DATE('30/04/2022'), 2, 5, 02, 123111112);

INSERT INTO BOOKING
VALUES ('3', TO_DATE('25/06/2022'), TO_DATE('05/07/2022'), 2, 10, 02, 123111113);


----------------------------------------------------PAYMENT INSERTS------------------------------------------------------------

INSERT INTO PAYMENT (pType, pPrice, pDescription, bookingID)

VALUES ('Cash', 50, '', '1');

INSERT INTO PAYMENT 
VALUES ('Visa', 50, 'Payment on check-OUT', '2');


----------------------------------------------------REGISTER INSERTS------------------------------------------------------------

INSERT INTO REGISTER (eAFM, cAFM, cRegisterDate)

VALUES (123456789, 123111111, '19/03/2022');

INSERT INTO REGISTER
VALUES (123456789, 123111116, '20/03/2022');

INSERT INTO REGISTER
VALUES (123456799, 123111112, '20/03/2022');

INSERT INTO REGISTER
VALUES (123456799, 123111113, '25/03/2022');

INSERT INTO REGISTER
VALUES (223456789, 123111114, '25/03/2022'); 

INSERT INTO REGISTER
VALUES (223456789, 123111115, '25/03/2022');

----------------------------------------------------RESERVE INSERTS------------------------------------------------------------
/*
INSERT INTO RESERVE (cAFM, bID)

VALUES (123111111, '00001');


INSERT INTO RESERVE 
VALUES (123111112, '00002');

*/









