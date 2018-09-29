package com.srdp.admin.time_manager.model.moudle;

public class SharedTable 
{
	private int id;
	private String timeShared;
	private String summary;
	private int thumbup;
	
	public SharedTable(int id, String timeShared, String summary, int thumbup)
	{
		this.id = id;
		this.timeShared = timeShared;
		this.summary = summary;
		this.thumbup = thumbup;
	}
	
	public int getId() {return id;}
	
	public void setTimeShared(String timeShared) {this.timeShared = timeShared;}
	public String getTimeShared() {return timeShared;}
	
	public void setSummary(String summary) {this.summary = summary;}
	public String getSummary() {return summary;}
	
	public void setThumbup(int thumbup) {this.thumbup = thumbup;}
	public int getThumbup() {return thumbup;}
}
