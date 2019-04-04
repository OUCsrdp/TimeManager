package main.servlet;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import main.model.services.AnalysisService;
import main.util.GetRequestUtil;

public class AnalysisServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public JSONObject GetDays(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException
	{
		JSONObject reqJson = new JSONObject();
		JSONObject resJson = new JSONObject();
		String jsonString=GetRequestUtil.getRequestJsonString(request);
		reqJson=JSON.parseObject(jsonString);
		String token = reqJson.getString("token");
		int userId = Integer.parseInt((token.split("_"))[0]);
		int days = AnalysisService.getDays(userId);
		resJson.put("days", days);
		return resJson;
	}
	
	public JSONObject GetDelayedTime(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		JSONObject reqJson = new JSONObject();
		JSONObject resJson = new JSONObject();
		String jsonString=GetRequestUtil.getRequestJsonString(request);
		reqJson=JSON.parseObject(jsonString);
		String token = reqJson.getString("token");
		String weekday = reqJson.getString("weekday");
		boolean isWeekday = false;
		if(weekday.equals("true"))
			isWeekday = true;
		int userId = Integer.parseInt((token.split("_"))[0]);
		String delayTime = AnalysisService.getDelayedTime(userId, isWeekday);
		resJson.put("delayedtime", delayTime);
		return resJson;
	}
	
	
	public JSONObject GetUnfinishedPercent(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		JSONObject reqJson = new JSONObject();
		JSONObject resJson = new JSONObject();
		String jsonString=GetRequestUtil.getRequestJsonString(request);
		reqJson=JSON.parseObject(jsonString);
		String token = reqJson.getString("token");
		String weekday = reqJson.getString("weekday");
		boolean isWeekday = false;
		if(weekday.equals("true"))
			isWeekday = true;
		int userId = Integer.parseInt((token.split("_"))[0]);
		int percent = AnalysisService.getUnfinishedPercent(userId, isWeekday);
		resJson.put("unfininshedPercent", percent + "%");
		return resJson;
	}
	
	public JSONObject GetChart(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		JSONObject reqJson = new JSONObject();
		JSONObject resJson = new JSONObject();
		String jsonString=GetRequestUtil.getRequestJsonString(request);
		reqJson=JSON.parseObject(jsonString);
		String token = reqJson.getString("token");
		String weekday = reqJson.getString("weekday");
		boolean isWeekday = false;
		if(weekday.equals("true"))
			isWeekday = true;
		String type = reqJson.getString("type");
		int userId = Integer.parseInt((token.split("_"))[0]);
		resJson = AnalysisService.getChart(userId, isWeekday, type);
		return resJson;
	}
}
