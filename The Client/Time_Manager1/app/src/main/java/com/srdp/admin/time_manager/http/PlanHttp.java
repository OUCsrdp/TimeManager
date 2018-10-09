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
 * Created by admin on 2018/5/7.
 */

public class PlanHttp extends BaseHttp implements GetRequest {
    private int year;
    private int month;
    private int day;
    private String timeSign;
    //表示请求的年月日
    public PlanHttp(int year,int month,int day,String timeSign)
    {
        super();
        this.year=year;
        this.month=month;
        this.day=day;
        this.timeSign=timeSign;
    }
    @Override
    public void requestByGet(final Handler handler) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                //urlConnection请求服务器，验证
                try {
                    Log.i("planrequest", TokenUtil.getToken()+year+month+day);
                    HttpURLConnection conn=null;//声明连接对象
                    String urlStr=baseUrl+"ScheduleServlet?method=OperateS&token="+ TokenUtil.getToken()+"&year="+year+"&month="+month+"&day="+day;
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
                        Log.i("planall", resultData);
                        //从服务器端获取字符串
                        jsonObject= JSONObject.parseObject(resultData);
                        //将服务器返回的数据转化成一个json对象
                        if(jsonObject.getString("status").equals("unlogin"))
                        {
                            msg.what = 0;
                        }
                        else if(jsonObject.getString("status").equals("success")){
                            msg.what = 1;//表示成功获取日程表
                            Log.i("planjson",jsonObject.toString());
                        }
                        else
                            msg.what=2;
                        jsonObject.put("TimeSign",timeSign);
                        msg.obj = jsonObject;
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
