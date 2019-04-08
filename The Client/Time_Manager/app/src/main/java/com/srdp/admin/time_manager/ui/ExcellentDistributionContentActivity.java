package com.srdp.admin.time_manager.ui;

<<<<<<< HEAD
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
=======
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.srdp.admin.time_manager.R;
import com.srdp.admin.time_manager.model.moudle.ExcellentContentMajors;
import com.srdp.admin.time_manager.model.moudle.ExcellentStudentsDistribution;
import com.srdp.admin.time_manager.model.moudle.JsonBean.ExcellentIntent;
import com.srdp.admin.time_manager.model.moudle.User;
import com.srdp.admin.time_manager.util.UserUtil;
import com.srdp.admin.time_manager.widget.adapters.ExcellentContentMajorsAdapter;
import com.srdp.admin.time_manager.widget.adapters.ExcellentDistributionListAdapter;
import com.srdp.admin.time_manager.util.HttpUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.forward.androids.views.ScrollPickerView;
import okhttp3.Call;
import okhttp3.Response;
import java.io.IOException;
>>>>>>> 8342845777e737e912c6b21b32707e1561d43aea

/*import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.behaviour.BottomNavBarFabBehaviour;
import com.ashokvarma.bottomnavigation.behaviour.BottomVerticalScrollBehavior;
import com.ashokvarma.bottomnavigation.utils.Utils;*/
import com.srdp.admin.time_manager.R;

import org.w3c.dom.Text;

import static com.alibaba.fastjson.JSON.parseObject;

public class ExcellentDistributionContentActivity extends AppCompatActivity {

<<<<<<< HEAD
    //private BottomNavigationBar mbottomNavigationBar;
=======
    private LinearLayout like_btn;//点赞
    private LinearLayout collect_btn;//收藏
    private ImageView like_img;//点赞图标
    private ImageView collect_img;//收藏图标

    private RecyclerView recyclerView;


    private TextView table_date;//分配表日期
    private TextView table_summary;//分配表总结
    private TextView content_school;//该学生学校
    private TextView content_major;//该学生专业
    private TextView content_name;//该学生昵称
    private ImageView content_icon;//该学生头像

    private ExcellentIntent excellentIntent;//储存该优秀学生姓名，学号等基本信息的对象

    List<ExcellentContentMajors> ExcellentContentMajorsList = new ArrayList<>();
    private String intent_date;
    private String intent_summary;
    private String intent_school;
    private String intent_major;
    private String intent_name;

    private String likeStatus;
    private String collectStatus;

    private boolean hasLike=false;
    private boolean hasCollect=false;



>>>>>>> 8342845777e737e912c6b21b32707e1561d43aea

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excellent_distribution_content);

<<<<<<< HEAD
        //底部按钮
        //mbottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        //mbottomNavigationBar.setActiveColor("#e6992f") //设置选中的颜色
//                .setInActiveColor("#e6992f")
//                .addItem(new BottomNavigationItem(R.drawable.like_btn, "赞"))
//                .addItem(new BottomNavigationItem(R.drawable.collect_btn, "收藏"))
//                .initialise();//所有的设置需在调用该方法前完成
=======
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }

        recyclerView = (RecyclerView) findViewById(R.id.task_list);
        table_date = (TextView)findViewById(R.id.table_date);
        table_summary = (TextView)findViewById(R.id.table_summary);//分配表总结
        content_school = (TextView)findViewById(R.id.content_school);//该学生学校
        content_major = (TextView)findViewById(R.id.content_major);//该学生专业
        content_name = (TextView)findViewById(R.id.content_name);//该学生昵称
        content_icon = (ImageView)findViewById(R.id.content_icon);//该学生头像

        like_btn = (LinearLayout)findViewById(R.id.like_btn);//点赞
        collect_btn = (LinearLayout)findViewById(R.id.collect_btn);//收藏
        like_img = (ImageView)findViewById(R.id.like_img);//点赞图标
        collect_img = (ImageView)findViewById(R.id.collect_img);//收藏图标


        initBasicInfor();
        requestDetails();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        like_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!hasLike)
                    thumbUp();
            }
        });
        collect_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!hasCollect)
                    collect();
            }
        });
    }
    //从list页面通过Intent传来的数据中获取基本消息并且显示在页面上
    private void initBasicInfor()
    {
       Intent intent=getIntent();
        String excelIntentString=intent.getStringExtra("BaseInforExcel");
        Log.i("excelIntentContent",excelIntentString);
        excellentIntent= parseObject(excelIntentString,ExcellentIntent.class);
        intent_name = excellentIntent.getName().toString();
        intent_summary = excellentIntent.getSummary().toString();
        intent_school = excellentIntent.getSchool().toString();
        intent_major = excellentIntent.getMajor().toString();
    }
    //请求页面详情信息并显示在页面上
    private void requestDetails()
    {
        int idTS=excellentIntent.getIdTS();
        JSONObject distriObject=new JSONObject();
        distriObject.put("idTS",idTS);
         HttpUtil.request("TimeSharingServlet?method=GetTSById","post",distriObject,new okhttp3.Callback(){
                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response){
                        try{
                            String jsonContent=response.body().string();
                            Log.i("jsonContent",jsonContent);
                            intent_date = parseObject(jsonContent).getString("date");
                            JSONArray resJsonContent = parseObject(jsonContent).getJSONArray("affairs");//获取json数组
                            Log.i("resJsonContent",resJsonContent.toString());
                            for(int i = 0; i < resJsonContent.size(); i++){
                                JSONObject resJsonItem = resJsonContent.getJSONObject(i);
                                String name = resJsonItem.getString("name");
                                int idLabel = resJsonItem.getIntValue("idLabel");
                                String timeStart = resJsonItem.getString("timeStart");
                                String timeEnd = resJsonItem.getString("timeEnd");
                                ExcellentContentMajors studentTS=new ExcellentContentMajors(name, idLabel, timeStart, timeEnd);
                                ExcellentContentMajorsList.add(studentTS);
                            };
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    table_date.setText(intent_date);
                                    table_summary.setText(intent_summary);
                                    content_name.setText(intent_name);
                                    content_major.setText(intent_major);
                                    content_school.setText(intent_school);
                                    ExcellentContentMajorsAdapter contentAdapter = new ExcellentContentMajorsAdapter(ExcellentContentMajorsList);
                                    recyclerView.setAdapter(contentAdapter);
                                }
                            });
                        } catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e){
                        //createFail();
                    }
                });
    }
    private void thumbUp(){
        int idST=excellentIntent.getIdST();
        JSONObject distriObject=new JSONObject();
        distriObject.put("idST",idST);
        distriObject.put("userId",UserUtil.getUserId());
        HttpUtil.request("ShareTableServlet?method=like","post",distriObject,new okhttp3.Callback(){
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response){
                try{
                    String jsonContentLike=response.body().string();
                    likeStatus = JSON.parseObject(jsonContentLike).getString("status");
                    if(likeStatus.equals("success"))
                    {
                        hasLike=true;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                like_img.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.like_button));
                            }
                        });
                    }

                } catch(Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e){
                //createFail();
            }
        });
    }
    private void collect(){
        int idST=excellentIntent.getIdST();
        JSONObject distriObject=new JSONObject();
        distriObject.put("idST",idST);
        distriObject.put("userId",UserUtil.getUserId());
        HttpUtil.request("ShareTableServlet?method=collect","post",distriObject,new okhttp3.Callback(){
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response){
                try{
                    String jsonContentCollect = response.body().string();
                    collectStatus = JSON.parseObject(jsonContentCollect).getString("status");
                    if(collectStatus.equals("success")){
                        hasCollect=true;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                collect_img.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.collect_button));
                            }
                        });
                    }
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e){
                //createFail();
            }
        });
>>>>>>> 8342845777e737e912c6b21b32707e1561d43aea
    }

}
