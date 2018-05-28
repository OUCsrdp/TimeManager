package com.srdp.admin.time_manager.model.moudle;

import android.app.Application;
import android.content.Context;

/**
 * Created by admin on 2018/5/12.
 */

public class MyApplication extends Application {
    private static MyApplication instance;
    private Affair timingAffair=new Affair();
    private S_Affair timingSAffair=new S_Affair();
    private String baseUrl="http://10.112.188.134:8080/";
    private int isAffair=1;
    public void onCreate()
    {
        super.onCreate();
        instance=this;

    }
    public static Context getMyApplication() {
        return instance;
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
