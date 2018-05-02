package main.model.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import main.model.moudle.*;;

public class ScheduleManager extends SqlServerManager
{	
	public static int add(int idUser, String date, int weekday) 
	{
		int id = -1;
		String sql_Insert = "INSERT INTO [dbo].[Schedule]" 
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
		String sql_Schedule = "DELETE FROM [dbo].[Schedule] WHERE id=?";
		try {
			PreparedStatement pst = con.prepareStatement(sql_Schedule);
			pst.setInt(1, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static Schedule findWithId(int id) 
	{
		int idUser = -1;
		String date = null;
		int weekday = -1;
		String sql = "select * from [dbo].[Schedule] where id = ?";
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
		if(idUser == -1)
			return null;
		return new Schedule(id, idUser, date, weekday);
	}
	
	public static ArrayList<Schedule> findWithIdUser(int idUser) 
	{
		ArrayList<Schedule> schedules = new ArrayList<Schedule>();
		int id = -1;
		String date = null;
		int weekday = -1;
		String sql = "select * from [dbo].[Schedule] where idUser = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, idUser);
			rs = pst.executeQuery();
			while(rs.next()) 
			{
				id = rs.getInt("id");
				date = rs.getString("date");
				weekday = rs.getInt("weekday");
				schedules.add(new Schedule(id, idUser, date, weekday));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		if(id == -1)
			return null;
		return schedules;
	}
	public static ArrayList<Schedule> findWithDate(String date) 
	{
		ArrayList<Schedule> schedules = new ArrayList<Schedule>();
		int id = -1;
		int idUser = -1;
		int weekday = -1;
		String sql = "select * from [dbo].[Schedule] where date = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, date);
			rs = pst.executeQuery();
			while(rs.next()) 
			{
				id = rs.getInt("id");
				idUser = rs.getInt("idUser");
				weekday = rs.getInt("weekday");
				schedules.add(new Schedule(id, idUser, date, weekday));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		if(id == -1)
			return null;
		return schedules;
	}
	public static ArrayList<Schedule> findWithWeekday(int weekday) 
	{
		ArrayList<Schedule> schedules = new ArrayList<Schedule>();
		int id = -1;
		int idUser = -1;
		String date = null;
		String sql = "select * from [dbo].[Schedule] where weekday = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, weekday);
			rs = pst.executeQuery();
			while(rs.next()) 
			{
				id = rs.getInt("id");
				idUser = rs.getInt("idUser");
				date  = rs.getString("date");
				schedules.add(new Schedule(id, idUser, date, weekday));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		if(id == -1)
			return null;
		return schedules;
	}
	
	public static boolean change(Schedule schedule) 
	{
		String sql = "UPDATE [dbo].[Schedule] SET idUser = ?, date = ?, weekday = ? WHERE id = ?";
		PreparedStatement pst;
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, schedule.getIdUser());
			pst.setString(2, schedule.getDate());
			pst.setInt(3, schedule.getWeekday());
			pst.setInt(4, schedule.getId());
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
