package main.model.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import main.model.moudle.*;;

public class S_AffairManager extends SqlServerManager 
{
	public static int add(int idTS, int idS, int idLabel, int satisfaction, String isImportant, String name, String tips, String timeStart, String timeEnd, String timeStartPlan, String timeEndPlan, String timeStartAlarm, String timeEndAlarm) 
	{
		int id = -1;
		String sql_Insert = "INSERT INTO [dbo].[S_Affairs]" 
				+ "([idTS], [idS], [idLabel], [satisfaction], [isImportant], [name], [tips], [timeStart], [timeEnd], [timeStartPlan], [timeEndPlan], [timeStartAlarm], [timeEndAlarm])" 
				+ "VALUES" 
				+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement pst = con.prepareStatement(sql_Insert, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, idTS);
			pst.setInt(2, idS);
			pst.setInt(3, idLabel);
			pst.setInt(4, satisfaction);
			pst.setString(5, isImportant);
			pst.setString(6, name);
			pst.setString(7, tips);
			pst.setString(8, timeStart);
			pst.setString(9, timeEnd);
			pst.setString(10, timeStartPlan);
			pst.setString(11, timeEndPlan);
			pst.setString(12, timeStartAlarm);
			pst.setString(13, timeEndAlarm);
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
		String sql_S_Affair = "DELETE FROM [dbo].[S_Affairs] WHERE id=?";
		try {
			PreparedStatement pst = con.prepareStatement(sql_S_Affair);
			pst.setInt(1, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static S_Affair findWithId(int id) 
	{
		int idTS = -1; 
		int idS = -1;
		int idLabel = -1; 
		int satisfaction = -1; 
		String isImportant = null;
		String name = null;
		String tips = null;
		String timeStart = null;
		String timeEnd = null;
		String timeStartPlan = null;
		String timeEndPlan = null;
		String timeStartAlarm = null;
		String timeEndAlarm = null;
		String sql = "select * from [dbo].[S_Affairs] where id = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			rs.next();
			idTS = rs.getInt("idTS");
			idS = rs.getInt("idS");
			idLabel = rs.getInt("idLabel");
			satisfaction = rs.getInt("satisfaction");
			isImportant = rs.getString("isImportant");
			name = rs.getString("name");
			tips = rs.getString("tips");
			timeStart = rs.getString("timeStart");
			timeEnd = rs.getString("timeEnd");
			timeStartPlan = rs.getString("timeStartPlan");
			timeEndPlan = rs.getString("timeEndPlan");
			timeStartAlarm = rs.getString("timeStartAlarm");
			timeEndAlarm = rs.getString("timeEndAlarm");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		if(idTS == -1 || idS == -1 || idLabel == -1)
			return null;
		return new S_Affair(id, idTS, idS, idLabel, satisfaction, isImportant, name, tips, timeStart, timeEnd, timeStartPlan, timeEndPlan, timeStartAlarm, timeEndAlarm);
	}
	
	public static ArrayList<S_Affair> findWithIdLabel(int idLabel) 
	{
		ArrayList<S_Affair> s_Affairs = new ArrayList<S_Affair>();
		int id = -1;
		int idTS = -1;
		int idS = -1;
		int satisfaction = -1;
		String isImportant = null;
		String name = null;
		String tips = null;
		String timeStart = null;
		String timeEnd = null;
		String timeStartPlan = null;
		String timeEndPlan = null;
		String timeStartAlarm = null;
		String timeEndAlarm = null;
		String sql = "select * from [dbo].[S_Affairs] where idLabel = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, idLabel);
			rs = pst.executeQuery();
			while(rs.next()) 
			{
				id = rs.getInt("id");
				idTS = rs.getInt("idTS");
				idS = rs.getInt("idS");
				satisfaction = rs.getInt("satisfaction");
				isImportant = rs.getString("isImportant");
				name = rs.getString("name");
				tips = rs.getString("tips");
				timeStart = rs.getString("timeStart");
				timeEnd = rs.getString("timeEnd");
				timeStartPlan = rs.getString("timeStartPlan");
				timeEndPlan = rs.getString("timeEndPlan");
				timeStartAlarm = rs.getString("timeStartAlarm");
				timeEndAlarm = rs.getString("timeEndAlarm");
				s_Affairs.add(new S_Affair(id, idTS, idS, idLabel, satisfaction, isImportant, name, tips, timeStart, timeEnd, timeStartPlan, timeEndPlan, timeStartAlarm, timeEndAlarm));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		if(id == -1)
			return null;
		return s_Affairs;
	}
	
	public static ArrayList<S_Affair> findWithIdTS(int idTS) 
	{
		ArrayList<S_Affair> s_Affairs = new ArrayList<S_Affair>();
		int id = -1;
		int idS = -1;
		int idLabel = -1;
		int satisfaction = -1;
		String isImportant = null;
		String name = null;
		String tips = null;
		String timeStart = null;
		String timeEnd = null;
		String timeStartPlan = null;
		String timeEndPlan = null;
		String timeStartAlarm = null;
		String timeEndAlarm = null;
		String sql = "select * from [dbo].[S_Affairs] where idTS = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, idTS);
			rs = pst.executeQuery();
			while(rs.next()) 
			{
				id = rs.getInt("id");
				idS = rs.getInt("idS");
				idLabel = rs.getInt("idLabel");
				satisfaction = rs.getInt("satisfaction");
				isImportant = rs.getString("isImportant");
				name = rs.getString("name");
				tips = rs.getString("tips");
				timeStart = rs.getString("timeStart");
				timeEnd = rs.getString("timeEnd");
				timeStartPlan = rs.getString("timeStartPlan");
				timeEndPlan = rs.getString("timeEndPlan");
				timeStartAlarm = rs.getString("timeStartAlarm");
				timeEndAlarm = rs.getString("timeEndAlarm");
				s_Affairs.add(new S_Affair(id, idTS, idS, idLabel, satisfaction, isImportant, name, tips, timeStart, timeEnd, timeStartPlan, timeEndPlan, timeStartAlarm, timeEndAlarm));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		if(id == -1)
			return null;
		return s_Affairs;
	}
	
	public static ArrayList<S_Affair> findWithIdS(int idS) 
	{
		ArrayList<S_Affair> s_Affairs = new ArrayList<S_Affair>();
		int id = -1;
		int idTS = -1;
		int idLabel = -1;
		int satisfaction = -1;
		String isImportant = null;
		String name = null;
		String tips = null;
		String timeStart = null;
		String timeEnd = null;
		String timeStartPlan = null;
		String timeEndPlan = null;
		String timeStartAlarm = null;
		String timeEndAlarm = null;
		String sql = "select * from [dbo].[S_Affairs] where idS = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, idS);
			rs = pst.executeQuery();
			while(rs.next()) 
			{
				id = rs.getInt("id");
				idTS = rs.getInt("idTS");
				idLabel = rs.getInt("idLabel");
				satisfaction = rs.getInt("satisfaction");
				isImportant = rs.getString("isImportant");
				name = rs.getString("name");
				tips = rs.getString("tips");
				timeStart = rs.getString("timeStart");
				timeEnd = rs.getString("timeEnd");
				timeStartPlan = rs.getString("timeStartPlan");
				timeEndPlan = rs.getString("timeEndPlan");
				timeStartAlarm = rs.getString("timeStartAlarm");
				timeEndAlarm = rs.getString("timeEndAlarm");
				s_Affairs.add(new S_Affair(id, idTS, idS, idLabel, satisfaction, isImportant, name, tips, timeStart, timeEnd, timeStartPlan, timeEndPlan, timeStartAlarm, timeEndAlarm));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		if(id == -1)
			return null;
		return s_Affairs;
	}
	
	public static ArrayList<S_Affair> findWithSatisfaction(int satisfaction) 
	{
		ArrayList<S_Affair> s_Affairs = new ArrayList<S_Affair>();
		int id = -1;
		int idTS = -1;
		int idS = -1;
		int idLabel = -1;
		String isImportant = null;
		String name = null;
		String tips = null;
		String timeStart = null;
		String timeEnd = null;
		String timeStartPlan = null;
		String timeEndPlan = null;
		String timeStartAlarm = null;
		String timeEndAlarm = null;
		String sql = "select * from [dbo].[S_Affairs] where satisfaction = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, idS);
			rs = pst.executeQuery();
			while(rs.next()) 
			{
				id = rs.getInt("id");
				idTS = rs.getInt("idTS");
				idS = rs.getInt("idS");
				idLabel = rs.getInt("idLabel");
				isImportant = rs.getString("isImportant");
				name = rs.getString("name");
				tips = rs.getString("tips");
				timeStart = rs.getString("timeStart");
				timeEnd = rs.getString("timeEnd");
				timeStartPlan = rs.getString("timeStartPlan");
				timeEndPlan = rs.getString("timeEndPlan");
				timeStartAlarm = rs.getString("timeStartAlarm");
				timeEndAlarm = rs.getString("timeEndAlarm");
				s_Affairs.add(new S_Affair(id, idTS, idS, idLabel, satisfaction, isImportant, name, tips, timeStart, timeEnd, timeStartPlan, timeEndPlan, timeStartAlarm, timeEndAlarm));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		if(id == -1)
			return null;
		return s_Affairs;
	}
	
	public static ArrayList<S_Affair> findWithName(String name) 
	{
		ArrayList<S_Affair> s_Affairs = new ArrayList<S_Affair>();
		int id = -1;
		int idTS = -1;
		int idS = -1;
		int idLabel = -1;
		int satisfaction = -1;
		String isImportant = null;
		String tips = null;
		String timeStart = null;
		String timeEnd = null;
		String timeStartPlan = null;
		String timeEndPlan = null;
		String timeStartAlarm = null;
		String timeEndAlarm = null;
		String sql = "select * from [dbo].[S_Affairs] where name = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, name);
			rs = pst.executeQuery();
			while(rs.next()) 
			{
				id = rs.getInt("id");
				idTS = rs.getInt("idTS");
				idS = rs.getInt("idS");
				idLabel = rs.getInt("idLabel");
				satisfaction = rs.getInt("satisfaction");
				isImportant = rs.getString("isImportant");
				tips = rs.getString("tips");
				timeStart = rs.getString("timeStart");
				timeEnd = rs.getString("timeEnd");
				timeStartPlan = rs.getString("timeStartPlan");
				timeEndPlan = rs.getString("timeEndPlan");
				timeStartAlarm = rs.getString("timeStartAlarm");
				timeEndAlarm = rs.getString("timeEndAlarm");
				s_Affairs.add(new S_Affair(id, idTS, idS, idLabel, satisfaction, isImportant, name, tips, timeStart, timeEnd, timeStartPlan, timeEndPlan, timeStartAlarm, timeEndAlarm));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		if(id == -1)
			return null;
		return s_Affairs;
	}
	
	public static ArrayList<S_Affair> findWithIsImportant(String isImportant) 
	{
		ArrayList<S_Affair> s_Affairs = new ArrayList<S_Affair>();
		int id = -1;
		int idTS = -1;
		int idS = -1;
		int idLabel = -1;
		int satisfaction = -1;
		String name = null;
		String tips = null;
		String timeStart = null;
		String timeEnd = null;
		String timeStartPlan = null;
		String timeEndPlan = null;
		String timeStartAlarm = null;
		String timeEndAlarm = null;
		String sql = "select * from [dbo].[S_Affairs] where isImportant = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, isImportant);
			rs = pst.executeQuery();
			while(rs.next()) 
			{
				id = rs.getInt("id");
				idTS = rs.getInt("idTS");
				idS = rs.getInt("idS");
				idLabel = rs.getInt("idLabel");
				satisfaction = rs.getInt("satisfaction");
				name = rs.getString("name");
				tips = rs.getString("tips");
				timeStart = rs.getString("timeStart");
				timeEnd = rs.getString("timeEnd");
				timeStartPlan = rs.getString("timeStartPlan");
				timeEndPlan = rs.getString("timeEndPlan");
				timeStartAlarm = rs.getString("timeStartAlarm");
				timeEndAlarm = rs.getString("timeEndAlarm");
				s_Affairs.add(new S_Affair(id, idTS, idS, idLabel, satisfaction, isImportant, name, tips, timeStart, timeEnd, timeStartPlan, timeEndPlan, timeStartAlarm, timeEndAlarm));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		if(id == -1)
			return null;
		return s_Affairs;
	}
	
	public static boolean change(S_Affair s_Affair) 
	{
		String sql = "UPDATE [dbo].[S_Affairs] SET idTS = ?, idS = ?, idLabel = ?, satisfaction = ?, isImportant = ?, name = ?, tips = ?, timeStart = ?, timeEnd = ?, timeStartPlan = ?, timeEndPlan = ?, timeStartAlarm = ?, timeEndAlarm = ? WHERE id = ?";
		PreparedStatement pst;
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, s_Affair.getIdTS());
			pst.setInt(2, s_Affair.getIdS());
			pst.setInt(3, s_Affair.getIdLabel());
			pst.setInt(4, s_Affair.getSatisfaction());
			pst.setString(5, s_Affair.getIsImportant());
			pst.setString(6, s_Affair.getName());
			pst.setString(7, s_Affair.getTips());
			pst.setString(8, s_Affair.getTimeStart());
			pst.setString(9, s_Affair.getTimeEnd());
			pst.setString(10, s_Affair.getTimeStartPlan());
			pst.setString(11, s_Affair.getTimeEndPlan());
			pst.setString(12, s_Affair.getTimeStartAlarm());
			pst.setString(13, s_Affair.getTimeEndAlarm());
			pst.setInt(14, s_Affair.getId());
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
