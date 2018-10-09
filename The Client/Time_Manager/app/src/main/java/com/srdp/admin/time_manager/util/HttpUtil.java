package com.srdp.admin.time_manager.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;
import com.srdp.admin.time_manager.model.moudle.MyApplication;
import com.srdp.admin.time_manager.ui.RegisterActivity;

import java.util.Iterator;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by admin on 2018/5/27.
 */

public class HttpUtil {
    private static String baseUrl= ((MyApplication)MyApplication.getMyApplication()).getBaseUrl();
    //获取基本的url
    private static MediaType json = MediaType.parse("application/json; charset=utf-8");
    //创建json类型的MediaType
    public static void request(String url, String type, JSONObject reqJson,okhttp3.Callback callback)
    {
        url=baseUrl+url;//拼接基本的url和具体的url
        try {
            OkHttpClient client=new OkHttpClient();

            Request request=null;

            if(type.equals("get"))
            {
                Map<String,Object> reqMap=reqJson.getInnerMap();//得到jsonObject内部的map
                String addUrl="?token=";
                addUrl=addUrl+TokenUtil.getToken();//url拼接token
                for (Map.Entry<String,Object> entry :reqMap.entrySet()) {
                    addUrl=addUrl+"&"+entry.getKey()+"="+entry.getValue();
                }
                url=url+addUrl;//拼接url和参数键值对
                request=new Request.Builder().url(url).build();
            }
            else if(type.equals("post"))
            {
                reqJson.put("token",TokenUtil.getToken());
                RequestBody requestBody=RequestBody.create(json,reqJson.toString());
                //请求体使用json格式
                request=new Request.Builder().url(url).post(requestBody).build();
            }
            client.newCall(request).enqueue(callback);
            //调用请求，成功后使用回调函数
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
