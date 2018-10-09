package main.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import main.model.moudle.*;;

public class LabelManager extends SqlServerManager
{
	public static int add(String name, String image, String color) 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = LabelManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		LabelManager.Create(stmt);
		int id = -1;
		String sql_Insert = "INSERT INTO [dbo].[Labels]" 
				+ "([name], [image], [color])" 
				+ "VALUES" 
				+ "(?, ?, ?)";
		try {
			pst = con.prepareStatement(sql_Insert, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, name);
			pst.setString(2, image);
			pst.setString(3, color);
            pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            rs.next();
            id = rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LabelManager.Close(con, stmt, rs, pst);
		}
		LabelManager.Close(con, stmt, rs, pst);
        return id;
	}

	public static boolean delete(int id) 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = LabelManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		LabelManager.Create(stmt);
		String sql_Label = "DELETE FROM [dbo].[Labels] WHERE id=?";
		try {
			pst = con.prepareStatement(sql_Label);
			pst.setInt(1, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LabelManager.Close(con, stmt, rs, pst);
			return false;
		}
		LabelManager.Close(con, stmt, rs, pst);
		return true;
	}
	
	public static ArrayList<Label> findWithNothing()
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = LabelManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		LabelManager.Create(stmt);
		ArrayList<Label> labels = new ArrayList<Label>();
		int id = -1;
		String name = null;
		String image = null;
		String color = null;
		String sql = "select * from [dbo].[Labels]";
		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()) 
			{
				id = rs.getInt("id");
				name = rs.getString("name");
				image = rs.getString("image");
				color = rs.getString("color");
				labels.add(new Label(id, name, image, color));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LabelManager.Close(con, stmt, rs, pst);
			return null;
		}
		LabelManager.Close(con, stmt, rs, pst);
		if(id == -1)
			return null;
		return labels;
	}
	
	public static Label findWithId(int id) 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = LabelManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		LabelManager.Create(stmt);
		String name = null;
		String image = null;
		String color = null;
		String sql = "select * from [dbo].[Labels] where id = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			rs.next();
			name = rs.getString("name");
			image = rs.getString("image");
			color = rs.getString("color");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LabelManager.Close(con, stmt, rs, pst);
			return null;
		}
		LabelManager.Close(con, stmt, rs, pst);
		if(name.isEmpty() || image.isEmpty() || color.isEmpty())
			return null;
		return new Label(id, name, image, color);
	}
	
	public static Label findWithName(String name) 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = LabelManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		LabelManager.Create(stmt);
		int id = -1;
		String image = null;
		String color = null;
		String sql = "select * from [dbo].[Labels] where convert(nvarchar(255),name) = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, name);
			rs = pst.executeQuery();
			rs.next();
			id = rs.getInt("id");
			image = rs.getString("image");
			color = rs.getString("color");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LabelManager.Close(con, stmt, rs, pst);
			return null;
		}
		LabelManager.Close(con, stmt, rs, pst);
		if(id == -1)
			return null;
		return new Label(id,name,image,color);
	}
	
	public static Label findWithColor(String color) 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = LabelManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		LabelManager.Create(stmt);
		int id = -1;
		String name = null;
		String image = null;
		String sql = "select * from [dbo].[Labels] where convert(nvarchar(255),color) = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, color);
			rs = pst.executeQuery();
			rs.next();
			id = rs.getInt("id");
			name = rs.getString("name");
			image = rs.getString("image");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LabelManager.Close(con, stmt, rs, pst);
			return null;
		}
		LabelManager.Close(con, stmt, rs, pst);
		if(id == -1)
			return null;
		return new Label(id,name,image,color);
	}
	
	public static boolean change(Label label) 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = LabelManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		LabelManager.Create(stmt);
		String sql = "UPDATE [dbo].[Labels] SET name = ?, image = ?, color = ? WHERE id = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, label.getName());
			pst.setString(2, label.getImage());
			pst.setString(3, label.getColor());
			pst.setInt(4, label.getId());
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LabelManager.Close(con, stmt, rs, pst);
			return false;
		}
		LabelManager.Close(con, stmt, rs, pst);
		return true;
	}
}
