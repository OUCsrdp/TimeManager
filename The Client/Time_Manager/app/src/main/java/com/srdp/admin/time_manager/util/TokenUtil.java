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
    public static String getToken(){
        return token;
    }
    public static void setToken(String t) {
        token=t;
    }
    public static void  initToken(Context context)
    {
        token=DeviceUtil.getUniqueId(context.getApplicationContext());
    }
}
