package com.srdp.admin.time_manager.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.app.ActionBar;

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
import com.srdp.admin.time_manager.model.moudle.ExcellentStudentsDistribution;
import com.srdp.admin.time_manager.model.moudle.User;
import com.srdp.admin.time_manager.util.UserUtil;
import com.srdp.admin.time_manager.widget.adapters.ExcellentDistributionListAdapter;
import com.srdp.admin.time_manager.util.HttpUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.forward.androids.views.ScrollPickerView;
import okhttp3.Call;
import okhttp3.Response;
import java.io.IOException;

public class ExcellentDistributionListActivity extends AppCompatActivity {

    List<ExcellentStudentsDistribution> ExcellentDistributionList = new ArrayList<>();

        private SwipeRefreshLayout listSwiplayout;
        private RecyclerView recyclerView;

        private ScrollPickerView select_major;
        private String userMajor;

        private Button choose_btn;
        private Button choose_res;

        private Context list_btn_context = this;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excellent_distribution_list);

        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }
        //获取用户的专业
        userMajor= UserUtil.getUserMajor();
        Log.i("excellMajor",userMajor);
        //按钮筛选
        choose_btn = (Button) findViewById(R.id.choose_btn);
        choose_res = (Button) findViewById(R.id.choose_res);
        recyclerView = (RecyclerView) findViewById(R.id.distribution_list);
        initDistributions(); // 初始化数据
        intoView();//下拉刷新

        //LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
//        ExcellentDistributionListAdapter listAdapter = new ExcellentDistributionListAdapter(ExcellentDistributionList);
//        recyclerView.setAdapter(listAdapter);


        choose_btn.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View v) {

                String res_word = choose_res.getText().toString();
                if(res_word == "不限专业") {
                    choose_res.setText("本专业");
                    initDistributions();
                }
                else if(res_word == "本专业") {
                    initDialog();
                    initDistributions();


//                    builder.setItems(strItems, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            timing_label_name.setText(strItems[i].toString());
//                        }
//                    });
//                    builder.create();
//                    builder.show();
//                    major_search.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            //选择专业
//                            String[] major = {"电子信息工程","物理学","计算机科学与技术","海洋技术","保密管理"};
//                            select_major.setData(Arrays.asList(major));
//                            select_major.setVisibility(View.VISIBLE);
//                            select_major.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    //放置专业选项
//                                    major_search.setText(select_major.getSelectedItem().toString());
//                                    select_major.setVisibility(View.INVISIBLE);
//                                    user.setMajor(select_major.getSelectedItem().toString());
//                                }
//                            });
//                        }
//                    });
                }
                else{
                    choose_res.setText("不限专业");
                    initDistributions();
                }
            }

            });

    };

    //下拉刷新
    private void intoView(){

        listSwiplayout = (SwipeRefreshLayout) findViewById(R.id.listswiplayout);
        //手势监听
        listSwiplayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新监听数据更新在这操作
                if (listSwiplayout.isRefreshing()){//判断是否刷新
//                    mSwiplayout.setRefreshing(false);//关掉刷新
                }
            }
        });

    }

    //搜索专业
    private void initDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(list_btn_context);
        //builder.setView(R.layout.schedule_label_dialog);
        //builder.setView(R.layout.schedule_label_dialog);
        // 获取布局
        View excellent_major_search = View.inflate(ExcellentDistributionListActivity.this, R.layout.excellent_major_search, null);
        // 获取布局中的控件
        final EditText major_search = (EditText) excellent_major_search.findViewById(R.id.major_search);
        builder.setView(excellent_major_search);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        major_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {   // 按下完成按钮，这里和上面imeOptions对应
                    //text.setText("Editing EditorInfo.IME_ACTION_DONE");
                    String major_text = major_search.getText().toString();
                    choose_res.setText(major_text);
                    return false;   //返回true，保留软键盘。false，隐藏软键盘
                }
                return true;
            }
        });



    };


    // 初始化数据
    private void initDistributions() {
        JSONObject test=new JSONObject();
        test.put("idTS",2);
        HttpUtil.request("TimeSharingServlet?method=GetTSById","post",test,new okhttp3.Callback(){
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response){
                try{
                    Log.i("TStest","test");
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e){
                createFail();
            }
        });
//            ExcellentStudentsDistribution apple = new ExcellentStudentsDistribution("君君", R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2018-11-6", 129);
//        ExcellentDistributionList.add(apple);
//            ExcellentStudentsDistribution banana = new ExcellentStudentsDistribution("LIZ", R.drawable.login_icon1, "中国海洋大学", "计算机系", "今天也是元气满满的一天鸭", "2018-11-5", 114);
//        ExcellentDistributionList.add(banana);
//            ExcellentStudentsDistribution orange = new ExcellentStudentsDistribution("栗子栗子", R.drawable.login_icon1, "中国海洋大学", "计算机系", "我觉得今天不错！很不错！非常不错！", "2018-11-5", 105);
//        ExcellentDistributionList.add(orange);
//            ExcellentStudentsDistribution watermelon = new ExcellentStudentsDistribution("蓝胖", R.drawable.login_icon1, "中国海洋大学", "计算机系", "课也太多了叭嘤嘤嘤", "2018-11-5", 101);
//        ExcellentDistributionList.add(watermelon);
//            ExcellentStudentsDistribution pear = new ExcellentStudentsDistribution("Yichen", R.drawable.login_icon1, "中国海洋大学", "计算机系", "写不下去了！想不出来了！啊啊啊！", "2018-11-5", 99);
//        ExcellentDistributionList.add(pear);
//            ExcellentStudentsDistribution grape = new ExcellentStudentsDistribution("Apple", R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2018-11-5", 87);
//        ExcellentDistributionList.add(grape);
//            ExcellentStudentsDistribution pineapple = new ExcellentStudentsDistribution("Apple", R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2018-11-5", 85);
//        ExcellentDistributionList.add(pineapple);
//            ExcellentStudentsDistribution strawberry = new ExcellentStudentsDistribution("Apple", R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2018-11-5", 79);
//        ExcellentDistributionList.add(strawberry);
//            ExcellentStudentsDistribution cherry = new ExcellentStudentsDistribution("Apple", R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2018-11-5", 66);
//        ExcellentDistributionList.add(cherry);
//            ExcellentStudentsDistribution mango = new ExcellentStudentsDistribution("Apple", R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2018-11-5", 54);
//        ExcellentDistributionList.add(mango);



        JSONObject distriObject = new JSONObject();
        distriObject.put("operation","getList");
        if(choose_res.getText().toString().equals("不限专业")){
            distriObject.put("major","all");
        }else if(choose_res.getText().toString().equals("本专业"))
        {
            distriObject.put("major",userMajor);
        }
        else{
            String major_other = choose_res.getText().toString();
            distriObject.put("major",major_other);
        }

        Log.i("distriObject",distriObject.toString());
        HttpUtil.request("ShareTableServlet?method=getList","post",distriObject,new okhttp3.Callback(){
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response){
                try{
                    ExcellentDistributionList.clear();
                    String jsonList=response.body().string();
                    Log.i("jsonList",jsonList);
                    JSONArray resJsonList = JSON.parseObject(jsonList).getJSONArray("jsonArray");
                    Log.i("resJsonList",resJsonList.toString());
                    for(int i = 0; i < resJsonList.size(); i++){
                        JSONObject resJsonItem = resJsonList.getJSONObject(i);
                        String name = resJsonItem.getString("name");
                        //int image = resJsonItem.getIntValue("image");
                        String school = resJsonItem.getString("school");
                        String major = resJsonItem.getString("major");
                        String summary = resJsonItem.getString("summary");
                        String timeShared = resJsonItem.getString("timeShared");
                        int thumbup = resJsonItem.getIntValue("thumbup");
                        ExcellentStudentsDistribution studentTS=new ExcellentStudentsDistribution(name, R.drawable.login_icon1,school,major,summary,timeShared,thumbup);
                        ExcellentDistributionList.add(studentTS);
                        //addEveryTS(studentTS);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ExcellentDistributionListAdapter listAdapter = new ExcellentDistributionListAdapter(ExcellentDistributionList);
                            recyclerView.setAdapter(listAdapter);
                        }
                    });

                } catch(Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e){
                createFail();
            }

        });

    }

    private void addEveryTS(final ExcellentStudentsDistribution studentTS){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ExcellentDistributionList.add(studentTS);
            }
        });
    }
    private void createFail(){
        Toast.makeText(this,"计时失败！",Toast.LENGTH_SHORT).show();
    }
}
