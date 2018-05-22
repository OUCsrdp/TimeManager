package main.servlet;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import main.model.moudle.User;
import main.model.services.UserService;
import main.util.GetRequestUtil;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    UserService userService=new UserService();   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//doPost(request, response);
		byte[] img = null;
		try {
			img=userService.getVerify();
			System.out.println("img:"+img.toString());
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally {
			response.setContentType("application/json;charset=UTF-8");
			 response.getOutputStream().write(img);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		JSONObject resJson = new JSONObject();
		JSONObject reqJson = new JSONObject();
		//System.out.println("get");
		try {
			System.out.println("enter");
			String jsonString=GetRequestUtil.getRequestJsonString(request);
			reqJson=JSON.parseObject(jsonString);
			if(reqJson.getString("operation").equals("register"))
			{
				String status=userService.register(JSON.parseObject(jsonString,User.class),reqJson.getString("verify"));
				resJson.put("status",status);
			}else if(reqJson.getString("operation").equals("login"))//登录,返回token
			{
				String token=userService.login(reqJson.getString("name"),reqJson.getString("pwd"),reqJson.getString("verify"),reqJson.getString("token"));
				if(token.equals("usernamefail")||token.equals("passwordfail")||token.equals("verifyfail"))
					resJson.put("status", token);
				else {
					resJson.put("status","success");
					resJson.put("token", token);
				}
			}else if(reqJson.getString("operation").equals("infor"))//修改用户信息
			{
				if(userService.judgeToken(reqJson.getString("token"))==1) {
					int ifSuccess=userService.changeInfor(JSON.parseObject(jsonString,User.class));
					if(ifSuccess==1)
						resJson.put("status","success");
					else
						resJson.put("status", "fail");
				}
				else
					resJson.put("status","unlogin");
			}else if(reqJson.getString("operation").equals("getUser"))//获取用户的基本信息
			{
				User user=null;
				if(userService.judgeToken(reqJson.getString("token"))==1) {
					user=userService.getUserInfor(reqJson.getString("name"));
					if(user!=null)
					{
						resJson=(JSONObject) JSON.toJSON(user);
						resJson.put("status","success");
					}
					else
						resJson.put("status", "fail");
				}
				else
					resJson.put("status","unlogin");
			}else if(reqJson.getString("operation").equals("logout"))//退出登录
			{
				if(userService.judgeToken(reqJson.getString("token"))==1) {
					int ifSuccess=userService.logout(reqJson.getIntValue("id"),reqJson.getString("token"));
					if(ifSuccess==1)
					{
						resJson.put("status","success");
					}
					else
						resJson.put("status", "fail");
				}
				else
					resJson.put("status","unlogin");
			}
			//修改密码
		}catch(Exception e){
			resJson.put("status", "fail");
		}
		finally {
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().append(resJson.toString());
		}
	}

}
