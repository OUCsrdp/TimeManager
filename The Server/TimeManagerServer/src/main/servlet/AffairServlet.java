package main.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import main.model.moudle.S_Affair;
import main.model.moudle.Affair;
import main.model.services.AffairService;
import main.model.services.UserService;
import main.util.GetRequestUtil;

public class AffairServlet extends BaseServlet{
	private static final long serialVersionUID = 1L;
	public JSONObject OperateAffair(HttpServletRequest request, HttpServletResponse response,String userId,String jsonString)throws ServletException, IOException{
		AffairService affairService=new AffairService();
		UserService userService=new UserService();
		JSONObject resJson = new JSONObject();
		JSONObject reqJson = new JSONObject();
		//String jsonString=GetRequestUtil.getRequestJsonString(request);
		reqJson=JSON.parseObject(jsonString);
		//���ǰ�˴�����json�ַ�������ת����jsonObject
		int sign1=reqJson.getIntValue("sign1");//1 means modify,0 means create,-1 means delete,-2means guessaffair
		int ifSuccess=0;
		if(sign1==-2)//guessAffair
		{
			String name=reqJson.getString("name");
			String date=reqJson.getString("date");
			String time=reqJson.getString("time");
			System.out.println("saffairsamename:"+time+" "+date+" "+name);
			System.out.println("saffairsamename:"+0);
			S_Affair saffair=affairService.guessAffair(time, date, name);//ͨ��ʱ�䣬���ڣ��������Ʋⴴ����ʱʱ���ܵ��ճ�
			System.out.println("saffairsamename:"+1);
			if(saffair==null)
			{
				resJson.put("status", "success");
				resJson.put("hasSAffair",0);//��ʾû������Ŀ����ճ�
			}	
			else {
				resJson=(JSONObject) JSON.toJSON(saffair);
				resJson.put("status","success");
				resJson.put("hasSAffair",1);//��ʾ������Ŀ����ճ�
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
			//String username=reqJson.getString("username");
			//��ȡuserId
			if(sign2==1)
			{
				ifSuccess=affairService.changeAffair(sign1, sign2,JSON.parseObject(jsonString,Affair.class),date,Integer.parseInt(userId));
				System.out.println("ifsuccess:"+ifSuccess);
			}else if(sign2==0)
			{
				ifSuccess=affairService.changeSAffair(sign1, sign2,JSON.parseObject(jsonString,S_Affair.class),date,Integer.parseInt(userId));
			}
			if(ifSuccess!=0)
			{
				//ifSuccess�������޸ĺʹ������¼���ID�����¼���id���Ƿ���affair����ǰ̨
				resJson.put("status","success");
				resJson.put("id", ifSuccess);
				if(sign2==1)
					resJson.put("isAffair", true);
				else
					resJson.put("isAffair", false);
			}
				
			else
				resJson.put("status","fail");
		}
		return resJson;
	}
	public JSONObject ChangeAffairTime(HttpServletRequest request, HttpServletResponse response,String userId,String jsonString)throws ServletException, IOException{
		AffairService affairService=new AffairService();
		JSONObject resJson = new JSONObject();
		JSONObject reqJson = new JSONObject();
		//���ǰ�˴�����json�ַ�������ת����jsonObject
		//String jsonString=GetRequestUtil.getRequestJsonString(request);
		reqJson=JSON.parseObject(jsonString);
		boolean success=false;
		success=affairService.changeAffairById(reqJson.getBooleanValue("isAffair"),reqJson.getIntValue("id"),reqJson.getString("StartTime"),reqJson.getString("EndTime"));
		//�����޸�һ��service��������isAffair�����͸�Ϊboolean
		if(success)
			resJson.put("status", "success");
		else
			resJson.put("status", "fail");
		return resJson;
	}
}
