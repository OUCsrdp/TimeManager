package com.srdp.admin.time_manager.ui;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.srdp.admin.time_manager.R;
import com.srdp.admin.time_manager.model.moudle.ExcellentStudentsDistribution;
import com.srdp.admin.time_manager.util.HttpUtil;
import com.srdp.admin.time_manager.widget.adapters.ExcellentDistributionListAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class ExcellentDistributionListActivity extends AppCompatActivity {

    private List<ExcellentStudentsDistribution> ExcellentDistributionList = new ArrayList<>();
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excellent_distribution_list);

        initDistributions(); // 初始化数据
        recyclerView = (RecyclerView) findViewById(R.id.distribution_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


    }

    private void initDistributions() {
        /*    ExcellentStudentsDistribution apple = new ExcellentStudentsDistribution("Apple", R.drawable.login_icon1, "中国海洋大学", "计算机系", "所以bug是解决了么？我希望是的呀", "2017-9-6", 129);
        ExcellentDistributionList.add(apple);
            ExcellentStudentsDistribution banana = new ExcellentStudentsDistribution("Apple", R.drawable.login_icon1, "中国海洋大学", "计算机系", "原来的viewHolder构造函数有问题呀", "2017-9-6", 129);
        ExcellentDistributionList.add(banana);
            ExcellentStudentsDistribution orange = new ExcellentStudentsDistribution("Apple", R.drawable.login_icon1, "中国海洋大学", "计算机系", "String distrName = ......把开头的String 去掉", "2017-9-6", 129);
        ExcellentDistributionList.add(orange);
            ExcellentStudentsDistribution watermelon = new ExcellentStudentsDistribution("Apple", R.drawable.login_icon1, "中国海洋大学", "计算机系", "皮这一下很开心", "2017-9-6", 129);
        ExcellentDistributionList.add(watermelon);
            ExcellentStudentsDistribution pear = new ExcellentStudentsDistribution("Apple", R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2017-9-6", 129);
        ExcellentDistributionList.add(pear);
            ExcellentStudentsDistribution grape = new ExcellentStudentsDistribution("Apple", R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2017-9-6", 129);
        ExcellentDistributionList.add(grape);
            ExcellentStudentsDistribution pineapple = new ExcellentStudentsDistribution("Apple", R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2017-9-6", 129);
        ExcellentDistributionList.add(pineapple);
            ExcellentStudentsDistribution strawberry = new ExcellentStudentsDistribution("Apple", R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2017-9-6", 129);
        ExcellentDistributionList.add(strawberry);
            ExcellentStudentsDistribution cherry = new ExcellentStudentsDistribution("Apple", R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2017-9-6", 129);
        ExcellentDistributionList.add(cherry);
            ExcellentStudentsDistribution mango = new ExcellentStudentsDistribution("Apple", R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2017-9-6", 129);
        ExcellentDistributionList.add(mango);*/

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