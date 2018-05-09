package main.model.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import main.model.moudle.*;;

public class LikeManager extends SqlServerManager
{
	public static int add(int idUser, int idTS) 
	{
		int id = -1;
		String sql_Insert = "INSERT INTO [dbo].[Like]" 
				+ "([idUser],[idTS])" 
				+ "VALUES" 
				+ "(?, ?)";
		try {
			PreparedStatement pst = con.prepareStatement(sql_Insert, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, idUser);
			pst.setInt(2, idTS);
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
		String sql_User = "DELETE FROM [dbo].[Like] WHERE id=?";
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
	
	public static Like findWithId(int id) 
	{
		int idUser = -1;
		int idTS = -1;
		String sql = "select * from [dbo].[Like] where id = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			rs.next();
			idUser = rs.getInt("idUser");
			idTS = rs.getInt("idTS");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		if(idUser == -1 || idTS == -1)
			return null;
		return new Like(id, idUser, idTS);
	}
	
	public static ArrayList<Like> findWithIdUser(int idUser) 
	{
		ArrayList<Like> likes = new ArrayList<Like>();
		int id = -1;
		int idTS = -1;
		String sql = "select * from [dbo].[Like] where idUser = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
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
			return null;
		}
		if(id == -1)
			return null;
		return likes;
	}
	
	public static ArrayList<Like> findWithIdTS(int idTS) 
	{
		ArrayList<Like> likes = new ArrayList<Like>();
		int id = -1;
		int idUser = -1;
		String sql = "select * from [dbo].[Like] where idTS = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
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
			return null;
		}
		if(id == -1)
			return null;
		return likes;
	}
	
	public static boolean change(Like like) 
	{
		String sql = "UPDATE [dbo].[Like] SET idUser = ?, idTS = ? WHERE id = ?";
		PreparedStatement pst;
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, like.getIdUser());
			pst.setInt(2, like.getIdTS());
			pst.setInt(3, like.getId());
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
}