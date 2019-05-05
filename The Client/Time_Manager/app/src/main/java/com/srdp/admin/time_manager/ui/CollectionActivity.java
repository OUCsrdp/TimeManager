package com.srdp.admin.time_manager.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.srdp.admin.time_manager.R;
import com.srdp.admin.time_manager.model.moudle.ExcellentStudentsDistribution;
import com.srdp.admin.time_manager.util.HttpUtil;
import com.srdp.admin.time_manager.util.UserUtil;
import com.srdp.admin.time_manager.widget.adapters.ExcellentDistributionListAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class CollectionActivity extends AppCompatActivity {

    private List<ExcellentStudentsDistribution> ExcellentDistributionList = new ArrayList<>();
    private ExcellentDistributionListAdapter listAdapter;

    private TextView content_school;//自己的学校
    private TextView content_major;//自己的专业
    private ImageView content_icon;//自己的头像
    private TextView content_name;//自己的昵称

    private String userMajor;
    private String userName;
    private String userImage;
    private String userSchool;
    private String displayMajor;

    private Context list_btn_context = this;

    private SwipeRefreshLayout listSwiplayout;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }

        content_school = (TextView)findViewById(R.id.content_school);
        content_major = (TextView)findViewById(R.id.content_major);
        content_icon = (ImageView)findViewById(R.id.content_icon);
        content_name = (TextView)findViewById(R.id.content_name);

        //初始化展示全部专业
        displayMajor="all";
        //获取用户的专业
        userMajor = UserUtil.getUserMajor();
        userName = UserUtil.getUser().getName();
        userImage = UserUtil.getUser().getImage();
        userSchool = UserUtil.getUser().getSchool();
        content_school.setText(userSchool);
        content_name.setText(userName);
        content_major.setText(userMajor);

        Log.i("excellMajor",userMajor);
        recyclerView = (RecyclerView) findViewById(R.id.distribution_list);
        initDistributions(); // 初始化数据
        intoView();//下拉刷新

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        listAdapter = new ExcellentDistributionListAdapter(list_btn_context,ExcellentDistributionList);
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

    // 初始化数据
    private void initDistributions() {

//            ExcellentStudentsDistribution apple = new ExcellentStudentsDistribution("君君",01, R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2018-11-6", 129,01,02);
//        ExcellentDistributionList.add(apple);
//            ExcellentStudentsDistribution banana = new ExcellentStudentsDistribution("LIZ",02, R.drawable.login_icon1, "中国海洋大学", "计算机系", "今天也是元气满满的一天鸭", "2018-11-5", 114,01,02);
//        ExcellentDistributionList.add(banana);
//            ExcellentStudentsDistribution orange = new ExcellentStudentsDistribution("栗子栗子",03, R.drawable.login_icon1, "中国海洋大学", "计算机系", "我觉得今天不错！很不错！非常不错！", "2018-11-5", 105,01,02);
//        ExcellentDistributionList.add(orange);
//            ExcellentStudentsDistribution watermelon = new ExcellentStudentsDistribution("蓝胖",04, R.drawable.login_icon1, "中国海洋大学", "计算机系", "课也太多了叭嘤嘤嘤", "2018-11-5", 101,01,02);
//        ExcellentDistributionList.add(watermelon);
//            ExcellentStudentsDistribution pear = new ExcellentStudentsDistribution("Yichen",05, R.drawable.login_icon1, "中国海洋大学", "计算机系", "写不下去了！想不出来了！啊啊啊！", "2018-11-5", 99,01,02);
//        ExcellentDistributionList.add(pear);
//            ExcellentStudentsDistribution grape = new ExcellentStudentsDistribution("Apple",06, R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2018-11-5", 87,01,02);
//        ExcellentDistributionList.add(grape);
//            ExcellentStudentsDistribution pineapple = new ExcellentStudentsDistribution("Apple", 06,R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2018-11-5", 85,01,02);
//        ExcellentDistributionList.add(pineapple);
//            ExcellentStudentsDistribution strawberry = new ExcellentStudentsDistribution("Apple", 06,R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2018-11-5", 79,01,02);
//        ExcellentDistributionList.add(strawberry);
//            ExcellentStudentsDistribution cherry = new ExcellentStudentsDistribution("Apple", 06,R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2018-11-5", 66,01,02);
//        ExcellentDistributionList.add(cherry);
//            ExcellentStudentsDistribution mango = new ExcellentStudentsDistribution("Apple",06, R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2018-11-5", 54,01,02);
//        ExcellentDistributionList.add(mango);




        //每次更新前清除列表
        ExcellentDistributionList.clear();
        JSONObject distriObject = new JSONObject();
        distriObject.put("operation", "getList");
        distriObject.put("major",displayMajor);
        /*//根据用户选择的专业改变请求的jsonObject的参数值
        if (choose_res.getText().toString().equals("不限专业")) {
            distriObject.put("major", "all");
        } else if (choose_res.getText().toString().equals("本专业")) {
            String major_mine = userMajor;
            distriObject.put("major", major_mine);
        } else {
            String major_other = choose_res.getText().toString();
            distriObject.put("major", major_other);
        }*/

        //请求的jsonObject示例
        /*{
            "operation":"getList"
            "major":
            //Major可能为某个专业名称,all表示不限专业
        }*/
        //响应的jsonobject示例
        /*{
            "status":"success" || "fail" || "unlogin",
                "jsonArray":
                [{
                “name”:”爱吃肉的兔子”,
                “userId”://分享的时间分配表用户id
                “image”://头像地址
                “school”:”中国海洋大学”,
                “major”:”计算机系”,
                “summary”:”今天超超级充实的”,
                “timeShared”:”2017 年5月4日”,//时间就这种格式吧
                “thumbup”:333
                “idTS”://时间分配表的id
                “idST”://已分享的时间分配表的id
                },
                {
                “name”:”爱吃肉的兔子”,
                “userId”://分享的时间分配表用户id
                “image”://头像地址
                “school”:”中国海洋大学”,
                “major”:”计算机系”,
                “summary”:”又是我”,
                “timeShared”:”2017 年5月5日”,//时间就这种格式吧
                “thumbup”:233
                },
                ]}
        }    */
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
                        int userId = resJsonItem.getIntValue("userId");
                        //int image = resJsonItem.getIntValue("image");
                        String school = resJsonItem.getString("school");
                        String major = resJsonItem.getString("major");
                        String summary = resJsonItem.getString("summary");
                        String timeShared = resJsonItem.getString("timeShared");
                        int thumbup = resJsonItem.getIntValue("thumbup");
                        int idTS = resJsonItem.getIntValue("idTS");
                        int idST = resJsonItem.getIntValue("idST");
                        //页面列表中添加该优秀学生时间分配表
                        ExcellentStudentsDistribution studentTS=new ExcellentStudentsDistribution(name, userId, R.drawable.login_icon1,school,major,summary,timeShared,thumbup, idTS, idST);
                        ExcellentDistributionList.add(studentTS);
                        //addEveryTS(studentTS);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listAdapter.notifyDataSetChanged();
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

    private void addEveryTS(final ExcellentStudentsDistribution studentTS){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ExcellentDistributionList.add(studentTS);
            }
        });
    }
}
