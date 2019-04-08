package com.srdp.admin.time_manager.model.moudle;

/**
 * Created by Lenovo on 2018/12/5.
 */

public class ExcellentContentMajors {

    private String name;
    private int idLabel;
    private String timeStart;
    private String timeEnd;

    public ExcellentContentMajors(String name, int idLabel, String timeStart, String timeEnd){
        this.name = name;
        this.idLabel = idLabel;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public void setIdLabel(int idLabel) {
        this.idLabel = idLabel;
    }
    public int getIdLabel(){
        return idLabel;
    }
    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }
    public String getTimeStart(){
        return timeStart;
    }
    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }
    public String getTimeEnd(){
        return timeEnd;
    }

}
