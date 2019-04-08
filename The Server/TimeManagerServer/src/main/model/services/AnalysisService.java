package main.model.services;

import  main.model.moudle.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import main.model.db.*;

public class AnalysisService{
	
	public static String getInsDate()
	{
		int y,m,d;    
		Calendar cal=Calendar.getInstance();    
		y=cal.get(Calendar.YEAR);    
		m=cal.get(Calendar.MONTH) + 1;    
		d=cal.get(Calendar.DATE);   
		String date = y + "-" + m + "-" + d;
		return date;
	}
	
	//����ֵΪ�û�ʹ�ø�APP��������
	public static int getDays(int userId) throws ParseException{ 
		User user = UserManager.findWithId(userId);
		String now = getInsDate();
		System.out.println(now);
		if(user == null) {return -1;} //user doesn't exist, return -1
		else
		{
			String timeR0 = user.getTimeRegister();
			String timeR1 = timeR0.replace('��', '-');
			String timeR2 = timeR1.replace('��', '-');
			String timeR = timeR2.substring(0,timeR2.length() - 1);

			SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
			
			long f = simple.parse(timeR).getTime();
			long t = simple.parse(now).getTime();
			
			int days = (int)((t - f)/(1000*60*60*24));
			return days;
		}
	}
	
<<<<<<< HEAD
	// �����û�ƽ���Ƴ�ʱ�䣨��ʽxxʱxx�֣�
	public static String getDelayedTime(int userId,boolean weekday) 
	{
		ArrayList<Schedule> schedules = ScheduleManager.findWithIdUser(userId);
		
		int total = 0; // ���¼���
		int delayMinutes = 0; // �ܹ��Ƴ��˶���ʱ��
		int delayHours = 0;
		
		if(weekday)
		{
			for(int i = 0; i < schedules.size(); i++)
			{
				if(schedules.get(i).getWeekday() == 6 || schedules.get(i).getWeekday() == 7)
				{
					schedules.remove(i);
					i--;
				}
			}
		}
		
		for(int i =0; i < schedules.size(); i++)
		{
			ArrayList<S_Affair> s_Affairs = S_AffairManager.findWithIdS(schedules.get(i).getId());
			for(int j = 0; j < s_Affairs.size(); j++)
			{
				if(s_Affairs.get(j).getTimeStart() != null)
				{
					String timeStart = s_Affairs.get(j).getTimeStart();
					String timeStartPlan = s_Affairs.get(j).getTimeStartPlan();
					
					int startHour = Integer.parseInt(((timeStart.split(":"))[0]));
					int startMinute = Integer.parseInt(((timeStart.split(":"))[1]));
					
					int startHourPlan = Integer.parseInt(((timeStartPlan.split(":"))[0]));
					int startMinutePlan = Integer.parseInt(((timeStartPlan.split(":"))[1]));
					
					if(startHour < startHourPlan || (startHour == startHourPlan && startMinute < startMinutePlan))
						continue;
					
					int delayHour = startHour - startHourPlan;
					int delayMinute = startMinute - startMinutePlan;
					
					if(delayMinute < 0)
					{
						delayMinute += 60;
						delayHour--;
					}
					
					delayMinutes += delayHour * 60 + delayMinute;
				}
				else
				{	
					String timeStartPlan = s_Affairs.get(j).getTimeStartPlan();
					
					int startHourPlan = Integer.parseInt(((timeStartPlan.split(":"))[0]));
					int startMinutePlan = Integer.parseInt(((timeStartPlan.split(":"))[1]));
					
					// ����ʱ�Ե����24��00��Ϊ����
					
					int delayHour = 24 - startHourPlan;
					int delayMinute = 0 - startMinutePlan;
					
					if(delayMinute < 0)
					{
						delayMinute += 60;
						delayHour--;
					}
					
					delayMinutes += delayHour * 60 + delayMinute;
					
					// �����������������Ƴ�ʱ��
					
					/* Calendar now = Calendar.getInstance();
					
					String dayStartPlan = schedules.get(i).getDate();
					
					int yearPlan = Integer.parseInt((dayStartPlan.split("��"))[0]);
					dayStartPlan = (dayStartPlan.split("��"))[1];
					int monthPlan = Integer.parseInt((dayStartPlan.split("��"))[0]);
					dayStartPlan = (dayStartPlan.split("��"))[1];
					int dayPlan = Integer.parseInt((dayStartPlan.split("��"))[0]);
					
					Calendar plan = Calendar.getInstance();
					plan.set(yearPlan, monthPlan - 1, dayPlan, startHourPlan, startMinutePlan);
					
					long delayTime = now.getTimeInMillis() - plan.getTimeInMillis();
					
					delayMinutes += delayTime / (60 * 1000); */
				}
			}
			total += s_Affairs.size();
		}
		
		delayMinutes /= total;
		// System.out.println(delayMinutes);
		delayHours = delayMinutes / 60;
		delayMinutes = delayMinutes % 60;
		
		String delayString = delayHours + "ʱ" + delayMinutes + "��";
		return delayString;
	}
	
	// ��ȡû����ɵ��¼���ռ��
	public static int getUnfinishedPercent(int userId,boolean weekday) 
	{
		float total = 0; // ���е��¼���
		float unfinish = 0; // û����ɵ��¼���
		int percent = 0;
		
		ArrayList<Schedule> schedules = ScheduleManager.findWithIdUser(userId);
		
		for(int i = 0; i < schedules.size(); i++)
		{
			if((schedules.get(i).getWeekday() == 6 || schedules.get(i).getWeekday() == 7) && weekday)
				continue;
			ArrayList<S_Affair> s_Affairs = S_AffairManager.findWithIdS(schedules.get(i).getId());
			for(int j = 0; j < s_Affairs.size(); j++)
			{
				if(s_Affairs.get(j).getTimeEnd() == null)
					unfinish++;
			}
			total += s_Affairs.size();
		}
		
		percent = (int)((unfinish/total) * 100);
		
		return percent;
	}
	
	//ͨ����ʼʱ�䡢����ʱ�����¼�����ʱ���
	private static float[] getStep(String from,String to) {
			
		String from1 = from.replace(':', '-');
		String to1 = to.replace(':', '-');
		SimpleDateFormat simple = new SimpleDateFormat("HH-mm");
			
		int[] l = new int[8];
		try {
				l[0] = (int)((simple.parse("00-00").getTime())/(1000*60));
				l[1] = (int)((simple.parse("06-00").getTime())/(1000*60));
				l[2] = (int)((simple.parse("08-00").getTime())/(1000*60));
				l[3] = (int)((simple.parse("12-00").getTime())/(1000*60));

				l[4] = (int)((simple.parse("13-30").getTime())/(1000*60));
				l[5] = (int)((simple.parse("17-30").getTime())/(1000*60));
				l[6] = (int)((simple.parse("22-00").getTime())/(1000*60));
				l[7] = (int)((simple.parse("24-00").getTime())/(1000*60));
				
			} catch (ParseException e1) {
				return null;
			}
			
			long f;
			try {
				f = simple.parse(from1).getTime();
			} catch (ParseException e) {
				return null;
			}
			long t;
			try {
				t = simple.parse(to1).getTime();
			} catch (ParseException e) {
				return null;
			}
			
			int all = (int)((t - f)/(1000*60));
			
			float[] per = new float[7];
			f = (int)(f/(1000*60));
			t = (int)(t/(1000*60));
			
			//per��������װÿ���׶εĵ�ǰ�¼�ռ�ȣ��Ż�ԭ������ÿ���׶���һ���ϼ���
			for(int i = 0;i < 7;i ++) {
				if(f >= l[i]&& f <= l[i+1]) { //���ҵ���ʼʱ�����ڽ׶�
					if(t >= l[i+1]) {
						per[i] = ((float)l[i+1] -(float) f) / all;
						f = l[i+1];
					}
					else {
						per[i] = ((float)t-(float)f)/all;
					}
				}
			}
			
			return per;
	}
	
	
	//ͨ����ʼʱ�䡢����ʱ�����¼�������ʱ�䣨���ط��ӣ�
	private static int getSpent(String from,String to) {
		String from1 = from.replace(':', '-');
		String to1 = to.replace(':', '-');
		SimpleDateFormat simple = new SimpleDateFormat("HH-mm");
		
=======
	//ͨ����ʼʱ�䡢����ʱ�����¼�����ʱ���
	public static float[] getStep(String from,String to) {
			
			String from1 = from.replace(':', '-');
			String to1 = to.replace(':', '-');
			SimpleDateFormat simple = new SimpleDateFormat("HH-mm");
			
			int[] l = new int[8];
			try {
				l[0] = (int)((simple.parse("00-00").getTime())/(1000*60));
				l[1] = (int)((simple.parse("06-00").getTime())/(1000*60));
				l[2] = (int)((simple.parse("08-00").getTime())/(1000*60));
				l[3] = (int)((simple.parse("12-00").getTime())/(1000*60));

				l[4] = (int)((simple.parse("13-30").getTime())/(1000*60));
				l[5] = (int)((simple.parse("17-30").getTime())/(1000*60));
				l[6] = (int)((simple.parse("22-00").getTime())/(1000*60));
				l[7] = (int)((simple.parse("24-00").getTime())/(1000*60));
				
			} catch (ParseException e1) {
				return null;
			}
			
			long f;
			try {
				f = simple.parse(from1).getTime();
			} catch (ParseException e) {
				return null;
			}
			long t;
			try {
				t = simple.parse(to1).getTime();
			} catch (ParseException e) {
				return null;
			}
			
			int all = (int)((t - f)/(1000*60));
			
			float[] per = new float[7];
			f = (int)(f/(1000*60));
			t = (int)(t/(1000*60));
			
			//per��������װÿ���׶εĵ�ǰ�¼�ռ�ȣ��Ż�ԭ������ÿ���׶���һ���ϼ���
			for(int i = 0;i < 7;i ++) {
				if(f >= l[i]&& f <= l[i+1]) { //���ҵ���ʼʱ�����ڽ׶�
					if(t >= l[i+1]) {
						per[i] = ((float)l[i+1] -(float) f) / all;
						f = l[i+1];
					}
					else {
						per[i] = ((float)t-(float)f)/all;
					}
				}
				System.out.println("per"+i+":"+per[i]);
			}
			
			
			return per;
		}
	
	
	//ͨ����ʼʱ�䡢����ʱ�����¼�������ʱ�䣨���ط��ӣ�
	public static int getSpent(String from,String to) {
		String from1 = from.replace(':', '-');
		String to1 = to.replace(':', '-');
		SimpleDateFormat simple = new SimpleDateFormat("HH-mm");
		
>>>>>>> 8342845777e737e912c6b21b32707e1561d43aea
		long f;
		try {
			f = simple.parse(from1).getTime();
		} catch (ParseException e) {
			return -1;
		}
		long t;
		try {
			t = simple.parse(to1).getTime();
		} catch (ParseException e) {
			return -1;
		}
		
		int mins = (int)((t - f)/(1000*60));
		return mins;
	}
	
<<<<<<< HEAD
	
=======
>>>>>>> 8342845777e737e912c6b21b32707e1561d43aea
	public static JSONObject getChart(int userId,boolean weekday,String type) {
		JSONObject back=new JSONObject();
		JSONArray array = new JSONArray();
		if(type.equals("SimpleAnalysis")) {
			//��Ӧ��Ϊģʽ��������ҳ(�������Ƹ�)
			//����jsonObject(���е�jsonArray�ܹ�11���Ӧ11����ǩ����ǩ��ʱ������ճ̰��Ÿñ�ǩ��ʱ��Ľ�Լ/��ʱ��ռ��)
			 ArrayList<Label> labelList = LabelManager.findWithNothing();
			 
			 for(Label label:labelList) {
				 float percent = 0;
				 int truth = 0; //��ǩ�����¼�ʵ������ʱ��
				 int schedule = 0;//��ǩ�����¼��ƻ�ʹ��ʱ��
				 
				 int idLabel = label.getId();
				 ArrayList<S_Affair> SAList = S_AffairManager.findWithIdLabel(idLabel);
				 JSONObject js = new JSONObject();
				 js.put("labelid", idLabel);
				 
				 if(SAList != null) {
					 for(S_Affair sa:SAList) {
						 Schedule s = ScheduleManager.findWithId(sa.getIdS());
						 if(s.getIdUser() == userId) { //�ǵ�ǰ�û����ճ�
							 truth += getSpent(sa.getTimeStart(),sa.getTimeEnd());
							 schedule += getSpent(sa.getTimeStartPlan(),sa.getTimeEndPlan());
						 }
					 }
				 }
				 
				 if(schedule != 0) {
					 percent = (Math.abs((float)schedule - (float)truth)/schedule)*100;
					percent = (float)(Math.round(percent*100))/100;
				 }
				 

				 js.put("percents", percent);
				 if(schedule >= truth) {
					 js.put("type", "timeSaving");
				 }
				 else {
					 js.put("type","timeOut");
				 }
				 
				 array.add(js);
			 }
			 back.put("chartInfor", array);
		}
		else if(type.equals("detailedAnalysis")) {
			//��Ӧ��Ϊģʽ������5ҳ(�������Ƹ�)
			//����jsonObject(�����ܹ�11���Ӧ11����ǩ�����¼�������ճ̰��ŵĽ�Լ��ʱ���¼�������)
			ArrayList<Label> labelList = LabelManager.findWithNothing();
			 
			 for(Label label:labelList) {
				 JSONArray percent = new JSONArray();
			
				 int save = 0,equal = 0,over = 0; //��Լ���������ʱ�¼���
				 
				 int idLabel = label.getId();
				 ArrayList<S_Affair> SAList = S_AffairManager.findWithIdLabel(idLabel);
				 JSONObject js = new JSONObject();
				 js.put("labelid", idLabel);
				 
				 if(SAList != null) {
					 for(S_Affair sa:SAList) {
						 Schedule s = ScheduleManager.findWithId(sa.getIdS());
						 if(s.getIdUser() == userId) { //�ǵ�ǰ�û����ճ�
							 int truth = 0; //��ǩ�����¼�ʵ������ʱ��
							 int schedule = 0;//��ǩ�����¼��ƻ�ʹ��ʱ��
							 truth = getSpent(sa.getTimeStart(),sa.getTimeEnd());
							 schedule = getSpent(sa.getTimeStartPlan(),sa.getTimeEndPlan());
							 if(truth > schedule) {over ++;}
							 else if(truth == schedule) {equal ++;}
							 else {save ++;}
						 }
					 }
				 }
				 
<<<<<<< HEAD
				 
=======
>>>>>>> 8342845777e737e912c6b21b32707e1561d43aea
				 int all = save + equal + over;
				 float per1 = 0;
				 float per2 = 0;
				 float per3 = 0;
				 if(all != 0) {
					  per1 = ((float)save/all)*100;
					  per2 = ((float)equal/all)*100;
					  per3 = 100 - per1 - per2;
					  per1 = (float)(Math.round(per1*100))/100;
						per2 = (float)(Math.round(per2*100))/100;
						per3 = (float)(Math.round(per3*100))/100;
				 }

<<<<<<< HEAD
				
				 
=======
>>>>>>> 8342845777e737e912c6b21b32707e1561d43aea
				 percent.add(per1);
				 percent.add(per2);
				 percent.add(per3);
				 js.put("percents", percent);
				 array.add(js);
			 }
			 
			 back.put("chartInfor", array);
		}
		else if(type.equals("densityAnalysis")) {
			//��Ӧ��Ϊģʽ������6ҳ(�������Ƹ�)
			//����jsonObject
			float[] count = new float[7];
			float[] count1 = new float[7];
			long all = 0;
<<<<<<< HEAD
			for(int i = 0;i < 7;i ++) {
				count[i] = 0;
			}
			ArrayList<Affair> AList = AffairManager.findWithNothing();
			for(Affair a:AList) {
				TimeSharing s = TimeSharingManager.findWithId(a.getIdTS());
				 if(s.getIdUser() == userId) {
					 //�ǵ�ǰ�û����ճ�
					 
					 count1 = getStep(a.getTimeStart(),a.getTimeEnd());
					 System.out.println(a.getTimeStart());
					 System.out.println(a.getTimeEnd());
					 for(int j = 0;j < 7;j ++) {
						 System.out.println(count1[j]);
						 count[j] += count1[j];
					 }
					 all ++;
				 }
			}
			
			float[] per = new float[7];
			per[0] = count[0] / 6;
			per[1] = count[1] / 3;
			per[2] = count[2] / 5;
			per[3] = (float) (count[3] / 1.5);
			per[4] = count[4] / 5;
			per[5] = (float) (count[5] / 4.5);
			per[6] = count[6] / 2;

			float density = 0;
			try {
				density = all / (getDays(userId) * 24);
			} catch (ParseException e) {
				return null;
			}
			
			//������λС��
			for(int i = 0;i<7;i++) {
				per[i] = (float)(Math.round(per[i]*100))/100;
			}
			back.put("density", density);
			JSONObject js = new JSONObject();
			js.put("period", "�賿");
			js.put("percents", per[0]);
			array.add(js);
			
			JSONObject js1 = new JSONObject();
			js1.put("period", "�糿");
			js1.put("percents", per[1]);
			array.add(js1);
			
			JSONObject js2 = new JSONObject();
			js2.put("period", "����");
			js2.put("percents", per[2]);
			array.add(js2);
			
			JSONObject js3 = new JSONObject();
			js3.put("period", "����");
			js3.put("percents", per[3]);
			array.add(js3);
			
			JSONObject js4 = new JSONObject();
			js4.put("period", "����");
			js4.put("percents", per[4]);
			array.add(js4);
			
			JSONObject js5 = new JSONObject();
			js5.put("period", "����");
			js5.put("percents", per[5]);
			array.add(js5);
			
			JSONObject js6 = new JSONObject();
			js6.put("period", "��ҹ");
			js6.put("percents", per[6]);
			array.add(js6);
			
			back.put("chartInfor", array);
		}
		else return null;
=======
			long allDays = 0;
			for(int i = 0;i < 7;i ++) {
				count[i] = 0;
			}
			ArrayList<TimeSharing> tsList = TimeSharingManager.findWithIdUser(userId);
			allDays = tsList.size();
			
			ArrayList<Affair> AList = AffairManager.findWithNothing();
			for(Affair a:AList) {
				TimeSharing s = TimeSharingManager.findWithId(a.getIdTS());
				 if(s.getIdUser() == userId) {
					 //�ǵ�ǰ�û����ճ�
					 count1 = getStep(a.getTimeStart(),a.getTimeEnd());
					 for(int j = 0;j < 7;j ++) {
						 count[j] += count1[j];
					 }
					 all ++;
				 }
			}
			
			float[] per = new float[7];
			per[0] = count[0] / (6*allDays);
			per[1] = count[1] / (3*allDays);
			per[2] = count[2] / (5*allDays);
			per[3] = (float) (count[3] / (1.5*allDays));
			per[4] = count[4] / (5*allDays);
			per[5] = (float) (count[5] / (4.5*allDays));
			per[6] = count[6] / (2*allDays);

			float density = 0;
			density = (float) all/(allDays*24);
			density = (float)(Math.round(density*100))/100;
			System.out.println("allDays:" + allDays);
			System.out.println("density:" + density);
			
			//������λС��
			for(int i = 0;i<7;i++) {
				per[i] = (float)(Math.round(per[i]*100))/100;
			}
			back.put("density", density);
			JSONObject js = new JSONObject();
			js.put("period", "�賿");
			js.put("percents", per[0]);
			array.add(js);
			
			JSONObject js1 = new JSONObject();
			js1.put("period", "�糿");
			js1.put("percents", per[1]);
			array.add(js1);
			
			JSONObject js2 = new JSONObject();
			js2.put("period", "����");
			js2.put("percents", per[2]);
			array.add(js2);
			
			JSONObject js3 = new JSONObject();
			js3.put("period", "����");
			js3.put("percents", per[3]);
			array.add(js3);
			
			JSONObject js4 = new JSONObject();
			js4.put("period", "����");
			js4.put("percents", per[4]);
			array.add(js4);
			
			JSONObject js5 = new JSONObject();
			js5.put("period", "����");
			js5.put("percents", per[5]);
			array.add(js5);
			
			JSONObject js6 = new JSONObject();
			js6.put("period", "��ҹ");
			js6.put("percents", per[6]);
			array.add(js6);
			
			back.put("chartInfor", array);
		}
		else return null;
		
		System.out.println(testForFile(back,type));
		return back;
	}
	
	private static String json2string(JSONObject json,String type) {
		String back = "";
		if(type.equals("SimpleAnalysis")) {
			JSONArray array = json.getJSONArray("chartInfor");
			for(int i=0;i<array.size();i++){
                JSONObject temp = array.getJSONObject(i);//��ÿһ������ת��json����
                String id = temp.getString("labelid");
                String percent = temp.getString("percents");
                String type1 =  temp.getString("type");
                back += "\n��"+(i+1)+"�����ݣ�\n��ǩid:"+id+"\n��ʱ���Լ������"+percent+"%\n"+type1+"\n";
			}
		}
		else if(type.equals("detailedAnalysis")) {
			JSONArray array = json.getJSONArray("chartInfor");
			for(int i=0;i<array.size();i++){
                JSONObject temp = array.getJSONObject(i);//��ÿһ������ת��json����
                String id = temp.getString("labelid");
                JSONArray a = temp.getJSONArray("percents");
                String less,equal,more;
                less = a.get(0).toString();
                equal = a.get(1).toString();
                more = a.get(2).toString();
                
                back += "\n��"+i+"�����ݣ�\n��ǩid:"+id
                		+"\n����Ԥ��ʱ���¼���������"+less
                		+"%\n����Ԥ��ʱ���¼���������"+equal
                		+"%\n����Ԥ��ʱ���¼���������"+more+"%\n";
			}
		}
		else {
			String density = json.getString("density");
			back += "����Ϊֹ��ƽ��ÿСʱ��ɵ��¼���:"+density;
			JSONArray array = json.getJSONArray("chartInfor");
			for(int i=0;i<array.size();i++){
                JSONObject temp = array.getJSONObject(i);//��ÿһ������ת��json����
                String period = temp.getString("period");
                String percent = temp.getString("percents");
                back += "\n��"+(i+1)+"��ʱ��Σ�\nʱ�������:"+period+"\n��ʱ���ÿСʱƽ�������¼�����"+percent+"\n";
			}
		}
>>>>>>> 8342845777e737e912c6b21b32707e1561d43aea
		return back;
	}
	
	private static boolean testForFile(JSONObject json,String type) {
		boolean back = false;
		String content = json2string(json,type);
		System.out.println(content);
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter("./testForFile.doc");
			fileWriter.write(content);
			fileWriter.flush();
			fileWriter.close();
			back = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return back;
	}
}