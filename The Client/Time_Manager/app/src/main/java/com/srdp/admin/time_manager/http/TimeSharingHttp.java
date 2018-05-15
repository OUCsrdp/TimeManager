package com.srdp.admin.time_manager.http;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.srdp.admin.time_manager.model.moudle.TimeSharing;
import com.srdp.admin.time_manager.util.TokenUtil;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by admin on 2018/4/17.
 */

public class TimeSharingHttp implements GetRequest{
    private int year;
    private int month;
    private int day;
    public TimeSharingHttp(int year,int month,int day)
    {
        this.year=year;
        this.month=month;
        this.day=day-2;
    }
    @Override
    public void requestByGet(final Handler handler) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                //urlConnection请求服务器，验证
                try {
                    Log.i("tsrequest",TokenUtil.getToken()+year+month+day);
                    HttpURLConnection conn=null;//声明连接对象
                    //String urlStr="http://localhost:8080/TimeManagerServer?token="+token+"&date=";
                    String urlStr="http://10.115.200.45:8080/TimeManagerServer/TimeSharingServlet?token="+ TokenUtil.getToken()+"&userId=2"+"&year="+year+"&month="+month+"&day="+day;
                    InputStream is = null;
                    String resultData = "";
                    JSONObject jsonObject=null;
                    TimeSharing newtable=null;
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
                        Log.i("tsall", resultData);
                        //从服务器端获取字符串
                        jsonObject= JSONObject.parseObject(resultData);
                        //将服务器返回的数据转化成一个json对象
                        if(jsonObject.getString("status").equals("unlogin"))
                        {
                            msg.what = 0;
                        }
                        else if(jsonObject.getString("status").equals("success")){
                            msg.what = 1;//表示成功获取时间分配表
                            msg.obj = jsonObject;
                            Log.i("tsjson",jsonObject.toString());
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
