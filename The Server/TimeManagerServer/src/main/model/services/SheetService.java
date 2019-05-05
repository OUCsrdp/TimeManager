package main.model.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import main.model.moudle.*;
import main.model.db.*;

public class SheetService 
{
	public JSONObject getDailySheet(int userId,String date)
	{
		JSONObject back = new JSONObject();
		ArrayList<TimeSharing> timeSharings = TimeSharingManager.findWithIdUser(userId);
		if(timeSharings.isEmpty() || timeSharings == null)
			return null;
		TimeSharing timeSharing = null;
		for(int i = 0; i < timeSharings.size(); i++)
		{
			if(timeSharings.get(i).getDate().equals(date))
			{
				timeSharing = timeSharings.get(i);
				break;
			}
		}
		if(timeSharing == null)
			return null;
		ArrayList<Affair> affairs = AffairManager.findWithIdTS(timeSharing.getId());
		if(affairs == null) affairs = new ArrayList<Affair>();
		ArrayList<S_Affair> s_Affairs = S_AffairManager.findWithIdTS(timeSharing.getId());
		if(s_Affairs == null) s_Affairs = new ArrayList<S_Affair>();
		String weekday_s;
		switch(timeSharing.getWeekday())
		{
			case 1: weekday_s = "星期一"; break;
			case 2: weekday_s = "星期二"; break;
			case 3: weekday_s = "星期三"; break;
			case 4: weekday_s = "星期四"; break;
			case 5: weekday_s = "星期五"; break;
			case 6: weekday_s = "星期六"; break;
			case 7: weekday_s = "星期日"; break;
			default: return null;
		}
		back.put("weekday", weekday_s);
		
		ArrayList<Label> labels = LabelManager.findWithNothing();
		JSONArray affairArray = new JSONArray();
		int minutesAll = 0;
		for(int i = 0; i < affairs.size(); i++)
		{
			if(affairs.get(i).getTimeEnd() == null || affairs.get(i).getTimeStart() == null) continue;
			int minutes = Integer.parseInt(affairs.get(i).getTimeEnd().substring(3, 5)) - Integer.parseInt(affairs.get(i).getTimeStart().substring(3, 5));
			int hours = Integer.parseInt(affairs.get(i).getTimeEnd().substring(0, 2)) - Integer.parseInt(affairs.get(i).getTimeStart().substring(0, 2));
			if(minutes < 0)
			{
				minutes += 60;
				hours--;
			}
			minutesAll += (minutes + hours*60);
		}
		for(int i = 0; i < labels.size(); i++)
		{
			String duration = "00:00";
			int minutesPerLabel = 0;
			int numberPerLabel = 0;
			float percent = 0.0f;
			float satisfaction = 0.0f;
			JSONObject labelAffair = new JSONObject();
			labelAffair.put("labelid", labels.get(i).getId());
			for(int j = 0; j < affairs.size(); j++)
			{
				if(affairs.get(j).getIdLabel() == labels.get(i).getId())
				{
					if(affairs.get(j).getTimeEnd() == null || affairs.get(j).getTimeStart() == null) continue;
					int minutes = Integer.parseInt(affairs.get(j).getTimeEnd().substring(3, 5)) - Integer.parseInt(affairs.get(j).getTimeStart().substring(3, 5));
					int hours = Integer.parseInt(affairs.get(j).getTimeEnd().substring(0, 2)) - Integer.parseInt(affairs.get(j).getTimeStart().substring(0, 2));
					if(minutes < 0)
					{
						minutes += 60;
						hours--;
					}
					minutesPerLabel += (minutes + hours*60);
					satisfaction += affairs.get(j).getSatisfaction();
					numberPerLabel ++;
				}
			}
			duration = String.valueOf(minutesPerLabel/60) + ":" + String.valueOf(minutesPerLabel%60);
			if(minutesPerLabel/60 < 10) {
				if(minutesPerLabel%60 < 10) 
					duration = "0" + String.valueOf(minutesPerLabel/60) + ":0" + String.valueOf(minutesPerLabel%60);
				else
					duration = "0" + String.valueOf(minutesPerLabel/60) + ":" + String.valueOf(minutesPerLabel%60);
			}
			else {
				if(minutesPerLabel%60 < 10) 
					duration = String.valueOf(minutesPerLabel/60) + ":0" + String.valueOf(minutesPerLabel%60);
				else
					duration = String.valueOf(minutesPerLabel/60) + ":" + String.valueOf(minutesPerLabel%60);
			}
			if(minutesAll != 0)
				percent = (float)minutesPerLabel/(float)minutesAll;
			else percent = 0;
			if(numberPerLabel != 0)
        		satisfaction /= numberPerLabel;
        	else satisfaction = 0;
			labelAffair.put("duration", duration);
			labelAffair.put("percent", percent);
			labelAffair.put("satisfaction", satisfaction);
			if(percent != 0) {
				affairArray.add(labelAffair);
			}
			
		}
		back.put("TimeSharing", affairArray);
		
		JSONArray s_AffairArray = new JSONArray();
		minutesAll = 0;
		for(int i = 0; i < s_Affairs.size(); i++)
		{
			if(s_Affairs.get(i).getTimeEnd() == null || s_Affairs.get(i).getTimeStart() == null) continue;
			int minutes = Integer.parseInt(s_Affairs.get(i).getTimeEnd().substring(3, 5)) - Integer.parseInt(s_Affairs.get(i).getTimeStart().substring(3, 5));
			int hours = Integer.parseInt(s_Affairs.get(i).getTimeEnd().substring(0, 2)) - Integer.parseInt(s_Affairs.get(i).getTimeStart().substring(0, 2));
			if(minutes < 0)
			{
				minutes += 60;
				hours--;
			}
			minutesAll += (minutes + hours*60);
		}
		for(int i = 0; i < labels.size(); i++)
		{
			String duration = "00:00";
			int minutesPerLabel = 0;
			float percent = 0.0f;
			JSONObject labelS_Affair = new JSONObject();
			labelS_Affair.put("labelid", labels.get(i).getId());
			for(int j = 0; j < s_Affairs.size(); j++)
			{
				if(s_Affairs.get(j).getIdLabel() == labels.get(i).getId())
				{
					if(s_Affairs.get(j).getTimeEnd() == null || s_Affairs.get(j).getTimeStart() == null) continue;
					int minutes = Integer.parseInt(s_Affairs.get(j).getTimeEnd().substring(3, 5)) - Integer.parseInt(s_Affairs.get(j).getTimeStart().substring(3, 5));
					int hours = Integer.parseInt(s_Affairs.get(j).getTimeEnd().substring(0, 2)) - Integer.parseInt(s_Affairs.get(j).getTimeStart().substring(0, 2));
					if(minutes < 0)
					{
						minutes += 60;
						hours--;
					}
					minutesPerLabel += (minutes + hours*60);
				}
			}
			if(minutesPerLabel/60 < 10) {
				if(minutesPerLabel%60 < 10) 
					duration = "0" + String.valueOf(minutesPerLabel/60) + ":0" + String.valueOf(minutesPerLabel%60);
				else
					duration = "0" + String.valueOf(minutesPerLabel/60) + ":" + String.valueOf(minutesPerLabel%60);
			}
			else {
				if(minutesPerLabel%60 < 10) 
					duration = String.valueOf(minutesPerLabel/60) + ":0" + String.valueOf(minutesPerLabel%60);
				else
					duration = String.valueOf(minutesPerLabel/60) + ":" + String.valueOf(minutesPerLabel%60);
			}
				
			if(minutesAll == 0)
				percent = 0;
			else
				percent = (float)minutesPerLabel/(float)minutesAll;
			labelS_Affair.put("duration", duration);
			labelS_Affair.put("percent", percent);
			if(percent != 0) {
				s_AffairArray.add(labelS_Affair);
			}
			
		}
		back.put("Schedule", s_AffairArray);
 		return back;
	}
	
	public JSONObject getWeeklyChange(int userId,String date,int labelid)
	{
		JSONObject back = new JSONObject();
		
		ArrayList<TimeSharing> timeSharings = TimeSharingManager.findWithIdUser(userId);
		
		//System.out.println(timeSharings.size());
		
		Pattern p = Pattern.compile("年"); 
		String [] split1 = p.split(date);
		String yearS = split1[0];
		p = Pattern.compile("月");
		String [] split2 = p.split(split1[1]);
		String monthS = split2[0];
		p = Pattern.compile("日");
		String dayS = p.split(split2[1])[0];
		Calendar timeOfDate = Calendar.getInstance();		
		timeOfDate.set(Integer.parseInt(yearS), Integer.parseInt(monthS), Integer.parseInt(dayS));
		
		String timeRegisterS = UserManager.findWithId(userId).getTimeRegister();
		p = Pattern.compile("年"); 
		split1 = p.split(timeRegisterS);
		yearS = split1[0];
		p = Pattern.compile("月");
		split2 = p.split(split1[1]);
		monthS = split2[0];
		p = Pattern.compile("日");
		dayS = p.split(split2[1])[0];
		Calendar timeRegister = Calendar.getInstance();
		timeRegister.set(Integer.parseInt(yearS), Integer.parseInt(monthS), Integer.parseInt(dayS));
		
		int week = 0;
		
        int day1 = timeRegister.get(Calendar.DAY_OF_YEAR);
        int day2 = timeOfDate.get(Calendar.DAY_OF_YEAR);
        
        int year1 = timeRegister.get(Calendar.YEAR);
        int year2 = timeOfDate.get(Calendar.YEAR);
        
        if(timeRegister.get(Calendar.DAY_OF_WEEK) > 1)
        	day1 +=  8 - (timeRegister.get(Calendar.DAY_OF_WEEK));
        if (year1 != year2) 
        {  //同一年
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) 
            {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) //闰年
                    timeDistance += 366;
                else
                    timeDistance += 365;
            }
            week = (timeDistance + (day2 - day1))/7 + 1;
        } 
        else 
        	week = (day2 - day1)/7 + 1;
        
        back.put("week", "第" + week + "周");
        //截至此获取到今天是使用的第几周
        
        ArrayList<String> durationArray = new ArrayList<String>();
        //用户没有时间分配表
        if(timeSharings == null) {
			for(int i = 0; i < 7; i++) {
				durationArray.add("00:00");
			}
			back.put("durationArray", durationArray);
	        return back;
		}
        
        timeOfDate.add(Calendar.DATE, -timeRegister.get(Calendar.DAY_OF_WEEK));
        for(int i = 0; i < 7; i++)
        {
        	String newDate = timeOfDate.get(Calendar.YEAR) + "年" + timeOfDate.get(Calendar.MONTH)+ "月" +timeOfDate.get(Calendar.DATE) + "日";
        	timeOfDate.add(Calendar.DATE, 1);
        	TimeSharing timeSharing = null;
  
        	for(int j = 0; j < timeSharings.size(); j++)
        	{
        		if(timeSharings.get(j).getDate().equals(newDate))
        		{
        			System.out.println(timeSharings.get(j).getDate());
        			timeSharing = timeSharings.get(j);
            		break;
        		}
        	}
        	if(timeSharing == null)
        	{
        		durationArray.add("00:00");
        		continue;
        	}
        	String duration = null;
        	ArrayList<Affair> affairs = AffairManager.findWithIdTS(timeSharing.getId());
        	if(affairs == null)
        		affairs = new ArrayList<Affair>();
        	int hours0 = 0,minutes0 = 0;
        	for(int j = 0; j < affairs.size(); j++)
			{
				if(affairs.get(j).getIdLabel() == labelid)
				{
					if(affairs.get(j).getTimeEnd() == null || affairs.get(j).getTimeStart() == null) continue;
					int minutes = Integer.parseInt(affairs.get(j).getTimeEnd().substring(3, 5)) - Integer.parseInt(affairs.get(j).getTimeStart().substring(3, 5));
					int hours = Integer.parseInt(affairs.get(j).getTimeEnd().substring(0, 2)) - Integer.parseInt(affairs.get(j).getTimeStart().substring(0, 2));
					if(minutes < 0)
					{
						minutes += 60;
						hours--;
					}
					minutes0 += minutes;
					hours0 += hours;
				}
			}
        	minutes0 /= 60;
        	hours0 %= 60;
        	
        	if(hours0 < 10)
				if(minutes0 < 10)
					duration ="0"+ String.valueOf(hours0) + ":0" + String.valueOf(minutes0);
				else 
					duration = "0"+String.valueOf(hours0) + ":" + String.valueOf(minutes0);
			else {
				if(minutes0 < 10)
					duration = String.valueOf(hours0) + ":0" + String.valueOf(minutes0);
				else 
					duration = String.valueOf(hours0) + ":" + String.valueOf(minutes0);
			}
				
			durationArray.add(duration);
        }
        back.put("durationArray", durationArray);
        return back;
	}
	
	
  	public JSONObject getWeeklySheet(int userId,String date)
  	{
  		JSONObject back = new JSONObject();
		
		ArrayList<TimeSharing> timeSharings = TimeSharingManager.findWithIdUser(userId);
		
		Pattern p = Pattern.compile("年"); 
		String [] split1 = p.split(date);
		String yearS = split1[0];
		p = Pattern.compile("月");
		String [] split2 = p.split(split1[1]);
		String monthS = split2[0];
		p = Pattern.compile("日");
		String dayS = p.split(split2[1])[0];
		Calendar timeOfDate = Calendar.getInstance();		
		timeOfDate.set(Integer.parseInt(yearS), Integer.parseInt(monthS), Integer.parseInt(dayS));
      

		
		String timeRegisterS = UserManager.findWithId(userId).getTimeRegister();
		p = Pattern.compile("年"); 
		split1 = p.split(timeRegisterS);
		yearS = split1[0];
		p = Pattern.compile("月");
		split2 = p.split(split1[1]);
		monthS = split2[0];
		p = Pattern.compile("日");
		dayS = p.split(split2[1])[0];
		Calendar timeRegister = Calendar.getInstance();
		timeRegister.set(Integer.parseInt(yearS), Integer.parseInt(monthS), Integer.parseInt(dayS));
		
		int week = 0;
		
        int day1 = timeRegister.get(Calendar.DAY_OF_YEAR);
        int day2 = timeOfDate.get(Calendar.DAY_OF_YEAR);
        
        int year1 = timeRegister.get(Calendar.YEAR);
        int year2 = timeOfDate.get(Calendar.YEAR);
        
        if(timeRegister.get(Calendar.DAY_OF_WEEK) > 1)
        	day1 +=  8 - (timeRegister.get(Calendar.DAY_OF_WEEK));
        if (year1 != year2) 
        {  //同一年
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) 
            {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) //闰年
                    timeDistance += 366;
                else
                    timeDistance += 365;
            }
            week = (timeDistance + (day2 - day1))/7 + 1;
        } 
        else 
        	week = (day2 - day1)/7 + 1;
        
        back.put("week", "第" + week + "周");
        
        int w = timeOfDate.get(Calendar.DAY_OF_WEEK);
        timeOfDate.add(Calendar.DATE, -w);       
        
        ArrayList<Label> labels = LabelManager.findWithNothing();
      //用户没有时间分配表
        if(timeSharings == null) {
        	JSONArray affairArray = new JSONArray();
        	back.put("TimeSharing", affairArray);
        	back.put("Schedule", affairArray);
	        return back;
		}
        
      //算出7天所有事件的时间
        int minutesAll = 0;
        
        for(int i = 0; i < 7; i++)
        {
        	//bug
        	timeOfDate.add(Calendar.DATE, 1);

        	TimeSharing timeSharing = null;
        	for(int j = 0; j < timeSharings.size(); j++)
        	{
        		if(timeSharings.get(j).getDate().equals(timeOfDate.get(Calendar.YEAR) + "年" + timeOfDate.get(Calendar.MONTH) + "月" +timeOfDate.get(Calendar.DATE) + "日"))
        		{
        			timeSharing = timeSharings.get(j);
            		break;
        		}
        	}
        	if(timeSharing == null)
    			continue;
        	
    		ArrayList<Affair> affairs = AffairManager.findWithIdTS(timeSharing.getId());
    		if(affairs == null) continue;
    		
    		for(int j = 0; j < affairs.size(); j++)
    		{
    			if(affairs.get(j).getTimeEnd() == null || affairs.get(j).getTimeStart() == null) continue;
    			int minutes = Integer.parseInt(affairs.get(j).getTimeEnd().substring(3, 5)) - Integer.parseInt(affairs.get(j).getTimeStart().substring(3, 5));
    			int hours = Integer.parseInt(affairs.get(j).getTimeEnd().substring(0, 2)) - Integer.parseInt(affairs.get(j).getTimeStart().substring(0, 2));
    			if(minutes < 0)
    			{
    				minutes += 60;
    				hours--;
    			}
    			minutesAll += (minutes + hours*60);
    		}
        }
        timeOfDate.add(Calendar.DATE, -7);	
        
      //分别计算出每一个id所占比例
        JSONArray affairArray = new JSONArray();
        for(int i = 0; i < labels.size(); i++)
        {
        	JSONObject labelAffair = new JSONObject();
        	labelAffair.put("labelid", labels.get(i).getId());
        	String duration = "00:00";
			int minutesPerLabel = 0;
			int numberPerLabel = 0;
			float percent = 0.0f;
			float satisfaction = 0.0f;
        	for(int j = 0; j < 7; j++)
            {
            	timeOfDate.add(Calendar.DATE, 1);
            	TimeSharing timeSharing = null;
            	for(int k = 0; k < timeSharings.size(); k++)
            	{
            		if(timeSharings.get(k).getDate().equals(timeOfDate.get(Calendar.YEAR) + "年" + timeOfDate.get(Calendar.MONTH) + "月" +timeOfDate.get(Calendar.DATE) + "日"))
            		{
            			timeSharing = timeSharings.get(k);
                		break;
            		}
            		
            	}
            	if(timeSharing == null)
            		continue;
            	ArrayList<Affair> affairs = AffairManager.findWithIdTS(timeSharing.getId());
            	if(affairs == null)
            		affairs = new ArrayList<Affair>();
            	for(int k = 0; k < affairs.size(); k++)
    			{
    				if(affairs.get(k).getIdLabel() == labels.get(i).getId())
    				{
    					if(affairs.get(k).getTimeEnd() == null || affairs.get(k).getTimeStart() == null) continue;
    					int minutes = Integer.parseInt(affairs.get(k).getTimeEnd().substring(3, 5)) - Integer.parseInt(affairs.get(k).getTimeStart().substring(3, 5));
    					int hours = Integer.parseInt(affairs.get(k).getTimeEnd().substring(0, 2)) - Integer.parseInt(affairs.get(k).getTimeStart().substring(0, 2));
    					if(minutes < 0)
    					{
    						minutes += 60;
    						hours--;
    					}
    					minutesPerLabel += (minutes + hours*60);
    					satisfaction += affairs.get(k).getSatisfaction();
    					numberPerLabel ++;
    				}
    			}
            }
        	duration = String.valueOf(minutesPerLabel/60) + ":" + String.valueOf(minutesPerLabel%60);
        	if(minutesPerLabel/60 < 10) {
				if(minutesPerLabel%60 < 10) 
					duration = "0" + String.valueOf(minutesPerLabel/60) + ":0" + String.valueOf(minutesPerLabel%60);
				else
					duration = "0" + String.valueOf(minutesPerLabel/60) + ":" + String.valueOf(minutesPerLabel%60);
			}
			else {
				if(minutesPerLabel%60 < 10) 
					duration = String.valueOf(minutesPerLabel/60) + ":0" + String.valueOf(minutesPerLabel%60);
				else
					duration = String.valueOf(minutesPerLabel/60) + ":" + String.valueOf(minutesPerLabel%60);
			}
        	if(minutesAll != 0)
        		percent = (float)minutesPerLabel/(float)minutesAll;
        	else percent = 0;
        	if(numberPerLabel != 0)
        		satisfaction /= numberPerLabel;
        	else satisfaction = 0;
			
			labelAffair.put("duration", duration);
			labelAffair.put("percent", percent);
			labelAffair.put("satisfaction", satisfaction);
			if(percent != 0) {
				affairArray.add(labelAffair);
			}
			//回溯到七天前
        	timeOfDate.add(Calendar.DATE, -7);
        }
        back.put("TimeSharing", affairArray);
        
        JSONArray s_AffairArray = new JSONArray();
        for(int i = 0; i < labels.size(); i++)
        {
        	JSONObject labelS_Affair = new JSONObject();
        	labelS_Affair.put("labelid", labels.get(i).getId());
        	String duration = "00:00";
			int minutesPerLabel = 0;
			int numberPerLabel = 0;
			float percent = 0.0f;
			float satisfaction = 0.0f;
        	for(int j = 0; j < 7; j++)
            {
            	timeOfDate.add(Calendar.DATE, 1);
            	TimeSharing timeSharing = null;
            	for(int k = 0; k < timeSharings.size(); k++)
            	{
            		if(timeSharings.get(k).getDate().equals(timeOfDate.get(Calendar.YEAR) + "年" + timeOfDate.get(Calendar.MONTH) + "月" +timeOfDate.get(Calendar.DATE) + "日"))
            		{
            			timeSharing = timeSharings.get(k);
                		break;
            		}
            		
            	}
            	if(timeSharing == null)
            		continue;
            	ArrayList<S_Affair> s_Affairs = S_AffairManager.findWithIdTS(timeSharing.getId());
            	if(s_Affairs == null)
            		s_Affairs = new ArrayList<S_Affair>();
            	for(int k = 0; k < s_Affairs.size(); k++)
    			{
    				if(s_Affairs.get(k).getIdLabel() == labels.get(i).getId())
    				{
    					if(s_Affairs.get(k).getTimeEnd() == null || s_Affairs.get(k).getTimeStart() == null) continue;
    					int minutes = Integer.parseInt(s_Affairs.get(k).getTimeEnd().substring(3, 5)) - Integer.parseInt(s_Affairs.get(k).getTimeStart().substring(3, 5));
    					int hours = Integer.parseInt(s_Affairs.get(k).getTimeEnd().substring(0, 2)) - Integer.parseInt(s_Affairs.get(k).getTimeStart().substring(0, 2));
    					if(minutes < 0)
    					{
    						minutes += 60;
    						hours--;
    					}
    					minutesPerLabel += (minutes + hours*60);
    					satisfaction += s_Affairs.get(k).getSatisfaction();
    					numberPerLabel ++;
    				}
    			}
            }
        	duration = String.valueOf(minutesPerLabel/60) + ":" + String.valueOf(minutesPerLabel%60);
        	if(minutesPerLabel/60 < 10) {
				if(minutesPerLabel%60 < 10) 
					duration = "0" + String.valueOf(minutesPerLabel/60) + ":0" + String.valueOf(minutesPerLabel%60);
				else
					duration = "0" + String.valueOf(minutesPerLabel/60) + ":" + String.valueOf(minutesPerLabel%60);
			}
			else {
				if(minutesPerLabel%60 < 10) 
					duration = String.valueOf(minutesPerLabel/60) + ":0" + String.valueOf(minutesPerLabel%60);
				else
					duration = String.valueOf(minutesPerLabel/60) + ":" + String.valueOf(minutesPerLabel%60);
			}
        	if(minutesAll != 0)
        		percent = (float)minutesPerLabel/(float)minutesAll;
        	else percent = 0;
        	if(numberPerLabel != 0)
        		satisfaction /= numberPerLabel;
        	else satisfaction = 0;
			labelS_Affair.put("duration", duration);
			labelS_Affair.put("percent", percent);
			labelS_Affair.put("satisfaction", satisfaction);
			if(percent != 0) {
				s_AffairArray.add(labelS_Affair);
			}
		
			//回溯到七天前
        	timeOfDate.add(Calendar.DATE, -7);
        }
        back.put("Schedule", s_AffairArray);
        
        return back;
  	}
}