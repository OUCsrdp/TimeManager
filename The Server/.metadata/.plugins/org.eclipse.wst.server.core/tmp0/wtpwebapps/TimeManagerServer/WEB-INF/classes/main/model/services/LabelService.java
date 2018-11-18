package main.model.services;

import java.util.ArrayList;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import main.model.db.LabelManager;
import main.model.moudle.Label;

public class LabelService {
	
	public JSONArray getLabelList()
	{
		JSONArray labelList = new JSONArray();
		ArrayList<Label> labels = LabelManager.findWithNothing();
		for(int i = 0; i < labels.size(); i++)
		{
			JSONObject label = new JSONObject();
			label.put("id", Integer.toString(labels.get(i).getId()));
			label.put("name", labels.get(i).getName());
			label.put("image", labels.get(i).getImage());
			label.put("color", labels.get(i).getColor());
			labelList.add(label);
		}
		return labelList;
	}
}
