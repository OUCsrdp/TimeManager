package com.example.lizliz.timemanager.model;

/**
 * Created by lizliz on 2018/5/11.
 */

public class ExcellentStudentsDistribution {

    private String name;//用户名
    private String userId;//用户id
    private int image;//头像地址
    private String school;//学校
    private String major;//专业
    private String summary;//总结
    private String timeShared;//分享时间
    private int thumbup;

    public ExcellentStudentsDistribution(String name, int image, String school, String major, String summary, String timeShared, int thumbup){
        this.name = name;
        this.image = image;
        this.school = school;
        this.major = major;
        this.summary = summary;
        this.timeShared = timeShared;
        this.thumbup = thumbup;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public void setImage(){
        this.image = image;
    }
    public int getImage(){
        return image;
    }

    public void setSchool(){
        this.school = school;
    }
    public String getSchool(){
        return school;
    }

    public void setMajor(){
        this.major = major;
    }
    public String getMajor(){
        return major;
    }

    public void setSummary(){
        this.summary = summary;
    }
    public String getSummary(){
        return summary;
    }

    public void setTimeShared(){
        this.timeShared = timeShared;
    }
    public String getTimeShared(){
        return timeShared;
    }

    public void setThumbup(){
        this.thumbup = thumbup;
    }
    public int getThumbup(){
        return thumbup;
    }


}
