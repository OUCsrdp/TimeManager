package main.model.db;

import java.sql.*;

public class SqlServerManager {
	private static String url = "jdbc:sqlserver://127.0.0.1:1433;databaseName=TimeManager";
	private static String username = "root";
	private static String password = "password";
	protected static Connection con;
	protected static Statement stmt;
    protected static PreparedStatement pst;
    protected static ResultSet rs;
    
    public static void Connect()
    {
    	try {
			Class.forName("com.micresoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    public static void Create()
    {
    	//String sql0 = "CREATE TABEL IF NOT EXISTS 'Affairs' ('id'int(4) unsigned NOT NULL AUTO_INCREMENT,'name'varchar(100) unsigned NOT NULL DEFAULT '','idTS'int(4) unsigned NOT NULL DEFAULT, 'idLabel'int(4) unsigned NOT NULL DEFAULT '', 'timeStart'time(5))";
    }
    
    public static void Close() {;} 
    
}
