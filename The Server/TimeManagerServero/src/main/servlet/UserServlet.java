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
		//ʹ��get��������ȡ��֤��ͼƬ
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
			if(reqJson.getString("operation").equals("register"))//�û�ע��
			{
				String status=userService.register(JSON.parseObject(jsonString,User.class),reqJson.getString("verify"));
				resJson.put("status",status);
			}else if(reqJson.getString("operation").equals("login"))//��¼,����token
			{
				String token=userService.login(reqJson.getString("name"),reqJson.getString("pwd"),reqJson.getString("verify"),reqJson.getString("token"));
				//���ط��������޸��豸�ź������token,token�������ݿ�
				if(token.equals("usernamefail")||token.equals("passwordfail")||token.equals("verifyfail"))
					resJson.put("status", token);
				else {
					HttpSession session = request.getSession();
					session.setAttribute("token",token);
					//�����¼�ɹ���token������token
					User user=userService.getUserInfor(reqJson.getString("name"));
					resJson =(JSONObject) JSON.toJSON(user);
					//��user�Ļ�����Ϣ�����ͻ���
					resJson.put("status","success");
					resJson.put("token", token);//�����������޸��豸�ź������token���ؿͻ���
				}
			}
			else {
				boolean tokenpass=false;//token�Ƿ���ȷ
				HttpSession session=request.getSession();
				if(session.isNew())//���session�´����������token
				{
					//������ݿ����и�token,������token��session��
					if(userService.judgeToken(reqJson.getString("token"))==1)
					{
						session.setAttribute("token",reqJson.getString("token"));
						tokenpass=true;
					}
				}else
				{
					//���session�Ѿ�������session��token��ͻ��˴����token��ȣ�tokenpass����Ϊtrue
					if(session.getAttribute("token").equals(reqJson.getString("token")))
					{
						tokenpass=true;
					}
				}
				if(tokenpass)
				{
					if(reqJson.getString("operation").equals("infor"))//�޸��û���Ϣ
					{
						int ifSuccess=userService.changeInfor(JSON.parseObject(jsonString,User.class));
						if(ifSuccess==1)
							resJson.put("status","success");
						else
							resJson.put("status", "fail");
					}
					else if(reqJson.getString("operation").equals("getUser"))//��ȡ�û��Ļ�����Ϣ
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
					}else if(reqJson.getString("operation").equals("logout"))//�˳���¼
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
				else//���tokenpass==false
				{
					resJson.put("status", "unlogin");
				}
			}
			//�޸�����
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
		session.setAttribute("token",token);//��token������session��
		User user=userService.getUserInfor(username);
		session.setAttribute("user",user);
	}*/

}
