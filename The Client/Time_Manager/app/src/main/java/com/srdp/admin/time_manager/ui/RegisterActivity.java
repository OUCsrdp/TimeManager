package com.srdp.admin.time_manager.ui;

import android.content.Intent;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.srdp.admin.time_manager.R;
import com.srdp.admin.time_manager.http.UserHttp;
import com.srdp.admin.time_manager.model.moudle.User;
import com.srdp.admin.time_manager.util.HttpUtil;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    private Button register_btn;

    private EditText register_name;
    private EditText register_number;
    private EditText register_password;
    private EditText register_captcha;
    private ImageView register_captcha_img;
    private Intent regist_school_intent;
    private User user=new User();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //隐藏默认actionbar
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }
        regist_school_intent=getIntent();

        //注册验证
        register_btn = (Button) findViewById(R.id.register_btn);

        register_name = (EditText) findViewById(R.id.register_name);
        register_number = (EditText) findViewById(R.id.register_number);
        register_password = (EditText) findViewById(R.id.register_password);
        register_captcha = (EditText) findViewById(R.id.register_captcha);
        register_captcha_img=(ImageView)findViewById(R.id.register_captcha_img);

        getVerify();
        register_captcha_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getVerify();
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                final String register_name_edit = register_name.getText().toString();//获取用户名
                final String register_number_edit = register_number.getText().toString();//获取学号
                final String register_password_edit = register_password.getText().toString();//获取密码
                String regist_verify=register_captcha.getText().toString();//获取验证码

                if(TextUtils.isEmpty(register_name_edit)){
                    Toast.makeText(RegisterActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(register_number_edit)){
                    Toast.makeText(RegisterActivity.this,"学号不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(register_password_edit)){
                    Toast.makeText(RegisterActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(register_name_edit.length()>15){
                    Toast.makeText(RegisterActivity.this,"用户名不能超过15个字符",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(register_password_edit.length()>20){
                    Toast.makeText(RegisterActivity.this,"密码不能超过20个字符",Toast.LENGTH_SHORT).show();
                    return;
                }
                user.setName(register_name_edit);
                user.setNumStu(register_number_edit);
                user.setPwd(register_password_edit);
                TryRegister(regist_verify);

            }
        });
    }

    private static class RegisterHandler extends Handler {
        //持有弱引用HandlerActivity,GC回收时会被回收掉.
        private final WeakReference<RegisterActivity> mActivty;

        public RegisterHandler(RegisterActivity activity) {
            mActivty = new WeakReference<RegisterActivity>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            RegisterActivity activity = mActivty.get();
            super.handleMessage(msg);
            if (activity != null) {
                switch (msg.what) {
                    case 5:
                        Toast.makeText(activity,"注册成功",Toast.LENGTH_SHORT).show();
                        //提示注册成功并去往首页
                        Intent go_activity_page = new Intent(activity,LoginActivity.class);
                        activity.startActivity(go_activity_page);
                        break;
                    case 2:
                        JSONObject registerStatus=(JSONObject)msg.obj;
                        if(registerStatus.getString("status").equals("usernamefail"))
                            Toast.makeText(activity,"用户名已经被使用",Toast.LENGTH_SHORT).show();
                        else if(registerStatus.getString("status").equals("verifyfail"))
                            Toast.makeText(activity,"验证码错误",Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(activity,"注册失败",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }
    }
    //获取验证码
    private void getVerify()
    {
        JSONObject reqJson=new JSONObject();
        HttpUtil.request("UserServlet?method=GetVerify&time="+new Date(),"post",reqJson,new okhttp3.Callback(){
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                try {
                    String ress=response.body().string();
                    JSONObject resJson = JSONObject.parseObject(ress);
                    byte[] img=resJson.getBytes("verifyImg");
                    Bitmap bm= BitmapFactory.decodeByteArray(img,0,img.length);
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
    //UI注册页刷新验证码
    private void refreshVerify(Bitmap bitmap)
    {
        register_captcha_img.setImageBitmap(bitmap);
    }
    private void TryRegister(String regist_verify)
    {
        user.setSchool(regist_school_intent.getStringExtra("school"));
        user.setMajor(regist_school_intent.getStringExtra("major"));
        user.setImage("for test");
        Date date=new Date();
        SimpleDateFormat df=new SimpleDateFormat("yyyy年MM月dd日");
        String timeRegister=df.format(date);
        user.setTimeRegister(timeRegister);
        JSONObject userJson =(JSONObject) JSON.toJSON(user);
        userJson.put("operation","register");
        userJson.put("verify",regist_verify);
        UserHttp userHttp=new UserHttp(userJson);
        userHttp.requestByPost(new RegisterHandler(this));
    }
    private void createFail()
    {
        Toast.makeText(this,"注册失败！",Toast.LENGTH_SHORT).show();
    }
}
