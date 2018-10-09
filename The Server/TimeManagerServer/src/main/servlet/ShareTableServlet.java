package main.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import main.model.services.CollectionService;
import main.model.services.LikeService;
import main.model.services.SharedtableService;
import main.util.GetRequestUtil;

public class ShareTableServlet extends BaseServlet{

	private static final long serialVersionUID = 1L;
	//把requestbody转化为jsonobject
	private JSONObject getRequestJson(HttpServletRequest request) throws IOException
	{
		String jsonString=GetRequestUtil.getRequestJsonString(request);
		JSONObject reqJson=JSON.parseObject(jsonString);
		return reqJson;
	}
	//分享时间分配表
	public JSONObject share(HttpServletRequest request, HttpServletResponse response,String userId)throws ServletException, IOException{
		SharedtableService sharedService=new SharedtableService();
		JSONObject reqJson=getRequestJson(request);
		JSONObject resJson=new JSONObject();
		String status=sharedService.share(reqJson.getIntValue("idTs"));
		resJson.put("status", status);
		//"success"||"gpafail"(gpa不足，不能分享)||"fail"
		return resJson;
	}
	//用户收藏喜欢的时间分配表
	public JSONObject collect(HttpServletRequest request, HttpServletResponse response,String userId)throws ServletException, IOException{
		CollectionService collectionService=new CollectionService();
		JSONObject reqJson=getRequestJson(request);
		JSONObject resJson=new JSONObject();
		String status=collectionService.collection(reqJson.getIntValue("idST"),Integer.parseInt(userId));
		resJson.put("status",status);
		//返回success或fail
		return resJson;
	}
	//用户给喜欢的时间分配表点赞
	public JSONObject like(HttpServletRequest request, HttpServletResponse response,String userId)throws ServletException, IOException{
		LikeService likeService=new LikeService();
		JSONObject reqJson=getRequestJson(request);
		JSONObject resJson=new JSONObject();
		String status=likeService.thumb(Integer.parseInt(userId),reqJson.getIntValue("idST"));
		resJson.put("status",status);
		//"success"||"fail"||"likedfail"(表示已经点过赞)
		return resJson;
	}
	//获取某个专业或者所有专业的优秀学生时间分配表
	public JSONObject getList(HttpServletRequest request, HttpServletResponse response,String userId)throws ServletException, IOException{
		SharedtableService sharedService=new SharedtableService();
		JSONObject reqJson=getRequestJson(request);
		JSONObject resJson=new JSONObject();
		JSONArray stlist=new JSONArray();
		stlist=sharedService.getSTList(reqJson.getString("major"));
		resJson.put("jsonArray",stlist);
		//包含要显示在前端页面上的每张优秀时间分配表基本信息的jsonarray
		resJson.put("status","success");
		//状态标志"success"
		return resJson;
		
	}
	//获取某用户的所有收藏
	public JSONObject getCollection(HttpServletRequest request, HttpServletResponse response,String userId)throws ServletException, IOException{
		CollectionService collectionService=new CollectionService();
		JSONObject reqJson=getRequestJson(request);
		JSONObject resJson=new JSONObject();
		resJson=collectionService.getCollection(Integer.parseInt(userId));
		//包含要显示在前端页面上的每张优秀时间分配表基本信息的jsonarray
		resJson.put("status", "success");
		//状态标志"success"
		return resJson;
	}
}
