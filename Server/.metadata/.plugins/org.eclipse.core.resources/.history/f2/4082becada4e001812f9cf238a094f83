package main.model.services;

import java.util.ArrayList;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import main.model.moudle.*;
import main.model.db.*;

public class TimeSharingService {
	//isSharingTable为1表示时间分配表，为0表示日程表
	//返回的JSONObject包括新的时间分配表的基本信息(Object)和所有事件(List)

	public JSONObject getSharingTable(String userId,String date)
	{
		JSONObject back=new JSONObject();
		TimeSharingManager managerTS = new TimeSharingManager();
		ArrayList<TimeSharing> arrayList = managerTS.findWithIdUser(Integer.parseInt(userId));
		TimeSharing curTS = new TimeSharing();
		if(arrayList != null) {
			for(TimeSharing TS:arrayList) { //遍历时间分配表，找到当前日期的时间分配表
				if(TS.getDate().equals(date)) curTS = TS;
			}
			
			//将curTS解析成JSONObject
			AffairManager managerA = new AffairManager();
			S_AffairManager managerSA = new S_AffairManager();
			int idTS1 = curTS.getId();
			ArrayList<Affair> affairList = managerA.findWithIdTS(idTS1); //得到属于这个时间分配表的所有事件
			ArrayList<S_Affair> s_affairList = managerSA.findWithIdTS(idTS1); //得到属于这个时间分配表的所有日程
			
			back.put("id",Integer.toString(curTS.getId()));
			back.put("idUser",Integer.toString(curTS.getIdUser()));
			back.put("date",curTS.getDate());
			back.put("weekday",Integer.toString(curTS.getWeekday()));
			
			JSONArray array1 = new JSONArray();
			JSONObject js = new JSONObject();
			for(Affair a:affairList) {
				int id = a.getId();
				int idTS = a.getIdTS();
				int idLabel = a.getIdLabel();
				
				int satisfaction = a.getSatisfaction();
				
			    String name = a.getName();
				String tips = a.getTips();
				
				String timeStart = a.getTimeStart();
				String timeEnd = a.getTimeEnd();
				String timeEndPlan = a.getTimeEndPlan();
				
				js.put("id",Integer.toString(id));
				js.put("idTS",Integer.toString(idTS));
				js.put("idLabel",Integer.toString(idLabel));
				js.put("satisfaction",Integer.toString(satisfaction));
				js.put("name",name);
				js.put("tips",tips);
				js.put("timeStart",timeStart);
				js.put("timeEnd",timeEnd);
				js.put("timeEndPlan",timeEndPlan);
				
				array1.put(js);
			}
			back.put("affair",array1);
			
			JSONArray array2 = new JSONArray();
			JSONObject js2 = new JSONObject();
			for(S_Affair a:s_affairList) {
				int id = a.getId();
				int idTS = a.getIdTS();
				int idLabel = a.getIdLabel();
				
				int satisfaction = a.getSatisfaction();
				
			    String name = a.getName();
				String tips = a.getTips();
				
				String timeStart = a.getTimeStart();
				String timeEnd = a.getTimeEnd();
				String timeEndPlan = a.getTimeEndPlan();
				int idS = a.getIdS();
				
				String isImportant = a.getIsImportant();
				
				String timeStartPlan = a.getTimeStartPlan();
				String timeStartAlarm = a.getTimeStartAlarm();
				String timeEndAlarm = a.getTimeEndAlarm();
				
				
				js2.put("id",Integer.toString(id));
				js2.put("idTS",Integer.toString(idTS));
				js2.put("idLabel",Integer.toString(idLabel));
				js2.put("satisfaction",Integer.toString(satisfaction));
				js2.put("name",name);
				js2.put("tips",tips);
				js2.put("timeStart",timeStart);
				js2.put("timeEnd",timeEnd);
				js2.put("timeEndPlan",timeEndPlan);
				js2.put("idS",Integer.toString(idS));
				js2.put("isImportant",isImportant);
				js2.put("timeStartPlan",timeStartPlan);
				js2.put("timeStartAlarm",timeStartAlarm);
				js2.put("timeEndAlarm",timeEndAlarm);
				
				array2.put(js2);
			}
			back.put("saffair",array2);
		}
		else return null;
		
		return back;
	}
}
