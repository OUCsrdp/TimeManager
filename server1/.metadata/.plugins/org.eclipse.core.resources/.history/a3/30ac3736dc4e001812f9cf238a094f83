package main.model.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import main.model.moudle.*;;

public class TokenManager extends SqlServerManager
{	
	public static int add(String token) 
	{
		int id = -1;
		String sql_Insert = "INSERT INTO [dbo].[Token]" 
				+ "([token])" 
				+ "VALUES" 
				+ "(?)";
		try {
			PreparedStatement pst = con.prepareStatement(sql_Insert, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, token);
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
		String sql_Token = "DELETE FROM [dbo].[Token] WHERE id=?";
		try {
			PreparedStatement pst = con.prepareStatement(sql_Token);
			pst.setInt(1, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static Token findWithId(int id) 
	{
		String token = null;
		String sql = "select * from [dbo].[Token] where id = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			rs.next();
			token = rs.getString("token");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		if(token.isEmpty())
			return null;
		return new Token(id, token);
	}
	public static Token findWithToken(String token) 
	{
		int id = -1;
		String sql = "select * from [dbo].[Token] where convert(nvarchar(255),token) = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, token);
			rs = pst.executeQuery();
			rs.next();
			id = rs.getInt("id");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		if(id == -1)
			return null;
		return new Token(id, token);
	}
	
	public static boolean change(Token token) 
	{
		String sql = "UPDATE [dbo].[Token] SET token = ? WHERE id = ?";
		PreparedStatement pst;
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, token.getToken());
			pst.setInt(2, token.getId());
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
