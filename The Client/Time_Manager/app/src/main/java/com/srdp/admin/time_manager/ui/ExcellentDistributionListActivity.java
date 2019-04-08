package com.srdp.admin.time_manager.ui;

<<<<<<< HEAD
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
=======
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

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Adapter;
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
>>>>>>> 8342845777e737e912c6b21b32707e1561d43aea

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

<<<<<<< HEAD
import okhttp3.Call;
import okhttp3.Response;

public class ExcellentDistributionListActivity extends AppCompatActivity {
=======
import cn.forward.androids.views.ScrollPickerView;
import okhttp3.Call;
import okhttp3.Response;
import java.io.IOException;

public class ExcellentDistributionListActivity extends AppCompatActivity {

    private List<ExcellentStudentsDistribution> ExcellentDistributionList = new ArrayList<>();

    private SwipeRefreshLayout listSwiplayout;
    private RecyclerView recyclerView;

    //当前用户的专业
    private String userMajor;
    private String displayMajor;

    private Button choose_btn;
    private Button choose_res;

    private Context list_btn_context = this;

    //选择专业的View和控件
    private View excellent_major_search;
    private ScrollPickerView select_major;
    private EditText major_search;
    private Button search_close_btn;

    //private User user=new User();
    private boolean firstIn=true;
    private ExcellentDistributionListAdapter listAdapter;


>>>>>>> 8342845777e737e912c6b21b32707e1561d43aea

    private List<ExcellentStudentsDistribution> ExcellentDistributionList = new ArrayList<>();
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excellent_distribution_list);

<<<<<<< HEAD
        initDistributions(); // 初始化数据
        recyclerView = (RecyclerView) findViewById(R.id.distribution_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

=======
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }
        //初始化展示全部专业
        displayMajor="all";
        //获取用户的专业
        userMajor= UserUtil.getUserMajor();
        Log.i("excellMajor",userMajor);

        //按钮筛选
        choose_btn = (Button) findViewById(R.id.choose_btn);
        choose_res = (Button) findViewById(R.id.choose_res);
        recyclerView = (RecyclerView) findViewById(R.id.distribution_list);
        //select_major = (ScrollPickerView) findViewById(R.id.select_major);
        initDistributions(); // 初始化数据
        intoView();//下拉刷新

        //LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        listAdapter = new ExcellentDistributionListAdapter(list_btn_context,ExcellentDistributionList);
        recyclerView.setAdapter(listAdapter);


        choose_btn.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View v) {

                String res_word = choose_res.getText().toString();
                if(res_word.equals("不限专业")) {
                    choose_res.setText("本专业");
                    displayMajor=userMajor;
                    initDistributions();
                }
                else if(res_word.equals("本专业")) {
                    initDialog();
                    //弹窗关闭后再请求优秀学生分配表数据
                    //initDistributions();


                    /*builder.setItems(strItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            timing_label_name.setText(strItems[i].toString());
                        }
                    });
                    builder.create();
                    builder.show();
                    major_search.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //选择专业
                            String[] major = {"电子信息工程","物理学","计算机科学与技术","海洋技术","保密管理"};
                            select_major.setData(Arrays.asList(major));
                            select_major.setVisibility(View.VISIBLE);
                            select_major.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //放置专业选项
                                    major_search.setText(select_major.getSelectedItem().toString());
                                    select_major.setVisibility(View.INVISIBLE);
                                    user.setMajor(select_major.getSelectedItem().toString());
                               }
                            });
                        }
                    });*/
                }
                else{
                    choose_res.setText("不限专业");
                    displayMajor="all";
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
>>>>>>> 8342845777e737e912c6b21b32707e1561d43aea

    }

    //搜索专业
    private void initDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(list_btn_context);
        //builder.setView(R.layout.schedule_label_dialog);
        //builder.setView(R.layout.schedule_label_dialog);

        // 获取布局
        excellent_major_search = View.inflate(ExcellentDistributionListActivity.this, R.layout.excellent_major_search, null);
        // 获取布局中的控件
        //关闭按钮
        search_close_btn = (Button)  excellent_major_search.findViewById(R.id.search_close_btn);
        major_search = (EditText) excellent_major_search.findViewById(R.id.major_search);
        select_major=(ScrollPickerView) excellent_major_search.findViewById(R.id.select_major);
        builder.setView(excellent_major_search);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        select_major.setData(null);

        //按键返回
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

        //动态搜索
        //为搜索框内容改变添加事件监听
        //请求的jsonobject示例
        /*{
               "majorKeyword":"电子"
        }*/
        //返回的jsonobject示例
        /*{
               "status":"success"||"fail"||"likedfail"||"unlogin"
               "majorKeyword":"电子",
                "majors":[
                     {"major":"电子信息"},
                     { "major":"电子信息工程"}
                 ]
        }*/
        major_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            // 输入的内容变化的监听
                String majorKeyword=major_search.getText().toString();
                //如果输入内容为空，不发送请求
                if(majorKeyword.equals("")) return;
                JSONObject distriObject=new JSONObject();
                distriObject.put("majorKeyword",majorKeyword);
                HttpUtil.request("ShareTableServlet?method=getgetSearchMajors","post",distriObject,new okhttp3.Callback(){
                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response){
                        try {
                            //保存服务器端响应的jsonstring
                            String jsonString=response.body().string();
                            JSONObject resJson=JSONObject.parseObject(jsonString);
                            //如果不成功则不进行操作
                            if(resJson.getString("status").equals("success"))
                            {
                                final JSONArray jsonArray=resJson.getJSONArray("majors");
                                //如果没有查到匹配专业则不更新动态搜索列表里的专业
                                if(jsonArray.size()>0)
                                {
                                    List<String> majors=new ArrayList<>();
                                    for(int i=0;i<jsonArray.size();i++)
                                    {
                                        majors.add(jsonArray.getJSONObject(i).getString("major"));
                                    }
                                    final List<String> majorsForSelect=majors;
                                    //在UI线程里更新动态搜素列表里的专业
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            select_major.setData(majorsForSelect);
                                        }
                                    });
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e){
                    }
                });
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
            int after) {
            // 输入前的监听
             }
             @Override
             public void afterTextChanged(Editable s) {
                    // 输入后的监听
             }
        });
        //点击编辑搜索专业时使动态搜索框可见
        major_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //选择专业
                // String[] major = {"电子信息工程","物理学","计算机科学与技术","海洋技术","保密管理"};
                //select_major.setData(Arrays.asList(major));
                /*if(major_search.getText().toString().equals(""))
                {
                    List<String> tempmajor=new ArrayList<>();
                    tempmajor.add(userMajor);
                    select_major.setData(tempmajor);
                }*/
                select_major.setVisibility(View.VISIBLE);
                select_major.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //放置专业选项
                        major_search.setText(select_major.getSelectedItem().toString());
                        select_major.setVisibility(View.INVISIBLE);
                        //user.setMajor(select_major.getSelectedItem().toString());

                                }
                            });
                        }
                    });
        //关闭弹窗
        search_close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String major_text = major_search.getText().toString();
                //设置筛选条件为该专业，并且修改displaymajor的值
                choose_res.setText(major_text);
                displayMajor=major_text;
                initDistributions();
                alertDialog.hide();

            }
        });


    };


    // 初始化数据
    private void initDistributions() {
<<<<<<< HEAD
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
=======
//
////            ExcellentStudentsDistribution apple = new ExcellentStudentsDistribution("君君",01, R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2018-11-6", 129,01,02);
////        ExcellentDistributionList.add(apple);
////            ExcellentStudentsDistribution banana = new ExcellentStudentsDistribution("LIZ",02, R.drawable.login_icon1, "中国海洋大学", "计算机系", "今天也是元气满满的一天鸭", "2018-11-5", 114,01,02);
////        ExcellentDistributionList.add(banana);
////            ExcellentStudentsDistribution orange = new ExcellentStudentsDistribution("栗子栗子",03, R.drawable.login_icon1, "中国海洋大学", "计算机系", "我觉得今天不错！很不错！非常不错！", "2018-11-5", 105,01,02);
////        ExcellentDistributionList.add(orange);
////            ExcellentStudentsDistribution watermelon = new ExcellentStudentsDistribution("蓝胖",04, R.drawable.login_icon1, "中国海洋大学", "计算机系", "课也太多了叭嘤嘤嘤", "2018-11-5", 101,01,02);
////        ExcellentDistributionList.add(watermelon);
////            ExcellentStudentsDistribution pear = new ExcellentStudentsDistribution("Yichen",05, R.drawable.login_icon1, "中国海洋大学", "计算机系", "写不下去了！想不出来了！啊啊啊！", "2018-11-5", 99,01,02);
////        ExcellentDistributionList.add(pear);
////            ExcellentStudentsDistribution grape = new ExcellentStudentsDistribution("Apple",06, R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2018-11-5", 87,01,02);
////        ExcellentDistributionList.add(grape);
////            ExcellentStudentsDistribution pineapple = new ExcellentStudentsDistribution("Apple", 06,R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2018-11-5", 85,01,02);
////        ExcellentDistributionList.add(pineapple);
////            ExcellentStudentsDistribution strawberry = new ExcellentStudentsDistribution("Apple", 06,R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2018-11-5", 79,01,02);
////        ExcellentDistributionList.add(strawberry);
////            ExcellentStudentsDistribution cherry = new ExcellentStudentsDistribution("Apple", 06,R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2018-11-5", 66,01,02);
////        ExcellentDistributionList.add(cherry);
////            ExcellentStudentsDistribution mango = new ExcellentStudentsDistribution("Apple",06, R.drawable.login_icon1, "中国海洋大学", "计算机系", "新出炉的时间分配表，请君共赏，现在二十个字了", "2018-11-5", 54,01,02);
////        ExcellentDistributionList.add(mango);
//
//
//
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
                           /* ExcellentDistributionListAdapter listAdapter = new ExcellentDistributionListAdapter(list_btn_context,ExcellentDistributionList);

                            if(firstIn) {
                                firstIn = false;
                                recyclerView.setAdapter(listAdapter);
                            }
                            else*/
                                listAdapter.notifyDataSetChanged();
                        }
                    });

>>>>>>> 8342845777e737e912c6b21b32707e1561d43aea
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
<<<<<<< HEAD
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e){
                createFail();
            }

        });
=======
                        @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e){
               //createFail();
            }

        });

>>>>>>> 8342845777e737e912c6b21b32707e1561d43aea
    }

    private void addEveryTS(final ExcellentStudentsDistribution studentTS){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ExcellentDistributionList.add(studentTS);
            }
        });
<<<<<<< HEAD
    }
    private void createFail(){
        Toast.makeText(this,"计时失败！",Toast.LENGTH_SHORT).show();
=======
>>>>>>> 8342845777e737e912c6b21b32707e1561d43aea
    }
}
