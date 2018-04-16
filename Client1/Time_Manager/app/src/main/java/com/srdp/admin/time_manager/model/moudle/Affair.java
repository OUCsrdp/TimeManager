package com.srdp.admin.time_manager.model.moudle;

import java.sql.Time;

public class Affair 
{
	private long id;
	private long idTS;
	private long idLabel;
	
	private int satisfaction;
	
	private String name;
	private String tips;
	
	private Time timeStart;
	private Time timeEnd;
	private Time timeEndPlan;
	
	public Affair(long id, long idTS, long idLabel, int satisfaction, String name, String tips, Time timeStart, Time timeEnd, Time timeEndPlan)
	{
		this.id = id;
		this.idTS = idTS;
		this.idLabel = idLabel;
		this.satisfaction = satisfaction;
		this.name = name;
		this.tips = tips;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.timeEndPlan = timeEndPlan;
	}

	public long getIdTS() {
		return idTS;
	}

	public void setIdTS(long idTS) {
		this.idTS = idTS;
	}

	public long getIdLabel() {
		return idLabel;
	}

	public void setIdLabel(long idLabel) {
		this.idLabel = idLabel;
	}

	public int getSatisfaction() {
		return satisfaction;
	}

	public void setSatisfaction(int satisfaction) {
		this.satisfaction = satisfaction;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public Time getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Time timeStart) {
		this.timeStart = timeStart;
	}

	public Time getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Time timeEnd) {
		this.timeEnd = timeEnd;
	}

	public Time getTimeEndPlan() {
		return timeEndPlan;
	}

	public void setTimeEndPlan(Time timeEndPlan) {
		this.timeEndPlan = timeEndPlan;
	}

	public long getId() {
		return id;
	}
}
