package main.model.services;

import main.model.moudle.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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
	
	public static String getDelayedTime(int idUser, boolean isWeekday)
	{
		String delayedTime = null;
		int hourDelayed = 0;
		int minuteDelayed = 0;
		int totalNumber = 0;
		ArrayList<Schedule> schedules = ScheduleManager.findWithIdUser(idUser);
		if(schedules == null)
			delayedTime = "0时0分";
		else if(!isWeekday)
		{
			for(int i = 0; i < schedules.size(); i++)
			{
				ArrayList<S_Affair> s_affairs= S_AffairManager.findWithIdS(schedules.get(i).getId());
				if(s_affairs == null)
					continue;
				for(int j = 0; j < s_affairs.size(); j++)
				{
					if(s_affairs.get(j).getTimeStart() != null)
					{
						totalNumber++;
						hourDelayed += Integer.parseInt(s_affairs.get(j).getTimeStart().split(":")[0]) - Integer.parseInt(s_affairs.get(j).getTimeStartPlan().split(":")[0]);
						minuteDelayed += Integer.parseInt(s_affairs.get(j).getTimeStart().split(":")[1]) - Integer.parseInt(s_affairs.get(j).getTimeStartPlan().split(":")[1]);						
					}
				}
				long secondDelayed = hourDelayed*3600 + minuteDelayed*60;
				secondDelayed /= totalNumber;
				minuteDelayed = (int) (secondDelayed/60);
				hourDelayed = (int)(minuteDelayed/60);
				minuteDelayed = minuteDelayed%60;
			}
			delayedTime = hourDelayed + "时" + minuteDelayed + "分";
		}
		else if(isWeekday)
		{
			for(int i = 0; i < schedules.size(); i++)
			{
				if(schedules.get(i).getWeekday() == 6 || schedules.get(i).getWeekday() == 7)
					continue;
				ArrayList<S_Affair> s_affairs= S_AffairManager.findWithIdS(schedules.get(i).getId());
				if(s_affairs == null)
					continue;
				for(int j = 0; j < s_affairs.size(); j++)
				{
					if(s_affairs.get(j).getTimeStart() != null)
					{
						totalNumber++;
						hourDelayed += Integer.parseInt(s_affairs.get(j).getTimeStart().split(":")[0]) - Integer.parseInt(s_affairs.get(j).getTimeStartPlan().split(":")[0]);
						minuteDelayed += Integer.parseInt(s_affairs.get(j).getTimeStart().split(":")[1]) - Integer.parseInt(s_affairs.get(j).getTimeStartPlan().split(":")[1]);						
					}
				}
				long secondDelayed = hourDelayed*3600 + minuteDelayed*60;
				secondDelayed /= totalNumber;
				minuteDelayed = (int) (secondDelayed/60);
				hourDelayed = (int)(minuteDelayed/60);
				minuteDelayed = minuteDelayed%60;
			}
			delayedTime = hourDelayed + "时" + minuteDelayed + "分";
		}
		return delayedTime;
	}
	
	//返回没有完成的比例
	public static float getUnfinishedPercent(int idUser, boolean isWeekday)
	{
		float unfinishedPercent = 0;
		float finishedNumber = 0;
		float totalNumber = 0;
		ArrayList<Schedule> schedules = null;
		schedules = ScheduleManager.findWithIdUser(idUser);
		if(schedules == null)
			unfinishedPercent = 0;
		else if(!isWeekday)
		{
			for(int i = 0; i < schedules.size(); i++)
			{
				ArrayList<S_Affair> s_affairs= S_AffairManager.findWithIdS(schedules.get(i).getId());
				if(s_affairs == null)
					continue;
				totalNumber += s_affairs.size();
				for(int j = 0; j < s_affairs.size(); j++)
				{
					if(s_affairs.get(j).getTimeEnd() != null)
						finishedNumber++;
				}
			}
			unfinishedPercent = (totalNumber - finishedNumber)/totalNumber;
		}
		else if(isWeekday)
		{
			for(int i = 0; i < schedules.size(); i++)
			{
				ArrayList<S_Affair> s_affairs= S_AffairManager.findWithIdS(schedules.get(i).getId());
				if(s_affairs == null)
					continue;
				if(schedules.get(i).getWeekday() == 6 || schedules.get(i).getWeekday() == 7)
				totalNumber += s_affairs.size();
				for(int j = 0; j < s_affairs.size(); j++)
				{
					if(s_affairs.get(j).getTimeEnd() != null)
						finishedNumber++;
				}
			}
			unfinishedPercent = (totalNumber - finishedNumber)/totalNumber;
		}
		return unfinishedPercent;
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
			String timeR1 = timeR0.replace('年', '-');
			String timeR2 = timeR1.replace('月', '-');
			String timeR = timeR2.substring(0,timeR2.length() - 1);

			SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
			
			long f = simple.parse(timeR).getTime();
			long t = simple.parse(now).getTime();
			
			int days = (int)((t - f)/(1000*60*60*24));
			return days;
		}
	}
	
	
	public static JSONObject getChart(int userId,boolean weekday,String type) {
		
		JSONObject result = new JSONObject();
		if(type.equals("SimpleAnalysis")) {
			
		}
		else if(type.equals("detailedAnalysis")) {
			
		}
		else if(type.equals("densityAnalysis")) {
			
		}
		else return null;
		return result;
	}
	
}