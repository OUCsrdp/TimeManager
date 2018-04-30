package com.srdp.admin.time_manager.model.moudle;

public class EventHour 
{
	private long id;
	
	private float numEvents;
	
	public float getNumEvents() {
		return numEvents;
	}

	public void setNumEvents(float numEvents) {
		this.numEvents = numEvents;
	}

	public boolean isWorkday() {
		return isWorkday;
	}

	public void setWorkday(boolean isWorkday) {
		this.isWorkday = isWorkday;
	}

	public int getTimeArea() {
		return timeArea;
	}

	public void setTimeArea(int timeArea) {
		this.timeArea = timeArea;
	}

	public long getId() {
		return id;
	}

	private boolean isWorkday;
	
	private int timeArea;//suspect
	
	public EventHour(long id, float numEvents, boolean isWorkday, int timeArea)
	{
		this.id = id;
		this.numEvents = numEvents;
		this.isWorkday = isWorkday;
		this.timeArea = timeArea;
	}
}
