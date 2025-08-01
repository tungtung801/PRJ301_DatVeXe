USE [master]
GO
/****** Object:  Database [PRJ301_TungLongDB_TEST_3]    Script Date: T3 - 08 - 07 - 2025 11:43 ******/
CREATE DATABASE [PRJ301_TungLongDB_TEST_3]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'PRJ301_TungLongDB_TEST_3', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\PRJ301_TungLongDB_TEST_3.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'PRJ301_TungLongDB_TEST_3_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\PRJ301_TungLongDB_TEST_3_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [PRJ301_TungLongDB_TEST_3] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [PRJ301_TungLongDB_TEST_3].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [PRJ301_TungLongDB_TEST_3] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [PRJ301_TungLongDB_TEST_3] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [PRJ301_TungLongDB_TEST_3] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [PRJ301_TungLongDB_TEST_3] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [PRJ301_TungLongDB_TEST_3] SET ARITHABORT OFF 
GO
ALTER DATABASE [PRJ301_TungLongDB_TEST_3] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [PRJ301_TungLongDB_TEST_3] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [PRJ301_TungLongDB_TEST_3] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [PRJ301_TungLongDB_TEST_3] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [PRJ301_TungLongDB_TEST_3] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [PRJ301_TungLongDB_TEST_3] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [PRJ301_TungLongDB_TEST_3] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [PRJ301_TungLongDB_TEST_3] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [PRJ301_TungLongDB_TEST_3] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [PRJ301_TungLongDB_TEST_3] SET  ENABLE_BROKER 
GO
ALTER DATABASE [PRJ301_TungLongDB_TEST_3] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [PRJ301_TungLongDB_TEST_3] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [PRJ301_TungLongDB_TEST_3] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [PRJ301_TungLongDB_TEST_3] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [PRJ301_TungLongDB_TEST_3] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [PRJ301_TungLongDB_TEST_3] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [PRJ301_TungLongDB_TEST_3] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [PRJ301_TungLongDB_TEST_3] SET RECOVERY FULL 
GO
ALTER DATABASE [PRJ301_TungLongDB_TEST_3] SET  MULTI_USER 
GO
ALTER DATABASE [PRJ301_TungLongDB_TEST_3] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [PRJ301_TungLongDB_TEST_3] SET DB_CHAINING OFF 
GO
ALTER DATABASE [PRJ301_TungLongDB_TEST_3] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [PRJ301_TungLongDB_TEST_3] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [PRJ301_TungLongDB_TEST_3] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [PRJ301_TungLongDB_TEST_3] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'PRJ301_TungLongDB_TEST_3', N'ON'
GO
ALTER DATABASE [PRJ301_TungLongDB_TEST_3] SET QUERY_STORE = OFF
GO
USE [PRJ301_TungLongDB_TEST_3]
GO
/****** Object:  Table [dbo].[Bookings]    Script Date: T3 - 08 - 07 - 2025 11:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Bookings](
	[BookingID] [int] IDENTITY(1,1) NOT NULL,
	[UserID] [varchar](10) NULL,
	[ScheduleID] [int] NULL,
	[BookingDate] [datetime] NULL,
	[TotalAmount] [decimal](10, 2) NULL,
	[Status] [nvarchar](20) NULL,
 CONSTRAINT [PK_Bookings] PRIMARY KEY CLUSTERED 
(
	[BookingID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Buses]    Script Date: T3 - 08 - 07 - 2025 11:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Buses](
	[BusID] [int] IDENTITY(1,1) NOT NULL,
	[BusNumber] [nvarchar](20) NOT NULL,
	[BusName] [nvarchar](100) NOT NULL,
	[BusTypeID] [int] NULL,
	[Description] [nvarchar](200) NULL,
	[Status] [nvarchar](20) NULL,
 CONSTRAINT [PK_Buses] PRIMARY KEY CLUSTERED 
(
	[BusID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BusTypes]    Script Date: T3 - 08 - 07 - 2025 11:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BusTypes](
	[BusTypeID] [int] IDENTITY(1,1) NOT NULL,
	[TypeName] [nvarchar](50) NOT NULL,
	[SeatCount] [int] NOT NULL,
	[Category] [nvarchar](20) NOT NULL,
	[Description] [nvarchar](200) NULL,
 CONSTRAINT [PK_BusTypes] PRIMARY KEY CLUSTERED 
(
	[BusTypeID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Employee]    Script Date: T3 - 08 - 07 - 2025 11:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Employee](
	[emp_id] [int] IDENTITY(1,1) NOT NULL,
	[emp_role] [nvarchar](50) NULL,
	[status] [nvarchar](50) NULL,
	[emp_name] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[emp_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Location]    Script Date: T3 - 08 - 07 - 2025 11:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Location](
	[location_id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](100) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[location_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Route]    Script Date: T3 - 08 - 07 - 2025 11:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Route](
	[route_id] [int] IDENTITY(1,1) NOT NULL,
	[departure] [nvarchar](100) NULL,
	[destination] [nvarchar](100) NULL,
	[distance_km] [float] NULL,
PRIMARY KEY CLUSTERED 
(
	[route_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Schedules]    Script Date: T3 - 08 - 07 - 2025 11:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Schedules](
	[ScheduleID] [int] IDENTITY(1,1) NOT NULL,
	[RouteID] [int] NULL,
	[BusID] [int] NULL,
	[DepartureTime] [datetime] NOT NULL,
	[ArrivalTime] [datetime] NOT NULL,
	[DriverID] [int] NULL,
	[AttendantID] [int] NULL,
	[Price] [decimal](10, 2) NOT NULL,
	[Status] [nvarchar](20) NULL,
 CONSTRAINT [PK_Schedules] PRIMARY KEY CLUSTERED 
(
	[ScheduleID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ScheduleSeats]    Script Date: T3 - 08 - 07 - 2025 11:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ScheduleSeats](
	[ScheduleSeatID] [int] IDENTITY(1,1) NOT NULL,
	[ScheduleID] [int] NULL,
	[SeatNumber] [nvarchar](10) NOT NULL,
	[SeatType] [nvarchar](20) NULL,
	[Position] [int] NULL,
	[Row] [int] NULL,
	[Column] [int] NULL,
	[Status] [nvarchar](20) NULL,
	[BookingID] [int] NULL,
 CONSTRAINT [PK_ScheduleSeats] PRIMARY KEY CLUSTERED 
(
	[ScheduleSeatID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[SeatTemplates]    Script Date: T3 - 08 - 07 - 2025 11:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SeatTemplates](
	[TemplateID] [int] IDENTITY(1,1) NOT NULL,
	[BusTypeID] [int] NULL,
	[SeatNumber] [nvarchar](10) NOT NULL,
	[SeatType] [nvarchar](20) NULL,
	[Position] [int] NULL,
	[Row] [int] NULL,
	[Column] [int] NULL,
 CONSTRAINT [PK_SeatTemplates] PRIMARY KEY CLUSTERED 
(
	[TemplateID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Tickets]    Script Date: T3 - 08 - 07 - 2025 11:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Tickets](
	[TicketID] [int] IDENTITY(1,1) NOT NULL,
	[BookingID] [int] NULL,
	[ScheduleSeatID] [int] NULL,
	[PassengerName] [nvarchar](100) NULL,
	[PassengerPhone] [nvarchar](20) NULL,
	[TicketCode] [nvarchar](20) NULL,
	[Status] [nvarchar](20) NULL,
 CONSTRAINT [PK_Tickets] PRIMARY KEY CLUSTERED 
(
	[TicketID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [UQ_Tickets_TicketCode] UNIQUE NONCLUSTERED 
(
	[TicketCode] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[User]    Script Date: T3 - 08 - 07 - 2025 11:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[User](
	[user_id] [varchar](10) NOT NULL,
	[fullname] [nvarchar](100) NULL,
	[email] [varchar](100) NULL,
	[password] [varchar](100) NULL,
	[role] [varchar](20) NULL,
	[phone] [varchar](20) NULL,
PRIMARY KEY CLUSTERED 
(
	[user_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[phone] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Bookings] ADD  DEFAULT (getdate()) FOR [BookingDate]
GO
ALTER TABLE [dbo].[Bookings] ADD  DEFAULT (N'Chờ xác nhận') FOR [Status]
GO
ALTER TABLE [dbo].[Buses] ADD  DEFAULT (N'Sẵn sàng') FOR [Status]
GO
ALTER TABLE [dbo].[Schedules] ADD  DEFAULT ((100000.00)) FOR [Price]
GO
ALTER TABLE [dbo].[Schedules] ADD  DEFAULT (N'Lên lịch') FOR [Status]
GO
ALTER TABLE [dbo].[ScheduleSeats] ADD  DEFAULT (N'Trống') FOR [Status]
GO
ALTER TABLE [dbo].[Tickets] ADD  DEFAULT (N'Hợp lệ') FOR [Status]
GO
ALTER TABLE [dbo].[User] ADD  DEFAULT ('user') FOR [role]
GO
ALTER TABLE [dbo].[Bookings]  WITH CHECK ADD  CONSTRAINT [FK_Bookings_Schedules] FOREIGN KEY([ScheduleID])
REFERENCES [dbo].[Schedules] ([ScheduleID])
GO
ALTER TABLE [dbo].[Bookings] CHECK CONSTRAINT [FK_Bookings_Schedules]
GO
ALTER TABLE [dbo].[Bookings]  WITH CHECK ADD  CONSTRAINT [FK_Bookings_Users] FOREIGN KEY([UserID])
REFERENCES [dbo].[User] ([user_id])
GO
ALTER TABLE [dbo].[Bookings] CHECK CONSTRAINT [FK_Bookings_Users]
GO
ALTER TABLE [dbo].[Buses]  WITH CHECK ADD  CONSTRAINT [FK_Buses_BusTypes] FOREIGN KEY([BusTypeID])
REFERENCES [dbo].[BusTypes] ([BusTypeID])
GO
ALTER TABLE [dbo].[Buses] CHECK CONSTRAINT [FK_Buses_BusTypes]
GO
ALTER TABLE [dbo].[Route]  WITH CHECK ADD  CONSTRAINT [FK_Route_Departure] FOREIGN KEY([departure])
REFERENCES [dbo].[Location] ([name])
GO
ALTER TABLE [dbo].[Route] CHECK CONSTRAINT [FK_Route_Departure]
GO
ALTER TABLE [dbo].[Route]  WITH CHECK ADD  CONSTRAINT [FK_Route_Destination] FOREIGN KEY([destination])
REFERENCES [dbo].[Location] ([name])
GO
ALTER TABLE [dbo].[Route] CHECK CONSTRAINT [FK_Route_Destination]
GO
ALTER TABLE [dbo].[Schedules]  WITH CHECK ADD  CONSTRAINT [FK_Schedules_AttendantID] FOREIGN KEY([AttendantID])
REFERENCES [dbo].[Employee] ([emp_id])
GO
ALTER TABLE [dbo].[Schedules] CHECK CONSTRAINT [FK_Schedules_AttendantID]
GO
ALTER TABLE [dbo].[Schedules]  WITH CHECK ADD  CONSTRAINT [FK_Schedules_Buses] FOREIGN KEY([BusID])
REFERENCES [dbo].[Buses] ([BusID])
GO
ALTER TABLE [dbo].[Schedules] CHECK CONSTRAINT [FK_Schedules_Buses]
GO
ALTER TABLE [dbo].[Schedules]  WITH CHECK ADD  CONSTRAINT [FK_Schedules_DriverID] FOREIGN KEY([DriverID])
REFERENCES [dbo].[Employee] ([emp_id])
GO
ALTER TABLE [dbo].[Schedules] CHECK CONSTRAINT [FK_Schedules_DriverID]
GO
ALTER TABLE [dbo].[Schedules]  WITH CHECK ADD  CONSTRAINT [FK_Schedules_Routes] FOREIGN KEY([RouteID])
REFERENCES [dbo].[Route] ([route_id])
GO
ALTER TABLE [dbo].[Schedules] CHECK CONSTRAINT [FK_Schedules_Routes]
GO
ALTER TABLE [dbo].[ScheduleSeats]  WITH CHECK ADD  CONSTRAINT [FK_ScheduleSeats_Bookings] FOREIGN KEY([BookingID])
REFERENCES [dbo].[Bookings] ([BookingID])
GO
ALTER TABLE [dbo].[ScheduleSeats] CHECK CONSTRAINT [FK_ScheduleSeats_Bookings]
GO
ALTER TABLE [dbo].[ScheduleSeats]  WITH CHECK ADD  CONSTRAINT [FK_ScheduleSeats_Schedules] FOREIGN KEY([ScheduleID])
REFERENCES [dbo].[Schedules] ([ScheduleID])
GO
ALTER TABLE [dbo].[ScheduleSeats] CHECK CONSTRAINT [FK_ScheduleSeats_Schedules]
GO
ALTER TABLE [dbo].[SeatTemplates]  WITH CHECK ADD  CONSTRAINT [FK_SeatTemplates_BusTypes] FOREIGN KEY([BusTypeID])
REFERENCES [dbo].[BusTypes] ([BusTypeID])
GO
ALTER TABLE [dbo].[SeatTemplates] CHECK CONSTRAINT [FK_SeatTemplates_BusTypes]
GO
ALTER TABLE [dbo].[Tickets]  WITH CHECK ADD  CONSTRAINT [FK_Tickets_Bookings] FOREIGN KEY([BookingID])
REFERENCES [dbo].[Bookings] ([BookingID])
GO
ALTER TABLE [dbo].[Tickets] CHECK CONSTRAINT [FK_Tickets_Bookings]
GO
ALTER TABLE [dbo].[Tickets]  WITH CHECK ADD  CONSTRAINT [FK_Tickets_ScheduleSeats] FOREIGN KEY([ScheduleSeatID])
REFERENCES [dbo].[ScheduleSeats] ([ScheduleSeatID])
GO
ALTER TABLE [dbo].[Tickets] CHECK CONSTRAINT [FK_Tickets_ScheduleSeats]
GO
/****** Object:  StoredProcedure [dbo].[CreateTemplate_Sleeper24]    Script Date: T3 - 08 - 07 - 2025 11:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[CreateTemplate_Sleeper24]
    @BusTypeID INT
AS
BEGIN
    -- Xóa các mẫu ghế hiện có cho BusTypeID này
    DELETE FROM SeatTemplates WHERE BusTypeID = @BusTypeID;

    -- Chèn dữ liệu cho tầng dưới
    -- Đã sửa lỗi bằng cách bao quanh 'Column' bằng dấu ngoặc vuông []
    INSERT INTO SeatTemplates (BusTypeID, SeatNumber, SeatType, Position, [Row], [Column])
    VALUES
        (@BusTypeID, 'A01', N'Tầng dưới', 1, 1, 1),
        (@BusTypeID, 'A02', N'Tầng dưới', 2, 1, 2),
        (@BusTypeID, 'A03', N'Tầng dưới', 3, 2, 1),
        (@BusTypeID, 'A04', N'Tầng dưới', 4, 2, 2),
        (@BusTypeID, 'A05', N'Tầng dưới', 5, 3, 1),
        (@BusTypeID, 'A06', N'Tầng dưới', 6, 3, 2),
        (@BusTypeID, 'A07', N'Tầng dưới', 7, 4, 1),
        (@BusTypeID, 'A08', N'Tầng dưới', 8, 4, 2),
        (@BusTypeID, 'A09', N'Tầng dưới', 9, 5, 1),
        (@BusTypeID, 'A10', N'Tầng dưới', 10, 5, 2),
        (@BusTypeID, 'A11', N'Tầng dưới', 11, 6, 1),
        (@BusTypeID, 'A12', N'Tầng dưới', 12, 6, 2);

    -- Chèn dữ liệu cho tầng trên
    -- Đã sửa lỗi bằng cách bao quanh 'Column' bằng dấu ngoặc vuông []
    INSERT INTO SeatTemplates (BusTypeID, SeatNumber, SeatType, Position, [Row], [Column])
    VALUES
        (@BusTypeID, 'B01', N'Tầng trên', 13, 1, 3),
        (@BusTypeID, 'B02', N'Tầng trên', 14, 1, 4),
        (@BusTypeID, 'B03', N'Tầng trên', 15, 2, 3),
        (@BusTypeID, 'B04', N'Tầng trên', 16, 2, 4),
        (@BusTypeID, 'B05', N'Tầng trên', 17, 3, 3),
        (@BusTypeID, 'B06', N'Tầng trên', 18, 3, 4),
        (@BusTypeID, 'B07', N'Tầng trên', 19, 4, 3),
        (@BusTypeID, 'B08', N'Tầng trên', 20, 4, 4),
        (@BusTypeID, 'B09', N'Tầng trên', 21, 5, 3),
        (@BusTypeID, 'B10', N'Tầng trên', 22, 5, 4),
        (@BusTypeID, 'B11', N'Tầng trên', 23, 6, 3),
        (@BusTypeID, 'B12', N'Tầng trên', 24, 6, 4);
END;
GO
USE [master]
GO
ALTER DATABASE [PRJ301_TungLongDB_TEST_3] SET  READ_WRITE 
GO

-- 10/07/2025 --------------------------------------------------------------
CREATE PROCEDURE [dbo].[CreateTemplate_Sleeper22]
    @BusTypeID INT
AS
BEGIN
    -- Xóa các mẫu ghế hiện có cho BusTypeID này
    DELETE FROM SeatTemplates WHERE BusTypeID = @BusTypeID;

    -- Chèn dữ liệu cho tầng dưới (10 ghế)
    INSERT INTO SeatTemplates (BusTypeID, SeatNumber, SeatType, Position, [Row], [Column])
    VALUES
        (@BusTypeID, 'A01', N'Tầng dưới', 1, 1, 1),
        (@BusTypeID, 'A02', N'Tầng dưới', 2, 1, 2),
        (@BusTypeID, 'A03', N'Tầng dưới', 3, 2, 1),
        (@BusTypeID, 'A04', N'Tầng dưới', 4, 2, 2),
        (@BusTypeID, 'A05', N'Tầng dưới', 5, 3, 1),
        (@BusTypeID, 'A06', N'Tầng dưới', 6, 3, 2),
        (@BusTypeID, 'A07', N'Tầng dưới', 7, 4, 1),
        (@BusTypeID, 'A08', N'Tầng dưới', 8, 4, 2),
        (@BusTypeID, 'A09', N'Tầng dưới', 9, 5, 1),
        (@BusTypeID, 'A10', N'Tầng dưới', 10, 5, 2);

    -- Chèn dữ liệu cho tầng trên (12 ghế)
    INSERT INTO SeatTemplates (BusTypeID, SeatNumber, SeatType, Position, [Row], [Column])
    VALUES
        (@BusTypeID, 'B01', N'Tầng trên', 11, 1, 3),
        (@BusTypeID, 'B02', N'Tầng trên', 12, 1, 4),
        (@BusTypeID, 'B03', N'Tầng trên', 13, 2, 3),
        (@BusTypeID, 'B04', N'Tầng trên', 14, 2, 4),
        (@BusTypeID, 'B05', N'Tầng trên', 15, 3, 3),
        (@BusTypeID, 'B06', N'Tầng trên', 16, 3, 4),
        (@BusTypeID, 'B07', N'Tầng trên', 17, 4, 3),
        (@BusTypeID, 'B08', N'Tầng trên', 18, 4, 4),
        (@BusTypeID, 'B09', N'Tầng trên', 19, 5, 3),
        (@BusTypeID, 'B10', N'Tầng trên', 20, 5, 4),
        (@BusTypeID, 'B11', N'Tầng trên', 21, 6, 3),
        (@BusTypeID, 'B12', N'Tầng trên', 22, 6, 4);
END;


--- PROCEDURE KẾT HỢP TẠO LỊCH TRÌNH VÀ CẢ TẠO GHẾ MỚI THEO XE ----
ALTER PROCEDURE [dbo].[CreateScheduleWithSeats]
    @RouteID INT,
    @BusID INT,
    @DepartureTime DATETIME,
    @ArrivalTime DATETIME,
    @DriverID INT,
    @AttendantID INT,
    @Price DECIMAL(10,2) = 100000.00,
    @Status NVARCHAR(20) = N'Lên lịch'
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        BEGIN TRANSACTION;

        -- 1. Kiểm tra điều kiện tiên quyết
        DECLARE @BusStatus NVARCHAR(20);
        DECLARE @DriverStatus NVARCHAR(20);
        DECLARE @AttendantStatus NVARCHAR(20);
        
        -- Lấy trạng thái hiện tại của xe, tài xế và tiếp viên
        SELECT @BusStatus = Status FROM Buses WHERE BusID = @BusID;
        SELECT @DriverStatus = status FROM Employee WHERE emp_id = @DriverID;
        SELECT @AttendantStatus = status FROM Employee WHERE emp_id = @AttendantID;
        
        -- Kiểm tra xe có sẵn sàng không
        IF @BusStatus <> N'Sẵn sàng'
        BEGIN
            THROW 60001, 'Xe không ở trạng thái sẵn sàng', 1;
        END

        -- Kiểm tra tài xế có nhàn rỗi không
        IF @DriverStatus <> N'Nhàn rỗi'
        BEGIN
            THROW 60002, 'Tài xế đang trong thực hiện hành trình khác', 1;
        END

        -- Kiểm tra tiếp viên có nhàn rỗi không
        IF @AttendantStatus <> N'Nhàn rỗi'
        BEGIN
            THROW 60006, 'Tiếp viên đang trong thực hiện hành trình khác', 1;
        END

        -- Kiểm tra trùng lịch trình cho xe
        IF EXISTS (
            SELECT 1 
            FROM Schedules 
            WHERE BusID = @BusID 
            AND (
                (@DepartureTime BETWEEN DepartureTime AND ArrivalTime)
                OR (@ArrivalTime BETWEEN DepartureTime AND ArrivalTime)
                OR (DepartureTime BETWEEN @DepartureTime AND @ArrivalTime)
            )
        )
        BEGIN
            THROW 60003, 'Xe đã có lịch trình trùng thời gian', 1;
        END
        
        -- Kiểm tra trùng lịch trình cho tài xế
        IF EXISTS (
            SELECT 1 
            FROM Schedules 
            WHERE DriverID = @DriverID 
            AND (
                (@DepartureTime BETWEEN DepartureTime AND ArrivalTime)
                OR (@ArrivalTime BETWEEN DepartureTime AND ArrivalTime)
                OR (DepartureTime BETWEEN @DepartureTime AND @ArrivalTime)
            )
        )
        BEGIN
            THROW 60004, 'Tài xế đã có lịch trình trùng thời gian', 1;
        END

        -- Kiểm tra trùng lịch trình cho tiếp viên
        IF EXISTS (
            SELECT 1 
            FROM Schedules 
            WHERE AttendantID = @AttendantID 
            AND (
                (@DepartureTime BETWEEN DepartureTime AND ArrivalTime)
                OR (@ArrivalTime BETWEEN DepartureTime AND ArrivalTime)
                OR (DepartureTime BETWEEN @DepartureTime AND @ArrivalTime)
            )
        )
        BEGIN
            THROW 60007, 'Tiếp viên đã có lịch trình trùng thời gian', 1;
        END
        
        -- 2. Thêm lịch trình mới
        INSERT INTO Schedules (
            RouteID, BusID, DepartureTime, ArrivalTime, 
            DriverID, AttendantID, Price, Status
        )
        VALUES (
            @RouteID, @BusID, @DepartureTime, @ArrivalTime,
            @DriverID, @AttendantID, @Price, @Status
        );
        
        DECLARE @NewScheduleID INT = SCOPE_IDENTITY();
        DECLARE @BusTypeID INT;
        
        -- 3. Lấy BusTypeID từ Bus
        SELECT @BusTypeID = BusTypeID FROM Buses WHERE BusID = @BusID;
        
        -- 4. Đảm bảo template ghế tồn tại
        IF NOT EXISTS (SELECT 1 FROM SeatTemplates WHERE BusTypeID = @BusTypeID)
        BEGIN
            -- Gọi stored procedure tạo template tương ứng
            DECLARE @SeatCount INT;
            SELECT @SeatCount = SeatCount FROM BusTypes WHERE BusTypeID = @BusTypeID;
            
            IF @SeatCount = 22
                EXEC CreateTemplate_Sleeper22 @BusTypeID;
            ELSE IF @SeatCount = 24
                EXEC CreateTemplate_Sleeper24 @BusTypeID;
            ELSE
                THROW 60005, 'Không tìm thấy template ghế cho loại xe này', 1;
        END
        
        -- 5. Tạo ghế ngồi từ template
        INSERT INTO ScheduleSeats (
            ScheduleID, SeatNumber, SeatType, Position, 
            [Row], [Column], Status
        )
        SELECT 
            @NewScheduleID, 
            SeatNumber, 
            SeatType, 
            Position, 
            [Row], 
            [Column],
            N'Trống' -- Trạng thái mặc định
        FROM SeatTemplates
        WHERE BusTypeID = @BusTypeID;
        
        -- 6. Cập nhật trạng thái xe, tài xế và tiếp viên thành "Bận"
        UPDATE Buses SET Status = N'Bận' WHERE BusID = @BusID;
        UPDATE Employee SET status = N'Bận' WHERE emp_id = @DriverID;
        UPDATE Employee SET status = N'Bận' WHERE emp_id = @AttendantID;
        
        COMMIT TRANSACTION;
        
        -- Trả về ID mới tạo
        SELECT @NewScheduleID AS NewScheduleID;
    END TRY
    BEGIN CATCH
        IF @@TRANCOUNT > 0
            ROLLBACK TRANSACTION;
            
        DECLARE @ErrorMessage NVARCHAR(4000) = ERROR_MESSAGE();
        DECLARE @ErrorSeverity INT = ERROR_SEVERITY();
        DECLARE @ErrorState INT = ERROR_STATE();
        
        RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState);
    END CATCH
END
GO
