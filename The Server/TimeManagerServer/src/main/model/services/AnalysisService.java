package main.model.services;

import main.model.moudle.*;

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
	
	
	// 返回用户平均推迟时间（格式xx时xx分）
	public static String getDelayedTime(int userId,boolean weekday) 
	{
		ArrayList<Schedule> schedules = ScheduleManager.findWithIdUser(userId);
		
		if(schedules == null)
			return "00时00分";
		
		int total = 0; // 总事件数
		int delayMinutes = 0; // 总共推迟了多少时间
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
			if(s_Affairs == null)
				continue;
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
					
					// 以下时以当天的24：00作为结束
					
					int delayHour = 24 - startHourPlan;
					int delayMinute = 0 - startMinutePlan;
					
					if(delayMinute < 0)
					{
						delayMinute += 60;
						delayHour--;
					}
					
					delayMinutes += delayHour * 60 + delayMinute;
					
					// 以下是算上天数的推迟时间
					
					/* Calendar now = Calendar.getInstance();
					
					String dayStartPlan = schedules.get(i).getDate();
					
					int yearPlan = Integer.parseInt((dayStartPlan.split("年"))[0]);
					dayStartPlan = (dayStartPlan.split("年"))[1];
					int monthPlan = Integer.parseInt((dayStartPlan.split("月"))[0]);
					dayStartPlan = (dayStartPlan.split("月"))[1];
					int dayPlan = Integer.parseInt((dayStartPlan.split("日"))[0]);
					
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
		
		String delayString = delayHours + "时" + delayMinutes + "分";
		return delayString;
	}
	
	// 获取没有完成的事件的占比
	public static int getUnfinishedPercent(int userId,boolean weekday) 
	{
		float total = 0; // 所有的事件数
		float unfinish = 0; // 没有完成的事件数
		int percent = 0;
		
		ArrayList<Schedule> schedules = ScheduleManager.findWithIdUser(userId);
		
		if(schedules == null)
			return 0;
		
		for(int i = 0; i < schedules.size(); i++)
		{
			if((schedules.get(i).getWeekday() == 6 || schedules.get(i).getWeekday() == 7) && weekday)
				continue;
			ArrayList<S_Affair> s_Affairs = S_AffairManager.findWithIdS(schedules.get(i).getId());
			if(s_Affairs == null)
				continue;
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
	
	//返回值为用户使用该APP的总天数
	public static int getDays(int userId) throws ParseException{ 
		User user = UserManager.findWithId(userId);
		String now = getInsDate();
		System.out.println(now);
		if(user == null) {return -1;} //user doesn't exist, return -1
		else
		{
			String timeR0 = user.getTimeRegister();

			SimpleDateFormat simple = new SimpleDateFormat("yyyy年MM月dd");
			long f = simple.parse(timeR0).getTime();
			simple = new SimpleDateFormat("yyyy-MM-dd");
			long t = simple.parse(now).getTime();
			
			int days = (int)((t - f)/(1000*60*60*24));
			return days;
		}
	}
	
	//通过开始时间、结束时间获得时间所在时间段
	public static float[] getStep(String from,String to, int[] l) {		
		
			SimpleDateFormat simple = new SimpleDateFormat("HH:mm");
				
			long f,t;
			try {
				f = simple.parse(from).getTime();
				t = simple.parse(to).getTime();
			} catch (ParseException e) {
				return null;
			}
			
			int all = (int)((t - f)/(1000*60));
			
			float[] per = new float[7];
			f = (int)(f/(1000*60));
			t = (int)(t/(1000*60));
			
			//per数组用来装每个阶段的当前事件占比，放回原函数在每个阶段逐一加上即可
			for(int i = 0;i < 7;i ++) {
				if(f >= l[i]&& f <= l[i+1]) { //先找到开始时间所在阶段
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
	
	
	//通过开始时间、结束时间获得事件的消耗时间（返回分钟）
	public static int getSpent(String from,String to) {
		SimpleDateFormat simple = new SimpleDateFormat("HH:mm");
		
		long f,t;
		try {
			f = simple.parse(from).getTime();
			t = simple.parse(to).getTime();
		} catch (ParseException e) {
			return -1;
		}
		
		int mins = (int)((t - f)/(1000*60));
		return mins;
	}
	
	//
	public static JSONObject getChart(int userId,boolean weekday,String type) {
		//weekday true 代表是工作日
		JSONObject back=new JSONObject();
		JSONArray array = new JSONArray();
		
		
		if(type.equals("SimpleAnalysis")) {
			//对应行为模式分析第四页
			//返回jsonObject（其中的jsonArray总共11项，对应11个标签各标签总时间相比日程安排该标签总时间的节约/超时所占比）
			 ArrayList<Label> labelList = LabelManager.findWithNothing();
			 
			 for(Label label:labelList) {
				 float percent = 0;
				 int truth = 0; //标签所有事件实际消耗时间
				 int schedule = 0;//标签所有时间计划使用时间
				 
				 int idLabel = label.getId();
				 ArrayList<S_Affair> SAList = S_AffairManager.findWithIdLabel(idLabel);
				 JSONObject js = new JSONObject();
				 js.put("labelid", idLabel);
				 
				 if(SAList != null) {
					 for(S_Affair sa:SAList) {
						 Schedule s = ScheduleManager.findWithId(sa.getIdS());
						 if(weekday && s.getWeekday() > 5) { //需要工作日但当前日程表为非工作日
							 System.out.println("out");
							 continue;
						 }
						 if((!weekday) && s.getWeekday() <= 5){ //需要非工作日但当前日程表为工作日
							 System.out.println("out");

							 continue;
						 }
						 if(sa.getTimeStart() == null || sa.getTimeEnd() == null) continue;
						 
						 if(s.getIdUser() == userId) { //是当前用户的日程
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
			//对应行为模式分析第5页
			//返回jsonObject(数组总共11项，对应11个标签所有事件中相比日程安排的节约超市的事件数比例)
			ArrayList<Label> labelList = LabelManager.findWithNothing();
			 
			 for(Label label:labelList) {
				 JSONArray percent = new JSONArray();
			
				 int save = 0,equal = 0,over = 0; //节约、相符、超时事件数
				 
				 int idLabel = label.getId();
				 ArrayList<S_Affair> SAList = S_AffairManager.findWithIdLabel(idLabel);
				 JSONObject js = new JSONObject();
				 js.put("labelid", idLabel);
				 
				 if(SAList != null) {
					 for(S_Affair sa:SAList) {
						 Schedule s = ScheduleManager.findWithId(sa.getIdS());
						 
						 if(weekday && s.getWeekday() > 5) { //需要工作日但当前日程表为非工作日
							 System.out.println("out");
							 continue;
						 }
						 if((!weekday) && s.getWeekday() <= 5){ //需要非工作日但当前日程表为工作日
							 System.out.println("out");

							 continue;
						 }
						 if(sa.getTimeStart() == null || sa.getTimeEnd() == null) continue;
						 
						 if(s.getIdUser() == userId) { //是当前用户的日程
							 int truth = 0; //标签所有事件实际消耗时间
							 int schedule = 0;//标签所有时间计划使用时间
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
			//对应行为模式分析第6页
			//返回jsonObject
			float[] count = new float[7];
			float[] count1 = new float[7];
			SimpleDateFormat simple = new SimpleDateFormat("HH-mm");
			long all = 0;
			long allDays = 0;
			for(int i = 0;i < 7;i ++) {
				count[i] = 0;
			}
			ArrayList<TimeSharing> tsList = TimeSharingManager.findWithIdUser(userId);
			
			for(TimeSharing t : tsList) {
				if(weekday && t.getWeekday() > 5) { //需要工作日但当前日程表为非工作日
					 System.out.println("out");
					 continue;
				 }
				 if((!weekday) && t.getWeekday() <= 5){ //需要非工作日但当前日程表为工作日
					 System.out.println("out");
					 continue;
				 }
				 allDays ++;
			}
			
			int[] l = new int[8];
			try {
				l[0] = (int)((simple.parse("00-00").getTime())/(1000*60));
				l[1] = (int)((simple.parse("06-00").getTime())/(1000*60));
				l[2] = (int)((simple.parse("08-00").getTime())/(1000*60));
				l[3] = (int)((simple.parse("12-00" ).getTime())/(1000*60));
				l[4] = (int)((simple.parse("13-30").getTime())/(1000*60));
				l[5] = (int)((simple.parse("17-30").getTime())/(1000*60));
				l[6] = (int)((simple.parse("22-00").getTime())/(1000*60));
				l[7] = (int)((simple.parse("24-00").getTime())/(1000*60));
				
			} catch (ParseException e1) {
				return null;
			}
			
			ArrayList<Affair> AList = AffairManager.findWithNothing();
			for(Affair a:AList) {
				TimeSharing s = TimeSharingManager.findWithId(a.getIdTS());
				
				if(weekday && s.getWeekday() > 5) { //需要工作日但当前日程表为非工作日
					 System.out.println("out");
					 continue;
				 }
				 if((!weekday) && s.getWeekday() <= 5){ //需要非工作日但当前日程表为工作日
					 System.out.println("out");
					 continue;
				 }
				 if(a.getTimeStart() == null || a.getTimeEnd() == null) continue;
				
				 if(s.getIdUser() == userId) {
					 //是当前用户的日程
					 count1 = getStep(a.getTimeStart(),a.getTimeEnd(),l);
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
			
			//保留两位小数
			for(int i = 0;i<7;i++) {
				per[i] = (float)(Math.round(per[i]*100))/100;
			}
			back.put("density", density);
			JSONObject js = new JSONObject();
			js.put("period", "凌晨");
			js.put("percents", per[0]);
			array.add(js);
			
			JSONObject js1 = new JSONObject();
			js1.put("period", "早晨");
			js1.put("percents", per[1]);
			array.add(js1);
			
			JSONObject js2 = new JSONObject();
			js2.put("period", "上午");
			js2.put("percents", per[2]);
			array.add(js2);
			
			JSONObject js3 = new JSONObject();
			js3.put("period", "中午");
			js3.put("percents", per[3]);
			array.add(js3);
			
			JSONObject js4 = new JSONObject();
			js4.put("period", "下午");
			js4.put("percents", per[4]);
			array.add(js4);
			
			JSONObject js5 = new JSONObject();
			js5.put("period", "晚上");
			js5.put("percents", per[5]);
			array.add(js5);
			
			JSONObject js6 = new JSONObject();
			js6.put("period", "深夜");
			js6.put("percents", per[6]);
			array.add(js6);
			
			back.put("chartInfor", array);
		}
		else return null;
		
		//System.out.println(testForFile(back,type));
		return back;
	}
	
	//toDOCfile process
	/*private static String json2string(JSONObject json,String type) {
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
		return back;
	}*/
	
	/*private static boolean testForFile(JSONObject json,String type) {
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
	}*/
}