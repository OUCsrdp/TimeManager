package com.srdp.admin.time_manager.model.moudle;

import android.app.Application;

/**
 * Created by admin on 2018/5/12.
 */

public class MyApplication extends Application {
    private Affair timingAffair=new Affair();
    private S_Affair timingSAffair=new S_Affair();
    private int isAffair=1;
    public void setTimingAffair(Affair affair)
    {
        timingAffair=affair;
    }
    public Affair getTimingAffair()
    {
        return timingAffair;
    }
    public void setTimingSAffair(S_Affair sAffair)
    {
        timingSAffair=sAffair;
    }
    public S_Affair getTimingSAffair()
    {
        return timingSAffair;
    }
    public void setIsAffair(int isAffair)
    {
        this.isAffair=isAffair;
    }
    public int getIsAffair()
    {
        return isAffair;
    }
}
