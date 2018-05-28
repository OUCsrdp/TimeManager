package main.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import main.model.moudle.*;;

public class LikeManager extends SqlServerManager
{
	public static int add(int idUser, int idTS) 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = LikeManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		LikeManager.Create(stmt);
		int id = -1;
		String sql_Insert = "INSERT INTO [dbo].[Like]" 
				+ "([idUser],[idTS])" 
				+ "VALUES" 
				+ "(?, ?)";
		try {
			pst = con.prepareStatement(sql_Insert, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, idUser);
			pst.setInt(2, idTS);
            pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            rs.next();
            id = rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LikeManager.Close(con, stmt, rs, pst);
			return -1;
		}
		LikeManager.Close(con, stmt, rs, pst);
        return id;
	}

	public static boolean delete(int id) 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = LikeManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		LikeManager.Create(stmt);
		String sql_User = "DELETE FROM [dbo].[Like] WHERE id=?";
		try {
			pst = con.prepareStatement(sql_User);
			pst.setInt(1, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LikeManager.Close(con, stmt, rs, pst);
			return false;
		}
		LikeManager.Close(con, stmt, rs, pst);
		return true;
	}
	
	public static ArrayList<Like> findWithNothing()
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = LikeManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		LikeManager.Create(stmt);
		ArrayList<Like> likes = new ArrayList<Like>();
		int id = -1;
		int idUser = -1;
		int idTS = -1;
		String sql = "select * from [dbo].[Like]";
		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()) 
			{
				id = rs.getInt("id");
				idUser = rs.getInt("idUser");
				idTS = rs.getInt("idTS");
				likes.add(new Like(id, idUser, idTS));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LikeManager.Close(con, stmt, rs, pst);
			return null;
		}
		LikeManager.Close(con, stmt, rs, pst);
		if(id == -1)
			return null;
		return likes;
	}
	
	public static Like findWithId(int id) 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = LikeManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		LikeManager.Create(stmt);
		int idUser = -1;
		int idTS = -1;
		String sql = "select * from [dbo].[Like] where id = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			rs.next();
			idUser = rs.getInt("idUser");
			idTS = rs.getInt("idTS");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LikeManager.Close(con, stmt, rs, pst);
			return null;
		}
		LikeManager.Close(con, stmt, rs, pst);
		if(idUser == -1 || idTS == -1)
			return null;
		return new Like(id, idUser, idTS);
	}
	
	public static ArrayList<Like> findWithIdUser(int idUser) 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = LikeManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		LikeManager.Create(stmt);
		ArrayList<Like> likes = new ArrayList<Like>();
		int id = -1;
		int idTS = -1;
		String sql = "select * from [dbo].[Like] where idUser = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, idUser);
			rs = pst.executeQuery();
			while(rs.next()) 
			{
				id = rs.getInt("id");
				idTS = rs.getInt("idTS");
				likes.add(new Like(id, idUser, idTS));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LikeManager.Close(con, stmt, rs, pst);
			return null;
		}
		LikeManager.Close(con, stmt, rs, pst);
		if(id == -1)
			return null;
		return likes;
	}
	
	public static ArrayList<Like> findWithIdTS(int idTS) 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = LikeManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		LikeManager.Create(stmt);
		ArrayList<Like> likes = new ArrayList<Like>();
		int id = -1;
		int idUser = -1;
		String sql = "select * from [dbo].[Like] where idTS = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, idTS);
			rs = pst.executeQuery();
			while(rs.next()) 
			{
				id = rs.getInt("id");
				idUser = rs.getInt("idUser");
				likes.add(new Like(id, idUser, idTS));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LikeManager.Close(con, stmt, rs, pst);
			return null;
		}
		LikeManager.Close(con, stmt, rs, pst);
		if(id == -1)
			return null;
		return likes;
	}
	
	public static boolean change(Like like) 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = LikeManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		LikeManager.Create(stmt);
		String sql = "UPDATE [dbo].[Like] SET idUser = ?, idTS = ? WHERE id = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, like.getIdUser());
			pst.setInt(2, like.getIdTS());
			pst.setInt(3, like.getId());
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LikeManager.Close(con, stmt, rs, pst);
			return false;
		}
		LikeManager.Close(con, stmt, rs, pst);
		return true;
	}
}
