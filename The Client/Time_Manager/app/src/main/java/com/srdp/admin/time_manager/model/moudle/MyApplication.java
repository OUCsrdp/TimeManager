package com.srdp.admin.time_manager.model.moudle;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.srdp.admin.time_manager.util.TokenUtil;
import com.srdp.admin.time_manager.util.UserUtil;

/**
 * Created by admin on 2018/5/12.
 */

public class MyApplication extends Application {
    private static MyApplication instance;
    private static Context context;
    private Affair timingAffair=new Affair();
    private S_Affair timingSAffair=new S_Affair();
    private String baseUrl="http://10.115.235.198:8080/TimeManagerServer/";
    private int isAffair=1;

    public void onCreate()
    {
        super.onCreate();
        //赋值全局context
        context = getApplicationContext();
        //赋值单例的application
        instance=this;
        //从sharedpreferences里读数据
        SharedPreferences sp=getContext().getSharedPreferences("user",MODE_PRIVATE);
        //获取json字符串形式的user
        String userString=sp.getString("user","");
        //获取字符串形式的token
        String token=sp.getString("token","");
        Log.i("applicationToken",token);
        //如果已经存了userString,设置userUtil和tokenUtil的值
        if(!userString.equals(""))
        {
            UserUtil.setUser(JSON.parseObject(userString));
            TokenUtil.setToken(token);
        }
        //如果没有默认user为空，获取设备号的md5值作为token
        else
        {
            TokenUtil.initToken(getContext());
        }
    }
    //获取单例的application
    public static Context getMyApplication() {
        return instance;
    }
    //获取全局context
    public static Context getContext() {
        return context;
    }
    public void setTimingAffair(Affair affair)
    {
        timingAffair=affair;
    }
    public Affair getTimingAffair()
    {
        return timingAffair;
    }
    public void setTimingSAffair(S_Affair sAffair)
    {
        timingSAffair=sAffair;
    }
    public S_Affair getTimingSAffair()
    {
        return timingSAffair;
    }
    public void setIsAffair(int isAffair)
    {
        this.isAffair=isAffair;
    }
    public int getIsAffair()
    {
        return isAffair;
    }
    public String getBaseUrl()
    {
        return baseUrl;
    }
}
