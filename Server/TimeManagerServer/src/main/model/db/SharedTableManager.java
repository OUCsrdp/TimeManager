package main.model.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import main.model.moudle.*;;

public class SharedTableManager extends SqlServerManager
{	
	public static int add(String timeShare, String summary, int thumbup) 
	{
		int id = -1;
		String sql_Insert = "INSERT INTO [dbo].[SharedTables]" 
				+ "([timeShare], [summary], [thumbup])" 
				+ "VALUES" 
				+ "(?, ?, ?)";
		try {
			PreparedStatement pst = con.prepareStatement(sql_Insert, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, timeShare);
			pst.setString(2, summary);
			pst.setInt(3, thumbup);
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
		String sql_SharedTable = "DELETE FROM [dbo].[SharedTables] WHERE id=?";
		try {
			PreparedStatement pst = con.prepareStatement(sql_SharedTable);
			pst.setInt(1, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static SharedTable findWithId(int id) 
	{
		String timeShared = null;
		String summary = null;
		int thumbup = -1;
		String sql = "select * from [dbo].[SharedTables] where id = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			rs.next();
			timeShared = rs.getString("timeShare");
			summary = rs.getString("summary");
			thumbup = rs.getInt("thumbup");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		if(thumbup == -1)
			return null;
		return new SharedTable(id, timeShared, summary, thumbup);
	}
	public static ArrayList<SharedTable> findWithTimeShared(String timeShared) 
	{
		ArrayList<SharedTable> sharedTables = new ArrayList<SharedTable>();
		int id = -1;
		String summary = null;
		int thumbup = 0;
		String sql = "select * from [dbo].[SharedTables] where timeShare = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, timeShared);
			rs = pst.executeQuery();
			while(rs.next()) 
			{
				id = rs.getInt("id");
				summary = rs.getString("summary");
				thumbup = rs.getInt("thumbup");
				sharedTables.add(new SharedTable(id, timeShared, summary, thumbup));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		if(id == -1)
			return null;
		return sharedTables;
	}
	public static ArrayList<SharedTable> findWithThumbup(int thumbup) 
	{
		ArrayList<SharedTable> sharedTables = new ArrayList<SharedTable>();
		int id = -1;
		String timeShared = null;
		String summary = null;
		String sql = "select * from [dbo].[SharedTables] where thumbup = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, thumbup);
			rs = pst.executeQuery();
			while(rs.next()) 
			{
				id = rs.getInt("id");
				timeShared = rs.getString("timeShare");
				summary = rs.getString("summary");
				sharedTables.add(new SharedTable(id, timeShared, summary, thumbup));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		if(id == -1)
			return null;
		return sharedTables;
	}
	
	public static boolean change(SharedTable sharedTable) 
	{
		String sql = "UPDATE [dbo].[SharedTables] SET timeShare = ?, summary = ?, thumbup = ? WHERE id = ?";
		PreparedStatement pst;
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, sharedTable.getTimeShared());
			pst.setString(2, sharedTable.getSummary());
			pst.setInt(3, sharedTable.getThumbup());
			pst.setInt(4, sharedTable.getId());
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
