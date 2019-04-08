package com.srdp.admin.time_manager.model.moudle.JsonBean;

public class ExcellentIntent {
    /**
     * name : 爱吃肉的兔子
     * userId : 2
     * image :
     * school : 中国海洋大学
     * major : 计算机系
     * summary : 今天超超级充实的
     * gpa : 3.7
     * idTS : 2
     * idST : 3
     */

    private String name;
    private int userId;
    private int image;
    private String school;
    private String major;
    private String summary;
    //private float gpa;
    private int idTS;
    private int idST;

    public ExcellentIntent(){}
    public ExcellentIntent(String name,int userId,int image,String school,String major,String summary,int idTS,int idST){
        this.name=name;
        this.userId=userId;
        this.image=image;
        this.school=school;
        this.major=major;
        this.summary=summary;
        this.idTS=idTS;
        this.idST=idST;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    /*public float getGpa() {
        return gpa;
    }

    public void setGpa(float gpa) {
        this.gpa = gpa;
    }*/

    public int getIdTS() {
        return idTS;
    }

    public void setIdTS(int idTS) {
        this.idTS = idTS;
    }

    public int getIdST() {
        return idST;
    }

    public void setIdST(int idST) {
        this.idST = idST;
    }
}
