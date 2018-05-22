package main.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import main.model.services.ScheduleService;
import main.model.services.UserService;

/**
 * Servlet implementation class ScheduleServlet
 */
@WebServlet("/ScheduleServlet")
public class ScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ScheduleService scheduleService=new ScheduleService();
	private UserService userService=new UserService();   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ScheduleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		JSONObject resJson = new JSONObject();
		try {
			String queryString=request.getQueryString();
			//token,userId,date
			String gotString[]=new String[5];
			gotString=queryString.split("&");
			String token=gotString[0].split("=")[1];
			String userId=gotString[1].split("=")[1];
			String year=gotString[2].split("=")[1];
			String month=gotString[3].split("=")[1]; 
			String day=gotString[4].split("=")[1]; 
			String date=year+"��"+month+"��"+day+"��";
			int isSharingTable=0;
			int judgeToken=userService.judgeToken(token);
			if (judgeToken==1)
			{
				resJson=scheduleService.getScheduleTable(userId,date);
				resJson.put("status","success");
			}
			else {
				resJson.put("status","unlogin");
			}	
		}catch(Exception e){
			resJson.put("status", "fail");
		}
		finally {
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().append(resJson.toString());
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
