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
	
	public JSONObject GetDays(HttpServletRequest request, HttpServletResponse response,String userId,String jsonString) throws IOException, ParseException
	{
		JSONObject reqJson = new JSONObject();
		JSONObject resJson = new JSONObject();
		reqJson=JSON.parseObject(jsonString);
		int days=AnalysisService.getDays(Integer.parseInt(userId));
		resJson.put("days", days);
		return resJson;
	}
	
	public JSONObject GetDelayedTime(HttpServletRequest request, HttpServletResponse response,String userId,String jsonString) throws IOException
	{
		JSONObject reqJson = new JSONObject();
		JSONObject resJson = new JSONObject();
		reqJson=JSON.parseObject(jsonString);
		String token = reqJson.getString("token");
		String weekday = reqJson.getString("weekday");
		boolean isWeekday = false;
		if(weekday.equals("True"))
			isWeekday = true;
		String delayTime = AnalysisService.getDelayedTime(Integer.parseInt(userId), isWeekday);
		resJson.put("delayedtime", delayTime);
		return resJson;
	}
	
	
	public JSONObject GetUnfinishedPercent(HttpServletRequest request, HttpServletResponse response,String userId,String jsonString) throws IOException
	{
		JSONObject reqJson = new JSONObject();
		JSONObject resJson = new JSONObject();
		reqJson=JSON.parseObject(jsonString);
		String token = reqJson.getString("token");
		String weekday = reqJson.getString("weekday");
		boolean isWeekday = false;
		if(weekday.equals("True"))
			isWeekday = true;
		int percent = AnalysisService.getUnfinishedPercent(Integer.parseInt(userId), isWeekday);
		resJson.put("unfininshedPercent", percent + "%");
		return resJson;
	}
	
	public JSONObject GetChart(HttpServletRequest request, HttpServletResponse response,String userId,String jsonString) throws IOException
	{
		JSONObject reqJson = new JSONObject();
		JSONObject resJson = new JSONObject();
		reqJson=JSON.parseObject(jsonString);
		String token = reqJson.getString("token");
		String weekday = reqJson.getString("weekday");
		boolean isWeekday = false;
		if(weekday.equals("true"))
			isWeekday = true;
		String type = reqJson.getString("type");
		resJson = AnalysisService.getChart(Integer.parseInt(userId), isWeekday, type);
		return resJson;
	}
}
