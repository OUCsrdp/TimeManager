package com.srdp.admin.time_manager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.srdp.admin.time_manager.R;
import com.srdp.admin.time_manager.http.UserHttp;
import com.srdp.admin.time_manager.util.DensityUtil;
import com.srdp.admin.time_manager.util.TokenUtil;
import com.srdp.admin.time_manager.util.UserUtil;

import java.lang.ref.WeakReference;


public class Person_Infor extends AppCompatActivity {

    private TextView schoolPop;
    private String[] schools={"中国海洋大学","华东师范大学","中国科技大学","中国海洋大学","华南理工大学","南京大学"};
   // private WindowManager wm;
    //private DisplayMetrics dm;//使用这2个参数来获取屏幕宽高
    private String nowSchool="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person__infor);
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }
        initPage();
        //将用户的基本信息显示在页面上
        schoolPop = (TextView) findViewById(R.id.userSchool);
        schoolPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog schoolDialog = new AlertDialog.Builder(Person_Infor.this, R.style.timeAlertDialog).create();
                final View dialogView = LayoutInflater.from(Person_Infor.this)
                        .inflate(R.layout.popschool,null);
                //为弹出层的布局添加xml
                ArrayAdapter<String> adapterSchool=new ArrayAdapter<String>(Person_Infor.this,R.layout.list_school,schools);
                ListView listSchool=(ListView) dialogView.findViewById(R.id.listSchool);
                listSchool.setAdapter(adapterSchool);
                //为listview添加时间
                schoolDialog.setView(dialogView);
                listSchool.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        nowSchool=schools[position];
                        ((TextView)findViewById(R.id.userSchool)).setText(nowSchool);
                        schoolDialog.hide();
                    }
                });
                schoolDialog.show();
                Window dialogWindow = schoolDialog.getWindow();
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                lp.width = DensityUtil.dip2px(Person_Infor.this,200);//宽高可设置具体大小
                lp.height =DensityUtil.dip2px(Person_Infor.this,180);
                schoolDialog.getWindow().setAttributes(lp);
            }
        });
        //弹出选择学校的listview
        final Button finishEdit=(Button)findViewById(R.id.EditPIFinsh);
        finishEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishEdit();
            }
        });
    }
    private static class PeditHandler extends Handler {
        //持有弱引用HandlerActivity,GC回收时会被回收掉.
        private final WeakReference<Person_Infor> mActivty;

        public PeditHandler(Person_Infor activity) {
            mActivty = new WeakReference<Person_Infor>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            Person_Infor activity = mActivty.get();
            super.handleMessage(msg);
            if (activity != null) {
                switch (msg.what) {
                    case 2:
                        Toast.makeText(activity,"修改失败",Toast.LENGTH_SHORT).show();
                        activity.finish();break;
                    case 1:
                        Toast.makeText(activity,"修改成功",Toast.LENGTH_SHORT).show();
                        activity.finish();break;
                    case 0:
                        activity.reLogin();
                        //重新登录
                }
            }
        }
    }
    //静态类handler，用于将网络请求线程里的数据调入主线程，并且使用当前活动的引用防止内存泄漏
    private void initPage(){
        ((TextView)findViewById(R.id.PersonInforName)).setText(UserUtil.getUser().getName());
        ((TextView)findViewById(R.id.PersonInforMajor)).setText(UserUtil.getUser().getMajor());
        ((TextView)findViewById(R.id.PersonInforNumber)).setText(UserUtil.getUser().getNumStu());
        ((TextView)findViewById(R.id.userSchool)).setText(UserUtil.getUser().getSchool());
        ((TextView)findViewById(R.id.PersonInforGpa)).setText(String.valueOf(UserUtil.getUser().getGPA()));
    };
    private void finishEdit(){
        String major=((TextView)findViewById(R.id.PersonInforMajor)).getText().toString();
        String numStu=((TextView)findViewById(R.id.PersonInforNumber)).getText().toString();
        if(major.equals(""))
            major=UserUtil.getUser().getMajor();
        if(numStu.equals(""))
            numStu=UserUtil.getUser().getNumStu();
        if(nowSchool.equals(""))
            nowSchool=UserUtil.getUser().getSchool();
        UserUtil.setUser(nowSchool,major,numStu);
        JSONObject jsonObject=(JSONObject) JSON.toJSON(UserUtil.getUser());
        jsonObject.put("token", TokenUtil.getToken());
        jsonObject.put("operation","infor");
        UserHttp userHttp=new UserHttp(jsonObject);
        userHttp.requestByPost(new PeditHandler(this));
    };
    private void reLogin()
    {
        Intent LoginIntent=new Intent(this,LoginActivity.class);
        startActivity(LoginIntent);
    }
}
