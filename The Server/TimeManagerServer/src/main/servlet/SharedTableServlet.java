package main.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		JSONObject reqJson = new JSONObject();//请求的jsonObject
		JSONObject resJson = new JSONObject();//回复的jsonObject
		try {
			String jsonString=GetRequestUtil.getRequestJsonString(request);
			reqJson=JSON.parseObject(jsonString);
			String status="fail";
			String token=reqJson.getString("token");
			//判断token是否正确
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
			if(tokenpass)//表示该用户尚未登录
			{
				resJson.put("status","unlogin");
			}else {
				String operation=reqJson.getString("operation");
				if(operation.equals("share"))//分享时间分配表,需要时间分配表的id
				{
					status=sharedService.share(reqJson.getIntValue("idTs"));
					resJson.put("status", status);//gpa不足返回gpafail 操作失败返回fail
				}
				else if(operation.equals("collect"))//收藏时间分配表，需要已分享的时间分配表的id,当前用户的id
				{
					status=collectionService.collection(reqJson.getIntValue("idST"),reqJson.getIntValue("userId"));
					resJson.put("status",status);//返回success或fail
				}
				else if(operation.equals("like"))//点赞时间分配表，需要当前用户的id和被点赞的已分享的时间分配表的id
				{
					//status=likeService.thumb(reqJson.getIntValue("userId"),reqJson.getIntValue("idST"));
					resJson.put("status",status);
					//返回”success”||”fail”||”likedfail”,likefail表示已经点过赞,不能重复点赞
				}
				else if(operation.equals("getList"))//获取优秀时间分配列表在，需要参数为专业，“具体专业”或者"all"
				{
					JSONArray stlist=new JSONArray();
					stlist=sharedService.getSTList(reqJson.getString("major"));
					resJson.put("status","success");
					resJson.put("jsonArray",stlist);
				}
				else if(operation.equals("getCollection"))//获取某用户收藏的时间分配表,参数为当前用户的id
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
