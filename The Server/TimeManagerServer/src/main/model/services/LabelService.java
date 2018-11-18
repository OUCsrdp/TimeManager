package main.model.services;

import java.util.ArrayList;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import main.model.db.AffairManager;
import main.model.db.LabelManager;
import main.model.db.S_AffairManager;
import main.model.moudle.Label;

public class LabelService
{	
	
	//使用日程表事件的id返回label
	public static JSONObject getLabelWithIdS(int idS)
	{
		JSONObject labelObject = new JSONObject();
		Label label = LabelManager.findWithId(S_AffairManager.findWithId(idS).getIdLabel());
		if(label == null)
			return null;
		else
		{
			labelObject.put("id", label.getId());
			labelObject.put("name", label.getName());
			labelObject.put("color", label.getColor());
			labelObject.put("image", label.getImage());
		}
		return labelObject;
	}
	
	//使用时间分配表的id返回label
	public static JSONObject getLabelWithIdA(int idA)
	{
		JSONObject labelObject = new JSONObject();
		Label label = LabelManager.findWithId(AffairManager.findWithId(idA).getIdLabel());
		if(label == null)
			return null;
		else
		{
			labelObject.put("id", label.getId());
			labelObject.put("name", label.getName());
			labelObject.put("color", label.getColor());
			labelObject.put("image", label.getImage());
		}
		return labelObject;
	}
	
	public static JSONArray getLabelsWithIdS(ArrayList<Integer> idS)
	{
		JSONArray labelArray = new JSONArray();
		for(int i = 0; i < idS.size(); i++)
		{
			Label label = LabelManager.findWithId(AffairManager.findWithId(idS.get(i)).getIdLabel());
			if(label != null)
			{
				JSONObject labelObject = new JSONObject();
				labelObject.put("id", label.getId());
				labelObject.put("name", label.getName());
				labelObject.put("color", label.getColor());
				labelObject.put("image", label.getImage());
				labelArray.add(labelObject);
			}
		}
		if(labelArray.size() == 0)
			return null;
		return labelArray;
	}
	
	public static JSONArray getLabelsWithIdA(ArrayList<Integer> idA)
	{
		JSONArray labelArray = new JSONArray();
		for(int i = 0; i < idA.size(); i++)
		{
			Label label = LabelManager.findWithId(AffairManager.findWithId(idA.get(i)).getIdLabel());
			if(label != null)
			{
				JSONObject labelObject = new JSONObject();
				labelObject.put("id", label.getId());
				labelObject.put("name", label.getName());
				labelObject.put("color", label.getColor());
				labelObject.put("image", label.getImage());
				labelArray.add(labelObject);
			}
		}
		if(labelArray.size() == 0)
			return null;
		return labelArray;
	}
}
