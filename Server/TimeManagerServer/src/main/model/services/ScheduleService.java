package main.model.services;

import com.alibaba.fastjson.JSONObject;

public class ScheduleService {
	public JSONObject getScheduleTable(int isSharingTable, String userId,String date)
	{
		JSONObject test=new JSONObject();
		test.put("date","2018��4��25��");
		return test;
	}
}
