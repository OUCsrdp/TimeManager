package com.srdp.admin.time_manager.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.srdp.admin.time_manager.R;
import com.srdp.admin.time_manager.model.moudle.Label;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/5/8.
 */

public class LabelUtil {
    static List<Label> labels=new ArrayList<Label>();
    static{
        labels.add(new Label(1,"学习",R.drawable.label_learning,R.color.LabelLearing));
        labels.add(new Label(2,"自学",R.drawable.label_self_learning,R.color.LabelSelfLearing));
        labels.add(new Label(3,"社团",R.drawable.label_club,R.color.LabelClub));
        labels.add(new Label(4,"娱乐",R.drawable.label_entertainment,R.color.LabelEntertainment));
        labels.add(new Label(5,"交通",R.drawable.label_transport,R.color.LabelTransport));
        labels.add(new Label(6,"吃饭",R.drawable.label_eat,R.color.LabelEat));
        labels.add(new Label(7,"休息",R.drawable.label_rest,R.color.LabelRest));
        labels.add(new Label(8,"睡觉",R.drawable.label_sleep,R.color.LabelSleep));
        labels.add(new Label(9,"生活",R.drawable.label_life,R.color.LabelLife));
        labels.add(new Label(10,"运动",R.drawable.label_sports,R.color.LabelSports));
        labels.add(new Label(11,"其他",R.drawable.label_other,R.color.LabelOther));
    }
    public static void setLabels(JSONObject j)
    {
        JSONArray jsarr=j.getJSONArray("Labels");
        String js=JSONObject.toJSONString(jsarr, SerializerFeature.WriteClassName);
        labels = JSONObject.parseArray(js,Label.class);
        //将labels的jsonarray转化成lsit
    }
    public static List<Label> getLabels()
    {
        return labels;
    }
    public static Label getLabel(int id)
    {
        for(int i=0;i<labels.size();i++)
        {
            if(labels.get(i).getId()==id)
                return labels.get(i);
        }
        return null;
    }
    public static Label getLabelByName(String name)
    {
        for(int i=0;i<labels.size();i++)
        {
            if(labels.get(i).getName()==name)
                return labels.get(i);
        }
        return null;
    }
}
