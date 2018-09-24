package main.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import main.model.moudle.*;

public class CollectionManager extends SqlServerManager
{
	public static int add(int idUser, int idTS) 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = CollectionManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		CollectionManager.Create(stmt);
		int id = -1;
		String sql_Insert = "INSERT INTO [dbo].[Collection]" 
				+ "([idUser], [idTS])" 
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
			CollectionManager.Close(con, stmt, rs, pst);
			return -1;
		}
		CollectionManager.Close(con, stmt, rs, pst);
        return id;
	}
	
	public static boolean delete(int id) 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = CollectionManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		CollectionManager.Create(stmt);
		String sql_Collection = "DELETE FROM [dbo].[Collection] WHERE id=?";
		try {
			pst = con.prepareStatement(sql_Collection);
			pst.setInt(1, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			CollectionManager.Close(con, stmt, rs, pst);
			return false;
		}
		CollectionManager.Close(con, stmt, rs, pst);
		return true;
	}
	
	public static ArrayList<Collection> findWithNothing()
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = CollectionManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		CollectionManager.Create(stmt);
		ArrayList<Collection> collections = new ArrayList<Collection>();
		int id = -1;
		int idUser = -1;
		int idTS = -1;
		String sql = "select * from [dbo].[Collection]";
		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()) 
			{
				id = rs.getInt("id");
				idUser = rs.getInt("idUser");
				idTS = rs.getInt("idTS");
				collections.add(new Collection(id, idUser, idTS));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			CollectionManager.Close(con, stmt, rs, pst);
			return null;
		}
		CollectionManager.Close(con, stmt, rs, pst);
		if(id == -1)
			return null;
		else
			return collections;
	}
	
	public static Collection findWithId(int id) 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = CollectionManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		CollectionManager.Create(stmt);
		int idUser = -1;
		int idTS = -1;
		String sql = "select * from [dbo].[Collection] where id = ?";
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
			CollectionManager.Close(con, stmt, rs, pst);
			return null;
		}
		CollectionManager.Close(con, stmt, rs, pst);
		if(idUser == -1 || idTS == -1)
			return null;
		else
			return new Collection(id, idUser, idTS);
	}
	
	public static ArrayList<Collection> findWithIdUser(int idUser) 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = CollectionManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		CollectionManager.Create(stmt);
		ArrayList<Collection> collections = new ArrayList<Collection>();
		int id = -1;
		int idTS = -1;
		String sql = "select * from [dbo].[Collection] where idUser = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, idUser);
			rs = pst.executeQuery();
			while(rs.next()) 
			{
				id = rs.getInt("id");
				idTS = rs.getInt("idTS");
				collections.add(new Collection(id, idUser, idTS));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			CollectionManager.Close(con, stmt, rs, pst);
			return null;
		}
		CollectionManager.Close(con, stmt, rs, pst);
		if(id == -1)
			return null;
		else
			return collections;
	}
	
	public static ArrayList<Collection> findWithIdTS(int idTS) 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = CollectionManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		CollectionManager.Create(stmt);
		ArrayList<Collection> collections = new ArrayList<Collection>();
		int id = -1;
		int idUser = -1;
		String sql = "select * from [dbo].[Collection] where idTS = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, idTS);
			rs = pst.executeQuery();
			while(rs.next()) 
			{
				id = rs.getInt("id");
				idUser = rs.getInt("idUser");
				collections.add(new Collection(id, idUser, idTS));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			CollectionManager.Close(con, stmt, rs, pst);
			return null;
		}
		CollectionManager.Close(con, stmt, rs, pst);
		if(id == -1)
			return null;
		else
			return collections;
	}
	
	public static boolean change(Collection collection) 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = CollectionManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		CollectionManager.Create(stmt);
		String sql = "UPDATE [dbo].[Collection] SET idUser = ?, idTS = ? WHERE id = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, collection.getIdUser());
			pst.setInt(2, collection.getIdTS());
			pst.setInt(3, collection.getId());
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			CollectionManager.Close(con, stmt, rs, pst);
			return false;
		}
		CollectionManager.Close(con, stmt, rs, pst);
		return true;
	}
}
