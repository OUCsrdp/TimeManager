package com.srdp.admin.time_manager.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.srdp.admin.time_manager.model.moudle.TimeSharing;
import com.srdp.admin.time_manager.util.TokenUtil;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * Created by admin on 2018/5/1.
 */

public class UserHttp implements GetRequest,PostRequest{
    private JSONObject requestJson;
    public UserHttp(JSONObject userJson)
    {
        this.requestJson=userJson;
    }
    public void requestByGet(final Handler handler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //urlConnection请求服务器，验证
                try {
                    HttpURLConnection conn=null;//声明连接对象
                    //String urlStr="http://localhost:8080/TimeManagerServer?token="+token+"&date=";
                    String urlStr="http://10.115.200.45:8080/TimeManagerServer/UserServlet?"+new Date();
                    InputStream is = null;
                    OutputStream os=null;
                    URL url = new URL(urlStr); //URL对象
                    conn = (HttpURLConnection)url.openConnection();
                    //使用URL打开一个链接,下面设置这个连接
                    Message msg = Message.obtain();
                    conn.setRequestMethod("GET"); //使用get请求
                    conn.setUseCaches(false);
                    if(conn.getResponseCode()==200) {//返回200表示连接成功
                        is = conn.getInputStream(); //获取输入流
                        //从服务器端获取字符串
                        Log.i("userverify",is.toString());
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        msg.what=7;//表示获得验证码成功
                        msg.obj=bitmap;
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
    };
    public void requestByPost(final Handler handler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //urlConnection请求服务器，验证
                try {
                    Log.i("userrequest",requestJson.toString());
                    HttpURLConnection conn=null;//声明连接对象
                    //String urlStr="http://localhost:8080/TimeManagerServer?token="+token+"&date=";
                    String urlStr="http://10.115.200.45:8080/TimeManagerServer/UserServlet";
                    InputStream is = null;
                    OutputStream os=null;
                    String resultData = "";
                    JSONObject jsonObject=null;
                    TimeSharing newtable=null;
                    URL url = new URL(urlStr); //URL对象
                    conn = (HttpURLConnection)url.openConnection();
                    //使用URL打开一个链接,下面设置这个连接
                    Message msg = Message.obtain();
                    conn.setDoInput(true);          //打开输入流，以便从服务器获取数据
                    conn.setDoOutput(true);         //打开输出流，以便向服务器提交数据
                    conn.setRequestMethod("POST"); //使用post请求
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setUseCaches(false);
                    os = conn.getOutputStream();
                    os.write(requestJson.toString().getBytes());
                    if(conn.getResponseCode()==200) {//返回200表示连接成功
                        is = conn.getInputStream(); //获取输入流
                        InputStreamReader isr = new InputStreamReader(is);
                        BufferedReader bufferReader = new BufferedReader(isr);
                        String inputLine = "";
                        while ((inputLine = bufferReader.readLine()) != null) {
                            resultData += inputLine;
                        }
                        Log.i("userall", resultData);
                        //从服务器端获取字符串
                        //jsonObject = new JSONObject(resultData);
                        //将服务器返回的数据转化成一个json对象
                        jsonObject= JSONObject.parseObject(resultData);
                        if(jsonObject.getString("status").equals("unlogin"))
                        {
                            msg.what = 0;
                        }
                        else if(jsonObject.getString("status").equals("success")){
                            if(requestJson.getString("operation").equals("infor"))
                                msg.what = 1;//表示成功修改用户
                            else if(requestJson.getString("operation").equals("getUser")){
                                msg.what = 3;//表示成功获取用户
                                msg.obj=jsonObject;
                            }else if(requestJson.getString("operation").equals("logout")){
                                msg.what=4;//表示成功退出登录
                            }else if(requestJson.getString("operation").equals("register"))
                            {
                                msg.what=5;//表示成功注册
                            }else if(requestJson.getString("operation").equals("login"))
                            {
                                msg.what=6;//表示成功登录
                                TokenUtil.setToken(jsonObject.getString("token"));
                            }else if(requestJson.getString("operation").equals("getVerify"))
                            {
                                msg.what=7;//表示成功获取验证码



                            }
                        }
                        else{
                            msg.what = 2;//表示失败
                            msg.obj=jsonObject;
                        }
                        handler.sendMessage(msg);
                    }
                    else {
                        msg.what = 2;//表示失败
                        handler.sendMessage(msg);
                    }
                    Log.i("userMsg",String.valueOf(msg.what));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    };
}
