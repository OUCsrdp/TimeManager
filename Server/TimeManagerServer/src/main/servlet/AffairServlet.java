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
			int sign1=reqJson.getIntValue("sign1");//1 means create,0 means modify
			int sign2=reqJson.getIntValue("sign2");//1 means affair,0 means saffair
			int judgeToken=userService.judgeToken(token);
			if (judgeToken==1)
			{
				String status="fail";
				if(sign1==-1) {
					int ifSuccess=affairService.DeleteAffair(reqJson.getIntValue("id"),sign2);
					if(ifSuccess==1)
					{
						resJson.put("status","success");
					}else {
						resJson.put("status", "fail");
					}
				}
				else {
					if(sign2==1)
					{
						status=affairService.changeAffair(sign1, sign2,JSON.parseObject(jsonString,Affair.class));
					}else if(sign2==0)
					{
						status=affairService.changeSAffair(sign1, sign2,JSON.parseObject(jsonString,S_Affair.class));
					}
						resJson.put("status",status);	
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
