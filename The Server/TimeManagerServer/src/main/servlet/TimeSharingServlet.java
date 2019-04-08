package main.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import main.model.services.TimeSharingService;

public class TimeSharingServlet extends BaseServlet{
	private static final long serialVersionUID = 1L;
	public JSONObject OperateTS(HttpServletRequest request, HttpServletResponse response,String userId,String jsonString)throws ServletException, IOException{
		TimeSharingService tsService=new TimeSharingService();
		JSONObject resJson = new JSONObject();
		//token,userId,date
		//String userId=request.getParameter("userId");
		//token中截取userId
		System.out.println("timesharingIn:");
		String year=request.getParameter("year");
		String month=request.getParameter("month"); 
		String day=request.getParameter("day") ;
		String date=year+"年"+month+"月"+day+"日";
		resJson=tsService.getSharingTable(userId,date);
		System.out.println("resJson:"+resJson);
		if(resJson==null)
		{
			resJson=new JSONObject();
			resJson.put("id", -1);
		}
		resJson.put("status","success");
		return resJson;
	}
	public JSONObject GetTSById(HttpServletRequest request, HttpServletResponse response,String userId,String jsonString)throws ServletException, IOException{
		TimeSharingService tsService=new TimeSharingService();
		JSONObject reqJson = new JSONObject();
		JSONObject resJson = new JSONObject();
		reqJson=JSON.parseObject(jsonString);
		int idTS=reqJson.getIntValue("idTS");//获取要请求的时间分配表ID
		resJson=tsService.getSTDetails(idTS);
		if(resJson==null)//如果请求不到（特指idTS有误的情况）
		{
			resJson=new JSONObject();
			resJson.put("id", -1);
		}
		resJson.put("status","success");
		return resJson;
	}
}
