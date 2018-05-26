package com.srdp.admin.time_manager.http;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.srdp.admin.time_manager.model.moudle.MyApplication;
import com.srdp.admin.time_manager.model.moudle.TimeSharing;




import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by admin on 2018/4/30.
 */

public class AffairHttp extends BaseHttp implements PostRequest {
    private JSONObject requestJson;
    public AffairHttp(JSONObject requestJson)
    {
        super();
        this.requestJson=requestJson;
    }
    @Override
    public void requestByPost(final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //urlConnection请求服务器，验证
                try {
                    Log.i("affairrequest", requestJson.toString());
                    HttpURLConnection conn=null;//声明连接对象
                    //String urlStr="http://localhost:8080/TimeManagerServer?token="+token+"&date=";
                    //String urlStr="http://172.17.146.1:8080/TimeManagerServer/AffairServlet";
                    String urlStr=baseUrl+"TimeManagerServer/AffairServlet";
                    InputStream is = null;
                    OutputStream os=null;
                    String resultData = "";
                    JSONObject jsonObject=null;
                    TimeSharing newtable=null;
                    URL url = new URL(urlStr); //URL对象
                    conn = (HttpURLConnection)url.openConnection();
                    //使用URL打开一个链接,下面设置这个连接
                    Message msg = Message.obtain();
                    conn.setRequestMethod("POST"); //使用post请求
                    conn.setUseCaches(false);
                    os = conn.getOutputStream();
                    os.write(requestJson.toString().getBytes());
                    os.flush();
                    //将json对象写入输出流中
                    if(conn.getResponseCode()==200) {//返回200表示连接成功
                        is = conn.getInputStream(); //获取输入流
                        InputStreamReader isr = new InputStreamReader(is);
                        BufferedReader bufferReader = new BufferedReader(isr);
                        String inputLine = "";
                        while ((inputLine = bufferReader.readLine()) != null) {
                            resultData += inputLine;
                        }
                        Log.i("affairall", resultData);
                        //从服务器端获取字符串
                        //jsonObject = new JSONObject(resultData);
                        jsonObject= JSONObject.parseObject(resultData);
                        //将服务器返回的数据转化成一个json对象
                        if(jsonObject.getString("status").equals("unlogin"))
                        {
                            msg.what = 0;
                        }
                        else if(jsonObject.getString("status").equals("success")){
                            if(requestJson.getString("time")!=null)
                            {
                                msg.what=4;
                                msg.obj=jsonObject;//表示获取同名日程事件成功
                            }
                            else if(requestJson.getIntValue("sign1")==-1)
                                msg.what=3;//表示成功删除事件
                            else
                                msg.what = 1;//表示成功修改创建事件
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
