package main.model.services;

import com.alibaba.fastjson.JSONObject;

public class TimeSharingService {
	public JSONObject getSharingTable(int isSharingTable, String userId,String date)
	{
		JSONObject test=new JSONObject();
		test.put("date",date);
		return test;
	}
}
