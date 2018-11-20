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
	public JSONObject test(HttpServletRequest request, HttpServletResponse response,String id)throws ServletException, IOException {
		JSONObject resJson = new JSONObject();
		System.out.println("id"+id);
		resJson.put("string", id);
		return resJson;
	}
	public JSONObject GetVerify(HttpServletRequest request, HttpServletResponse response,String userId)throws ServletException, IOException {
		JSONObject resJson = new JSONObject();
		byte[] img;
		UserService userService=new UserService();
		img=userService.getVerify();
		String str=Base64.getEncoder().encodeToString(img);
		System.out.println("str:"+str);
		resJson.put("verifyImg",img);
		return resJson;
	}
	public JSONObject OperateUser(HttpServletRequest request, HttpServletResponse response,String userId)throws ServletException, IOException {
		UserService userService=new UserService();
		JSONObject resJson = new JSONObject();
		JSONObject reqJson = new JSONObject();
		String jsonString=GetRequestUtil.getRequestJsonString(request);
		reqJson=JSON.parseObject(jsonString);
		if(reqJson.getString("operation").equals("register"))//�û�ע��
		{
			String status=userService.register(JSON.parseObject(jsonString,User.class),reqJson.getString("verify"));
			resJson.put("status",status);
		}else if(reqJson.getString("operation").equals("login"))//��¼,����token
		{
			String token="";
			try {
				token = userService.login(reqJson.getString("name"),reqJson.getString("pwd"),reqJson.getString("verify"),reqJson.getString("token"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//���ط��������޸��豸�ź������token,token�������ݿ�
			if(token.equals("usernamefail")||token.equals("passwordfail")||token.equals("verifyfail"))
				resJson.put("status", token);
			else {
				HttpSession session = request.getSession();
				session.setAttribute("token",token);
				//�����¼�ɹ���token������token
				User user=userService.getUserInfor(Integer.parseInt(userId));
				resJson =(JSONObject) JSON.toJSON(user);
				//��user�Ļ�����Ϣ�����ͻ���
				resJson.put("status","success");
				resJson.put("token", token);//�����������޸��豸�ź������token���ؿͻ���
			}
		}else {
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
					user=userService.getUserInfor(Integer.parseInt(userId));
					if(user!=null)
					{
						resJson=(JSONObject) JSON.toJSON(user);
						resJson.put("status","success");
					}
					else
						resJson.put("status", "fail");
				}else if(reqJson.getString("operation").equals("logout"))//�˳���¼
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