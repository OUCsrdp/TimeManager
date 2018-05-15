package com.srdp.admin.time_manager.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by admin on 2018/3/23.
 */

public class TimeUtil {
    public static String getFormatTime(long time)
    {
        if (time <= 0) {
            return "00:00";
        }
        int hour=(int)((time/1000)/1440);
        int min =(int)((time / 1000) / 60)-60*hour;
        int sec =(int)((time / 1000) % 60);
        String h="";
        String m,s;
        if(hour>0)
            h = hour >= 10 ? String.valueOf(hour) : "0"
                    + String.valueOf(hour);
        m = min >= 10 ? String.valueOf(min) : "0"
                + String.valueOf(min);
        s = sec >= 10 ? String.valueOf(sec) : "0"
                + String.valueOf(sec);
        if(hour>0)
            return h+":"+m+":"+s;
        else
            return m+":"+s;
    }
    public static int compareTime(String time1,String time2){
        time1=time1.split(" ")[1];
        time2=time2.split(" ")[1];
        String[] t1=time1.split(":");
        String[] t2=time2.split(":");
        if(Integer.parseInt(t1[0])<Integer.parseInt(t2[0]))
            return 1;
        else if(Integer.parseInt(t1[0])==Integer.parseInt(t2[0])&&Integer.parseInt(t1[1])<Integer.parseInt(t2[1]))
            return 1;
        else
            return 0;
    }
    //比较带日期的时间
    public static int compareOnlyTime(String time1,String time2){
        String[] t1=time1.split(":");
        String[] t2=time2.split(":");
        if(Integer.parseInt(t1[0])<Integer.parseInt(t2[0]))
            return 1;
        else if(Integer.parseInt(t1[0])==Integer.parseInt(t2[0])&&Integer.parseInt(t1[1])<Integer.parseInt(t2[1]))
            return 1;
        else
            return 0;
    }
    public static Calendar createCalendar()
    {
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar;
    }
    public static String getDate(Calendar calendar,int changeDate)
    {

        calendar.add(Calendar.DATE, changeDate);
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH)+1;
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        return year+"年"+month+"月"+day+"日";
    }
    public static String getHourandmin()
    {
        Calendar calendar=createCalendar();
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int min=calendar.get(Calendar.MINUTE);
        String finalTime="";
        if(hour<10)
            finalTime+="0"+hour+":";
        else
            finalTime+=hour+":";
        if(min<10)
            finalTime+="0"+min;
        else
            finalTime+=min;
        return finalTime;
    }
    //比较2个时间，比如10:00与11:19，小于返回1，否则返回0
//    public static String getTimeDuration(String time1,String time2){
//        time1=time1.split(" ")[1];
//        time2=time2.split(" ")[1];
//        String[] t1=time1.split(":");
//        String[] t2=time2.split(":");
//        if(Integer.parseInt(t1[1])<Integer.parseInt(t2[1]))
//        {
//            t1[0]=String.valueOf(Integer.parseInt(t1[0])-1-Integer.parseInt(t2[0]));
//            t1[1]=String.valueOf(Integer.parseInt(t1[1])+60-Integer.parseInt(t2[1]));
//        }
//        else
//        {
//            t1[0]=String.valueOf(Integer.parseInt(t1[0])-Integer.parseInt(t2[0]));
//            t1[1]=String.valueOf(Integer.parseInt(t1[1])-Integer.parseInt(t2[1]));
//        }
//
//    }
}

