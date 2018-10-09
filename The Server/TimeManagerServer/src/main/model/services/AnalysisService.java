package main.model.services;

import main.model.moudle.*;

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
		int y,m,d,h,mi,s;    
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