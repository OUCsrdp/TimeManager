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
        String methodName = request.getParameter("method");
        // û��Ĭ�Ϸ���
        System.out.println("BaseServlet : ������ִ�з��� :  " + methodName);
        JSONObject reqJson=new JSONObject();
        JSONObject resJson=new JSONObject();
        try {
        	boolean identityPass=false;//ʹ��token��֤���
        	String requestToken="";
        	String id;//�û�id
        	//��������ת����jsonobject��ʽ
        	String jsonString=GetRequestUtil.getRequestJsonString(request);
			reqJson=JSON.parseObject(jsonString);
			//ע���¼�ͻ�ȡ��֤�����ǰ�����贫token
			if(reqJson.getString("operation").equals("register")||reqJson.getString("operation").equals("login")||
					methodName.equals("getVerify")){
				//Ĭ�������֤ͨ����IdΪ��
				identityPass=true;
				id="";
			}else {
				if(methodName.equals("OperateS")||methodName.equals("OperateTS")){
	        		requestToken=request.getParameter("token");
	        	}else {
	    			requestToken=reqJson.getString("token");
	        	}
				identityPass=judgeToken(request,requestToken);
				//����һЩ������token�е�id
				id=requestToken.substring(64);
			}
        	
            //��������֤ͨ����ʹ�÷���ִ�������еķ���
        	if(identityPass)
        	{
        		// ͨ�������õ�ǰ��������ָ������,��ʽ����
        		Method executeMethod = this.getClass().getMethod(methodName,
                        HttpServletRequest.class, HttpServletResponse.class,String.class);
                // ����ִ�з���
                resJson = (JSONObject) executeMethod.invoke(this, request,
                        response,id);
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
        	response.setContentType("application/json;charset=UTF-8");
        	//�ж�������ǻ�ȡ��֤��ͼƬ������Ӧ������ַ���
        	if(!reqJson.getString("operation").equals("getVerify"))
        	{
        		response.getWriter().append(resJson.toString());
        		System.out.println(resJson.toString());
        	}
        	else { //�ж�������ǻ�ȡ��֤��ͼƬ������Ӧ�����������
        		response.getOutputStream().write(resJson.getBytes("verifyImg"));
        	}
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
			if(session.getAttribute("token").equals(requestToken))
			{
				return true;
			}
		}
		return false;
	}
}
