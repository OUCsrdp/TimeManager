package main.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import main.model.moudle.*;;

public class TokenManager extends SqlServerManager
{	
	public static int add(String token) 
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
		int id = -1;
		String sql_Insert = "INSERT INTO [dbo].[Token]" 
				+ "([token])" 
				+ "VALUES" 
				+ "(?)";
		try {
			pst = con.prepareStatement(sql_Insert, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, token);
            pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            rs.next();
            id = rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			TokenManager.Close(con, stmt, rs, pst);
			return -1;
		}
		TokenManager.Close(con, stmt, rs, pst);
        return id;
	}

	public static boolean delete(int id) 
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
		String sql_Token = "DELETE FROM [dbo].[Token] WHERE id=?";
		try {
			pst = con.prepareStatement(sql_Token);
			pst.setInt(1, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			TokenManager.Close(con, stmt, rs, pst);
			return false;
		}
		TokenManager.Close(con, stmt, rs, pst);
		return true;
	}
	
	public static ArrayList<Token> findWithNothing()
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
		ArrayList<Token> tokens = new ArrayList<Token>();
		int id = -1;
		String token = null;
		String sql = "select * from [dbo].[Token]";
		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next())
			{
				id = rs.getInt("id");
				token = rs.getString("token");
				tokens.add(new Token(id,token));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			TokenManager.Close(con, stmt, rs, pst);
			return null;
		}
		TokenManager.Close(con, stmt, rs, pst);
		if(id == -1)
			return null;
		return tokens;
	}
	
	public static Token findWithId(int id) 
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
		String token = null;
		String sql = "select * from [dbo].[Token] where id = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			rs.next();
			token = rs.getString("token");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			TokenManager.Close(con, stmt, rs, pst);
			return null;
		}
		TokenManager.Close(con, stmt, rs, pst);
		if(token.isEmpty())
			return null;
		return new Token(id, token);
	}
	public static Token findWithToken(String token) 
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
		int id = -1;
		String sql = "select * from [dbo].[Token] where convert(nvarchar(255),token) = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, token);
			rs = pst.executeQuery();
			rs.next();
			id = rs.getInt("id");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			TokenManager.Close(con, stmt, rs, pst);
			return null;
		}
		TokenManager.Close(con, stmt, rs, pst);
		if(id == -1)
			return null;
		return new Token(id, token);
	}
	
	public static boolean change(Token token) 
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
		String sql = "UPDATE [dbo].[Token] SET token = ? WHERE id = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, token.getToken());
			pst.setInt(2, token.getId());
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			TokenManager.Close(con, stmt, rs, pst);
			return false;
		}
		TokenManager.Close(con, stmt, rs, pst);
		return true;
	}
}
