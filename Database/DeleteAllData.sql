USE PRJ301_TungLongDB_TEST_3
GO

-- Xóa dữ liệu bảng phụ trước (phụ thuộc FK)
DELETE FROM Tickets;
DELETE FROM ScheduleSeats;
DELETE FROM Bookings;
DELETE FROM SeatTemplates;

-- Xóa dữ liệu bảng Schedules sau khi đã xóa các bảng liên quan
DELETE FROM Schedules;

-- Xóa các bảng độc lập hoặc ít liên kết hơn
DELETE FROM Buses;
DELETE FROM BusTypes;
DELETE FROM Route;
DELETE FROM Location;
DELETE FROM Employee;


-- Cuối cùng xóa bảng User
DELETE FROM [User];

DBCC CHECKIDENT ('Tickets', RESEED, 0);
DBCC CHECKIDENT ('ScheduleSeats', RESEED, 0);
DBCC CHECKIDENT ('Bookings', RESEED, 0);
DBCC CHECKIDENT ('Schedules', RESEED, 0);
DBCC CHECKIDENT ('Buses', RESEED, 0);
DBCC CHECKIDENT ('BusTypes', RESEED, 0);
DBCC CHECKIDENT ('Route', RESEED, 0);
DBCC CHECKIDENT ('Location', RESEED, 0);
DBCC CHECKIDENT ('Employee', RESEED, 0);
DBCC CHECKIDENT ('SeatTemplates', RESEED, 0);

