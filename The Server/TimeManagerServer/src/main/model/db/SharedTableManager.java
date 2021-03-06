package main.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import main.model.moudle.*;;

public class SharedTableManager extends SqlServerManager
{	
	public static int add(int idUser, int idTS, String timeShare, String summary, int thumbup) 
	{
		if(UserManager.findWithId(idUser) == null)
			return -1;
		if(TimeSharingManager.findWithId(idTS).getIdUser() != idUser)
			return -1;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = SharedTableManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SharedTableManager.Create(stmt);
		int id = -1;
		String sql_Insert = "INSERT INTO [dbo].[SharedTables]" 
				+ "([idUser], [idTS], [timeShare], [summary], [thumbup])" 
				+ "VALUES" 
				+ "(? , ? ,?, ?, ?)";
		try {
			pst = con.prepareStatement(sql_Insert, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, idUser);
			pst.setInt(2, idTS);
			pst.setString(3, timeShare);
			pst.setString(4, summary);
			pst.setInt(5, thumbup);
            pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            rs.next();
            id = rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			SharedTableManager.Close(con, stmt, rs, pst);
			return -1;
		}
		SharedTableManager.Close(con, stmt, rs, pst);
        return id;
	}

	public static boolean delete(int id) 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = SharedTableManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SharedTableManager.Create(stmt);
		String sql_SharedTable = "DELETE FROM [dbo].[SharedTables] WHERE id=?";
		try {
			pst = con.prepareStatement(sql_SharedTable);
			pst.setInt(1, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			SharedTableManager.Close(con, stmt, rs, pst);
			return false;
		}
		SharedTableManager.Close(con, stmt, rs, pst);
		return true;
	}
	
	public static ArrayList<SharedTable> findWithNothing()
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = SharedTableManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SharedTableManager.Create(stmt);
		ArrayList<SharedTable> sharedTables = new ArrayList<SharedTable>();
		int id = -1;
		int idUser = -1;
		int idTS = -1;
		String timeShared = null;
		String summary = null;
		int thumbup = 0;
		String sql = "select * from [dbo].[SharedTables]";
		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()) 
			{
				id = rs.getInt("id");
				idUser = rs.getInt("idUser");
				idTS = rs.getInt("idTS");
				timeShared = rs.getString("timeShare");
				summary = rs.getString("summary");
				thumbup = rs.getInt("thumbup");
				sharedTables.add(new SharedTable(id, idUser, idTS, timeShared, summary, thumbup));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			SharedTableManager.Close(con, stmt, rs, pst);
			return null;
		}
		SharedTableManager.Close(con, stmt, rs, pst);
		if(id == -1)
			return null;
		return sharedTables;
	}
	
	public static SharedTable findWithId(int id) 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = SharedTableManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SharedTableManager.Create(stmt);
		int idUser = -1;
		int idTS = -1;
		String timeShared = null;
		String summary = null;
		int thumbup = -1;
		String sql = "select * from [dbo].[SharedTables] where id = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if(!rs.next())
			{
				SharedTableManager.Close(con, stmt, rs, pst);
				return null;
			}
			idUser = rs.getInt("idUser");
			idTS = rs.getInt("idTS");
			timeShared = rs.getString("timeShare");
			summary = rs.getString("summary");
			thumbup = rs.getInt("thumbup");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			SharedTableManager.Close(con, stmt, rs, pst);
			return null;
		}
		SharedTableManager.Close(con, stmt, rs, pst);
		if(thumbup == -1)
			return null;
		return new SharedTable(id, idUser, idTS, timeShared, summary, thumbup);
	}
	
	public static SharedTable findWithIdTS(int idTS) 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = SharedTableManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SharedTableManager.Create(stmt);
		int id = -1;
		int idUser = -1;
		String timeShared = null;
		String summary = null;
		int thumbup = -1;
		String sql = "select * from [dbo].[SharedTables] where idTS = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, idTS);
			rs = pst.executeQuery();
			if(!rs.next())
			{
				SharedTableManager.Close(con, stmt, rs, pst);
				return null;
			}
			id = rs.getInt("id");
			idUser = rs.getInt("idUser");
			timeShared = rs.getString("timeShare");
			summary = rs.getString("summary");
			thumbup = rs.getInt("thumbup");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			SharedTableManager.Close(con, stmt, rs, pst);
			return null;
		}
		SharedTableManager.Close(con, stmt, rs, pst);
		if(thumbup == -1)
			return null;
		return new SharedTable(id, idUser, idTS, timeShared, summary, thumbup);
	}
	
	public static ArrayList<SharedTable> findWithIdUser(int idUser)
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = SharedTableManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SharedTableManager.Create(stmt);
		ArrayList<SharedTable> sharedTables = new ArrayList<SharedTable>();
		int id = -1;
		int idTS = -1;
		String timeShared = null;
		String summary = null;
		int thumbup = 0;
		String sql = "select * from [dbo].[SharedTables] where idUser = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, idUser);
			rs = pst.executeQuery();
			while(rs.next()) 
			{
				id = rs.getInt("id");
				idTS = rs.getInt("idTS");
				timeShared = rs.getString("timeShare");
				summary = rs.getString("summary");
				thumbup = rs.getInt("thumbup");
				sharedTables.add(new SharedTable(id, idUser, idTS, timeShared, summary, thumbup));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			SharedTableManager.Close(con, stmt, rs, pst);
			return null;
		}
		SharedTableManager.Close(con, stmt, rs, pst);
		if(id == -1)
			return null;
		return sharedTables;
	}
	
	public static ArrayList<SharedTable> findWithTimeShared(String timeShared) 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = SharedTableManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SharedTableManager.Create(stmt);
		ArrayList<SharedTable> sharedTables = new ArrayList<SharedTable>();
		int id = -1;
		int idUser = -1;
		int idTS = -1;
		String summary = null;
		int thumbup = 0;
		String sql = "select * from [dbo].[SharedTables] where timeShare = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, timeShared);
			rs = pst.executeQuery();
			while(rs.next()) 
			{
				id = rs.getInt("id");
				idUser = rs.getInt("idUser");
				idTS = rs.getInt("idTS");
				summary = rs.getString("summary");
				thumbup = rs.getInt("thumbup");
				sharedTables.add(new SharedTable(id, idUser, idTS, timeShared, summary, thumbup));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			SharedTableManager.Close(con, stmt, rs, pst);
			return null;
		}
		SharedTableManager.Close(con, stmt, rs, pst);
		if(id == -1)
			return null;
		return sharedTables;
	}
	
	public static ArrayList<SharedTable> findWithThumbup(int thumbup) 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = SharedTableManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SharedTableManager.Create(stmt);
		ArrayList<SharedTable> sharedTables = new ArrayList<SharedTable>();
		int id = -1;
		int idUser = -1;
		int idTS = -1;
		String timeShared = null;
		String summary = null;
		String sql = "select * from [dbo].[SharedTables] where thumbup = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, thumbup);
			rs = pst.executeQuery();
			while(rs.next()) 
			{
				id = rs.getInt("id");
				idUser = rs.getInt("idUser");
				idTS = rs.getInt("idTS");
				timeShared = rs.getString("timeShare");
				summary = rs.getString("summary");
				sharedTables.add(new SharedTable(id, idUser, idTS, timeShared, summary, thumbup));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			SharedTableManager.Close(con, stmt, rs, pst);
			return null;
		}
		SharedTableManager.Close(con, stmt, rs, pst);
		if(id == -1)
			return null;
		return sharedTables;
	}
	
	public static boolean change(SharedTable sharedTable) 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = SharedTableManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SharedTableManager.Create(stmt);
		if(UserManager.findWithId(sharedTable.getIdUser()) == null)
			return false;
		String sql = "UPDATE [dbo].[SharedTables] SET idUser = ?, idTS = ?, timeShare = ?, summary = ?, thumbup = ? WHERE id = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, sharedTable.getIdUser());
			pst.setInt(2, sharedTable.getIdTS());
			pst.setString(3, sharedTable.getTimeShared());
			pst.setString(4, sharedTable.getSummary());
			pst.setInt(5, sharedTable.getThumbup());
			pst.setInt(6, sharedTable.getId());
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			SharedTableManager.Close(con, stmt, rs, pst);
			return false;
		}
		SharedTableManager.Close(con, stmt, rs, pst);
		return true;
	}
	
	public static ArrayList<SharedTable> sortWithNothing()
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = SharedTableManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SharedTableManager.Create(stmt);
		ArrayList<SharedTable> sharedTables = SharedTableManager.findWithNothing();
		//System.out.println("success");
		ArrayList<Float> topGPA = new ArrayList<Float>();
		for(int i = 0; i < Math.ceil((sharedTables.size()-1)/2); i++)
		{
			SharedTable temp = sharedTables.get(i);
			sharedTables.set(i, sharedTables.get(sharedTables.size() - i - 1));
			sharedTables.set(sharedTables.size() - i - 1, temp);
		}
		for(int i = 0; i < sharedTables.size(); i++)
		{
			if(topGPA.isEmpty())
				topGPA.add(UserManager.findWithId(sharedTables.get(i).getIdUser()).getGPA());
			else if(topGPA.size() <= 5)
			{
				for(int j = 0; j < topGPA.size(); j++)
				{
					if(UserManager.findWithId(sharedTables.get(i).getIdUser()).getGPA() == topGPA.get(j) && j < topGPA.size() - 1)
						break;
					else if(UserManager.findWithId(sharedTables.get(i).getIdUser()).getGPA() < topGPA.get(j) && j == topGPA.size() - 1)
						topGPA.add(UserManager.findWithId(sharedTables.get(i).getIdUser()).getGPA());
					else if(UserManager.findWithId(sharedTables.get(i).getIdUser()).getGPA() > topGPA.get(j)) 
					{
						topGPA.add(j, UserManager.findWithId(sharedTables.get(i).getIdUser()).getGPA());
						break;
					}
				}
			}
			else
			{
				for(int j = 0; j < 5; j++)
				{
					if(UserManager.findWithId(sharedTables.get(i).getIdUser()).getGPA() == topGPA.get(j))
						break;
					else if(UserManager.findWithId(sharedTables.get(i).getIdUser()).getGPA() > topGPA.get(j)) 
					{
						topGPA.add(j, UserManager.findWithId(sharedTables.get(i).getIdUser()).getGPA());
						topGPA.remove(5);
						break;
					}
				}
			}
		}
		//System.out.println(topGPA.size());
		for(int i = 0; i < topGPA.size(); i++)
		{
			ArrayList<User> topUsers = UserManager.findWithGPA(topGPA.get(topGPA.size() - 1 -i));
			for(int j = 0; j < topUsers.size(); j++)
			{
				//System.out.println(topUsers.size());
				ArrayList<SharedTable> topSharedTables = SharedTableManager.findWithIdUser(topUsers.get(j).getId());
				if(topSharedTables == null)
					continue;
				//System.out.println(topSharedTables.size());
				QuickSort(topSharedTables, 0, topSharedTables.size() - 1);
				if(topSharedTables.size() > 3)
				{
					for(int k = 0; k < topSharedTables.size() - 3; k++)
						topSharedTables.remove(k);
				}
				for(int k = 0; k < topSharedTables.size(); k++)
				{
					SharedTable temp = topSharedTables.get(k);
					for(int m = 0; m < sharedTables.size(); m++)
					{
						if(sharedTables.get(m).getIdUser() == temp.getIdUser() && sharedTables.get(m).getThumbup() == temp.getThumbup())
						{
							sharedTables.remove(m);
							sharedTables.add(0,temp);
						}
					}
				}
			}
		}
		SharedTableManager.Close(con, stmt, rs, pst);
		return sharedTables;
	}
	
	public static ArrayList<SharedTable> sortWithSchool(String school)
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = SharedTableManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SharedTableManager.Create(stmt);
		ArrayList<SharedTable> sharedTables = SharedTableManager.findWithNothing();
		ArrayList<Float> topGPA = new ArrayList<Float>();
		int size = sharedTables.size();
		for(int i = 0; i < size; i++)
		{
			if(!UserManager.findWithId(sharedTables.get(i).getIdUser()).getSchool().equals(school))
			{
				sharedTables.remove(i);
				i--;
				size--;
			}
		}
		for(int i = 0; i < Math.ceil((sharedTables.size()-1)/2); i++)
		{
			SharedTable temp = sharedTables.get(i);
			sharedTables.set(i, sharedTables.get(sharedTables.size() - i - 1));
			sharedTables.set(sharedTables.size() - i - 1, temp);
		}
		for(int i = 0; i < sharedTables.size(); i++)
		{
			if(topGPA.isEmpty())
				topGPA.add(UserManager.findWithId(sharedTables.get(i).getIdUser()).getGPA());
			else if(topGPA.size() <= 5)
			{
				for(int j = 0; j < topGPA.size(); j++)
				{
					if(UserManager.findWithId(sharedTables.get(i).getIdUser()).getGPA() == topGPA.get(j) && j < topGPA.size() - 1)
						break;
					else if(UserManager.findWithId(sharedTables.get(i).getIdUser()).getGPA() < topGPA.get(j) && j == topGPA.size() - 1)
						topGPA.add(UserManager.findWithId(sharedTables.get(i).getIdUser()).getGPA());
					else if(UserManager.findWithId(sharedTables.get(i).getIdUser()).getGPA() > topGPA.get(j)) 
					{
						topGPA.add(j, UserManager.findWithId(sharedTables.get(i).getIdUser()).getGPA());
						break;
					}
				}
			}
			else
			{
				for(int j = 0; j < 5; j++)
				{
					if(UserManager.findWithId(sharedTables.get(i).getIdUser()).getGPA() == topGPA.get(j))
						break;
					else if(UserManager.findWithId(sharedTables.get(i).getIdUser()).getGPA() > topGPA.get(j)) 
					{
						topGPA.add(j, UserManager.findWithId(sharedTables.get(i).getIdUser()).getGPA());
						topGPA.remove(5);
						break;
					}
				}
			}
		}
		for(int i = 0; i < topGPA.size(); i++)
		{
			ArrayList<User> topUsers = UserManager.findWithGPA(topGPA.get(topGPA.size() - 1 -i));
			for(int j = 0; j < topUsers.size(); j++)
			{
				ArrayList<SharedTable> topSharedTables = SharedTableManager.findWithIdUser(topUsers.get(j).getId());
				if(topSharedTables == null)
					continue;
				QuickSort(topSharedTables, 0, topSharedTables.size() - 1);
				if(topSharedTables.size() > 3)
				{
					for(int k = 0; k < topSharedTables.size() - 3; k++)
						topSharedTables.remove(k);
				}
				for(int k = 0; k < topSharedTables.size(); k++)
				{
					SharedTable temp = topSharedTables.get(k);
					for(int m = 0; m < sharedTables.size(); m++)
					{
						if(sharedTables.get(m).getIdUser() == temp.getIdUser() && sharedTables.get(m).getThumbup() == temp.getThumbup())
						{
							sharedTables.remove(m);
							sharedTables.add(0,temp);
						}
					}
				}
			}
		}
		SharedTableManager.Close(con, stmt, rs, pst);
		return sharedTables;
	}
	
	public static ArrayList<SharedTable> sortWithMajorAndSchool(String major, String school)
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = SharedTableManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SharedTableManager.Create(stmt);
		ArrayList<SharedTable> sharedTables = SharedTableManager.findWithNothing();
		ArrayList<Float> topGPA = new ArrayList<Float>();
		int size = sharedTables.size();
		for(int i = 0; i < size; i++)
		{
			if(!UserManager.findWithId(sharedTables.get(i).getIdUser()).getMajor().equals(major) || !UserManager.findWithId(sharedTables.get(i).getIdUser()).getSchool().equals(school))
			{
				sharedTables.remove(i);
				i--;
				size--;
			}
		}
		for(int i = 0; i < Math.ceil((sharedTables.size()-1)/2); i++)
		{
			SharedTable temp = sharedTables.get(i);
			sharedTables.set(i, sharedTables.get(sharedTables.size() - i - 1));
			sharedTables.set(sharedTables.size() - i - 1, temp);
		}
		for(int i = 0; i < sharedTables.size(); i++)
		{
			if(topGPA.isEmpty())
				topGPA.add(UserManager.findWithId(sharedTables.get(i).getIdUser()).getGPA());
			else if(topGPA.size() <= 5)
			{
				for(int j = 0; j < topGPA.size(); j++)
				{
					if(UserManager.findWithId(sharedTables.get(i).getIdUser()).getGPA() == topGPA.get(j) && j < topGPA.size() - 1)
						break;
					else if(UserManager.findWithId(sharedTables.get(i).getIdUser()).getGPA() < topGPA.get(j) && j == topGPA.size() - 1)
						topGPA.add(UserManager.findWithId(sharedTables.get(i).getIdUser()).getGPA());
					else if(UserManager.findWithId(sharedTables.get(i).getIdUser()).getGPA() > topGPA.get(j)) 
					{
						topGPA.add(j, UserManager.findWithId(sharedTables.get(i).getIdUser()).getGPA());
						break;
					}
				}
			}
			else
			{
				for(int j = 0; j < 5; j++)
				{
					if(UserManager.findWithId(sharedTables.get(i).getIdUser()).getGPA() == topGPA.get(j))
						break;
					else if(UserManager.findWithId(sharedTables.get(i).getIdUser()).getGPA() > topGPA.get(j)) 
					{
						topGPA.add(j, UserManager.findWithId(sharedTables.get(i).getIdUser()).getGPA());
						topGPA.remove(5);
						break;
					}
				}
			}
		}
		for(int i = 0; i < topGPA.size(); i++)
		{
			ArrayList<User> topUsers = UserManager.findWithGPA(topGPA.get(topGPA.size() - 1 -i));
			for(int j = 0; j < topUsers.size(); j++)
			{
				ArrayList<SharedTable> topSharedTables = SharedTableManager.findWithIdUser(topUsers.get(j).getId());
				if(topSharedTables == null)
					continue;
				QuickSort(topSharedTables, 0, topSharedTables.size() - 1);
				if(topSharedTables.size() > 3)
				{
					for(int k = 0; k < topSharedTables.size() - 3; k++)
						topSharedTables.remove(k);
				}
				for(int k = 0; k < topSharedTables.size(); k++)
				{
					SharedTable temp = topSharedTables.get(k);
					for(int m = 0; m < sharedTables.size(); m++)
					{
						if(sharedTables.get(m).getIdUser() == temp.getIdUser() && sharedTables.get(m).getThumbup() == temp.getThumbup())
						{
							sharedTables.remove(m);
							sharedTables.add(0,temp);
						}
					}
				}
			}
		}
		SharedTableManager.Close(con, stmt, rs, pst);
		return sharedTables;
	}
	
	public static ArrayList<SharedTable> sortWithMajor(String major)
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		con = SharedTableManager.Connect();
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SharedTableManager.Create(stmt);
		ArrayList<SharedTable> sharedTables = SharedTableManager.findWithNothing();
		ArrayList<Float> topGPA = new ArrayList<Float>();
		int size = sharedTables.size();
		for(int i = 0; i < size; i++)
		{
			if(!UserManager.findWithId(sharedTables.get(i).getIdUser()).getMajor().equals(major))
			{
				sharedTables.remove(i);
				i--;
				size--;
			}
		}
		for(int i = 0; i < Math.ceil((sharedTables.size()-1)/2); i++)
		{
			SharedTable temp = sharedTables.get(i);
			sharedTables.set(i, sharedTables.get(sharedTables.size() - i - 1));
			sharedTables.set(sharedTables.size() - i - 1, temp);
		}
		for(int i = 0; i < sharedTables.size(); i++)
		{
			if(topGPA.isEmpty())
				topGPA.add(UserManager.findWithId(sharedTables.get(i).getIdUser()).getGPA());
			else if(topGPA.size() <= 5)
			{
				for(int j = 0; j < topGPA.size(); j++)
				{
					if(UserManager.findWithId(sharedTables.get(i).getIdUser()).getGPA() <= topGPA.get(j) && j < topGPA.size() - 1)
						break;
					else if(UserManager.findWithId(sharedTables.get(i).getIdUser()).getGPA() < topGPA.get(j) && j == topGPA.size() - 1)
						topGPA.add(UserManager.findWithId(sharedTables.get(i).getIdUser()).getGPA());
					else if(UserManager.findWithId(sharedTables.get(i).getIdUser()).getGPA() > topGPA.get(j)) 
					{
						topGPA.add(j, UserManager.findWithId(sharedTables.get(i).getIdUser()).getGPA());
						break;
					}
				}
			}
			else
			{
				for(int j = 0; j < 5; j++)
				{
					if(UserManager.findWithId(sharedTables.get(i).getIdUser()).getGPA() == topGPA.get(j))
						break;
					else if(UserManager.findWithId(sharedTables.get(i).getIdUser()).getGPA() > topGPA.get(j)) 
					{
						topGPA.add(j, UserManager.findWithId(sharedTables.get(i).getIdUser()).getGPA());
						topGPA.remove(5);
						break;
					}
				}
			}
		}
		for(int i = 0; i < topGPA.size(); i++)
		{
			ArrayList<User> topUsers = UserManager.findWithGPA(topGPA.get(topGPA.size() - 1 -i));
			for(int j = 0; j < topUsers.size(); j++)
			{
				ArrayList<SharedTable> topSharedTables = SharedTableManager.findWithIdUser(topUsers.get(j).getId());
				if(topSharedTables == null)
					continue;
				QuickSort(topSharedTables, 0, topSharedTables.size() - 1);
				if(topSharedTables.size() > 3)
				{
					for(int k = 0; k < topSharedTables.size() - 3; k++)
						topSharedTables.remove(k);
				}
				for(int k = 0; k < topSharedTables.size(); k++)
				{
					SharedTable temp = topSharedTables.get(k);
					for(int m = 0; m < sharedTables.size(); m++)
					{
						if(sharedTables.get(m).getIdUser() == temp.getIdUser() && sharedTables.get(m).getThumbup() == temp.getThumbup())
						{
							sharedTables.remove(m);
							sharedTables.add(0,temp);
						}
					}
				}
			}
		}
		SharedTableManager.Close(con, stmt, rs, pst);
		return sharedTables;
	}
	
	private static void QuickSort(ArrayList<SharedTable> sharedTables, int startIndex, int endIndex)
	{
		int start = startIndex;
		int end = endIndex;
		if(start >= end)
			return;
		int thumbup = sharedTables.get(start).getThumbup();
		while(start < end)
		{
			while(start<end && sharedTables.get(end).getThumbup() < thumbup)
				end--;
			sharedTables.get(start).setThumbup(sharedTables.get(end).getThumbup());
			while(start<end && sharedTables.get(start).getThumbup() >= thumbup)
				start++;
			sharedTables.get(end).setThumbup(sharedTables.get(start).getThumbup());
		}
		sharedTables.get(start).setThumbup(thumbup);
		QuickSort(sharedTables, startIndex, start -1);
		QuickSort(sharedTables, start+1, endIndex);
	}
}
