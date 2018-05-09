package main.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSONObject;

import main.model.services.TimeSharingService;
import main.model.services.UserService;

/**
 * Servlet implementation class TimeSharingServlet
 */
@WebServlet("/TimeSharingServlet")
public class TimeSharingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TimeSharingService timeSharingService=new TimeSharingService();
	private UserService userService=new UserService();
	public void init() throws ServletException {
		
	}
	public void destroy() {
	}
    public TimeSharingServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    //��ȡʱ������
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		JSONObject resJson = new JSONObject();
		//System.out.println("get");
		try {
			String queryString=request.getQueryString();
			//token,userId,date
			String gotString[]=new String[3];
			gotString=queryString.split("&");
			String token=gotString[0].split("=")[1];
			String userId=gotString[1].split("=")[1];
			String date=gotString[2].split("=")[1];
			int isSharingTable=1;
			int judgeToken=userService.judgeToken(token);
			if (judgeToken==1)
			{
				resJson=timeSharingService.getSharingTable(isSharingTable,userId,date);
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