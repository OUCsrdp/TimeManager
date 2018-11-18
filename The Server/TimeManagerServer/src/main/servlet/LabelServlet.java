package main.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import main.model.services.LabelService;
import main.util.GetRequestUtil;

public class LabelServlet extends BaseServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	//通过Affair的id来找到对应的Label，一次传一个JSONObject
	//JSONObject{"id", int}
	public JSONObject GetAffairLabel(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		JSONObject reqJson = new JSONObject();
		JSONObject resJson = new JSONObject();
		String jsonString=GetRequestUtil.getRequestJsonString(request);
		reqJson=JSON.parseObject(jsonString);
		int idA = reqJson.getIntValue("id");
		JSONObject labelJson = LabelService.getLabelWithIdA(idA);
		if(labelJson == null)
			resJson.put("status", "fail");
		else
		{
			resJson = labelJson;
			resJson.put("status", "success");
		}
		return resJson;
	}
	
	
	//通过S_Affair的id来找到对应的Label，一次传一个JSONObject
	//JSONObject{"id", int}
	public JSONObject GetS_AffairLabel(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		JSONObject reqJson = new JSONObject();
		JSONObject resJson = new JSONObject();
		String jsonString=GetRequestUtil.getRequestJsonString(request);
		reqJson=JSON.parseObject(jsonString);
		int idS = reqJson.getIntValue("id");
		JSONObject labelJson = LabelService.getLabelWithIdA(idS);
		if(labelJson == null)
			resJson.put("status", "fail");
		else
		{
			resJson = labelJson;
			resJson.put("status", "success");
		}
		return resJson;
	}
	
	//通过Affair的id来找到对应的Label，一次传一个JSONArray,个数不限
	//JSONObject{"id", int}
	//JSONArray: 若干个同样的JSONObject合成
	public JSONArray GetAffairLabels(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		JSONObject status = new JSONObject();
		JSONArray reqJson = new JSONArray();
		JSONArray resJson = new JSONArray();
		String jsonString=GetRequestUtil.getRequestJsonString(request);
		reqJson=JSON.parseArray(jsonString);
		ArrayList<Integer> idA = new ArrayList<Integer>();
		for(int i = 0; i < reqJson.size(); i++)
		{
			int id = reqJson.getJSONObject(i).getIntValue("id");
			idA.add(id);
		}
		JSONArray labelJson = LabelService.getLabelsWithIdA(idA);
		if(labelJson == null)
		{
			status.put("status", "fail");
			resJson.add(status);
		}
		else
		{
			status.put("status", "fail");
			resJson.add(status);
			for(int i = 0; i < labelJson.size(); i++)
				resJson.add(labelJson.getJSONObject(i));
		}
		return resJson;
	}
	
	
	//通过S_Affair的id来找到对应的Label，一次传一个JSONArray,个数不限
	//JSONObject{"id", int}
	//JSONArray: 若干个同样的JSONObject合成
	public JSONArray GetS_AffairLabels(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		JSONObject status = new JSONObject();
		JSONArray reqJson = new JSONArray();
		JSONArray resJson = new JSONArray();
		String jsonString=GetRequestUtil.getRequestJsonString(request);
		reqJson=JSON.parseArray(jsonString);
		ArrayList<Integer> idS = new ArrayList<Integer>();
		for(int i = 0; i < reqJson.size(); i++)
		{
			int id = reqJson.getJSONObject(i).getIntValue("id");
			idS.add(id);
		}
		JSONArray labelJson = LabelService.getLabelsWithIdS(idS);
		if(labelJson == null)
		{
			status.put("status", "fail");
			resJson.add(status);
		}
		else
		{
			status.put("status", "fail");
			resJson.add(status);
			for(int i = 0; i < labelJson.size(); i++)
				resJson.add(labelJson.getJSONObject(i));
		}
		return resJson;
	}
	
}
