package com.srdp.admin.time_manager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.srdp.admin.time_manager.R;
import com.srdp.admin.time_manager.http.AffairHttp;
import com.srdp.admin.time_manager.model.moudle.Affair;
import com.srdp.admin.time_manager.model.moudle.S_Affair;
import com.srdp.admin.time_manager.util.DensityUtil;
import com.srdp.admin.time_manager.util.TimeUtil;
import com.srdp.admin.time_manager.util.TokenUtil;
import com.srdp.admin.time_manager.util.UserUtil;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Date;

public class Timesharing_edit extends AppCompatActivity {
    private LinearLayout allSatisfyLayout;
    private String[] allHours=new String[24];
    private String[] allMinutes=new String[60];
    private Affair nowAffair;
    private S_Affair nowSAffair;
    private String startHour="00";
    private String startMin="00";
    private String endHour="00";
    private String endMin="00";
    private int theIndex=0;//表示满意度
    private String date;
    private Intent intent;
    private int nowid=0;//现在使用的affair或者saffair的id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timesharing_edit);
        intent=getIntent();
        //获取timelsharing传送过来的数据
        initData();
        EditText editTips=(EditText)findViewById(R.id.EditTips);
        EditText editName=(EditText)findViewById(R.id.EditName);
        //把备注的光标移到文字的最后
        if(intent.getStringExtra("type").equals("edit"))
        {
            initPage();
            //editName.setFocusable(false);
        }
        else
        {
            ((TextView)findViewById(R.id.editStartTime)).setText("00:00");
            ((TextView)findViewById(R.id.editEndTime)).setText("00:00");
        }
        for(int i=0;i<24;i++)
        {
            String str = String.format("%2d",i).replace(" ", "0");
            allHours[i]=str;
        }
        for(int i=0;i<60;i++)
        {
            String str = String.format("%2d",i).replace(" ", "0");
            allMinutes[i]=str;
        }
        //为弹窗的listview设置时间
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }
        //隐藏原来的actionBar
        Switch theSwitch=(Switch) findViewById(R.id.turnSatisfy);
        allSatisfyLayout=(LinearLayout) findViewById(R.id.allSatisy);
        theSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    allSatisfyLayout.setVisibility(View.VISIBLE);
                } else {
                    allSatisfyLayout.setVisibility(View.INVISIBLE);
                }

            }
        });
        // 根据开关的变化显示或者隐藏满意度选择
        Button deleleAffair=(Button)findViewById(R.id.EditTSDelete);
        Button finishEdit=(Button)findViewById(R.id.EditTSFinish);
        deleleAffair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAffair();
            }
        });
        //删除事件
        finishEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit();
            }
        });
    }

    private static class TSeditHandler extends Handler {
        //持有弱引用HandlerActivity,GC回收时会被回收掉.
        private final WeakReference<Timesharing_edit> mActivty;

        public TSeditHandler(Timesharing_edit activity) {
            mActivty = new WeakReference<Timesharing_edit>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            Timesharing_edit activity = mActivty.get();
            super.handleMessage(msg);
            if (activity != null) {
                switch (msg.what) {
                    case 3:
                        Log.i("affairhandler","deletesuccess");
                        activity.backToTs();break;
                        //表示删除成功
                    case 2:
                        //表示失败
                    case 1:
                        Log.i("affairhandler","createsuccess");
                        activity.backToTs();break;
                        //表示创建或者编辑事件成功
                    case 0:
                        activity.reLogin();break;
                        //表示身份信息失效，要重新登录
                }
            }
        }
    }
    //静态类handler，用于将网络请求线程里的数据调入主线程，并且使用当前活动的引用防止内存泄漏
    private void initData()
    {
        //Calendar calendar= Calendar.getInstance();
        //calendar.setTime(new Date());
        //date=calendar.get(Calendar.YEAR)+"年"+String.valueOf(calendar.get(Calendar.MONTH)+1)+"月"+calendar.get(Calendar.DAY_OF_MONTH) +"日";
        date=intent.getStringExtra("date");
        TextView editStart=(TextView)findViewById(R.id.editStartTime);
        TextView editEnd=(TextView)findViewById(R.id.editEndTime);
        editStart.setText(date);
        editEnd.setText(date);
    }
    //如果是修改页面的话，先初始化页面，让用户看到该事件的基本信息
    private void initPage(){
        Affair originAffair=null;
        JSONObject jaffiar;
        if(intent.getStringExtra("affairType").equals("affair"))
        {
            jaffiar=JSONObject.parseObject(intent.getStringExtra("affair"));
            nowAffair=new Affair(jaffiar.getIntValue("id"),jaffiar.getIntValue("idTS"),jaffiar.getIntValue("idLabel"),
                    jaffiar.getIntValue("satisfaction"),jaffiar.getString("name"),jaffiar.getString("tips"),
                    jaffiar.getString("timeStart"),jaffiar.getString("timeEnd"),jaffiar.getString("timeEndPlan"));
            originAffair=nowAffair;
        }
        else
        {
            jaffiar=JSONObject.parseObject(intent.getStringExtra("saffair"));
            Log.i("intentsclassbefore","success");
            nowSAffair=new S_Affair(jaffiar.getIntValue("id"),jaffiar.getIntValue("idTS"),jaffiar.getIntValue("idS"),
                    jaffiar.getIntValue("idLabel"),jaffiar.getIntValue("satisfaction"),jaffiar.getString("isImportant"),
                    jaffiar.getString("name"),jaffiar.getString("tips"),
                    jaffiar.getString("timeStart"),jaffiar.getString("timeEnd"),jaffiar.getString("timeStartPlan"),
                    jaffiar.getString("timeEndPlan"),jaffiar.getString("timeStartAlarm"),jaffiar.getString("timeEndAlarm"));
            Log.i("intentsclass","success");
            originAffair=(Affair)nowSAffair;
        }
        //初始化页面的affair,saffair信息
        startHour=originAffair.getTimeStart().split(":")[0];
        startMin=originAffair.getTimeStart().split(":")[1];
        endHour=originAffair.getTimeEnd().split(":")[0];
        endMin=originAffair.getTimeEnd().split(":")[1];
        //初始化时间
        Log.i("intentaffair",originAffair.getName());
        ((TextView)findViewById(R.id.EditName)).setText(originAffair.getName());
        ((TextView)findViewById(R.id.editStartTime)).setText(originAffair.getTimeStart());
        ((TextView)findViewById(R.id.editEndTime)).setText(originAffair.getTimeEnd());
        ((EditText)findViewById(R.id.EditTips)).setText(originAffair.getTips());
        nowid=originAffair.getId();
        //给页面的控件设置值
    }
    private void deleteAffair() {
        JSONObject jsonObject = new JSONObject();
        if (intent.getStringExtra("type").equals("edit")) {
            if (intent.getStringExtra("affairType").equals("affair")) {
                jsonObject.put("sign2", 1);
            } else
                jsonObject.put("sign2", 0);
            //1表示affair,0表示saffair
            jsonObject.put("token", TokenUtil.getToken());
            jsonObject.put("sign1", -1);
            //1表示修改，0表示创建，-1表示删除
            jsonObject.put("id", nowid);
        } else
            finish();
        //如果是创建页面直接不保存返回时间分配表
        AffairHttp affairHttp=new AffairHttp(jsonObject);
        affairHttp.requestByPost(new TSeditHandler(this));
    }
    private void reLogin()
    {
//        Intent intent =new Intent(assign_table.this,.class);
//        startActivity(intent);
    }
    private void backToTs()
    {
        Intent intent =new Intent(Timesharing_edit.this,assign_table.class);
        startActivity(intent);
    }
    private void edit()
    {
        String timeStart=startHour+":"+startMin;
        String timeEnd=endHour+":"+endMin;
        Log.i("intenttime",timeStart+" "+timeEnd);
        JSONObject jsonObject=null;
        if(TimeUtil.compareOnlyTime(timeStart,timeEnd)==0)
        {
            Toast.makeText(this, "开始时间不能晚于结束时间!", Toast.LENGTH_SHORT).show();
            return;
        }
        String name=((TextView)findViewById(R.id.EditName)).getText().toString();
        String tips=((EditText)findViewById(R.id.EditTips)).getText().toString();
        if(intent.getStringExtra("type").equals("create"))
        {
            int idTs=intent.getIntExtra("idTS",0);
            nowAffair=new Affair(0,idTs,0,theIndex,name,tips,timeStart,timeEnd,null);
            jsonObject=(JSONObject)JSON.toJSON(nowAffair);
            jsonObject.put("sign1",0);
            jsonObject.put("sign2",1);
            jsonObject.put("token", TokenUtil.getToken());
            //设置token，sign1 1表示修改 0表示创建 sign21表示affair 0表示saffair
        }
        else if(intent.getStringExtra("affairType").equals("affair"))
        {
            nowAffair.setSatisfaction(theIndex);
            nowAffair.setName(name);
            nowAffair.setTips(tips);
            nowAffair.setTimeStart(timeStart);
            nowAffair.setTimeEnd(timeEnd);
            jsonObject=(JSONObject)JSON.toJSON(nowAffair);
            jsonObject.put("sign1",1);
            jsonObject.put("sign2",1);
        }
        else{
            nowSAffair.setSatisfaction(theIndex);
            nowSAffair.setName(name);
            nowSAffair.setTips(tips);
            nowSAffair.setTimeStart(timeStart);
            nowSAffair.setTimeEnd(timeEnd);
            jsonObject=(JSONObject)JSON.toJSON(nowSAffair);
            Log.i("changesaffair",jsonObject.toString());
            jsonObject.put("sign1",1);
            jsonObject.put("sign2",0);
        }
        jsonObject.put("token", TokenUtil.getToken());
        jsonObject.put("date",intent.getStringExtra("date"));
        jsonObject.put("username",UserUtil.getUser().getName());
        AffairHttp affairHttp=new AffairHttp(jsonObject);
        Log.i("affairedit",jsonObject.toString());
        affairHttp.requestByPost(new TSeditHandler(this));
    }
    //编辑时间分配表事件时获取事件类转化成jsonObject
    public void setSatisfy(View view)
    {
        switch(view.getId())
        {
            case R.id.satisfy1:
                theIndex=1;break;
            case R.id.satisfy2:
                theIndex=2;break;
            case R.id.satisfy3:
                theIndex=3;break;
            case R.id.satisfy4:
                theIndex=4;break;
            case R.id.satisfy5:
                theIndex=5;break;
        }
        ViewGroup allSatisfy=(ViewGroup) findViewById(R.id.allSatisy);
        //Drawable minipoint = getResources().getDrawable(R.drawable.minipoint);
        for(int i=0;i<theIndex;i++)
        {
            ImageView theImage=(ImageView) allSatisfy.getChildAt(i);
            theImage.setImageResource(R.drawable.minipoint);
            //theImage.setImageDrawable(minipoint);
        }
        for(int i=theIndex;i<5;i++)
        {
            ImageView theImage=(ImageView) allSatisfy.getChildAt(i);
            theImage.setImageResource(R.drawable.minipoint2);
        }
    }
    public void showChangeTime(View view)
    {
        /*AlertDialog.Builder timeDialog =
                new AlertDialog.Builder(Timesharing_edit.this);*/
        final int theId=view.getId();
        final TextView editStart=(TextView)findViewById(R.id.editStartTime);
        final TextView editEnd=(TextView)findViewById(R.id.editEndTime);
        final AlertDialog timeDialog = new AlertDialog.Builder(Timesharing_edit.this,R.style.timeAlertDialog).create();
        final View dialogView = LayoutInflater.from(Timesharing_edit.this)
                .inflate(R.layout.settime_dialog,null);
        //为弹出层的布局添加xml
        ArrayAdapter<String> adapterHour=new ArrayAdapter<String>(Timesharing_edit.this,R.layout.list_time,allHours);
        timeDialog.setView(dialogView);ListView listHour=(ListView) dialogView.findViewById(R.id.PopSetHour);
        listHour.setAdapter(adapterHour);
        ArrayAdapter<String> adapterMin=new ArrayAdapter<String>(Timesharing_edit.this,R.layout.list_time,allMinutes);
        ListView listMin=(ListView) dialogView.findViewById(R.id.PopSetMin);
        listMin.setAdapter(adapterMin);
        //为listview添加时间
        listHour.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(theId==R.id.editStartTime) {
                    startHour = allHours[position];
                    editStart.setText(startHour+":"+startMin);
                }
                    else {
                    endHour = allHours[position];
                    editEnd.setText(endHour+":"+endMin);
                }
            }
        });
        listMin.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(theId==R.id.editStartTime) {
                    startMin = allMinutes[position];
                    editStart.setText(startHour+":"+startMin);
                }
                else {
                    endMin = allMinutes[position];
                    editEnd.setText(endHour+":"+endMin);
                }
            }
        });
        //点击的时候获取时间
        timeDialog.show();
        //为弹出层添加布局并展示
       Window dialogWindow = timeDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = DensityUtil.dip2px(Timesharing_edit.this,200);//宽高可设置具体大小
        lp.height =DensityUtil.dip2px(Timesharing_edit.this,180);
        timeDialog.getWindow().setAttributes(lp);
        //设置弹出层的大小

    }
}
