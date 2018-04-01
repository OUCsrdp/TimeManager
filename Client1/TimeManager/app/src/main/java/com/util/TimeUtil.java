package com.util;

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
}
