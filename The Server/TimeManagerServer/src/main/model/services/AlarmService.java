package main.model.services;

import java.util.ArrayList;
import java.util.Calendar;

import main.model.db.*;
import main.model.moudle.*;

public class AlarmService {
	
	
	//使用userId来找到对应user当天的所有日程表安排的时间
	public static ArrayList<Long> getS_AffairAlarmTimeWithIdUser(int idUser, String dateString)
	{
		if(UserManager.findWithId(idUser) == null)
			return null;
		Calendar calendar = Calendar.getInstance();
		ArrayList<Long> times = new ArrayList<Long>();
		ArrayList<S_Affair> s_Affairs = new ArrayList<S_Affair>();
		s_Affairs = S_AffairManager.findWithIdTS(TimeSharingManager.findWithDate(idUser, dateString).getId());
		if(s_Affairs == null)
			return null;
		for(int i = 0; i < s_Affairs.size(); i++)
		{
			String timeString = s_Affairs.get(i).getTimeStartAlarm();
			if(timeString == null)
				continue;
			int hour = Integer.parseInt(timeString.substring(0, 1));
			int minute = Integer.parseInt(timeString.substring(3, 4));
			if(calendar.get(Calendar.HOUR_OF_DAY) < hour || (calendar.get(Calendar.HOUR_OF_DAY) == hour && calendar.get(Calendar.MINUTE) < minute))
			{
				Calendar alarmTime = Calendar.getInstance();
				alarmTime.set(Calendar.HOUR_OF_DAY, hour);
				alarmTime.set(Calendar.MINUTE, minute);
				long time = alarmTime.getTime().getTime();
				times.add(time);
			}
		}
		return times;
	}
	
	public static ArrayList<Long> getS_AffairPlanTimeWithIdUser(int idUser, String dateString)
	{
		if(UserManager.findWithId(idUser) == null)
			return null;
		Calendar calendar = Calendar.getInstance();
		ArrayList<Long> times = new ArrayList<Long>();
		ArrayList<S_Affair> s_Affairs = new ArrayList<S_Affair>();
		s_Affairs = S_AffairManager.findWithIdTS(TimeSharingManager.findWithDate(idUser, dateString).getId());
		if(s_Affairs == null)
			return null;
		for(int i = 0; i < s_Affairs.size(); i++)
		{
			String timeString = s_Affairs.get(i).getTimeStartPlan();
			if(timeString == null)
				continue;
			int hour = Integer.parseInt(timeString.substring(0, 1));
			int minute = Integer.parseInt(timeString.substring(3, 4));
			if(calendar.get(Calendar.HOUR_OF_DAY) < hour || (calendar.get(Calendar.HOUR_OF_DAY) == hour && calendar.get(Calendar.MINUTE) < minute))
			{
				Calendar alarmTime = Calendar.getInstance();
				alarmTime.set(Calendar.HOUR_OF_DAY, hour);
				alarmTime.set(Calendar.MINUTE, minute);
				long time = alarmTime.getTime().getTime();
				times.add(time);
			}
		}
		return times;
	}
}
