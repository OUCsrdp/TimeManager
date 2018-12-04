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
        // ���ִ�еķ�����
        //String methodName = request.getParameter("method");
        String queryString=request.getQueryString();
        String methodName=queryString.split("&")[0].split("=")[1];        
        // û��Ĭ�Ϸ���
        System.out.println("BaseServlet : ������ִ�з��� :  " + methodName);
        JSONObject reqJson=new JSONObject();
        JSONObject resJson=new JSONObject();
        try {
        	boolean identityPass=false;//ʹ��token��֤���
        	String requestToken="";
        	String id;//�û�id
        	String jsonString="";
        	if(!methodName.equals("OperateS")&&!methodName.equals("OperateTS"))
        	{
        		jsonString=GetRequestUtil.getRequestJsonString(request);
    			reqJson=JSON.parseObject(jsonString);
        	}
        	/*//�������������֤�롢�ƻ���ʱ��������������ת����jsonobject��ʽ
        	if(!methodName.equals("GetVerify")&!methodName.equals("OperateS")&!methodName.equals("OperateTS"))
        	{
        		String jsonString=GetRequestUtil.getRequestJsonString(request);
    			reqJson=JSON.parseObject(jsonString);
    			System.out.println("reqJson:"+reqJson);
        	}*/
			//ע���¼�ͻ�ȡ��֤�����ǰ�����贫token��Ĭ����֤ͨ��
			String operation=reqJson.getString("operation");
			if(operation==null)
				operation="";
			if(methodName.equals("GetVerify")||operation.equals("register")
					||operation.equals("login")
			){
				//Ĭ�������֤ͨ����IdΪ��
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
				//����һЩ������token�е�id
				id=TokenUtil.decodeToken(requestToken);
			}
        	
            //��������֤ͨ����ʹ�÷���ִ�������еķ���
        	if(identityPass)
        	{
        		// ͨ�������õ�ǰ��������ָ������,��ʽ����
        		Method executeMethod = this.getClass().getMethod(methodName,
                        HttpServletRequest.class, HttpServletResponse.class,String.class,String.class);
                // ����ִ�з���
                resJson = (JSONObject) executeMethod.invoke(this, request,
                        response,id,jsonString);
                //��json���ݷ���
        	}else
        	{
        		resJson.put("status", "unlogin");
        	}
            
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("δ֪����  : " + methodName);
        } catch (Exception e) {
        	//������������󷵻�ʧ��״̬
            resJson.put("status", "fail");
        }finally {
        	//����Ӧ�з������л��ĵ�json����
        	response.setContentType("application/json;charset=UTF-8");
        	response.getWriter().append(resJson.toString());
        	System.out.println(resJson.toString());
        	//�ж�������ǻ�ȡ��֤��ͼƬ������Ӧ������ַ���
        	/*if(!methodName.equals("GetVerify"))
        	{
        		response.getWriter().append(resJson.toString());
        		System.out.println(resJson.toString());
        	}
        	else { //�ж�����ǻ�ȡ��֤��ͼƬ������Ӧ�����������
        		System.out.println("baseServlet:"+resJson.getBytes("verifyImg"));
        		byte[] veriImg=resJson.getBytes("verifyImg");
        		//System.out.println("response1:"+response.toString());
        		response.getOutputStream().write(veriImg);
        		//System.out.println("response2:"+response.toString());
        		//System.out.println("outstream:"+response.getOutputStream().toString());
        	}*/
        }
	}
	//�ж�token��֤�Ƿ�ɹ�
	public boolean judgeToken(HttpServletRequest request,String requestToken)
	{
		HttpSession session=request.getSession();
		UserService userService=new UserService();
		if(session.isNew())//���session�´����������token
		{
			//������ݿ����и�token,������token��session��
			if(userService.judgeToken(requestToken)==1)
			{
				session.setAttribute("token",requestToken);
				return true;
			}
		}else
		{
			//���session�Ѿ�������session��token��ͻ��˴����token��ȣ�tokenpass����Ϊtrue
			System.out.println("Session exists!");
			if(session.getAttribute("token").equals(requestToken))
			{
				return true;
			}
		}
		return false;
	}
}
