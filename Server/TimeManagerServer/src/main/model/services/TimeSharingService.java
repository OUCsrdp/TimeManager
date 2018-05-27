package main.model.services;

import java.util.ArrayList;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import main.model.moudle.*;
import main.model.db.*;

public class TimeSharingService {
	//isSharingTableΪ1��ʾʱ������Ϊ0��ʾ�ճ̱�
	//���ص�JSONObject�����µ�ʱ������Ļ�����Ϣ(Object)�������¼�(List)

	public JSONObject getSharingTable(String userId,String date)
	{
		JSONObject back=new JSONObject();
		TimeSharingManager managerTS = new TimeSharingManager();
		ArrayList<TimeSharing> arrayList = managerTS.findWithIdUser(Integer.parseInt(userId));
		TimeSharing curTS = new TimeSharing();
		curTS = null;
		if(arrayList != null) {
			for(TimeSharing TS:arrayList) { //����ʱ�������ҵ���ǰ���ڵ�ʱ������
				if(TS.getDate().equals(date)) curTS = TS;
			}
			if(curTS == null) return null;
			
			//��curTS������JSONObject
			AffairManager managerA = new AffairManager();
			S_AffairManager managerSA = new S_AffairManager();
			int idTS1 = curTS.getId();
			ArrayList<Affair> affairList=new ArrayList<Affair>();
			ArrayList<S_Affair> s_affairList=new ArrayList<S_Affair>();
			affairList = managerA.findWithIdTS(idTS1); //�õ��������ʱ������������¼�
			s_affairList = managerSA.findWithIdTS(idTS1); //�õ��������ʱ������������ճ�
			
			if(affairList != null) {
				affairList = SortService.sortAByTime(affairList);
			}
			if(s_affairList != null) {
				s_affairList = SortService.sortSByTime(s_affairList);
			}
			
			back.put("id",curTS.getId());
			back.put("idUser",curTS.getIdUser());
			back.put("date",curTS.getDate());
			back.put("weekday",curTS.getWeekday());
			
			JSONArray array1 = new JSONArray();
			if(affairList!=null) {
				for(Affair a:affairList) {
					JSONObject js = new JSONObject();
					int id = a.getId();
					int idTS = a.getIdTS();
					int idLabel = a.getIdLabel();
					
					int satisfaction = a.getSatisfaction();
					
				    String name = a.getName();
					String tips = a.getTips();
					
					String timeStart = a.getTimeStart();
					String timeEnd = a.getTimeEnd();
					String timeEndPlan = a.getTimeEndPlan();
					
					js.put("id",id);
					js.put("idTS",idTS);
					js.put("idLabel",idLabel);
					js.put("satisfaction",satisfaction);
					js.put("name",name);
					js.put("tips",tips);
					js.put("timeStart",timeStart);
					js.put("timeEnd",timeEnd);
					js.put("timeEndPlan",timeEndPlan);
					
					array1.add(js);
				}
			}
			back.put("affair",array1);
			JSONArray array2 = new JSONArray();
			if(s_affairList!=null)
			{
				for(S_Affair a:s_affairList) {
					JSONObject js2 = new JSONObject();
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
					
					
					js2.put("id",id);
					js2.put("idTS",idTS);
					js2.put("idLabel",idLabel);
					js2.put("satisfaction",satisfaction);
					js2.put("name",name);
					js2.put("tips",tips);
					js2.put("timeStart",timeStart);
					js2.put("timeEnd",timeEnd);
					js2.put("timeEndPlan",timeEndPlan);
					js2.put("idS",idS);
					js2.put("isImportant",isImportant);
					js2.put("timeStartPlan",timeStartPlan);
					js2.put("timeStartAlarm",timeStartAlarm);
					js2.put("timeEndAlarm",timeEndAlarm);
					
					array2.add(js2);
				}
			}
			back.put("saffair",array2);
		}
		else return null;
		
		return back;
	}
}