package main.model.db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import main.model.moudle.*;;
public class MajorManager extends SqlServerManager
{
	public static int add(String major) 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = MajorManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		MajorManager.Create(stmt);
		int id = -1;
		String sql_Insert = "INSERT INTO [dbo].[Major]" 
				+ "([name])" 
				+ "VALUES" 
				+ "(?)";
		try {
			pst = con.prepareStatement(sql_Insert, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, major);
            pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            rs.next();
            id = rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			MajorManager.Close(con, stmt, rs, pst);
			return -1;
		}
		MajorManager.Close(con, stmt, rs, pst);
        return id;
	}
	
	public static boolean delete(int id) 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = MajorManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		MajorManager.Create(stmt);
		String sql_Major = "DELETE FROM [dbo].[Major] WHERE id=?";
		try {
			pst = con.prepareStatement(sql_Major);
			pst.setInt(1, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			MajorManager.Close(con, stmt, rs, pst);
			return false;
		}
		MajorManager.Close(con, stmt, rs, pst);
		return true;
	}
	
	public static boolean change(Major major) 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = MajorManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		MajorManager.Create(stmt);
		String sql = "UPDATE [dbo].[Major] SET name = ? WHERE id = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, major.getMajor());
			pst.setInt(2, major.getId());
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			MajorManager.Close(con, stmt, rs, pst);
			return false;
		}
		MajorManager.Close(con, stmt, rs, pst);
		return true;
	}
	
	public static ArrayList<Major> findWithNothing()
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = TokenManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		TokenManager.Create(stmt);
		ArrayList<Major> majors = new ArrayList<Major>();
		int id = -1;
		String major = null;
		String sql = "select * from [dbo].[Major]";
		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next())
			{
				id = rs.getInt("id");
				major = rs.getString("name");
				majors.add(new Major(id,major));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			MajorManager.Close(con, stmt, rs, pst);
			return null;
		}
		MajorManager.Close(con, stmt, rs, pst);
		if(id == -1)
			return null;
		return majors;
	}
	
	
	public static String replaceSearch(String search) {
		String result;
		result = search.replace(' ', '%');
		return result;
	}
	public static ArrayList<Major> findWithWords(String search)
	{
		String search1 = replaceSearch(search); //改变调查语句格式以便后面接在查询语句后面
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		
		con = MajorManager.Connect();
		
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		MajorManager.Create(stmt);
		ArrayList<Major> majors = new ArrayList<Major>();
		int id = -1;
		String major = null;
		String sql = "select * from [dbo].[Major] where name like '%"+search1+"%'";
		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next())
			{
				id = rs.getInt("id");
				major = rs.getString("name");
				majors.add(new Major(id,major));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			MajorManager.Close(con, stmt, rs, pst);
			return null;
		}
		MajorManager.Close(con, stmt, rs, pst);
		if(id == -1)
			return null;
		return majors;
	}
}



	
	
	
	
	

