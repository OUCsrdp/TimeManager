package com.srdp.admin.time_manager.db;
import org.litepal.*;

import java.util.List;

public class DbBase<T> {
    public boolean insert(T table){
        return table.save();
    }
    public boolean update(T table){
        return table.updateAll();
    }
    public List<T> query(){
        List<T> objects =DataSupport.find(T.class);
                return objects;
    }
    public T queryById(int id){
        List<T> objects =DataSupport.where("id = ?",id).find(T.class);
        return objects.get(0);
    }
}
