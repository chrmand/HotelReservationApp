--SELECT * FROM LOGIN;
SELECT * FROM EMPLOYEE;
SELECT * FROM CLIENT;
SELECT * FROM BOOKING;
SELECT * FROM ROOM;
SELECT * FROM STORAGE;


UPDATE ROOM  SET rStatus= 'Not Booked' ;

DELETE BOOKING WHERE bID= '1';

DELETE BOOKING WHERE roomID= 6;

DELETE PAYMENT WHERE pID= '1';
DELETE PAYMENT WHERE pID= '2';

--SELECT ~��� ��������� ��� ������ ��������, �� ���� ������� ������, �� ���, �����, �������, �������� ��� ������.
SELECT BOOKING.bID, ROOM.rID, BOOKING.clientAFM, BOOKING.bFirstname, BOOKING.bLastname, BOOKING.bPhone
FROM BOOKING
FULL OUTER JOIN ROOM ON BOOKING.roomID=ROOM.rID
ORDER BY BOOKING.bID;


UPDATE ROOM SET rStatus= 'Booked' WHERE rID= 1;
UPDATE ROOM SET rStatus= 'Not Booked' WHERE rID= 1;


SELECT * FROM ROOM  WHERE rStatus= 'Not Booked';
SELECT * FROM ROOM  WHERE rStatus= 'Booked';
