package main.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import main.model.services.CollectionService;
import main.model.services.LikeService;
import main.model.services.SharedtableService;
import main.model.services.UserService;
import main.util.GetRequestUtil;

/**
 * Servlet implementation class SharedTableServlet
 */
@WebServlet("/SharedTableServlet")
public class SharedTableServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService=new UserService();
	private CollectionService collectionService=new CollectionService();
	private LikeService likeService=new LikeService();
	private SharedtableService sharedService=new SharedtableService();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SharedTableServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		JSONObject reqJson = new JSONObject();//�����jsonObject
		JSONObject resJson = new JSONObject();//�ظ���jsonObject
		try {
			String jsonString=GetRequestUtil.getRequestJsonString(request);
			reqJson=JSON.parseObject(jsonString);
			String status="fail";
			String token=reqJson.getString("token");
			int judgeToken=userService.judgeToken(token);
			if(judgeToken==0)//��ʾ���û���δ��¼
			{
				resJson.put("status","unlogin");
			}else {
				String operation=reqJson.getString("operation");
				if(operation.equals("share"))//����ʱ������,��Ҫʱ�������id
				{
					status=sharedService.share(reqJson.getIntValue("idTs"));
					resJson.put("status", status);//gpa���㷵��gpafail ����ʧ�ܷ���fail
				}
				else if(operation.equals("collect"))//�ղ�ʱ��������Ҫ�ѷ����ʱ�������id,��ǰ�û���id
				{
					status=collectionService.collection(reqJson.getIntValue("idST"),reqJson.getIntValue("userId"));
					resJson.put("status",status);//����success��fail
				}
				else if(operation.equals("like"))//����ʱ��������Ҫ��ǰ�û���id�ͱ����޵��ѷ����ʱ�������id
				{
					//status=likeService.thumb(reqJson.getIntValue("userId"),reqJson.getIntValue("idST"));
					resJson.put("status",status);
					//���ء�success��||��fail��||��likedfail��,likefail��ʾ�Ѿ������,�����ظ�����
				}
				else if(operation.equals("getList"))//��ȡ����ʱ������б��ڣ���Ҫ����Ϊרҵ��������רҵ������"all"
				{
					JSONArray stlist=new JSONArray();
					stlist=sharedService.getSTList(reqJson.getString("major"));
					resJson.put("status","success");
					resJson.put("jsonArray",stlist);
				}
				else if(operation.equals("getCollection"))//��ȡĳ�û��ղص�ʱ������,����Ϊ��ǰ�û���id
				{
					resJson=collectionService.getCollection(reqJson.getIntValue("userId"));
					resJson.put("status", "success");
				}
			}
		}catch (Exception e) {
			resJson.put("status","fail");
		}finally {
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().append(resJson.toString());
		}
	}

}
