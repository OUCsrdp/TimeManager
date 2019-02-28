package com.srdp.admin.time_manager.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.app.Activity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.CheckBox;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.Base64;
import com.srdp.admin.time_manager.R;
import com.srdp.admin.time_manager.http.UserHttp;
import com.srdp.admin.time_manager.util.HttpUtil;
import com.srdp.admin.time_manager.util.TokenUtil;
import com.srdp.admin.time_manager.util.UserUtil;


import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private TextView go_register;

    private EditText login_name;
    private EditText login_password;
    private EditText login_captcha;
    private Button login_btn;
    private CheckBox remember_me;
    private String login_name_edit;
    private String login_password_edit;
    private ImageView login_captcha_image;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //隐藏默认actionbar
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }
        //TokenUtil.initToken(this);
        //初始化token
        getVerify();
        //进入页面获取验证码
        login_captcha_image=(ImageView) findViewById(R.id.login_captcha_img);
        login_captcha_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getVerify();
            }
        });
        Log.d("Image", login_captcha_image.toString());
        //跳转注册
        go_register = (TextView) findViewById(R.id.go_register);


        //登录验证
        login_btn = (Button) findViewById(R.id.login_btn);
        login_name = (EditText) findViewById(R.id.login_name);
        login_password = (EditText) findViewById(R.id.login_password);
        login_captcha = (EditText) findViewById(R.id.login_captcha);


        View.OnClickListener listener = new View.OnClickListener(){
            public void onClick(View v){
                switch(v.getId()){
                    case R.id.login_captcha_img:
                        getVerify();
                        break;
                    case R.id.go_register:
                        //跳转注册
                        Intent go_register_page = new Intent(LoginActivity.this,RegisterSchoolActivity.class);
                        startActivity(go_register_page);
                        break;

                    case R.id.login_btn:
                        //登录验证
                        login_name_edit = login_name.getText().toString();//获取用户名
                        login_password_edit = login_password.getText().toString();//获取密码

                        if(TextUtils.isEmpty(login_name_edit)){
                            Toast.makeText(LoginActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
                            getVerify();
                            return;
                        }
                        if(TextUtils.isEmpty(login_password_edit)){
                            Toast.makeText(LoginActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                            getVerify();
                            return;
                        }
                        if(login_name_edit.length()>15){
                            Toast.makeText(LoginActivity.this,"用户名不能超过15个字符",Toast.LENGTH_SHORT).show();
                            getVerify();
                            return;
                        }
                        if(login_name_edit.length()>20){
                            Toast.makeText(LoginActivity.this,"密码不能超过20个字符",Toast.LENGTH_SHORT).show();
                            getVerify();
                            return;
                        }
                        TryLogin();
                        break;


                }
            }
        };
        go_register.setOnClickListener(listener);
        login_btn.setOnClickListener(listener);

    }

    private static class LoginHandler extends Handler {
        //持有弱引用HandlerActivity,GC回收时会被回收掉.
        private final WeakReference<LoginActivity> mActivty;

        public LoginHandler(LoginActivity activity) {
            mActivty = new WeakReference<LoginActivity>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            LoginActivity activity = mActivty.get();
            super.handleMessage(msg);
            if (activity != null) {
                switch (msg.what) {
                    case 7:
                        Log.i("userinhandler7","success");
                        activity.refreshVerify((Bitmap)msg.obj);
                        Log.d("verify",msg.obj.toString());
                        break;
                    case 6:
                        UserUtil.setUserName(activity.getLogin_name_edit());
                        Toast.makeText(activity,"登录成功",Toast.LENGTH_SHORT).show();
                        //提示登录成功并去往首页
                        activity.saveUser((JSONObject)msg.obj);
                        Intent go_activity_page = new Intent(activity,Index_Timing_Change.class);
                        activity.startActivity(go_activity_page);
                        break;
                    case 2:
                        JSONObject failstatus=(JSONObject)msg.obj;
                        if(failstatus.getString("status").equals("usernamefail"))
                            Toast.makeText(activity,"用户名不存在",Toast.LENGTH_SHORT).show();
                        else if(failstatus.getString("status").equals("passwordfail"))
                            Toast.makeText(activity,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                        else if(failstatus.getString("status").equals("verifyfail"))
                        Toast.makeText(activity,"验证码错误",Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(activity,"登录失败",Toast.LENGTH_SHORT).show();
                        break;
                    case 0:
                        activity.reLogin();
                        break;
                }
            }
        }
    }
    private String getLogin_name_edit(){
        return login_name_edit;
    }
    private void TryLogin()
    {
        UserUtil.setUserName(login_name_edit);
        //在userUtil中设置用户名字
        JSONObject loginJson=new JSONObject();
        loginJson.put("name",login_name_edit);
        loginJson.put("pwd",login_password_edit);
        loginJson.put("verify",login_captcha.getText().toString());
        TokenUtil.initToken(this);
        loginJson.put("token", TokenUtil.getToken());
        loginJson.put("operation","login");
        UserHttp userHttp=new UserHttp(loginJson);
        userHttp.requestByPost(new LoginHandler(this));
    }
    private void getVerify()
    {
        /*JSONObject loginJson=new JSONObject();
        UserHttp userHttp=new UserHttp(loginJson);
        userHttp.requestByGet(new LoginHandler(this));*/
        JSONObject reqJson=new JSONObject();
        HttpUtil.request("UserServlet?method=GetVerify&time="+new Date(),"post",reqJson,new okhttp3.Callback(){
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                try {
                    String ress=response.body().string();
                    Log.i("userverifybody",ress);
                    JSONObject resJson = JSONObject.parseObject(ress);
                    Log.i("userjson",resJson.toString());
                    byte[] img=resJson.getBytes("verifyImg");
                    Bitmap bm=BitmapFactory.decodeByteArray(img,0,img.length);
                    /*InputStream is = response.body().byteStream();
                    Bitmap bm = BitmapFactory.decodeStream(is);*/
                    //Bitmap bm=BitmapFactory.decodeByteArray(is, 0, is.length);
                    //根据服务器返回的状态处理响应
                    refreshVerify(bm);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(@NonNull Call call,@NonNull IOException e) {
                createFail();
            }
        });
    }
    private void refreshVerify(Bitmap bitmap)
    {
        login_captcha_image.setImageBitmap(bitmap);
    }
    private void saveUser(JSONObject user)
    {
        SharedPreferences.Editor editor=getSharedPreferences("user",MODE_PRIVATE).edit();
        TokenUtil.setToken(user.getString("token"));
        editor.putString("token",user.getString("token"));//存入token
        user.remove("token");
        user.remove("status");//移除jsonobject中的token和status
        editor.putString("user",user.toString());//存入user转化成的string
        editor.apply();//异步存储，无法返回成功失败
        UserUtil.setUser(user);
    }
    private void createFail()
    {
        Log.d("CreateFail", "fail");
        //Toast.makeText(this,"计时失败！",Toast.LENGTH_SHORT).show();
    }
    private void reLogin()
    {
        Intent reLogin=new Intent(this,LoginActivity.class);
        startActivity(reLogin);
    }
}
