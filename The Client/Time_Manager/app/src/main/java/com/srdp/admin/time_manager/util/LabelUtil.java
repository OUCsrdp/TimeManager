package com.srdp.admin.time_manager.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.srdp.admin.time_manager.model.moudle.Label;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/5/8.
 */

public class LabelUtil {
    static List<Label> labels=new ArrayList<Label>();
    public static void setLabels(JSONObject j)
    {
        JSONArray jsarr=j.getJSONArray("Labels");
        String js=JSONObject.toJSONString(jsarr, SerializerFeature.WriteClassName);
        labels = JSONObject.parseArray(js,Label.class);
        //将labels的jsonarray转化成lsit
    }
    public static Label findById(int id)
    {
        for(int i=0;i<labels.size();i++)
        {
            if(labels.get(i).getId()==id)
                return labels.get(i);
        }
        return null;
    }
}
