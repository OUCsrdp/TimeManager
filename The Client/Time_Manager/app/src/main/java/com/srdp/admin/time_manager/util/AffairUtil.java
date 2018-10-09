package com.srdp.admin.time_manager.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.alibaba.fastjson.JSONObject;
import com.srdp.admin.time_manager.model.moudle.MyApplication;


import static android.content.Context.MODE_PRIVATE;

/*这个工具类主要用于使用sharedPerference存储和读取当前进行的affair或saffair中的时间信息
该文件的名称为countTime
其中含有的数据为
int id;
boolean isAffair;
boolean hasRecord;
String StartTime;
*/
public class AffairUtil {
    private static Context c= MyApplication.getContext();
    //在创建计时的时候存入当前事务的基本信息（affairId和事务类型）
    public static void init(int id, boolean isAffair)
    {
        SharedPreferences.Editor editor=c.getSharedPreferences("countTime",MODE_PRIVATE).edit();
        editor.putInt("id",id);
        editor.putBoolean("isAffair",isAffair);
        editor.apply();
    }
    //判断文件是否记录开始时间
    public static boolean hasRecorded()
    {
        SharedPreferences sp=c.getSharedPreferences("countTime",MODE_PRIVATE);
        boolean hasRecord=sp.getBoolean("hasRecord",false);
        return hasRecord;
    }
    //读取开始时间
    public static String readStartTime()
    {
        SharedPreferences sp=c.getSharedPreferences("countTime",MODE_PRIVATE);
        String startTime=sp.getString("StartTime","");
        return startTime;
    }
    //将开始时间写入文件
    public static void writeStartTime(String startTime)
    {
        SharedPreferences.Editor editor=c.getSharedPreferences("countTime",MODE_PRIVATE).edit();
        editor.putString("StartTime",startTime);
        editor.putBoolean("hasRecord",true);
        editor.apply();
    }
    //删除文件中记载的当前事务的信息
    public static void deleteAffairInfor()
    {
        SharedPreferences.Editor editor=c.getSharedPreferences("countTime",MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }
    public static JSONObject getAffairTime()
    {
        SharedPreferences sp=c.getSharedPreferences("countTime",MODE_PRIVATE);
        int id=sp.getInt("id",0);
        boolean isAffair=sp.getBoolean("isAffair",true);
        String StartTime=sp.getString("StartTime","");
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("id",id);
        jsonObject.put("isAffair",isAffair);
        jsonObject.put("StartTime",StartTime);
        return jsonObject;
    }
}
