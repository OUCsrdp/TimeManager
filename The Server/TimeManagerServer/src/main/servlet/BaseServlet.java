package main.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import main.model.services.UserService;
import main.util.GetRequestUtil;
import main.util.TokenUtil;

public abstract class BaseServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        // 获得执行的方法名
        //String methodName = request.getParameter("method");
        String queryString=request.getQueryString();
        String methodName=queryString.split("&")[0].split("=")[1];        
        // 没有默认方法
        System.out.println("BaseServlet : 本次所执行方法 :  " + methodName);
        JSONObject reqJson=new JSONObject();
        JSONObject resJson=new JSONObject();
        try {
        	boolean identityPass=false;//使用token验证身份
        	String requestToken="";
        	String id;//用户id
        	String jsonString="";
        	if(!methodName.equals("OperateS")&&!methodName.equals("OperateTS"))
        	{
        		jsonString=GetRequestUtil.getRequestJsonString(request);
    			reqJson=JSON.parseObject(jsonString);
        	}
        	/*//如果不是请求验证码、计划表、时间分配表，将请求体转化成jsonobject形式
        	if(!methodName.equals("GetVerify")&!methodName.equals("OperateS")&!methodName.equals("OperateTS"))
        	{
        		String jsonString=GetRequestUtil.getRequestJsonString(request);
    			reqJson=JSON.parseObject(jsonString);
    			System.out.println("reqJson:"+reqJson);
        	}*/
			//注册登录和获取验证码操作前段无需传token，默认验证通过
			String operation=reqJson.getString("operation");
			if(operation==null)
				operation="";
			if(methodName.equals("GetVerify")||operation.equals("register")
					||operation.equals("login")
			){
				//默认身份验证通过，Id为空
				identityPass=true;
				id="";
				System.out.println("location0");
			}else {
				if(methodName.equals("OperateS")||methodName.equals("OperateTS")){
	        		//requestToken=request.getParameter("token");
					requestToken=queryString.split("&")[1].split("=")[1];
	        	}else {
	    			requestToken=reqJson.getString("token");
	        	}
				identityPass=judgeToken(request,requestToken);
				//进行一些处理获得token中的id
				id=TokenUtil.decodeToken(requestToken);
			}
        	
            //如果身份验证通过，使用反射执行子类中的方法
        	if(identityPass)
        	{
        		// 通过反射获得当前运行类中指定方法,形式参数
        		Method executeMethod = this.getClass().getMethod(methodName,
                        HttpServletRequest.class, HttpServletResponse.class,String.class,String.class);
                // 反射执行方法
                resJson = (JSONObject) executeMethod.invoke(this, request,
                        response,id,jsonString);
                //将json数据返回
        	}else
        	{
        		resJson.put("status", "unlogin");
        	}
            
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("未知方法  : " + methodName);
        } catch (Exception e) {
        	//如果服务器错误返回失败状态
            resJson.put("status", "fail");
        }finally {
        	//在响应中放入序列化的的json对象
        	response.setContentType("application/json;charset=UTF-8");
        	response.getWriter().append(resJson.toString());
        	System.out.println(resJson.toString());
        	//判断如果不是获取验证码图片，在响应中输出字符串
        	/*if(!methodName.equals("GetVerify"))
        	{
        		response.getWriter().append(resJson.toString());
        		System.out.println(resJson.toString());
        	}
        	else { //判断如果是获取验证码图片，在响应中输出比特流
        		System.out.println("baseServlet:"+resJson.getBytes("verifyImg"));
        		byte[] veriImg=resJson.getBytes("verifyImg");
        		//System.out.println("response1:"+response.toString());
        		response.getOutputStream().write(veriImg);
        		//System.out.println("response2:"+response.toString());
        		//System.out.println("outstream:"+response.getOutputStream().toString());
        	}*/
        }
	}
	//判断token认证是否成功
	public boolean judgeToken(HttpServletRequest request,String requestToken)
	{
		HttpSession session=request.getSession();
		UserService userService=new UserService();
		if(session.isNew())//如果session新创建，就添加token
		{
			//如果数据库里有该token,就设置token到session中
			if(userService.judgeToken(requestToken)==1)
			{
				session.setAttribute("token",requestToken);
				return true;
			}
		}else
		{
			//如果session已经存在且session中token与客户端传入的token相等，tokenpass设置为true
			System.out.println("Session exists!");
			if(session.getAttribute("token").equals(requestToken))
			{
				return true;
			}
		}
		return false;
	}
}
