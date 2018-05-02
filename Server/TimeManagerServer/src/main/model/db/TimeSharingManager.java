package main.model.db;

import java.sql.* ;
import java.util.ArrayList;
import main.model.moudle.*;;

public class TimeSharingManager extends SqlServerManager
{

	public static int add(int idUser,String date,int weekday) 
	{
		int id = -1;
		String sql_Insert = "INSERT INTO [dbo].[TimeSharing]" 
				+ "([idUser], [date], [weekday])" 
				+ "VALUES" 
				+ "(?, ?, ?)";
		try {
			PreparedStatement pst = con.prepareStatement(sql_Insert, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, idUser);
			pst.setString(2, date);
			pst.setInt(3, weekday);
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
		String sql_TimeSharing = "DELETE FROM [dbo].[TimeSharing] WHERE id=?";
		try {
			PreparedStatement pst = con.prepareStatement(sql_TimeSharing);
			pst.setInt(1, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static TimeSharing findWithId(int id) 
	{
		int idUser = -1;
		String date = null;
		int weekday = -1;
		String sql = "select * from [dbo].[TimeSharing] where id = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			rs.next();
			idUser = rs.getInt("idUser");
			date = rs.getString("date");
			weekday = rs.getInt("weekday");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		if(idUser == -1 || weekday == -1)
			return null;
		return new TimeSharing(id, idUser, date, weekday);
	}
	
	public static ArrayList<TimeSharing> findWithIdUser(int idUser) 
	{
		ArrayList<TimeSharing> timeSharing = new ArrayList<TimeSharing>();
		int id = -1;
		String date = null;
		int weekday;
		String sql = "select * from [dbo].[TimeSharing] where idUser = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, idUser);
			rs = pst.executeQuery();
			while(rs.next()) 
			{
				id = rs.getInt("id");
				date = rs.getString("date");
				weekday = rs.getInt("weekday");
				timeSharing.add(new TimeSharing(id, idUser, date, weekday));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		if(id == -1)
			return null;
		return timeSharing;
	}
	
	public static ArrayList<TimeSharing> findWithDate(String date) 
	{
		ArrayList<TimeSharing> timeSharing = new ArrayList<TimeSharing>();
		int id = -1;
		int idUser = -1;
		int weekday = -1;
		String sql = "select * from [dbo].[TimeSharing] where date = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, date);
			rs = pst.executeQuery();
			while(rs.next()) 
			{
				id = rs.getInt("id");
				idUser = rs.getInt("idUser");
				weekday = rs.getInt("weekday");
				timeSharing.add(new TimeSharing(id, idUser, date, weekday));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		if(id == -1)
			return null;
		return timeSharing;
	}
	public static ArrayList<TimeSharing> findWithWeekday(int weekday) 
	{
		ArrayList<TimeSharing> timeSharing = new ArrayList<TimeSharing>();
		int id = -1;
		int idUser = -1;
		String date = null;
		String sql = "select * from [dbo].[TimeSharing] where weekday = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, weekday);
			rs = pst.executeQuery();
			while(rs.next()) 
			{
				id = rs.getInt("id");
				idUser = rs.getInt("idUser");
				date = rs.getString("date");
				timeSharing.add(new TimeSharing(id, idUser, date, weekday));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		if(id == -1)
			return null;
		return timeSharing;
	}
	
	public static boolean change(TimeSharing timeSharing)
	{
		String sql = "UPDATE [dbo].[TimeSharing] SET idUSer = ?, date = ?, weekday = ? WHERE id = ?";
		PreparedStatement pst;
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, timeSharing.getIdUser());
			pst.setString(2, timeSharing.getDate());
			pst.setInt(3, timeSharing.getWeekday());
			pst.setInt(4, timeSharing.getId());
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
