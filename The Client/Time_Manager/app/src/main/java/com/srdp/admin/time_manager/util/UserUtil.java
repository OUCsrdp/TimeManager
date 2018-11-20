package com.srdp.admin.time_manager.util;

import android.content.SharedPreferences;

import com.alibaba.fastjson.JSONObject;
import com.srdp.admin.time_manager.model.moudle.MyApplication;
import com.srdp.admin.time_manager.model.moudle.User;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by admin on 2018/5/1.
 */

public class UserUtil {
    static private User user=new User();
    static public User getUser() {
        return user;
    }
    static public void setUserName(String name) {
        user.setName(name);
    }
    static public void setUser(String school,String major,String number){
        user.setSchool(school);
        user.setMajor(major);
        user.setNumStu(number);
    }
    static public void setUser(User u){
        user=u;
    }
    static public void setUser(JSONObject u)
    {
        user=new User(u.getIntValue("id"),u.getString("numStu"),u.getString("school"),u.getString("major"),
                u.getFloatValue("GPA"),u.getString("name"),u.getString("pwd"),u.getString("image"),
                u.getString("timeRegister"));
    }

}
