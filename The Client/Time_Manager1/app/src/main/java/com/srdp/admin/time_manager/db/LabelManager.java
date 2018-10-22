package com.srdp.admin.time_manager.db;

import android.support.annotation.NonNull;

import com.srdp.admin.time_manager.model.moudle.Label;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class LabelManager {
    //初始化添加所有标签
    private DbBase<Label> labeldb=new DbBase<Label>();
    public void initialInsert(){
        List<Label> labels=new ArrayList<Label>();
        labels.add(new Label(1,"学习","D:/1234","#ffffff"));
        labels.add(new Label(2,"休息","D:/1234","#ffff00"));
        Iterator<Label> iter = labels.iterator();
        while(iter.hasNext()){  //执行过程中会执行数据锁定，性能稍差.
            labeldb.insert(iter.next());
        }
    }
    public List<Label> queryAll(){
        List<Label> labels=labeldb.query();
        return labels;
    }
    public Label queryById(int id){
        Label label=labeldb.queryById(id);
        return label;
    }
}
