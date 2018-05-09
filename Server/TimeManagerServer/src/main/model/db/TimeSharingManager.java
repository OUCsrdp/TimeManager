package main.model.db;

import java.sql.* ;
import java.util.ArrayList;
import main.model.moudle.*;;

public class TimeSharingManager extends SqlServerManager
{
	public static int add(int idUser,String date,int weekday) 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = TimeSharingManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		TimeSharingManager.Create(stmt);
		int id = -1;
		String sql_Insert = "INSERT INTO [dbo].[TimeSharing]" 
				+ "([idUser], [date], [weekday])" 
				+ "VALUES" 
				+ "(?, ?, ?)";
		try {
			pst = con.prepareStatement(sql_Insert, Statement.RETURN_GENERATED_KEYS);
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
			TimeSharingManager.Close(con, stmt, rs, pst);
			return -1;
		}
		TimeSharingManager.Close(con, stmt, rs, pst);
        return id;
	}

	public static boolean delete(int id)
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = TimeSharingManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		TimeSharingManager.Create(stmt);
		String sql_TimeSharing = "DELETE FROM [dbo].[TimeSharing] WHERE id=?";
		try {
			pst = con.prepareStatement(sql_TimeSharing);
			pst.setInt(1, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			TimeSharingManager.Close(con, stmt, rs, pst);
			return false;
		}
		TimeSharingManager.Close(con, stmt, rs, pst);
		return true;
	}
	
	public static ArrayList<TimeSharing> findWithNothing()
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = TimeSharingManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		TimeSharingManager.Create(stmt);
		ArrayList<TimeSharing> timeSharing = new ArrayList<TimeSharing>();
		int id = -1;
		int idUser = -1;
		String date = null;
		int weekday;
		String sql = "select * from [dbo].[TimeSharing]";
		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()) 
			{
				id = rs.getInt("id");
				idUser = rs.getInt("idUser");
				date = rs.getString("date");
				weekday = rs.getInt("weekday");
				timeSharing.add(new TimeSharing(id, idUser, date, weekday));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			TimeSharingManager.Close(con, stmt, rs, pst);
			return null;
		}
		TimeSharingManager.Close(con, stmt, rs, pst);
		if(id == -1)
			return null;
		return timeSharing;
	}
	
	public static TimeSharing findWithId(int id) 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = TimeSharingManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		TimeSharingManager.Create(stmt);
		int idUser = -1;
		String date = null;
		int weekday = -1;
		String sql = "select * from [dbo].[TimeSharing] where id = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			rs.next();
			idUser = rs.getInt("idUser");
			date = rs.getString("date");
			weekday = rs.getInt("weekday");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			TimeSharingManager.Close(con, stmt, rs, pst);
			return null;
		}
		TimeSharingManager.Close(con, stmt, rs, pst);
		if(idUser == -1 || weekday == -1)
			return null;
		return new TimeSharing(id, idUser, date, weekday);
	}
	
	public static ArrayList<TimeSharing> findWithIdUser(int idUser) 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = TimeSharingManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		TimeSharingManager.Create(stmt);
		ArrayList<TimeSharing> timeSharing = new ArrayList<TimeSharing>();
		int id = -1;
		String date = null;
		int weekday;
		String sql = "select * from [dbo].[TimeSharing] where idUser = ?";
		try {
			pst = con.prepareStatement(sql);
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
			TimeSharingManager.Close(con, stmt, rs, pst);
			return null;
		}
		TimeSharingManager.Close(con, stmt, rs, pst);
		if(id == -1)
			return null;
		return timeSharing;
	}
	
	public static ArrayList<TimeSharing> findWithDate(String date) 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = TimeSharingManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		TimeSharingManager.Create(stmt);
		ArrayList<TimeSharing> timeSharing = new ArrayList<TimeSharing>();
		int id = -1;
		int idUser = -1;
		int weekday = -1;
		String sql = "select * from [dbo].[TimeSharing] where date = ?";
		try {
			pst = con.prepareStatement(sql);
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
			TimeSharingManager.Close(con, stmt, rs, pst);
			return null;
		}
		TimeSharingManager.Close(con, stmt, rs, pst);
		if(id == -1)
			return null;
		return timeSharing;
	}
	public static ArrayList<TimeSharing> findWithWeekday(int weekday) 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = TimeSharingManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		TimeSharingManager.Create(stmt);
		ArrayList<TimeSharing> timeSharing = new ArrayList<TimeSharing>();
		int id = -1;
		int idUser = -1;
		String date = null;
		String sql = "select * from [dbo].[TimeSharing] where weekday = ?";
		try {
			pst = con.prepareStatement(sql);
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
			TimeSharingManager.Close(con, stmt, rs, pst);
			return null;
		}
		TimeSharingManager.Close(con, stmt, rs, pst);
		if(id == -1)
			return null;
		return timeSharing;
	}
	
	public static boolean change(TimeSharing timeSharing)
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = TimeSharingManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		TimeSharingManager.Create(stmt);
		String sql = "UPDATE [dbo].[TimeSharing] SET idUSer = ?, date = ?, weekday = ? WHERE id = ?";
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
			TimeSharingManager.Close(con, stmt, rs, pst);
			return false;
		}
		TimeSharingManager.Close(con, stmt, rs, pst);
		return true;
	}
}
