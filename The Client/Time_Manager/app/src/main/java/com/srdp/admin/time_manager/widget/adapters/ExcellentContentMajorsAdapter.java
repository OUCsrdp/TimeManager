package com.srdp.admin.time_manager.widget.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.srdp.admin.time_manager.R;
import com.srdp.admin.time_manager.model.moudle.ExcellentContentMajors;
import com.srdp.admin.time_manager.model.moudle.ExcellentStudentsDistribution;

import java.util.List;

/**
 * Created by Lenovo on 2018/12/5.
 */

public class ExcellentContentMajorsAdapter  extends RecyclerView.Adapter<ExcellentContentMajorsAdapter.ViewHolder> {

    private List<ExcellentContentMajors> mExcellentContentMajors;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View distriContentView;
        TextView taskName;//事件名称
        TextView timeStart;//开始时间
        TextView timeEnd;//结束时间


        public ViewHolder(View view) {
            super(view);
            distriContentView = view;
            taskName = (TextView) view.findViewById(R.id.content_task_name);//获取布局内事件名称
            timeStart = (TextView) view.findViewById(R.id.content_task_time_start);//获取布局内开始时间
            timeEnd = (TextView) view.findViewById(R.id.content_task_time_end);//获取布局内结束时间
        }
    }

    public ExcellentContentMajorsAdapter(List<ExcellentContentMajors> ExcellentContentMajorsList){
        mExcellentContentMajors = ExcellentContentMajorsList;
    }


    @Override
    public ExcellentContentMajorsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ExcellentContentMajorsAdapter.ViewHolder holder, int position){
        ExcellentContentMajors contentList = mExcellentContentMajors.get(position);

        holder.taskName.setText(contentList.getName());//为事件名称设置文本内容
        holder.timeStart.setText(contentList.getTimeStart());//为开始时间设置文本内容
        holder.timeEnd.setText(contentList.getTimeEnd());//为结束时间设置文本内容
    }

    @Override
    public int getItemCount(){
        return mExcellentContentMajors.size();
    }
}
