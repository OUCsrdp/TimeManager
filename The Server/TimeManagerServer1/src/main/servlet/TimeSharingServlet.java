package main.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import main.model.services.TimeSharingService;

public class TimeSharingServlet extends BaseServlet{
	private static final long serialVersionUID = 1L;
	public JSONObject OperateTS(HttpServletRequest request, HttpServletResponse response,String userId)throws ServletException, IOException{
		TimeSharingService tsService=new TimeSharingService();
		JSONObject resJson = new JSONObject();
		//token,userId,date
		//String userId=request.getParameter("userId");
		//token中截取userId
		String year=request.getParameter("year");
		String month=request.getParameter("month"); 
		String day=request.getParameter("day") ;
		String date=year+"年"+month+"月"+day+"日";
		System.out.println("userId:"+userId);
		System.out.println("date:"+date);
		resJson=tsService.getSharingTable(userId,date);
		if(resJson==null)
		{
			resJson=new JSONObject();
			resJson.put("id", -1);
		}
		resJson.put("status","success");
		return resJson;
	}
}
