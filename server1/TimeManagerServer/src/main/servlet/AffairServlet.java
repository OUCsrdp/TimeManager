package main.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import main.model.moudle.*;
import main.model.services.AffairService;
import main.model.services.UserService;
import main.util.GetRequestUtil;

/**
 * Servlet implementation class AffairServlet
 */
@WebServlet("/AffairServlet")
public class AffairServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService=new UserService();
	private AffairService affairService=new AffairService();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AffairServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		JSONObject resJson = new JSONObject();
		JSONObject reqJson = new JSONObject();
		try {
			String jsonString=GetRequestUtil.getRequestJsonString(request);
			reqJson=JSON.parseObject(jsonString);
			//获得前端传来的json字符串并且转化成jsonObject
			String token=reqJson.getString("token");
			int sign1=reqJson.getIntValue("sign1");//1 means create,0 means modify,-1 means delete,-2means guessaffair
			int judgeToken=userService.judgeToken(token);
			if (judgeToken==1)//如果用户已经登录
			{
				int ifSuccess=0;
				if(sign1==-2)//guessAffair
				{
					String name=reqJson.getString("name");
					String date=reqJson.getString("date");
					String time=reqJson.getString("time");
					System.out.println("saffairsamename:"+time+" "+date+" "+name);
					System.out.println("saffairsamename:"+0);
					S_Affair saffair=affairService.guessAffair(time, date, name);//通过时间，日期，姓名来推测创建计时时可能的日程
					System.out.println("saffairsamename:"+1);
					if(saffair==null)
					{
						resJson.put("status", "success");
						resJson.put("hasSAffair",0);//表示没有相近的可坑日程
					}	
					else {
						resJson=(JSONObject) JSON.toJSON(saffair);
						resJson.put("status","success");
						resJson.put("hasSAffair",1);//表示有相近的可坑日程
					}
						
				}
				else if(sign1==-1)//deleteAffair
				{
					int sign2=reqJson.getIntValue("sign2");//1 means affair,0 means saffair
					ifSuccess=affairService.DeleteAffair(reqJson.getIntValue("id"),sign2);
					if(ifSuccess==1)
					{
						resJson.put("status","success");
					}else {
						resJson.put("status", "fail");
					}
				}
				else //create or modify affair
				{
					int sign2=reqJson.getIntValue("sign2");//1 means affair,0 means saffair
					String date=reqJson.getString("date");
					String username=reqJson.getString("username");
					if(sign2==1)
					{
						System.out.println("affairTobesaved:"+date+" "+username);
						ifSuccess=affairService.changeAffair(sign1, sign2,JSON.parseObject(jsonString,Affair.class),date,username);
						System.out.println("ifsuccess:"+ifSuccess);
					}else if(sign2==0)
					{
						ifSuccess=affairService.changeSAffair(sign1, sign2,JSON.parseObject(jsonString,S_Affair.class),date,username);
					}
					if(ifSuccess==1)
						resJson.put("status","success");
					else
						resJson.put("status","fail");
				}			
			}
			else {
				resJson.put("status","unlogin");
			}
		} catch (Exception e) {
			resJson.put("status","fail");
		}finally {
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().append(resJson.toString());
		}
		/*S_Affair affair = new S_Affair(1, 2, 3, 4, 5, false, "lisan", "no tips", "9:00", "9:01", "8:00","10:00","7:55","11:00");
		JSONObject affairJson =(JSONObject) JSON.toJSON(affair);
		affairJson.put("sign1", 1);
		affairJson.put("sign2", 0);
		String affairS=affairJson.toString();
		System.out.println(affairS);
		S_Affair affair2=JSON.parseObject(affairS,S_Affair.class);
		System.out.println(affair2.getId());
		System.out.println(affair2.getTimeStartAlarm());
		System.out.println(affair2.getTimeEndAlarm());	*/
	}

}
