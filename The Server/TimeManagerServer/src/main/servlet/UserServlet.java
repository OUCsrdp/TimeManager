package main.servlet;

import java.io.IOException;
import java.util.Base64;

import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import main.model.db.UserManager;
import main.model.moudle.User;
import main.model.services.UserService;
import main.util.GetRequestUtil;

public class UserServlet extends BaseServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/*	public JSONObject test(HttpServletRequest request, HttpServletResponse response,String id,JSONObject reJson)throws ServletException, IOException {
		JSONObject resJson = new JSONObject();
		System.out.println("id"+id);
		resJson.put("string", id);
		return resJson;
	}*/
	public JSONObject GetVerify(HttpServletRequest request, HttpServletResponse response,String userId,String jsonString)throws ServletException, IOException {
		JSONObject resJson = new JSONObject();
		byte[] img;
		UserService userService=new UserService();
		img=userService.getVerify();
		String str=Base64.getEncoder().encodeToString(img);
		resJson.put("verifyImg",img);
		return resJson;
	}
	public JSONObject OperateUser(HttpServletRequest request, HttpServletResponse response,String userId,String jsonString)throws ServletException, IOException {
		System.out.println("inuserservlet");
		UserService userService=new UserService();
		JSONObject resJson = new JSONObject();
		JSONObject reqJson = new JSONObject();/*
		String jsonString=GetRequestUtil.getRequestJsonString(request);
		System.out.println("userservletjsonstring:"+jsonString);*/
		/*resJson.put("userJsonstring",jsonString);
		return resJson;*/
		reqJson=JSON.parseObject(jsonString);
		if(reqJson.getString("operation").equals("register"))//用户注册
		{
			String status=userService.register(JSON.parseObject(jsonString,User.class),reqJson.getString("verify"));
			resJson.put("status",status);
		}else if(reqJson.getString("operation").equals("login"))//登录,返回token
		{
			String token="";
			try {
				token = userService.login(reqJson.getString("name"),reqJson.getString("pwd"),reqJson.getString("verify"),reqJson.getString("token"));
				System.out.println("token:"+token);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		}else {
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
					user=userService.getUserInfor(Integer.parseInt(userId));
					if(user!=null)
					{
						resJson=(JSONObject) JSON.toJSON(user);
						resJson.put("status","success");
					}
					else
						resJson.put("status", "fail");
				}else if(reqJson.getString("operation").equals("logout"))//退出登录
				{
					int ifSuccess=userService.logout(Integer.parseInt(userId),reqJson.getString("token"));
					if(ifSuccess==1)
					{
						resJson.put("status","success");
					}
					else
					resJson.put("status", "fail");
				}
		}
		return resJson;
	}
}
