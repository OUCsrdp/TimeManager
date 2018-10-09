package com.srdp.admin.time_manager.http;

import android.os.Handler;

import com.srdp.admin.time_manager.model.moudle.MyApplication;

/**
 * Created by admin on 2018/5/26.
 */

public class BaseHttp{
    protected String baseUrl="";
    public BaseHttp()
    {
        MyApplication application=(MyApplication) MyApplication.getMyApplication();
        baseUrl=application.getBaseUrl();
    }
}
