package com.srdp.admin.time_manager.http;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by admin on 2018/5/8.
 */

public class LabelHttp extends BaseHttp implements GetRequest {
    public LabelHttp()
    {
        super();
    }
    @Override
    public void requestByGet(final Handler handler) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                //urlConnection请求服务器，验证
                try {
                    HttpURLConnection conn=null;//声明连接对象
                    String urlStr=baseUrl+"TimeManagerServer/LabelServlet";
                    InputStream is = null;
                    String resultData = "";
                    JSONObject jsonObject=null;
                    URL url = new URL(urlStr); //URL对象
                    conn = (HttpURLConnection)url.openConnection();
                    //使用URL打开一个链接,下面设置这个连接
                    Message msg = Message.obtain();
                    conn.setRequestMethod("GET"); //使用get请求
                    if(conn.getResponseCode()==200) {//返回200表示连接成功
                        is = conn.getInputStream(); //获取输入流
                        InputStreamReader isr = new InputStreamReader(is);
                        BufferedReader bufferReader = new BufferedReader(isr);
                        String inputLine = "";
                        while ((inputLine = bufferReader.readLine()) != null) {
                            resultData += inputLine;
                        }
                        Log.i("labelall", resultData);
                        //从服务器端获取字符串
                        jsonObject= JSONObject.parseObject(resultData);
                        //将服务器返回的数据转化成一个json对象
                        if(jsonObject.getString("status").equals("success")){
                            msg.what = 1;//表示成功获取时间分配表
                            msg.obj = jsonObject;
                            Log.i("labeljson",jsonObject.toString());
                        }
                        else
                            msg.what=2;
                        handler.sendMessage(msg);
                    }
                    else {
                        msg.what = 2;//表示失败
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
