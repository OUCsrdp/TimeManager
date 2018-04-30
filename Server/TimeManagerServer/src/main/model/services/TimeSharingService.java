package main.model.services;

import com.alibaba.fastjson.JSONObject;
import main.model.moudle.*;
import main.model.db.*;

public class TimeSharingService {
	//isSharingTableΪ1��ʾʱ������Ϊ0��ʾ�ճ̱�
	//���ص�JSONObject�����µ�ʱ������Ļ�����Ϣ(Object)�������¼�(List)

	public JSONObject getSharingTable(String userId,String date)
	{
		JSONObject back=new JSONObject();
		TimeSharingManager managerTS = new TimeSharingManaer();
		ArrayList<TimeSharing> arrayList = managerTS.findWithIdUser(userId);
		TimeSharing curTS = new TimeSharing();
		if(arrayList != null) {
			for(TimeSharing TS:arrayList) { //����ʱ�������ҵ���ǰ���ڵ�ʱ������
				if(TS.getDate().equals(date)) curTS = TS;
			}
			
			//��curTS������JSONObject
			AffairManager managerA = new AffairManager();
			S_AffairManager managerSA = new S_AffairManager();
			int idTS = curTS.getId();
			ArrayList<Affair> affairList = managerA.findWithIdTS(idTS); //�õ��������ʱ������������¼�
			ArrayList<S_Affair> s_affairList = managerSA.findWithIdTS(idTS); //�õ��������ʱ������������ճ�
			
			back.put("id",curTS.getId().toString());
			back.put("idUser",curTS.getIdUser().toString());
			back.put("date",curTS.getDate());
			back.put("weekday",curTS.getWeekday.toString());
			
			JSONArray array1 = new JSONArray();
			JSONObject js = new JSONObject();
			for(Affair a:affairList) {
				int id = a.getId();
				int idTS = a.getIdTS();
				int idLabel = a.getIdLabel;
				
				int satisfaction = a.getSatisfaction();
				
			    String name = a.getName();
				String tips = a.getTips();
				
				String timeStart = a.getTimeStart();
				String timeEnd = a.getTimeEnd();
				String timeEndPlan = a.getTimeEndPlan();
				
				js.put"id",id.toString());
				js.put("idTS",idTS.toString());
				js.put("idLabel",idLabel.toString());
				js.put("satisfaction",satisfaction.toString());
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
