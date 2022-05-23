----------------------------------------------------STORAGE INSERTS------------------------------------------------------------

INSERT INTO STORAGE (sStorageName, sID, sCategory, sProductName, sQuantity)

VALUES ('Cleaning Storage', '100', 'Cleaning Product', 'Plastic Gloves', '25');

INSERT INTO STORAGE
VALUES ('Cleaning Storage', '101', 'Cleaning Product', 'Chlorine', '25');

INSERT INTO STORAGE
VALUES ('Cleaning Storage', '102', 'Cleaning Product', 'Garbage Bags', '450');

INSERT INTO STORAGE
VALUES ('Cleaning Storage', '103', 'Cleaning Product', 'Head Sampoo', '450');

INSERT INTO STORAGE
VALUES ('Cleaning Storage', '104', 'Cleaning Product', 'Body Sampoo', '450');

INSERT INTO STORAGE
VALUES ('Linen Storage', '200', 'Linen Product', 'Katosentono', '50');

INSERT INTO STORAGE
VALUES ('Linen Storage', '201', 'Linen Product', 'Panosentono', '50');

INSERT INTO STORAGE
VALUES ('Linen Storage', '203', 'Linen Product', 'Pillowcases', '50');

INSERT INTO STORAGE
VALUES ('Linen Storage', '204', 'Linen Product', 'Protective Bed Coatings', '50');

INSERT INTO STORAGE
VALUES ('Linen Storage', '205', 'Linen Product', 'Bath Towel', '50');

INSERT INTO STORAGE
VALUES ('Linen Storage', '206', 'Linen Product', 'Hand Towel', '50');


----------------------------------------------------EMPLOYEE INSERTS------------------------------------------------------------

INSERT INTO EMPLOYEE (eAFM, eFirstname, eLastname, eHireDate)

VALUES (123456789, 'Christos', 'Mandravelis', TO_DATE('19/03/2022'));

INSERT INTO EMPLOYEE VALUES 
(123456799, 'Maria', 'Karavasili', TO_DATE('19/03/2022'));

INSERT INTO EMPLOYEE VALUES 
(223456789, 'Kostas', 'Kapas', TO_DATE('28/05/2022'));

INSERT INTO EMPLOYEE VALUES 
(223456719, 'Alexandra', 'Pasourh', TO_DATE('22/05/2022'));

INSERT INTO EMPLOYEE VALUES 
(223456700, 'Mixaela', 'Giasourh', TO_DATE('22/05/2022'));

INSERT INTO EMPLOYEE VALUES 
(223456110, 'Lakis', 'Kamakaris', TO_DATE('22/06/2022'));


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

----------------------------------------------------ROOM INSERTS----------------------------------------------------------------

INSERT INTO ROOM (rID, rType, rBeds, rPrice, rStatus)

VALUES (01, 'Monoklino' , 1, '30', 'Booked');

INSERT INTO ROOM VALUES (02, 'Monoklino', 1, '30', 'Not Booked');

INSERT INTO ROOM VALUES (03, 'Monoklino', 1, '30', 'Not Booked');

INSERT INTO ROOM VALUES (04, 'Monoklino', 1, '30', 'Not Booked');

INSERT INTO ROOM VALUES (05, 'Monoklino', 1, '30', 'Not Booked');

INSERT INTO ROOM VALUES (06, 'Diklino', 2, '40', 'Booked');

INSERT INTO ROOM VALUES (07, 'Diklino', 2, '40', 'Not Booked');

INSERT INTO ROOM VALUES (08, 'Diklino', 2, '40', 'Not Booked');

INSERT INTO ROOM VALUES (09, 'Diklino', 2, '40', 'Not Booked');

INSERT INTO ROOM VALUES (10, 'Diklino', 2, '40', 'Not Booked');

INSERT INTO ROOM VALUES (11, 'Triklino', 3, '50', 'Booked');

INSERT INTO ROOM VALUES (12, 'Triklino', 3, '50', 'Not Booked');

INSERT INTO ROOM VALUES (13, 'Triklino', 3, '50', 'Not Booked');

INSERT INTO ROOM VALUES (14, 'Triklino', 3, '50', 'Not Booked');

INSERT INTO ROOM VALUES (15, 'Triklino', 3, '50', 'Not Booked');

----------------------------------------------------BOOKING INSERTS------------------------------------------------------------

INSERT INTO BOOKING (bID, clientAFM, bFirstname, bLastname, bPhone, bCheckIN, bPersons, roomID, bRoomType, bRoomBeds, bPrice, bNumOfDays, bTotalAmount, bCheckOUT)
VALUES ('1', 123111111, 'Sakis', 'Makritis', 698123320, TO_DATE('05/05/2022'), 1,  01, 'Monoklino', 1, 30, null, null, TO_DATE(''));

INSERT INTO BOOKING 
VALUES ('2', 123111112, 'Katerina', 'Nikou', 698123321, TO_DATE('02/05/2022'), 2,  06, 'Diklino', 2, 40, null, null, TO_DATE(''));

INSERT INTO BOOKING 
VALUES ('3', 123111113, 'Giannis', 'Emmanouhl', 698123322, TO_DATE('27/04/2022'), 3,  11, 'Triklino', 3, 50, null, null, TO_DATE(''));

commit;


