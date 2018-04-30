package main.model.services;

import com.alibaba.fastjson.JSONObject;
import main.model.moudle.*;
import main.model.db.*;

public class ScheduleService {
	public JSONObject getScheduleTable( String userId,String date)
	{
		JSONObject back=new JSONObject();
			ScheduleManager managerS = new ScheduleManaer();
			ArrayList<Schedule> arrayList = managerS.findWithIdUser(userId);
			Schedule curS = new Schedule();
			if(arrayList != null) {
				for(Schedule S:arrayList) { //�����ճ̱��ҵ���ǰ���ڵ�ʱ������
					if(S.getDate().equals(date)) curS = S;
				}
				
				//��curS������JSONObject
				S_AffairManager managerSA = new S_AffairManager();
				int idS = curS.getId();
				
				ArrayList<S_Affair> s_affairList = managerSA.findWithIdS(idS); //�õ���������ճ̱�������ճ�
				
				back.put("id",curS.getId().toString());
				back.put("idUser",curS.getIdUser().toString());
				back.put("date",curS.getDate());
				back.put("weekday",curS.getWeekday.toString());
				
				JSONArray array2 = new JSONArray();
				JSONObject js2 = new JSONObject();
				for(S_Affair a:s_affairList) {
					int id = a.getId();
					int idTS = a.getIdTS();
					int idLabel = a.getIdLabel;
					
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
					
					
					js2.put"id",id.toString());
					js2.put("idTS",idTS.toString());
					js2.put("idLabel",idLabel.toString());
					js2.put("satisfaction",satisfaction.toString());
					js2.put("name",name);
					js2.put("tips",tips);
					js2.put("timeStart",timeStart);
					js2.put("timeEnd",timeEnd);
					js2.put("timeEndPlan",timeEndPlan);
					js2.put("idS",idS.toString());
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
