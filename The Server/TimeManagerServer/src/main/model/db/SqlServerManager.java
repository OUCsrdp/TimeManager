package main.model.db;

import java.sql.*;

public class SqlServerManager {
	
	private static String url = "jdbc:sqlserver://localhost:1433;databaseName=TimeManager";
	//118.89.242.245:������ip��ַ
	private static String username = "root";
	private static String password = "password";
	//protected static Connection con;
	//protected static Statement stmt;
    //protected static PreparedStatement pst;
    //protected static ResultSet rs;
    
    public static Connection Connect()
    {
    	Connection con = null;
    	try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	try {
			con = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    	return con;
    }
    
    public static void Create(Statement stmt)
    {
    	String sql_UseDataBase = "USE TimeManager";
    	String sql_SetANSI = "SET ANSI_NULLS ON";
    	String sql_SetQUOTED = "SET QUOTED_IDENTIFIER ON";
    	String sql_Affairs = "if not exists "
    				+ "(select * from sysobjects where id = object_id('Affairs') and OBJECTPROPERTY(id, 'IsUserTable') = 1) "
    				+ "CREATE TABLE dbo.Affairs ("
    				+ "id int IDENTITY(1,1) NOT NULL, "
    				+ "name nvarchar(50) NOT NULL, "
    				+ "idTS int NOT NULL, "
    				+ "idLabel int NOT NULL, "
    				+ "timeStart nvarchar(50) NULL, "
    				+ "timeEnd nvarchar(50) NULL, "
    				+ "timeEndPlan nvarchar(50) NOT NULL,  "
    				+ "tips nvarchar(50) NULL, "
    				+ "satisfaction int NULL, "
    				+ "CONSTRAINT PK_Affairs PRIMARY KEY CLUSTERED(id ASC)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]"
    				+ ") ON [PRIMARY]";
    	String sql_Collection = "if not exists "
					+ "(select * from sysobjects where id = object_id('Collection') and OBJECTPROPERTY(id, 'IsUserTable') = 1) "
    			    + "CREATE TABLE dbo.Collection ("
    				+ "id int IDENTITY(1,1) NOT NULL, "
    				+ "idUser int NOT NULL, "
    				+ "idTS int NOT NULL, "
    				+ "CONSTRAINT PK_Collection PRIMARY KEY CLUSTERED(id ASC) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]" 
    				+ ") ON [PRIMARY]";
    	String sql_EventHour = "if not exists "
					+ "(select * from sysobjects where id = object_id('EventHour') and OBJECTPROPERTY(id, 'IsUserTable') = 1) "
    			    + "CREATE TABLE dbo.EventHour ("
					+ "id int IDENTITY(1,1) NOT NULL, "
					+ "numEvents real NOT NULL, "
					+ "timeArea int NOT NULL, "
					+ "isWorkday nchar(1) NOT NULL, "
					+ "CONSTRAINT PK_Collection PRIMARY KEY CLUSTERED(id ASC) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]" 
					+ ") ON [PRIMARY]";
    	String sql_Labels = "if not exists "
					+ "(select * from sysobjects where id = object_id('Labels') and OBJECTPROPERTY(id, 'IsUserTable') = 1) "
    				+ "CREATE TABLE dbo.Labels ("
    				+ "id int IDENTITY(1,1) NOT NULL,"
    				+ "name nvarchar(10) NOT NULL, "
    				+ "image ntext NOT NULL, "
    				+ "color ntext NOT NULL, "
    				+ "CONSTRAINT PK_Labels PRIMARY KEY CLUSTERED (id ASC) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]" 
    				+ ") ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]";
    	String sql_Like = "if not exists "
					+ "(select * from sysobjects where id = object_id('Like') and OBJECTPROPERTY(id, 'IsUserTable') = 1) "
    				+ "CREATE TABLE dbo.[Like] ("
    				+ "id int IDENTITY(1,1) NOT NULL, "
    				+ "idUser int NOT NULL, "
    				+ "idTS int NOT NULL, "
    				+ "CONSTRAINT PK_Like PRIMARY KEY CLUSTERED(id ASC) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]" 
    				+ ") ON [PRIMARY]";
    	String sql_S_Affairs = "if not exists "
					+ "(select * from sysobjects where id = object_id('S_Affairs') and OBJECTPROPERTY(id, 'IsUserTable') = 1) "
					+ "CREATE TABLE dbo.S_Affairs ("
    				+ "id int IDENTITY(1,1) NOT NULL, "
    				+ "name nvarchar(50) NOT NULL, "
    				+ "idTS int NOT NULL, "
    				+ "idLabel int NOT NULL, "
    				+ "timeStart nvarchar(50) NULL, "
    				+ "timeEnd nvarchar(50) NULL, "
    				+ "timeStartPlan nvarchar(50) NOT NULL, "
    				+ "timeEndPlan nvarchar(50) NOT NULL, "
    				+ "timeStartAlarm nvarchar(50) NULL, "
    				+ "timeEndAlarm nvarchar(50) NULL, "
    				+ "tips nvarchar(50) NULL, "
    				+ "satisfaction int NULL, "
    				+ "isImportant nvarchar(10) NOT NULL,"
    				+ "CONSTRAINT PK_S_Affairs PRIMARY KEY CLUSTERED(id ASC)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]"
    				+ ") ON [PRIMARY]";
    	String sql_Schedule = "if not exists "
					+ "(select * from sysobjects where id = object_id('Schedule') and OBJECTPROPERTY(id, 'IsUserTable') = 1) "
    				+ "CREATE TABLE dbo.Schedule ("
    				+ "id int IDENTITY(1,1) NOT NULL, "
    				+ "idUser int NOT NULL, "
    				+ "date nvarchar(50) NOT NULL, "
    				+ "weekday int NOT NULL, "
    				+ "CONSTRAINT PK_Schedule PRIMARY KEY CLUSTERED(id ASC)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]"
    				+ ") ON [PRIMARY]";
    	String sql_SharedTables = "if not exists "
					+ "(select * from sysobjects where id = object_id('SharedTables') and OBJECTPROPERTY(id, 'IsUserTable') = 1) "
					+ "CREATE TABLE dbo.SharedTables ("
    				+ "id int IDENTITY(1,1) NOT NULL, "
					+ "idUser int NOT NULL, "
					+ "idTS int NOT NULL, "
    				+ "timeShare nvarchar(50) NOT NULL, "
    				+ "summary ntext NULL, "
    				+ "thumbup int NOT NULL, "
    				+ "CONSTRAINT PK_SharedTables PRIMARY KEY CLUSTERED(id ASC)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]"
    				+ ") ON [PRIMARY]";
    	String sql_TimeSharing = "if not exists "
					+ "(select * from sysobjects where id = object_id('TimeSharing') and OBJECTPROPERTY(id, 'IsUserTable') = 1) "
					+ "CREATE TABLE dbo.TimeSharing ("
    				+ "id int IDENTITY(1,1) NOT NULL, "
    				+ "idUser int NOT NULL, "
    				+ "date nvarchar(50) NOT NULL, "
    				+ "weekday int NOT NULL, "
    				+ "CONSTRAINT PK_TimeSharing PRIMARY KEY CLUSTERED(id ASC)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]"
    				+ ") ON [PRIMARY]";
    	String sql_Token = "if not exists "
					+ "(select * from sysobjects where id = object_id('Token') and OBJECTPROPERTY(id, 'IsUserTable') = 1) "
					+ "CREATE TABLE dbo.Token ("
    				+ "id int IDENTITY(1,1) NOT NULL, "
    				+ "token ntext NOT NULL, "
    				+ "CONSTRAINT PK_Token PRIMARY KEY CLUSTERED(id ASC)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]"
    				+ ") ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]";
    	String sql_Users = "if not exists "
					+ "(select * from sysobjects where id = object_id('Users') and OBJECTPROPERTY(id, 'IsUserTable') = 1) "
					+ "CREATE TABLE dbo.Users ("
    				+ "id int IDENTITY(1,1) NOT NULL, "
    				+ "school nvarchar(50) NOT NULL, "
    				+ "numStu nvarchar(50) NOT NULL, "
    				+ "major nvarchar(50) NOT NULL, "
    				+ "gpa real NOT NULL, "
    				+ "name nvarchar(20) NOT NULL, "
    				+ "image ntext NOT NULL, "
    				+ "pwd ntext NOT NULL, "
    				+ "timeRegister nvarchar(50) NOT NULL, "
    				+ "CONSTRAINT PK_Users PRIMARY KEY CLUSTERED(id ASC)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]"
    				+ ") ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]";
    	String sql_Link1 = "if not exists "
				+ "(select * from sysobjects WHERE id = object_id('FK_Affairs_TimeSharing'))"
    			+ "ALTER TABLE [dbo].[Affairs]  WITH CHECK ADD  CONSTRAINT [FK_Affairs_TimeSharing] FOREIGN KEY([idTS])"
    			+ "REFERENCES [dbo].[TimeSharing] ([id])"
    			+ "ON UPDATE CASCADE "
    			+ "ON DELETE CASCADE";
    	String sql_LinkCheck1 = "ALTER TABLE [dbo].[Affairs] CHECK CONSTRAINT [FK_Affairs_TimeSharing]";
    	String sql_Link2 = "if not exists "
				+ "(select * from sysobjects WHERE id = object_id('FK_Collection_TimeSharing'))"
    			+ "ALTER TABLE [dbo].[Collection]  WITH CHECK ADD  CONSTRAINT [FK_Collection_TimeSharing] FOREIGN KEY([idTS])"
    			+ "REFERENCES [dbo].[TimeSharing] ([id])"
    			+ "ON UPDATE CASCADE "
    			+ "ON DELETE CASCADE";
    	String sql_LinkCheck2 = "ALTER TABLE [dbo].[Collection] CHECK CONSTRAINT [FK_Collection_TimeSharing]";
    	String sql_Link3 = "if not exists "
				+ "(select * from sysobjects WHERE id = object_id('FK_Like_TimeSharing'))"
    			+ "ALTER TABLE [dbo].[Like]  WITH CHECK ADD  CONSTRAINT [FK_Like_TimeSharing] FOREIGN KEY([idTS])" 
				+ "REFERENCES [dbo].[TimeSharing] ([id])" 
    			+ "ON UPDATE CASCADE " 
				+ "ON DELETE CASCADE";
    	String sql_LinkCheck3 = "ALTER TABLE [dbo].[Like] CHECK CONSTRAINT [FK_Like_TimeSharing]";
    	String sql_Link4 = "if not exists "
				+ "(select * from sysobjects WHERE id = object_id('FK_SharedTables_TimeSharing'))"
    			+ "ALTER TABLE [dbo].[SharedTables]  WITH CHECK ADD  CONSTRAINT [FK_SharedTables_TimeSharing] FOREIGN KEY([idTS])" 
				+ "REFERENCES [dbo].[TimeSharing] ([id])" 
    			+ "ON UPDATE CASCADE " 
				+ "ON DELETE CASCADE";
    	String sql_LinkCheck4 = "ALTER TABLE [dbo].[SharedTables] CHECK CONSTRAINT [FK_SharedTables_TimeSharing]";
    	String sql_Link5 = "if not exists "
				+ "(select * from sysobjects WHERE id = object_id('FK_S_Affairs_Schedule'))"
    			+ "ALTER TABLE [dbo].[S_Affairs]  WITH CHECK ADD  CONSTRAINT [FK_S_Affairs_Schedule] FOREIGN KEY([idS])" 
				+ "REFERENCES [dbo].[Schedule] ([id])" 
    			+ "ON UPDATE CASCADE " 
    			+ "ON DELETE CASCADE";
    	String sql_LinkCheck5 = "ALTER TABLE [dbo].[S_Affairs] CHECK CONSTRAINT [FK_S_Affairs_Schedule]";
    	String sql_Link6 ="if not exists "
				+ "(select * from sysobjects WHERE id = object_id('FK_Schedule_Users'))"
    			+ "ALTER TABLE [dbo].[Schedule]  WITH CHECK ADD  CONSTRAINT [FK_Schedule_Users] FOREIGN KEY([idUser])" 
				+ "REFERENCES [dbo].[Users] ([id])" 
    			+ "ON UPDATE CASCADE "
    			+ "ON DELETE CASCADE";
    	String sql_LinkCheck6 = "ALTER TABLE [dbo].[Schedule] CHECK CONSTRAINT [FK_Schedule_Users]";
    	String sql_Link7 = "if not exists "
				+ "(select * from sysobjects WHERE id = object_id('FK_TimeSharing_Users'))"
    			+ "ALTER TABLE [dbo].[TimeSharing]  WITH CHECK ADD  CONSTRAINT [FK_TimeSharing_Users] FOREIGN KEY([idUser])" + 
    			"REFERENCES [dbo].[Users] ([id])" + 
    			"ON UPDATE CASCADE " + 
    			"ON DELETE CASCADE";
    	String sql_LinkCheck7 = "ALTER TABLE [dbo].[TimeSharing] CHECK CONSTRAINT [FK_TimeSharing_Users]";
    	try {
			stmt.executeUpdate(sql_UseDataBase);
	    	stmt.executeUpdate(sql_SetANSI);
	    	stmt.executeUpdate(sql_SetQUOTED);
	    	stmt.executeUpdate(sql_Affairs);
	    	stmt.executeUpdate(sql_SetANSI);
	    	stmt.executeUpdate(sql_SetQUOTED);
	    	stmt.executeUpdate(sql_Collection);
	    	stmt.executeUpdate(sql_SetANSI);
	    	stmt.executeUpdate(sql_SetQUOTED);
	    	stmt.executeUpdate(sql_EventHour);
	    	stmt.executeUpdate(sql_SetANSI);
	    	stmt.executeUpdate(sql_SetQUOTED);
	    	stmt.executeUpdate(sql_Labels);
	    	stmt.executeUpdate(sql_SetANSI);
	    	stmt.executeUpdate(sql_SetQUOTED);
	    	stmt.executeUpdate(sql_Like);
	    	stmt.executeUpdate(sql_SetANSI);
	    	stmt.executeUpdate(sql_SetQUOTED);
	    	stmt.executeUpdate(sql_S_Affairs);
	    	stmt.executeUpdate(sql_SetANSI);
	    	stmt.executeUpdate(sql_SetQUOTED);
	    	stmt.executeUpdate(sql_Schedule);
	    	stmt.executeUpdate(sql_SetANSI);
	    	stmt.executeUpdate(sql_SetQUOTED);
	    	stmt.executeUpdate(sql_SharedTables);
	    	stmt.executeUpdate(sql_SetANSI);
	    	stmt.executeUpdate(sql_SetQUOTED);
	    	stmt.executeUpdate(sql_TimeSharing);
	    	stmt.executeUpdate(sql_SetANSI);
	    	stmt.executeUpdate(sql_SetQUOTED);
	    	stmt.executeUpdate(sql_Token);
	    	stmt.executeUpdate(sql_SetANSI);
	    	stmt.executeUpdate(sql_SetQUOTED);
	    	stmt.executeUpdate(sql_Users);
	    	stmt.executeUpdate(sql_Link1);
	    	stmt.executeUpdate(sql_LinkCheck1);
	    	stmt.executeUpdate(sql_Link2);
	    	stmt.executeUpdate(sql_LinkCheck2);
	    	stmt.executeUpdate(sql_Link3);
	    	stmt.executeUpdate(sql_LinkCheck3);
	    	stmt.executeUpdate(sql_Link4);
	    	stmt.executeUpdate(sql_LinkCheck4);
	    	stmt.executeUpdate(sql_Link5);
	    	stmt.executeUpdate(sql_LinkCheck5);
	    	stmt.executeUpdate(sql_Link6);
	    	stmt.executeUpdate(sql_LinkCheck6);
	    	stmt.executeUpdate(sql_Link7);
	    	stmt.executeUpdate(sql_LinkCheck7);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void Close(Connection con, Statement stmt, ResultSet rs, PreparedStatement pst) 
    {
		try {
		    if(rs != null)
		    	rs.close();
		    if(pst != null)
		    	pst.close();
		    if(stmt != null)
		    	stmt.close();
		    if(con != null)
		   		con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    } 
    
}
