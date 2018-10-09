package com.srdp.admin.time_manager.widget.adapters;

import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.srdp.admin.time_manager.R;
import com.srdp.admin.time_manager.model.moudle.ExcellentStudentsDistribution;

/**
 * Created by lizliz on 2018/5/11.
 */

public class ExcellentDistributionListAdapter  extends ArrayAdapter<ExcellentStudentsDistribution>{

    private final int resourceId;



    public ExcellentDistributionListAdapter(Context context, int textViewResourceId, List<ExcellentStudentsDistribution> objects){
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ExcellentStudentsDistribution distribution = (ExcellentStudentsDistribution) getItem(position);// 获取当前项的distribution实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);//实例化一个对象

        ImageView distrIcon = (ImageView) view.findViewById(R.id.excellent_list_icon);//获取布局内头像
        TextView distrName = (TextView) view.findViewById(R.id.excellent_list_name);//获取布局内用户名
        TextView distrSchool = (TextView) view.findViewById(R.id.excellent_list_school);//获取布局内学校
        TextView distrMajor = (TextView) view.findViewById(R.id.excellent_list_major);//获取布局内专业
        TextView distrIntro = (TextView) view.findViewById(R.id.list_intro_content);//获取布局内简介
        Button distrLike = (Button) view.findViewById(R.id.list_like_button);//获取布局内点赞按钮
        TextView distrTime = (TextView) view.findViewById(R.id.list_intro_time);//获取布局内时间

        distrIcon.setImageResource(distribution.getImage());//为头像设置图片资源
        distrName.setText(distribution.getName());//为用户名设置文本内容
        distrSchool.setText(distribution.getSchool());//为学校设置文本内容
        distrMajor.setText(distribution.getMajor());//为专业设置文本内容
        distrIntro.setText(distribution.getSummary());//为简介设置文本内容
        distrLike.setText(distribution.getThumbup());//为按钮设置赞数
        distrTime.setText(distribution.getTimeShared());//为时间设置文本内容

        return view;

    }

}
