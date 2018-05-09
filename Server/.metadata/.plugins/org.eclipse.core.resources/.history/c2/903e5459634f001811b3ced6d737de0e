package main.model.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import main.model.moudle.*;;

public class EventHourManager extends SqlServerManager
{
	public static int add(float numEvents, String isWorkday, int timeArea) 
	{
		int id = -1;
		String sql_Insert = "INSERT INTO [dbo].[EventHour]" 
				+ "([numEvents], [isWorkday], [timeArea])" 
				+ "VALUES" 
				+ "(?, ?, ?)";
		try {
			PreparedStatement pst = con.prepareStatement(sql_Insert, Statement.RETURN_GENERATED_KEYS);
			pst.setFloat(1, numEvents);
			pst.setString(2, isWorkday);
			pst.setInt(3, timeArea);
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
		String sql_EventHour = "DELETE FROM [dbo].[EventHour] WHERE id=?";
		try {
			PreparedStatement pst = con.prepareStatement(sql_EventHour);
			pst.setInt(1, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static EventHour findWithId(int id) 
	{
		float numEvents = -1.0f;
		String isWorkday = null;
		int timeArea = -1;
		String sql = "select * from [dbo].[EventHour] where id = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			rs.next();
			numEvents = rs.getFloat("numEvents");
			isWorkday = rs.getString("isWorkday");
			timeArea = rs.getInt("timeArea");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		if(numEvents == -1.0f)
			return null;
		return new EventHour(id, numEvents, isWorkday, timeArea);
	}
	
	public static ArrayList<EventHour> findWithIsWorkday(String isWorkday) 
	{
		ArrayList<EventHour> eventHours = new ArrayList<EventHour>();
		int id = -1;
		float numEvents = -1.0f;
		int timeArea = -1;
		String sql = "select * from [dbo].[EventHour] where isWorkday = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, isWorkday);
			rs = pst.executeQuery();
			while(rs.next()) 
			{
				id = rs.getInt("id");
				numEvents = rs.getFloat("numEvents");
				timeArea = rs.getInt("timeArea");
				eventHours.add(new EventHour(id, numEvents, isWorkday, timeArea));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		if(id == -1)
			return null;
		return eventHours;
	}
	public static ArrayList<EventHour> findWithTimeArea(int timeArea) 
	{
		ArrayList<EventHour> eventHours = new ArrayList<EventHour>();
		int id = -1;
		float numEvents = -1.0f;
		String isWorkday = null;
		String sql = "select * from [dbo].[EventHour] where timeArea = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, timeArea);
			rs = pst.executeQuery();
			while(rs.next()) 
			{
				id = rs.getInt("id");
				numEvents = rs.getFloat("numEvents");
				isWorkday = rs.getString("isWorkday");
				eventHours.add(new EventHour(id, numEvents, isWorkday, timeArea));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		if(id == -1)
			return null;
		return eventHours;
	}
	
	public static boolean change(EventHour eventHour) 
	{
		String sql = "UPDATE [dbo].[EventHour] SET numEvents = ?, isWorkday = ?, timeArea = ? WHERE id = ?";
		PreparedStatement pst;
		try {
			pst = con.prepareStatement(sql);
			pst.setFloat(1, eventHour.getNumEvents());
			pst.setString(2, eventHour.getIsWorkday());
			pst.setInt(3, eventHour.getTimeArea());
			pst.setInt(4, eventHour.getId());
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
