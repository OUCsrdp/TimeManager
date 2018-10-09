package com.srdp.admin.time_manager.util;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by admin on 2018/4/30.
 */

public class TokenUtil {
    static String token="";
    //获取token值
    public static String getToken(){
        return token;
    }
    public static void setToken(String t) {
        token=t;
    }
    //获取设备信息设置初始的token值，在登录的时候发到服务器端进行处理
    public static void  initToken(Context context)
    {
        token=DeviceUtil.getUniqueId(context.getApplicationContext());
    }
}
