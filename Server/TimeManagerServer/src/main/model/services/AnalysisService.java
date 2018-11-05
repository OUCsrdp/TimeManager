package main.model.services;

import main.model.services.moudle.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import main.model.services.db.*;

public class AnalysisService{
	
	public static String getInsDate()
	{
		int y,m,d,h,mi,s;    
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
			}
			
			
			return per;
		}
	
	
	//ͨ����ʼʱ�䡢����ʱ�����¼�������ʱ�䣨���ط��ӣ�
	public static int getSpent(String from,String to) {
		String from1 = from.replace(':', '-');
		String to1 = to.replace(':', '-');
		SimpleDateFormat simple = new SimpleDateFormat("HH-mm");
		
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
		return back;
	}
	
}