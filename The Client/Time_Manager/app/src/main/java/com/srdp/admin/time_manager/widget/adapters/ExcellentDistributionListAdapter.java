package com.srdp.admin.time_manager.widget.adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.srdp.admin.time_manager.R;
import com.srdp.admin.time_manager.model.moudle.ExcellentStudentsDistribution;
import com.srdp.admin.time_manager.model.moudle.JsonBean.ExcellentIntent;
import com.srdp.admin.time_manager.ui.ExcellentDistributionContentActivity;
import com.srdp.admin.time_manager.ui.ExcellentDistributionListActivity;
import com.srdp.admin.time_manager.util.HttpUtil;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by lizliz on 2018/5/11.
 */

public class ExcellentDistributionListAdapter  extends RecyclerView.Adapter<ExcellentDistributionListAdapter.ViewHolder>{

    private Context mContext;//当前上下文
     private List<ExcellentStudentsDistribution> mExcellentDistributionList;

     //获取点击数据
     String i_name;//用户名
     int i_userId;//用户id
     int i_image;//头像地址
     String i_school;//学校
     String i_major;//专业
     String i_summary;//总结
     String i_timeShared;//分享时间
     int i_thumbup;//点赞数
     int i_idTS;//时间分配表的id
     int i_idST;//已分享的时间分配表的id




    static class ViewHolder extends RecyclerView.ViewHolder {
        View distriListView;
        ImageView distrIcon;//头像
        TextView distrName;//用户名
        TextView distrSchool;//学校
        TextView distrMajor;//专业
        TextView distrIntro;//简介
        Button distrLike;//点赞按钮
        TextView distrTime;//时间

        public ViewHolder(View view) {
            super(view);
            distriListView = view;
            distrIcon = (ImageView) view.findViewById(R.id.excellent_list_icon);//获取布局内头像
            distrName = (TextView) view.findViewById(R.id.excellent_list_name);//获取布局内用户名
            distrSchool = (TextView) view.findViewById(R.id.excellent_list_school);//获取布局内学校
            distrMajor = (TextView) view.findViewById(R.id.excellent_list_major);//获取布局内专业
            distrIntro = (TextView) view.findViewById(R.id.list_intro_content);//获取布局内简介
            distrLike = (Button) view.findViewById(R.id.list_like_button);//获取布局内点赞按钮
            distrTime = (TextView) view.findViewById(R.id.list_intro_time);//获取布局内时间
        }
    }

    public ExcellentDistributionListAdapter(Context context,List<ExcellentStudentsDistribution> ExcellentDistributionList){
        mContext = context;
        mExcellentDistributionList = ExcellentDistributionList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.distriListView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.i("ExeclAdapter","in");
                int position = holder.getAdapterPosition();
                ExcellentStudentsDistribution excellentStudentsDistribution = mExcellentDistributionList.get(position);

                //获取数据
                i_name = excellentStudentsDistribution.getName();//用户名
                i_userId = excellentStudentsDistribution.getUserId();//用户id
                i_image = excellentStudentsDistribution.getImage();//头像地址
                i_school = excellentStudentsDistribution.getSchool();//学校
                i_major = excellentStudentsDistribution.getMajor();//专业
                i_summary = excellentStudentsDistribution.getSummary();//总结
                i_timeShared = excellentStudentsDistribution.getTimeShared();//分享时间
                i_thumbup = excellentStudentsDistribution.getThumbup();//点赞数
                i_idTS = excellentStudentsDistribution.getIdTS();//时间分配表的id
                i_idST = excellentStudentsDistribution.getIdST();//已分享的时间分配表的id
                Log.i("execlIntent",i_name);
                ExcellentIntent excellentIntent=new ExcellentIntent(i_name,i_userId,i_image,i_school,i_major,i_summary,i_idTS,i_idST);
                Intent intent = new Intent(mContext, ExcellentDistributionContentActivity.class);
                //把实体类转成json串
                intent.putExtra("BaseInforExcel", JSONObject.toJSONString(excellentIntent));
                //跳转页面 传递intent
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        ExcellentStudentsDistribution distributionList = mExcellentDistributionList.get(position);
        holder.distrIcon.setImageResource(distributionList.getImage());//为头像设置图片资源
        holder.distrName.setText(distributionList.getName());//为用户名设置文本内容
        holder.distrSchool.setText(distributionList.getSchool());//为学校设置文本内容
        holder.distrMajor.setText(distributionList.getMajor());//为专业设置文本内容
        holder.distrIntro.setText(distributionList.getSummary());//为简介设置文本内容
        holder.distrLike.setText(String.valueOf(distributionList.getThumbup()));//为按钮设置赞数
        holder.distrTime.setText(distributionList.getTimeShared());//为时间设置文本内容
    }

    @Override
    public int getItemCount(){
        return mExcellentDistributionList.size();
    }

}
