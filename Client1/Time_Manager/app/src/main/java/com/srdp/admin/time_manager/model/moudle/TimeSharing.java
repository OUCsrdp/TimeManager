package com.srdp.admin.time_manager.model.moudle;

import java.sql.Date;

public class TimeSharing 
{
	private long id;
	private long idUser;
	
	private Date date;
    
    private Weekday weekday;
    
    public TimeSharing(long id, long idUser, Date date, Weekday weekday)
    {
    	this.id = id;
    	this.idUser = idUser;
    	this.date = date;
    	this.weekday = weekday;
    }
    
    public long getId() {return id;}
    
    public void setIdUser(long idUser) {this.idUser = idUser;}
    public long getIdUser() {return idUser;}
    
    public void setDate(Date date) {this.date = date;}
    public Date getDate() {return date;}
    
    public void setWeekday(Weekday weekday) {this.weekday = weekday;}
    public int getWeekday()
    {
    	if(weekday == Weekday.Monday)
    		return 1;
    	else if(weekday == Weekday.Tuesday)
    		return 2;
    	else if(weekday == Weekday.Wednesday)
    		return 3;
    	else if(weekday == Weekday.Thursday)
    		return 4;
    	else if(weekday == Weekday.Friday)
    		return 5;
    	else if(weekday == Weekday.Saturday)
    		return 6;
    	else if(weekday == Weekday.Sunday)
    		return 7;
    	else
    		return -1;//Error
    }
}
