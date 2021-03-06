package com.srdp.admin.time_manager.ui;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import com.srdp.admin.time_manager.R;
import com.srdp.admin.time_manager.model.moudle.ExcellentStudentsDistribution;
import com.srdp.admin.time_manager.widget.adapters.ExcellentDistributionListAdapter;
import com.srdp.admin.time_manager.http.UserHttp;
import com.srdp.admin.time_manager.util.HttpUtil;
import com.srdp.admin.time_manager.util.TokenUtil;
import com.srdp.admin.time_manager.util.UserUtil;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.Date;

public class ExcellentDistributionListActivity extends AppCompatActivity {

    List<ExcellentStudentsDistribution> ExcellentDistributionList = new ArrayList<>();

    private SwipeRefreshLayout listSwiplayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excellent_distribution_list);

        initDistributions(); // 初始化数据
        intoView();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.distribution_list);
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        ExcellentDistributionListAdapter listAdapter = new ExcellentDistributionListAdapter(ExcellentDistributionList);
        recyclerView.setAdapter(listAdapter);


    }

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

    public void initDistributions() {

//            ExcellentStudentsDistribution apple = new ExcellentStudentsDistribution("Apple", R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2017-9-6", 129);
//        ExcellentDistributionList.add(apple);
//            ExcellentStudentsDistribution banana = new ExcellentStudentsDistribution("Apple", R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2017-9-6", 129);
//        ExcellentDistributionList.add(banana);
//            ExcellentStudentsDistribution orange = new ExcellentStudentsDistribution("Apple", R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2017-9-6", 129);
//        ExcellentDistributionList.add(orange);
//            ExcellentStudentsDistribution watermelon = new ExcellentStudentsDistribution("Apple", R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2017-9-6", 129);
//        ExcellentDistributionList.add(watermelon);
//            ExcellentStudentsDistribution pear = new ExcellentStudentsDistribution("Apple", R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2017-9-6", 129);
//        ExcellentDistributionList.add(pear);
//            ExcellentStudentsDistribution grape = new ExcellentStudentsDistribution("Apple", R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2017-9-6", 129);
//        ExcellentDistributionList.add(grape);
//            ExcellentStudentsDistribution pineapple = new ExcellentStudentsDistribution("Apple", R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2017-9-6", 129);
//        ExcellentDistributionList.add(pineapple);
//            ExcellentStudentsDistribution strawberry = new ExcellentStudentsDistribution("Apple", R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2017-9-6", 129);
//        ExcellentDistributionList.add(strawberry);
//            ExcellentStudentsDistribution cherry = new ExcellentStudentsDistribution("Apple", R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2017-9-6", 129);
//        ExcellentDistributionList.add(cherry);
//            ExcellentStudentsDistribution mango = new ExcellentStudentsDistribution("Apple", R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2017-9-6", 129);
//        ExcellentDistributionList.add(mango);

        JSONObject distriObject = new JSONObject();
        distriObject.put("operation","getList");
        distriObject.put("major","all");
        Log.i("distriObject",distriObject.toString());
        HttpUtil.request("ShareTableServlet?method=getList","post",distriObject,new okhttp3.Callback(){
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response){
                try{
                    String jsonList=response.body().string();
                    Log.i("jsonList",jsonList);
                    JSONArray resJsonList = JSON.parseArray(jsonList);
                    Log.i("resJsonList",resJsonList.toString());
                    for(int i = 0; i < resJsonList.size(); i++){
                        JSONObject resJsonItem = resJsonList.getJSONObject(i);
                        String name = resJsonItem.getString("name");
                        int image = resJsonItem.getIntValue("image");
                        String school = resJsonItem.getString("school");
                        String major = resJsonItem.getString("major");
                        String summary = resJsonItem.getString("summary");
                        String timeShared = resJsonItem.getString("timeShared");
                        int thumbup = resJsonItem.getIntValue("thumbup");
                        ExcellentDistributionList.add(new ExcellentStudentsDistribution(name,image,school,major,summary,timeShared,thumbup));
                    }

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

    private void createFail()
    {
        Toast.makeText(this,"获取失败！",Toast.LENGTH_SHORT).show();
    }
}
