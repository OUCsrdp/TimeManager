package main.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import main.model.services.ScheduleService;

public class ScheduleServlet extends BaseServlet{
	private static final long serialVersionUID = 1L;
	public JSONObject OperateS(HttpServletRequest request, HttpServletResponse response,String userId,String jsonString)throws ServletException, IOException{
		JSONObject resJson = new JSONObject();
		ScheduleService sService=new ScheduleService();
		//String userId=request.getParameter("userId");
		//token中截取userId
		String year=request.getParameter("year");
		String month=request.getParameter("month"); 
		String day=request.getParameter("day"); 
		String date=year+"年"+month+"月"+day+"日";
		resJson=sService.getScheduleTable(userId,date);
		resJson.put("status","success");
		return resJson;
	}
}
