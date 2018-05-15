package com.srdp.admin.time_manager.util;

import com.srdp.admin.time_manager.model.moudle.User;

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
}
