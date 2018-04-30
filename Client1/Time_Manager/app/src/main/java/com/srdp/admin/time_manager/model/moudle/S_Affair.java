package com.srdp.admin.time_manager.model.moudle;

import java.sql.Time;

public class S_Affair 
{
	private long id;
	private long idTS;
	private long idS;
	private long idLabel;
	
	private int satisfaction;
	
	private String name;
	private String tips;
	
	private boolean isImportant;
	
	private Time timeStart;
	private Time timeEnd;
	private Time timeStartPlan;
	private Time timeEndPlan;
	private Time timeStartAlarm;
	private Time timeEndAlarm;
	
	public long getId() {
		return id;
	}

	public long getIdTS() {
		return idTS;
	}

	public void setIdTS(long idTS) {
		this.idTS = idTS;
	}

	public long getIdS() {
		return idS;
	}

	public void setIdS(long idS) {
		this.idS = idS;
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

	public boolean isImportant() {
		return isImportant;
	}

	public void setImportant(boolean isImportant) {
		this.isImportant = isImportant;
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

	public Time getTimeStartPlan() {
		return timeStartPlan;
	}

	public void setTimeStartPlan(Time timeStartPlan) {
		this.timeStartPlan = timeStartPlan;
	}

	public Time getTimeEndPlan() {
		return timeEndPlan;
	}

	public void setTimeEndPlan(Time timeEndPlan) {
		this.timeEndPlan = timeEndPlan;
	}

	public Time getTimeStartAlarm() {
		return timeStartAlarm;
	}

	public void setTimeStartAlarm(Time timeStartAlarm) {
		this.timeStartAlarm = timeStartAlarm;
	}

	public Time getTimeEndAlarm() {
		return timeEndAlarm;
	}

	public void setTimeEndAlarm(Time timeEndAlarm) {
		this.timeEndAlarm = timeEndAlarm;
	}

	public S_Affair(long id, long idTS, long idS, long idLabel, int satisfaction, String name, String tips, boolean isImportant, Time timeStart, Time timeEnd, Time timeStartPlan, Time timeEndPlan, Time timeStartAlarm, Time timeEndAlarm)
	{
		this.id = id;
		this.idTS = idTS;
		this.idS = idS;
		this.idLabel = idLabel;
		this.satisfaction = satisfaction;
		this.name = name;
		this.tips = tips;
		this.isImportant = isImportant;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.timeStartPlan = timeStartPlan;
		this.timeEndPlan = timeEndPlan;
		this.timeStartAlarm = timeStartAlarm;
		this.timeEndAlarm = timeEndAlarm;
	}
}
