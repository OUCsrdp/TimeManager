package main.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import main.model.services.AlarmService;
import main.util.GetRequestUtil;

public class AlarmServlet extends BaseServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JSONArray refreshS_AffairTimes(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		JSONObject status = new JSONObject();
		JSONObject reqJson = new JSONObject();
		JSONArray resJson = new JSONArray();
		String jsonString=GetRequestUtil.getRequestJsonString(request);
		reqJson=JSON.parseObject(jsonString);
		int idUser = reqJson.getIntValue("idUser");
		String dateString = reqJson.getString("date");
		int sign = reqJson.getIntValue("sign");
		//sign == 0标志着返回Start Alarm的时间
		if(sign == 0)
		{
			ArrayList<Long> times = AlarmService.getS_AffairAlarmTimeWithIdUser(idUser, dateString);
			if(times == null)
			{
				status.put("status", "fail");
				resJson.add(status);
			}
			for(int i = 0; i < times.size(); i++)
			{
				JSONObject time = new JSONObject();
				time.put("time", times.get(i));
				resJson.add(time);
			}
			status.put("status", "success");
			resJson.add(status);
		}
		//sign == 1标志着返回计划开始的时间
		else if(sign == 1)
		{
			ArrayList<Long> times = AlarmService.getS_AffairPlanTimeWithIdUser(idUser, dateString);
			if(times == null)
			{
				status.put("status", "fail");
				resJson.add(status);
			}
			for(int i = 0; i < times.size(); i++)
			{
				JSONObject time = new JSONObject();
				time.put("time", times.get(i));
				resJson.add(time);
			}
			status.put("status", "success");
			resJson.add(status);
		}
		return resJson;
	}
	
}
