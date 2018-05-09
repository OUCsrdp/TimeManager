package main.model.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import main.model.moudle.* ;

public class AffairManager extends SqlServerManager
{	
	public static int add(int idTS, int idLabel, int satisfaction, String name, String tips, String timeStart, String timeEnd, String timeEndPlan) 
	{
		int id = -1;
		String sql_Insert = "INSERT INTO [dbo].[Affairs]" 
				+ "([idTS], [idLabel], [satisfaction], [name], [tips], [timeStart], [timeEnd], [timeEndPlan])" 
				+ "VALUES" 
				+ "(?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement pst = con.prepareStatement(sql_Insert, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, idTS);
			pst.setInt(2, idLabel);
			pst.setInt(3, satisfaction);
			pst.setString(4, name);
			pst.setString(5, tips);
			pst.setString(6, timeStart);
			pst.setString(7, timeEnd);
			pst.setString(8, timeEndPlan);
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
		String sql_Affair = "DELETE FROM [dbo].[Affairs] WHERE id=?";
		try {
			PreparedStatement pst = con.prepareStatement(sql_Affair);
			pst.setInt(1, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static Affair findWithId(int id) 
	{
		int idTS = -1;
		int idLabel = -1;
		int satisfaction = -1;
		String name = null;
		String tips = null;
		String timeStart = null;
		String timeEnd = null;
		String timeEndPlan = null;
		String sql = "select * from [dbo].[Affairs] where id = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			rs.next();
			idTS = rs.getInt("idTS");
			idLabel = rs.getInt("idLabel");
			satisfaction = rs.getInt("satisfaction");
			name = rs.getString("name");
			tips = rs.getString("tips");
			timeStart = rs.getString("timeStart");
			timeEnd = rs.getString("timeEnd");
			timeEndPlan = rs.getString("timeEndPlan");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		if(idTS == -1)
			return null;
		else
			return new Affair(id, idTS, idLabel, satisfaction, name, tips, timeStart, timeEnd, timeEndPlan);
	}
	
	public static ArrayList<Affair> findWithIdTS(int idTS) 
	{
		ArrayList<Affair> affairs = new ArrayList<Affair>();
		int id = -1;
		int idLabel = -1;
		int satisfaction = -1;
		String name = null;
		String tips = null;
		String timeStart = null;
		String timeEnd = null;
		String timeEndPlan = null;
		String sql = "select * from [dbo].[Affairs] where idTS = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, idTS);
			rs = pst.executeQuery();
			while(rs.next()) 
			{
				id = rs.getInt("id");
				idLabel = rs.getInt("idLabel");
				satisfaction = rs.getInt("satisfaction");
				name = rs.getString("name");
				tips = rs.getString("tips");
				timeStart = rs.getString("timeStart");
				timeEnd = rs.getString("timeEnd");
				timeEndPlan = rs.getString("timeEndPlan");
				affairs.add(new Affair(id, idTS, idLabel, satisfaction, name, tips, timeStart, timeEnd, timeEndPlan));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		if(id == -1)
			return null;
		else
			return affairs;
	}
	
	public static ArrayList<Affair> findWithIdLabel(int idLabel) 
	{
		ArrayList<Affair> affairs = new ArrayList<Affair>();
		int id = -1;
		int idTS = -1;
		int satisfaction = -1;
		String name = null;
		String tips = null;
		String timeStart = null;
		String timeEnd = null;
		String timeEndPlan = null;
		String sql = "select * from [dbo].[Affairs] where idLabel = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, idLabel);
			rs = pst.executeQuery();
			while(rs.next()) 
			{
				id = rs.getInt("id");
				idTS = rs.getInt("idTS");
				satisfaction = rs.getInt("satisfaction");
				name = rs.getString("name");
				tips = rs.getString("tips");
				timeStart = rs.getString("timeStart");
				timeEnd = rs.getString("timeEnd");
				timeEndPlan = rs.getString("timeEndPlan");
				affairs.add(new Affair(id, idTS, idLabel, satisfaction, name, tips, timeStart, timeEnd, timeEndPlan));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		if(id == -1)
			return null;
		else
			return affairs;
	}
	
	public static ArrayList<Affair> findWithSatisfaction(int satisfaction) 
	{
		ArrayList<Affair> affairs = new ArrayList<Affair>();
		int id = -1;
		int idTS = -1;
		int idLabel = -1;
		String name = null;
		String tips = null;
		String timeStart = null;
		String timeEnd = null;
		String timeEndPlan = null;
		String sql = "select * from [dbo].[Affairs] where satisfaction = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, satisfaction);
			rs = pst.executeQuery();
			while(rs.next()) 
			{
				id = rs.getInt("id");
				idTS = rs.getInt("idTS");
				idLabel = rs.getInt("idLabel");
				name = rs.getString("name");
				tips = rs.getString("tips");
				timeStart = rs.getString("timeStart");
				timeEnd = rs.getString("timeEnd");
				timeEndPlan = rs.getString("timeEndPlan");
				affairs.add(new Affair(id, idTS, idLabel, satisfaction, name, tips, timeStart, timeEnd, timeEndPlan));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		if(id == -1)
			return null;
		else
			return affairs;
	}
	
	public static ArrayList<Affair> findWithName(String name)
	{
		ArrayList<Affair> affairs = new ArrayList<Affair>();
		int id = -1;
		int idTS = -1;
		int idLabel = -1;
		int satisfaction = -1;
		String tips = null;
		String timeStart = null;
		String timeEnd = null;
		String timeEndPlan = null;
		String sql = "select * from [dbo].[Affairs] where name = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, name);
			rs = pst.executeQuery();
			while(rs.next()) 
			{
				id = rs.getInt("id");
				idTS = rs.getInt("idTS");
				idLabel = rs.getInt("idLabel");
				satisfaction = rs.getInt("satisfaction");
				tips = rs.getString("tips");
				timeStart = rs.getString("timeStart");
				timeEnd = rs.getString("timeEnd");
				timeEndPlan = rs.getString("timeEndPlan");
				affairs.add(new Affair(id, idTS, idLabel, satisfaction, name, tips, timeStart, timeEnd, timeEndPlan));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		if(id == -1)
			return null;
		else
			return affairs;
	}
	
	public static boolean change(Affair affair) 
	{
		String sql = "UPDATE [dbo].[Affairs] SET idTS = ?, idLabel = ?, satisfaction = ?, name = ?, tips = ?, timeStart = ?, timeEnd = ?, timeEndPlan = ? WHERE id = ?";
		PreparedStatement pst;
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, affair.getIdTS());
			pst.setInt(2, affair.getIdLabel());
			pst.setInt(3, affair.getSatisfaction());
			pst.setString(4, affair.getName());
			pst.setString(5, affair.getTips());
			pst.setString(6, affair.getTimeStart());
			pst.setString(7, affair.getTimeEnd());
			pst.setString(8, affair.getTimeEndPlan());
			pst.setInt(9, affair.getId());
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
}