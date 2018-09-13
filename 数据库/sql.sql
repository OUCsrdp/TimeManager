USE [master]
GO
/****** Object:  Database [TimeManager]    Script Date: 2018/9/13 23:36:42 ******/
CREATE DATABASE [TimeManager]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'TimeManager', FILENAME = N'D:\Microsoft SQL Server\MSSQL13.MSSQLSERVER\MSSQL\DATA\TimeManager.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'TimeManager_log', FILENAME = N'D:\Microsoft SQL Server\MSSQL13.MSSQLSERVER\MSSQL\DATA\TimeManager_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
GO
ALTER DATABASE [TimeManager] SET COMPATIBILITY_LEVEL = 130
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [TimeManager].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [TimeManager] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [TimeManager] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [TimeManager] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [TimeManager] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [TimeManager] SET ARITHABORT OFF 
GO
ALTER DATABASE [TimeManager] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [TimeManager] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [TimeManager] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [TimeManager] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [TimeManager] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [TimeManager] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [TimeManager] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [TimeManager] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [TimeManager] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [TimeManager] SET  DISABLE_BROKER 
GO
ALTER DATABASE [TimeManager] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [TimeManager] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [TimeManager] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [TimeManager] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [TimeManager] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [TimeManager] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [TimeManager] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [TimeManager] SET RECOVERY FULL 
GO
ALTER DATABASE [TimeManager] SET  MULTI_USER 
GO
ALTER DATABASE [TimeManager] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [TimeManager] SET DB_CHAINING OFF 
GO
ALTER DATABASE [TimeManager] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [TimeManager] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [TimeManager] SET DELAYED_DURABILITY = DISABLED 
GO
EXEC sys.sp_db_vardecimal_storage_format N'TimeManager', N'ON'
GO
ALTER DATABASE [TimeManager] SET QUERY_STORE = OFF
GO
USE [TimeManager]
GO
ALTER DATABASE SCOPED CONFIGURATION SET LEGACY_CARDINALITY_ESTIMATION = OFF;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET LEGACY_CARDINALITY_ESTIMATION = PRIMARY;
GO
ALTER DATABASE SCOPED CONFIGURATION SET MAXDOP = 0;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET MAXDOP = PRIMARY;
GO
ALTER DATABASE SCOPED CONFIGURATION SET PARAMETER_SNIFFING = ON;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET PARAMETER_SNIFFING = PRIMARY;
GO
ALTER DATABASE SCOPED CONFIGURATION SET QUERY_OPTIMIZER_HOTFIXES = OFF;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET QUERY_OPTIMIZER_HOTFIXES = PRIMARY;
GO
USE [TimeManager]
GO
/****** Object:  Table [dbo].[Affairs]    Script Date: 2018/9/13 23:36:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Affairs](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](50) NOT NULL,
	[idTS] [int] NOT NULL,
	[idLabel] [int] NOT NULL,
	[timeStart] [nvarchar](50) NULL,
	[timeEnd] [nvarchar](50) NULL,
	[timeEndPlan] [nvarchar](50) NOT NULL,
	[tips] [nvarchar](50) NULL,
	[satisfaction] [int] NULL,
 CONSTRAINT [PK_Affairs] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Collection]    Script Date: 2018/9/13 23:36:45 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Collection](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[idUser] [int] NOT NULL,
	[idTS] [int] NOT NULL,
 CONSTRAINT [PK_Collection] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[EventHour]    Script Date: 2018/9/13 23:36:45 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[EventHour](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[numEvents] [real] NOT NULL,
	[timeArea] [int] NOT NULL,
	[isWorkday] [nchar](1) NOT NULL,
 CONSTRAINT [PK_EventHour] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Labels]    Script Date: 2018/9/13 23:36:45 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Labels](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](10) NOT NULL,
	[image] [ntext] NOT NULL,
	[color] [ntext] NOT NULL,
 CONSTRAINT [PK_Labels] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Like]    Script Date: 2018/9/13 23:36:45 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Like](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[idUser] [int] NOT NULL,
	[idTS] [int] NOT NULL,
 CONSTRAINT [PK_Like] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Major]    Script Date: 2018/9/13 23:36:45 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Major](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](20) NOT NULL,
 CONSTRAINT [PK_Major] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[S_Affairs]    Script Date: 2018/9/13 23:36:45 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[S_Affairs](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](50) NOT NULL,
	[idTS] [int] NOT NULL,
	[idS] [int] NOT NULL,
	[idLabel] [int] NOT NULL,
	[timeStart] [nvarchar](50) NULL,
	[timeEnd] [nvarchar](50) NULL,
	[timeStartPlan] [nvarchar](50) NOT NULL,
	[timeEndPlan] [nvarchar](50) NOT NULL,
	[timeStartAlarm] [nvarchar](50) NULL,
	[timeEndAlarm] [nvarchar](50) NULL,
	[tips] [nvarchar](50) NULL,
	[satisfaction] [int] NULL,
	[isImportant] [nvarchar](10) NOT NULL,
 CONSTRAINT [PK_S_Affairs] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Schedule]    Script Date: 2018/9/13 23:36:45 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Schedule](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[idUser] [int] NOT NULL,
	[date] [nvarchar](50) NOT NULL,
	[weekday] [int] NOT NULL,
 CONSTRAINT [PK_Schedule] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[SharedTables]    Script Date: 2018/9/13 23:36:45 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SharedTables](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[idUser] [int] NOT NULL,
	[idTS] [int] NOT NULL,
	[timeShare] [nvarchar](50) NOT NULL,
	[summary] [ntext] NULL,
	[thumbup] [int] NOT NULL,
 CONSTRAINT [PK_SharedTables] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TimeSharing]    Script Date: 2018/9/13 23:36:45 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TimeSharing](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[idUser] [int] NOT NULL,
	[date] [nvarchar](50) NOT NULL,
	[weekday] [int] NOT NULL,
 CONSTRAINT [PK_TimeSharing] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Token]    Script Date: 2018/9/13 23:36:45 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Token](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[token] [ntext] NOT NULL,
 CONSTRAINT [PK_Token] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Users]    Script Date: 2018/9/13 23:36:45 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Users](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[school] [nvarchar](50) NOT NULL,
	[numStu] [nvarchar](50) NOT NULL,
	[major] [nvarchar](50) NOT NULL,
	[gpa] [real] NOT NULL,
	[name] [nvarchar](20) NOT NULL,
	[image] [ntext] NOT NULL,
	[pwd] [ntext] NOT NULL,
	[timeRegister] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_Users] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
ALTER TABLE [dbo].[Affairs]  WITH CHECK ADD  CONSTRAINT [FK_Affairs_TimeSharing] FOREIGN KEY([idTS])
REFERENCES [dbo].[TimeSharing] ([id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Affairs] CHECK CONSTRAINT [FK_Affairs_TimeSharing]
GO
ALTER TABLE [dbo].[Collection]  WITH CHECK ADD  CONSTRAINT [FK_Collection_TimeSharing] FOREIGN KEY([idTS])
REFERENCES [dbo].[TimeSharing] ([id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Collection] CHECK CONSTRAINT [FK_Collection_TimeSharing]
GO
ALTER TABLE [dbo].[Like]  WITH CHECK ADD  CONSTRAINT [FK_Like_TimeSharing] FOREIGN KEY([idTS])
REFERENCES [dbo].[TimeSharing] ([id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Like] CHECK CONSTRAINT [FK_Like_TimeSharing]
GO
ALTER TABLE [dbo].[S_Affairs]  WITH CHECK ADD  CONSTRAINT [FK_S_Affairs_Schedule] FOREIGN KEY([idS])
REFERENCES [dbo].[Schedule] ([id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[S_Affairs] CHECK CONSTRAINT [FK_S_Affairs_Schedule]
GO
ALTER TABLE [dbo].[Schedule]  WITH CHECK ADD  CONSTRAINT [FK_Schedule_Users] FOREIGN KEY([idUser])
REFERENCES [dbo].[Users] ([id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Schedule] CHECK CONSTRAINT [FK_Schedule_Users]
GO
ALTER TABLE [dbo].[SharedTables]  WITH CHECK ADD  CONSTRAINT [FK_SharedTables_TimeSharing] FOREIGN KEY([idTS])
REFERENCES [dbo].[TimeSharing] ([id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[SharedTables] CHECK CONSTRAINT [FK_SharedTables_TimeSharing]
GO
ALTER TABLE [dbo].[TimeSharing]  WITH CHECK ADD  CONSTRAINT [FK_TimeSharing_Users] FOREIGN KEY([idUser])
REFERENCES [dbo].[Users] ([id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[TimeSharing] CHECK CONSTRAINT [FK_TimeSharing_Users]
GO
USE [master]
GO
ALTER DATABASE [TimeManager] SET  READ_WRITE 
GO
