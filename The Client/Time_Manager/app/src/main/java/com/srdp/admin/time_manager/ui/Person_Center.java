package com.srdp.admin.time_manager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.srdp.admin.time_manager.R;
import com.srdp.admin.time_manager.http.UserHttp;
import com.srdp.admin.time_manager.model.moudle.User;
import com.srdp.admin.time_manager.util.TokenUtil;
import com.srdp.admin.time_manager.util.UserUtil;

import java.lang.ref.WeakReference;

public class Person_Center extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person__center);
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }
        getUser();
        ((TextView)findViewById(R.id.CenterUsername)).setText(UserUtil.getUser().getName());
        TextView personInforEdit=(TextView)findViewById(R.id.PersonInforEdit);
        personInforEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Person_Center.this,Person_Infor.class);
                startActivity(intent);
            }
        });
        //点击“个人资料”的时候跳转到个人信息页
        Button LogoutButton=(Button)findViewById(R.id.LogoutButton);
        LogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

    }
    private static class PCeditHandler extends Handler {
        //持有弱引用HandlerActivity,GC回收时会被回收掉.
        private final WeakReference<Person_Center> mActivty;

        public PCeditHandler(Person_Center activity) {
            mActivty = new WeakReference<Person_Center>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            Person_Center activity = mActivty.get();
            super.handleMessage(msg);
            if (activity != null) {
                switch (msg.what) {
                    case 4:
                        activity.getToast("退出登录成功");
                        Log.i("userhandler2","outsuccess");
                        activity.reLogin();
                        break;
                        //重新登录（跳转到登录页面）
                    case 3:
                        activity.getToast("获取用户信息成功");
                        activity.initData((JSONObject)msg.obj);
                        Log.i("userhandler1","getsuccess");
                        break;
                    case 2:
                        activity.getToast("退出登录失败");
                        activity.finish();break;
                    case 0:
                        activity.reLogin();
                        //重新登录
                }
            }
        }
    }
    //静态类handler，用于将网络请求线程里的数据调入主线程，并且使用当前活动的引用防止内存泄漏
    private void initData(JSONObject jsonObject){
        User user=JSON.parseObject(jsonObject.toString(),User.class);
        UserUtil.setUser(user);
    }
    private void getToast(String text)
    {
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }
    private void getUser()
    {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("operation","getUser");
        jsonObject.put("token",TokenUtil.getToken());
        jsonObject.put("name",UserUtil.getUser().getName());
        UserHttp userHttp=new UserHttp(jsonObject);
        userHttp.requestByPost(new PCeditHandler(this));
    }
    private void logout()
    {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("operation","logout");
        jsonObject.put("token", TokenUtil.getToken());
        jsonObject.put("id", UserUtil.getUser().getId());
        UserHttp userHttp=new UserHttp(jsonObject);
        userHttp.requestByPost(new PCeditHandler(this));
    }
    private void reLogin()
    {
        Intent LoginIntent=new Intent(this,LoginActivity.class);
        startActivity(LoginIntent);
    }
}
