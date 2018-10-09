package main.model.services;

import main.model.moudle.*;

import java.util.ArrayList;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import main.model.db.*;

public class SharedtableService{
	
	public String share(int idTS) 
	{
		SharedTable sharedTable = SharedTableManager.findWithIdTS(idTS);
		if(sharedTable.getIdTS() != idTS)
			return "fail";
		User curUser = UserManager.findWithId(sharedTable.getIdUser());
		float gpa = curUser.getGPA();
		if(gpa >= 3) 
		{
			if(SharedTableManager.add(sharedTable.getIdUser(), sharedTable.getIdTS(), sharedTable.getTimeShared(), sharedTable.getSummary(), 0) != -1) 
				return "success";
			else return "fail";
		}
		else return "gpafail";
	}

	public JSONArray getSTList(String major) 
	{
		JSONArray sharedTableArray = new JSONArray();
		if(major.equals("all")) 
		{ 
			ArrayList<SharedTable> sharedTables = SharedTableManager.sortWithNothing();
			for(int i = 0; i < sharedTables.size(); i++)
			{
				JSONObject sharedTable = new JSONObject();
				User user = UserManager.findWithId(sharedTables.get(i).getIdUser());
				sharedTable.put("name", user.getName());
				sharedTable.put("userId", user.getId());
				sharedTable.put("image", user.getImage());
				sharedTable.put("school", user.getSchool());
				sharedTable.put("major", user.getMajor());
				sharedTable.put("summary", sharedTables.get(i).getSummary());
				sharedTable.put("timeShared", sharedTables.get(i).getTimeShared());
				sharedTable.put("thumbup", Integer.toString(sharedTables.get(i).getThumbup()));
				sharedTable.put("idTS", Integer.toString(sharedTables.get(i).getIdTS()));
				sharedTable.put("idST", Integer.toString(sharedTables.get(i).getId()));
				sharedTableArray.add(sharedTable);
			 }
		 }
		 else 
		 { 
			 ArrayList<SharedTable> sharedTables = SharedTableManager.sortWithMajor(major);
			 if(sharedTables == null)
				 return null;
			 for(int i = 0; i < sharedTables.size(); i++)
			 {
				 JSONObject sharedTable = new JSONObject();
				 User user = UserManager.findWithId(sharedTables.get(i).getIdUser());
				 sharedTable.put("name", user.getName());
				 sharedTable.put("userId", user.getId());
				 sharedTable.put("image", user.getImage());
				 sharedTable.put("school", user.getSchool());
				 sharedTable.put("major", user.getMajor());
				 sharedTable.put("summary", sharedTables.get(i).getSummary());
				 sharedTable.put("timeShared", sharedTables.get(i).getTimeShared());
				 sharedTable.put("thumbup", Integer.toString(sharedTables.get(i).getThumbup()));
				 sharedTable.put("idTS", Integer.toString(sharedTables.get(i).getIdTS()));
				 sharedTable.put("idST", Integer.toString(sharedTables.get(i).getId()));
				 sharedTableArray.add(sharedTable);
			 }
		 }
		 return sharedTableArray;
	}
}