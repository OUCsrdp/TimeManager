package com.srdp.admin.time_manager.model.moudle;

import java.sql.Time;

public class SharedTable 
{
	private long id;
	private Time timeShared;
	private String summary;
	private int thumbup;
	
	public SharedTable(long id, Time timeShared, String summary, int thumbup)
	{
		this.id = id;
		this.timeShared = timeShared;
		this.summary = summary;
		this.thumbup = thumbup;
	}
	
	public long getId() {return id;}
	
	public void setTimeShared(Time timeShared) {this.timeShared = timeShared;}
	public Time getTimeShared() {return timeShared;}
	
	public void setSummary(String summary) {this.summary = summary;}
	public String getSummary() {return summary;}
	
	public void setThumbup(int thumbup) {this.thumbup = thumbup;}
	public int getThumbup() {return thumbup;}
}
