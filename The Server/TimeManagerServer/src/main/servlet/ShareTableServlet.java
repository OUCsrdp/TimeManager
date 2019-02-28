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
	//��requestbodyת��Ϊjsonobject
	private JSONObject getRequestJson(String jsonString) throws IOException
	{
		JSONObject reqJson=JSON.parseObject(jsonString);
		return reqJson;
	}
	//����ʱ������
	public JSONObject share(HttpServletRequest request, HttpServletResponse response,String userId,String jsonString)throws ServletException, IOException{
		SharedtableService sharedService=new SharedtableService();
		JSONObject reqJson=getRequestJson(jsonString);
		JSONObject resJson=new JSONObject();
		String status=sharedService.share(reqJson.getIntValue("idTs"));
		resJson.put("status", status);
		//"success"||"gpafail"(gpa���㣬���ܷ���)||"fail"
		return resJson;
	}
	//�û��ղ�ϲ����ʱ������
	public JSONObject collect(HttpServletRequest request, HttpServletResponse response,String userId,String jsonString)throws ServletException, IOException{
		CollectionService collectionService=new CollectionService();
		JSONObject reqJson=getRequestJson(jsonString);;
		JSONObject resJson=new JSONObject();
		String status=collectionService.collection(reqJson.getIntValue("idST"),Integer.parseInt(userId));
		resJson.put("status",status);
		//����success��fail
		return resJson;
	}
	//�û���ϲ����ʱ����������
	public JSONObject like(HttpServletRequest request, HttpServletResponse response,String userId,String jsonString)throws ServletException, IOException{
		LikeService likeService=new LikeService();
		JSONObject reqJson=getRequestJson(jsonString);;
		JSONObject resJson=new JSONObject();
		String status=likeService.thumb(Integer.parseInt(userId),reqJson.getIntValue("idST"));
		resJson.put("status",status);
		//"success"||"fail"||"likedfail"(��ʾ�Ѿ������)
		return resJson;
	}
	//��ȡĳ��רҵ��������רҵ������ѧ��ʱ������
	public JSONObject getList(HttpServletRequest request, HttpServletResponse response,String userId,String jsonString)throws ServletException, IOException{
		SharedtableService sharedService=new SharedtableService();
		JSONObject reqJson=getRequestJson(jsonString);;
		JSONObject resJson=new JSONObject();
		JSONArray stlist=new JSONArray();
		stlist=sharedService.getSTList(reqJson.getString("major"));
		resJson.put("jsonArray",stlist);
		//����Ҫ��ʾ��ǰ��ҳ���ϵ�ÿ������ʱ������������Ϣ��jsonarray
		resJson.put("status","success");
		//״̬��־"success"
		return resJson;
		
	}
	//��ȡĳ�û��������ղ�
	public JSONObject getCollection(HttpServletRequest request, HttpServletResponse response,String userId,String jsonString)throws ServletException, IOException{
		CollectionService collectionService=new CollectionService();
		JSONObject reqJson=getRequestJson(jsonString);;
		JSONObject resJson=new JSONObject();
		resJson=collectionService.getCollection(Integer.parseInt(userId));
		//����Ҫ��ʾ��ǰ��ҳ���ϵ�ÿ������ʱ������������Ϣ��jsonarray
		resJson.put("status", "success");
		//״̬��־"success"
		return resJson;
	}
	public JSONObject getgetSearchMajors(HttpServletRequest request, HttpServletResponse response,String userId,String jsonString)throws ServletException, IOException{
		SharedtableService sharedTableService=new SharedtableService();
		JSONObject reqJson=getRequestJson(jsonString);
		JSONObject resJson=new JSONObject();
		String keyword=reqJson.getString("majorKeyword");
		resJson=sharedTableService.getMajorList(keyword);
		resJson.put("status", "success");
		//״̬��־"success"
		return resJson;
	}
}