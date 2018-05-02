package main.model.db;

import java.util.ArrayList;
import main.model.moudle.*;
import java.sql.*;

public class UserManager extends SqlServerManager{
	
	public static int add(String numStu, String school, String major, float gpa, String name, String image, String pwd, String timeRegister) 
	{
		int id = -1;
		String sql_Insert = "INSERT INTO [dbo].[Users]" 
				+ "([numStu], [school], [major], [gpa], [name], [image], [pwd], [timeRegister])" 
				+ "VALUES" 
				+ "(?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement pst = con.prepareStatement(sql_Insert, Statement.RETURN_GENERATED_KEYS);
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
		}
        return id;
	}

	public static boolean delete(int id) 
	{
		String sql_User = "DELETE FROM [dbo].[Users] WHERE id=?";
		try {
			PreparedStatement pst = con.prepareStatement(sql_User);
			pst.setInt(1, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static User findWithId(int id) 
	{
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
			PreparedStatement pst = con.prepareStatement(sql);
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
			return null;
		}
		if(numStu.isEmpty())
			return null;
		return new User(id, numStu, school, major, GPA, name, pwd, image, timeRegister);
	}
	
	public static User findWithNumStu(String numStu) 
	{
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
			PreparedStatement pst = con.prepareStatement(sql);
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
			return null;
		}
		if(id == -1)
			return null;
		return new User(id, numStu, school, major, GPA, name, pwd, image, timeRegister);
	}
	
	public static ArrayList<User> findWithSchool(String school) 
	{
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
			PreparedStatement pst = con.prepareStatement(sql);
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
			return null;
		}
		if(id == -1)
			return null;
		return users;
	}
	
	public static ArrayList<User> findWithMajor(String major) 
	{
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
			PreparedStatement pst = con.prepareStatement(sql);
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
			return null;
		}
		if(id == -1)
			return null;
		return users;
	}
	
	public static ArrayList<User> findWithName(String name) 
	{
		ArrayList<User> users = new ArrayList<User>();
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
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, name);
			rs = pst.executeQuery();
			while(rs.next()) 
			{
				id = rs.getInt("id");
				numStu = rs.getString("numStu");
				school = rs.getString("school");
				major = rs.getString("major");
				GPA = rs.getFloat("gpa");
				pwd = rs.getString("pwd");
				image = rs.getString("image");
				timeRegister = rs.getString("timeRegister");
				users.add(new User(id, numStu, school, major, GPA, name, pwd, image, timeRegister));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		if(id == -1)
			return null;
		return users;
	}
	
	public static ArrayList<User> findWithTimeRegister(String timeRegister)
	{
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
			PreparedStatement pst = con.prepareStatement(sql);
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
			return null;
		}
		if(id == -1)
			return null;
		return users;
	}
	
	public static boolean change(User user)
	{
		String sql = "UPDATE [dbo].[Users] SET numStu = ?, school = ?, major = ?, gpa = ?, name = ?, pwd = ?, image = ?, timeRegister = ? WHERE id = ?";
		PreparedStatement pst;
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
			return false;
		}
		return true;
	}
}
