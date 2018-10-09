package main.model.services;

import java.util.ArrayList;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import main.model.moudle.*;
import main.model.db.*;

public class ScheduleService {
	public JSONObject getScheduleTable( String userId,String date)
	{
		JSONObject back=new JSONObject();
			ArrayList<Schedule> arrayList = ScheduleManager.findWithIdUser(Integer.parseInt(userId));
			Schedule curS = new Schedule();
			if(arrayList != null) {
				for(Schedule S:arrayList) { //�����ճ̱��ҵ���ǰ���ڵ�ʱ������
					if(S.getDate().equals(date)) curS = S;
				}
				
				//��curS������JSONObject
				
				int idS1 = curS.getId();
				
				ArrayList<S_Affair> s_affairL = S_AffairManager.findWithIdS(idS1); //�õ���������ճ̱�������ճ�
				ArrayList<S_Affair> s_affairList = SortService.sortSByTime(s_affairL); //����
				
				back.put("id",Integer.toString(curS.getId()));
				back.put("idUser",Integer.toString(curS.getIdUser()));
				back.put("date",curS.getDate());
				back.put("weekday",Integer.toString(curS.getWeekday()));
				
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
					
					array2.add(js2);
				}
				back.put("saffair",array2);
			}
			else return null;
		return back;
	}
}
