package main.servlet;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		//使用get请求来获取验证码图片
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
			String jsonString=GetRequestUtil.getRequestJsonString(request);
			reqJson=JSON.parseObject(jsonString);
			if(reqJson.getString("operation").equals("register"))//用户注册
			{
				String status=userService.register(JSON.parseObject(jsonString,User.class),reqJson.getString("verify"));
				resJson.put("status",status);
			}else if(reqJson.getString("operation").equals("login"))//登录,返回token
			{
				String token=userService.login(reqJson.getString("name"),reqJson.getString("pwd"),reqJson.getString("verify"),reqJson.getString("token"));
				//返回服务器端修改设备号后产生的token,token存入数据库
				if(token.equals("usernamefail")||token.equals("passwordfail")||token.equals("verifyfail"))
					resJson.put("status", token);
				else {
					HttpSession session = request.getSession();
					session.setAttribute("token",token);
					//如果登录成功把token设置入token
					User user=userService.getUserInfor(reqJson.getString("name"));
					resJson =(JSONObject) JSON.toJSON(user);
					//把user的基本信息传给客户端
					resJson.put("status","success");
					resJson.put("token", token);//将服务器端修改设备号后产生的token传回客户端
				}
			}
			else {
				boolean tokenpass=false;//token是否正确
				HttpSession session=request.getSession();
				if(session.isNew())//如果session新创建，就添加token
				{
					//如果数据库里有该token,就设置token到session中
					if(userService.judgeToken(reqJson.getString("token"))==1)
					{
						session.setAttribute("token",reqJson.getString("token"));
						tokenpass=true;
					}
				}else
				{
					//如果session已经存在且session中token与客户端传入的token相等，tokenpass设置为true
					if(session.getAttribute("token").equals(reqJson.getString("token")))
					{
						tokenpass=true;
					}
				}
				if(tokenpass)
				{
					if(reqJson.getString("operation").equals("infor"))//修改用户信息
					{
						int ifSuccess=userService.changeInfor(JSON.parseObject(jsonString,User.class));
						if(ifSuccess==1)
							resJson.put("status","success");
						else
							resJson.put("status", "fail");
					}
					else if(reqJson.getString("operation").equals("getUser"))//获取用户的基本信息
					{
						User user=null;
						user=userService.getUserInfor(reqJson.getString("name"));
						if(user!=null)
						{
							resJson=(JSONObject) JSON.toJSON(user);
							resJson.put("status","success");
						}
						else
							resJson.put("status", "fail");
					}else if(reqJson.getString("operation").equals("logout"))//退出登录
					{
						int ifSuccess=userService.logout(reqJson.getIntValue("id"),reqJson.getString("token"));
						if(ifSuccess==1)
						{
							resJson.put("status","success");
						}
						else
						resJson.put("status", "fail");
					}
				}
				else//如果tokenpass==false
				{
					resJson.put("status", "unlogin");
				}
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
	/*private void setSession(String username,String token,HttpSession session)
	{
		session.setAttribute("token",token);//将token设置入session中
		User user=userService.getUserInfor(username);
		session.setAttribute("user",user);
	}*/

}
