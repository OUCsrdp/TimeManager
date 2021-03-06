package main.model.db;

import java.util.ArrayList;
import main.model.moudle.*;
import java.sql.*;

public class UserManager extends SqlServerManager{
	
	public static int add(String numStu, String school, String major, float gpa, String name, String image, String pwd, String timeRegister) 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = UserManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		UserManager.Create(stmt);
		int id = -1;
		String sql_Insert = "INSERT INTO [dbo].[Users]" 
				+ "([numStu], [school], [major], [gpa], [name], [image], [pwd], [timeRegister])" 
				+ "VALUES" 
				+ "(?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			pst = con.prepareStatement(sql_Insert, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, numStu);
			pst.setString(2, school);
			pst.setString(3, major);
			pst.setFloat(4, gpa);
			pst.setString(5, name);
			pst.setString(6, image);
			pst.setString(7, pwd);
			pst.setString(8, timeRegister);
            pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            rs.next();
            id = rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			UserManager.Close(con, stmt, rs, pst);
			return -1;
		}
		UserManager.Close(con, stmt, rs, pst);
        return id;
	}

	public static boolean delete(int id) 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = UserManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		UserManager.Create(stmt);
		ArrayList<SharedTable>sharedTables = SharedTableManager.findWithIdUser(id);
		for(int i = 0; i < sharedTables.size(); i++)
			SharedTableManager.delete(sharedTables.get(i).getId());
		String sql_User = "DELETE FROM [dbo].[Users] WHERE id=?";
		try {
			pst = con.prepareStatement(sql_User);
			pst.setInt(1, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			UserManager.Close(con, stmt, rs, pst);
			return false;
		}
		UserManager.Close(con, stmt, rs, pst);
		return true;
	}
	
	public static ArrayList<User> findWithNothing()
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = UserManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		UserManager.Create(stmt);
		ArrayList<User> users = new ArrayList<User>();
		int id = -1;
		String numStu = null;
		String school = null;
		String major = null;
		float GPA = 0.0f;
		String name = null;
		String pwd = null;
		String image = null;
		String timeRegister = null;
		String sql = "select * from [dbo].[Users]";
		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()) 
			{
				id = rs.getInt("id");
				numStu = rs.getString("numStu");
				school = rs.getString("school");
				major = rs.getString("major");
				GPA = rs.getFloat("gpa");
				name = rs.getString("name");
				pwd = rs.getString("pwd");
				image = rs.getString("image");
				timeRegister = rs.getString("timeRegister");
				users.add(new User(id, numStu, school, major, GPA, name, pwd, image, timeRegister));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			UserManager.Close(con, stmt, rs, pst);
			return null;
		}
		UserManager.Close(con, stmt, rs, pst);
		if(id == -1)
			return null;
		return users;
	}
	
	public static User findWithId(int id) 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = UserManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		UserManager.Create(stmt);
		String numStu = null;
		String school = null;
		String major = null;
		float GPA = 0.0f;
		String name = null;
		String pwd = null;
		String image = null;
		String timeRegister = null;
		String sql = "select * from [dbo].[Users] where id = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			rs.next();
			numStu = rs.getString("numStu");
			school = rs.getString("school");
			major = rs.getString("major");
			GPA = rs.getFloat("gpa");
			name = rs.getString("name");
			pwd = rs.getString("pwd");
			image = rs.getString("image");
			timeRegister = rs.getString("timeRegister");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			UserManager.Close(con, stmt, rs, pst);
			return null;
		}
		UserManager.Close(con, stmt, rs, pst);
		if(numStu.isEmpty())
			return null;
		return new User(id, numStu, school, major, GPA, name, pwd, image, timeRegister);
	}
	
	public static User findWithNumStu(String numStu) 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = UserManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		UserManager.Create(stmt);
		int id = -1;
		String school = null;
		String major = null;
		float GPA = 0.0f;
		String name = null;
		String pwd = null;
		String image = null;
		String timeRegister = null;
		String sql = "select * from [dbo].[Users] where numStu = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, numStu);
			rs = pst.executeQuery();
			rs.next();
			id = rs.getInt("id");
			school = rs.getString("school");
			major = rs.getString("major");
			GPA = rs.getFloat("gpa");
			name = rs.getString("name");
			pwd = rs.getString("pwd");
			image = rs.getString("image");
			timeRegister = rs.getString("timeRegister");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			UserManager.Close(con, stmt, rs, pst);
			return null;
		}
		UserManager.Close(con, stmt, rs, pst);
		if(id == -1)
			return null;
		return new User(id, numStu, school, major, GPA, name, pwd, image, timeRegister);
	}
	
	public static ArrayList<User> findWithSchool(String school) 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = UserManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		UserManager.Create(stmt);
		ArrayList<User> users = new ArrayList<User>();
		int id = -1;
		String numStu = null;
		String major = null;
		float GPA = 0.0f;
		String name = null;
		String pwd = null;
		String image = null;
		String timeRegister = null;
		String sql = "select * from [dbo].[Users] where school = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, school);
			rs = pst.executeQuery();
			while(rs.next()) 
			{
				id = rs.getInt("id");
				numStu = rs.getString("numStu");
				major = rs.getString("major");
				GPA = rs.getFloat("gpa");
				name = rs.getString("name");
				pwd = rs.getString("pwd");
				image = rs.getString("image");
				timeRegister = rs.getString("timeRegister");
				users.add(new User(id, numStu, school, major, GPA, name, pwd, image, timeRegister));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			UserManager.Close(con, stmt, rs, pst);
			return null;
		}
		UserManager.Close(con, stmt, rs, pst);
		if(id == -1)
			return null;
		return users;
	}
	
	public static ArrayList<User> findWithMajor(String major) 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = UserManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		UserManager.Create(stmt);
		ArrayList<User> users = new ArrayList<User>();
		int id = -1;
		String numStu = null;
		String school = null;
		float GPA = 0.0f;
		String name = null;
		String pwd = null;
		String image = null;
		String timeRegister = null;
		String sql = "select * from [dbo].[Users] where major = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, major);
			rs = pst.executeQuery();
			while(rs.next()) 
			{
				id = rs.getInt("id");
				numStu = rs.getString("numStu");
				school = rs.getString("school");
				GPA = rs.getFloat("gpa");
				name = rs.getString("name");
				pwd = rs.getString("pwd");
				image = rs.getString("image");
				timeRegister = rs.getString("timeRegister");
				users.add(new User(id, numStu, school, major, GPA, name, pwd, image, timeRegister));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			UserManager.Close(con, stmt, rs, pst);
			return null;
		}
		UserManager.Close(con, stmt, rs, pst);
		if(id == -1)
			return null;
		return users;
	}
	
	public static ArrayList<User> findWithGPA(float GPA)
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = UserManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		UserManager.Create(stmt);
		ArrayList<User> users = new ArrayList<User>();
		int id = -1;
		String numStu = null;
		String school = null;
		String major = null;
		String name = null;
		String pwd = null;
		String image = null;
		String timeRegister = null;
		String sql = "select * from [dbo].[Users] where gpa = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setFloat(1, GPA);
			rs = pst.executeQuery();
			while(rs.next()) 
			{
				id = rs.getInt("id");
				numStu = rs.getString("numStu");
				school = rs.getString("school");
				major = rs.getString("major");
				name = rs.getString("name");
				pwd = rs.getString("pwd");
				image = rs.getString("image");
				timeRegister = rs.getString("timeRegister");
				users.add(new User(id, numStu, school, major, GPA, name, pwd, image, timeRegister));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			UserManager.Close(con, stmt, rs, pst);
			return null;
		}
		UserManager.Close(con, stmt, rs, pst);
		if(id == -1)
			return null;
		return users;
	}
	
	public static User findWithName(String name) 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = UserManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		UserManager.Create(stmt);
		int id = -1;
		String numStu = null;
		String school = null;
		String major = null;
		float GPA = 0.0f;
		String pwd = null;
		String image = null;
		String timeRegister = null;
		String sql = "select * from [dbo].[Users] where name = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, name);
			rs = pst.executeQuery();
			if(!rs.next())
				return null;
			id = rs.getInt("id");
			numStu = rs.getString("numStu");
			school = rs.getString("school");
			major = rs.getString("major");
			GPA = rs.getFloat("gpa");
			pwd = rs.getString("pwd");
			image = rs.getString("image");
			timeRegister = rs.getString("timeRegister");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			UserManager.Close(con, stmt, rs, pst);
			return null;
		}
		UserManager.Close(con, stmt, rs, pst);
		if(id == -1)
			return null;
		return new User(id, numStu, school, major, GPA, name, pwd, image, timeRegister); 
	}
	
	public static ArrayList<User> findWithTimeRegister(String timeRegister)
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = UserManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		UserManager.Create(stmt);
		ArrayList<User> users = new ArrayList<User>();
		int id = -1;
		String numStu = null;
		String school = null;
		String major = null;
		float GPA = 0.0f;
		String name = null;
		String pwd = null;
		String image = null;
		String sql = "select * from [dbo].[Users] where timeRegister = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, timeRegister);
			rs = pst.executeQuery();
			while(rs.next()) 
			{
				id = rs.getInt("id");
				numStu = rs.getString("numStu");
				school = rs.getString("school");
				major = rs.getString("major");
				GPA = rs.getFloat("gpa");
				name = rs.getString("name");
				pwd = rs.getString("pwd");
				image = rs.getString("image");
				users.add(new User(id, numStu, school, major, GPA, name, pwd, image, timeRegister));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			UserManager.Close(con, stmt, rs, pst);
			return null;
		}
		UserManager.Close(con, stmt, rs, pst);
		if(id == -1)
			return null;
		return users;
	}
	
	public static boolean change(User user)
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = UserManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		UserManager.Create(stmt);
		String sql = "UPDATE [dbo].[Users] SET numStu = ?, school = ?, major = ?, gpa = ?, name = ?, pwd = ?, image = ?, timeRegister = ? WHERE id = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, user.getNumStu());
			pst.setString(2, user.getSchool());
			pst.setString(3, user.getMajor());
			pst.setFloat(4, user.getGPA());
			pst.setString(5, user.getName());
			pst.setString(6, user.getPwd());
			pst.setString(7, user.getImage());
			pst.setString(8, user.getTimeRegister());
			pst.setInt(9, user.getId());
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			UserManager.Close(con, stmt, rs, pst);
			return false;
		}
		UserManager.Close(con, stmt, rs, pst);
		return true;
	}
}
