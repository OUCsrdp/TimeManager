package main.model.services;

import  main.model.moudle.*;

import java.util.ArrayList;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import main.model.db.*;

public class SharedtableService{
	
	//搜索专业函数
	public static JSONObject getMajorList(String majorKeyword)// majorKeyword以“电子信息”为例
	{
		JSONObject back=new JSONObject();
		back.put("majorKeyword", majorKeyword);
		ArrayList<Major> arrayList = MajorManager.findWithWords(majorKeyword);
		JSONArray array = new JSONArray();
		  if(arrayList!=null) {
			  for(Major a:arrayList) {
					String name = a.getMajor();
					if(name.equals(majorKeyword)) { //完全一致最先
						JSONObject js = new JSONObject();
						js.put("major",name);
						array.add(js);
					}
				}
			  
			for(Major a:arrayList) { //包括的
				String name = a.getMajor();
				if(name.indexOf(majorKeyword) != -1 && !name.equals(majorKeyword)) {
					JSONObject js = new JSONObject();
					js.put("major",name);
					
					array.add(js);
				}
			}
			
			
		}
		else return null;
		if(array.size()<=0) return null;
		back.put("majors",array);
		
		return back;
	//根据相关程度排列出先后顺序，比如示例查询电子信息，先“电子信息”，再“电子信息与技术”
	}

	
	public String share(int idTS) 
	{
		//锞咃緪锝讹緩gpa锝ｏ交
		//锞咃緪锝讹緩锞婏緡锝奉厬锞橈礁锝达椒锞栵緩锟�
		//锝讹緮锞擄練SharedTables锝碉緞锝愁惖锝硷交锝�
		SharedTable sharedTable = SharedTableManager.findWithIdTS(idTS); //锞愰潹锝緪锞傦桨璞庯骄锞�锟�
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
	//gpa锝诧交锞楃練锝碉交锞榞pafail 锝诧緳锞楊柇锝э桨锞滐椒锝碉交锞榝ail

	public JSONArray getSTList(String major) 
	{
		JSONArray sharedTableArray = new JSONArray();
		if(major.equals("all")) 
		{ //锝诧交锞忥緸锞楋建锞掞降
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
		 { //锝憋骄锞楋建锞掞降
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
	/*Userid锞庯姜锝碉奖锞囷桨锞擄緝锝伙涧id,Major锝匡緣锞勶緶锞庯姜锞勶匠锝割摼锝緬锝碉緝铷わ締锝ｏ浆锝★奖original锝★奖锝辨锝撅奖锝撅緱锝緬锝�,锝★奖all锝★奖锝辨锝撅讲锝伙緩锞烇緱锝緬锝�
	锝凤降锝伙緲jsonArray*/
	//锞咃緟锞愶拷

}