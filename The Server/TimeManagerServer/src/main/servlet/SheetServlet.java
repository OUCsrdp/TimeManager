package main.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.model.services.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class SheetServlet extends BaseServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public JSONObject GetDailySheet (HttpServletRequest request, HttpServletResponse response,String userId,String jsonString)throws ServletException, IOException
	{
		JSONObject resJson = new JSONObject();
		JSONObject reqJson = new JSONObject();
		
		reqJson = JSON.parseObject(jsonString);
		boolean success = false;
		
		int idUser = Integer.parseInt(userId);
		String date = reqJson.getString("date");
		
		SheetService sheetService = new SheetService();
		resJson = sheetService.getDailySheet(idUser, date);
		if(resJson != null)
			success = true;
		if(success)
			resJson.put("status", "success");
		else
			resJson.put("status", "fail");
		return resJson;
	}
	
	public JSONObject GetWeeklySheet (HttpServletRequest request, HttpServletResponse response,String userId,String jsonString)throws ServletException, IOException
	{
		JSONObject resJson = new JSONObject();
		JSONObject reqJson = new JSONObject();
		
		reqJson = JSON.parseObject(jsonString);
		boolean success = false;
		
		int idUser = Integer.parseInt(userId);
		String date = reqJson.getString("date");
		
		SheetService sheetService = new SheetService();
		resJson = sheetService.getWeeklySheet(idUser, date);
		if(resJson != null)
			success = true;
		if(success)
			resJson.put("status", "success");
		else
			resJson.put("status", "fail");
		return resJson;
	}
	
	public JSONObject GetWeeklyChange (HttpServletRequest request, HttpServletResponse response,String userId,String jsonString)throws ServletException, IOException
	{
		JSONObject resJson = new JSONObject();
		JSONObject reqJson = new JSONObject();
		
		reqJson = JSON.parseObject(jsonString);
		boolean success = false;
		
		int idUser = Integer.parseInt(userId);
		String date = reqJson.getString("date");
		int labelId = reqJson.getIntValue("labelId");
		
		SheetService sheetService = new SheetService();
		resJson = sheetService.getWeeklyChange(idUser, date, labelId);
		if(resJson != null)
			success = true;
		if(success)
			resJson.put("status", "success");
		else
			resJson.put("status", "fail");
		return resJson;
	}
	
	
}
