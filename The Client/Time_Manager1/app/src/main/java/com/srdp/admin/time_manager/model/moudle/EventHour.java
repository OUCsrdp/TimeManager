package com.srdp.admin.time_manager.model.moudle;

public class EventHour 
{
	private int id;
	
	private float numEvents;

	private String isWorkday;
	
	private int timeArea;//suspect
	
	public float getNumEvents() {
		return numEvents;
	}

	public void setNumEvents(float numEvents) {
		this.numEvents = numEvents;
	}

	public String getIsWorkday() {
		return isWorkday;
	}

	public void setIsWorkday(String isWorkday) {
		this.isWorkday = isWorkday;
	}

	public int getTimeArea() {
		return timeArea;
	}

	public void setTimeArea(int timeArea) {
		this.timeArea = timeArea;
	}

	public int getId() {
		return id;
	}
	
	public EventHour(int id, float numEvents, String isWorkday, int timeArea)
	{
		this.id = id;
		this.numEvents = numEvents;
		this.isWorkday = isWorkday;
		this.timeArea = timeArea;
	}
}
