package main.model.moudle;

public class S_Affair extends Affair
{
	private int idS;
	
	private String isImportant;
	
	private String timeStartPlan;
	private String timeStartAlarm;
	private String timeEndAlarm;
	
	public S_Affair() {
		// TODO Auto-generated constructor stub
	}
	public S_Affair(int id, int idTS, int idS, int idLabel, int satisfaction, String isImportant, String name, String tips, String timeStart, String timeEnd, String timeStartPlan, String timeEndPlan, String timeStartAlarm, String timeEndAlarm) 
	{
		super(id, idTS, idLabel, satisfaction, name, tips, timeStart, timeEnd, timeEndPlan);
		this.idS = idS;
		this.isImportant = isImportant;
		this.timeStartPlan = timeStartPlan;
		this.timeStartAlarm = timeStartAlarm;
		this.timeEndAlarm = timeEndAlarm;
		// TODO Auto-generated constructor stub
	}

	public int getIdS() {
		return idS;
	}

	public void setIdS(int idS) {
		this.idS = idS;
	}

	public String getIsImportant() {
		return isImportant;
	}

	public void setIsImportant(String isImportant) {
		this.isImportant = isImportant;
	}

	public String getTimeStartPlan() {
		return timeStartPlan;
	}

	public void setTimeStartPlan(String timeStartPlan) {
		this.timeStartPlan = timeStartPlan;
	}

	public String getTimeStartAlarm() {
		return timeStartAlarm;
	}

	public void setTimeStartAlarm(String timeStartAlarm) {
		this.timeStartAlarm = timeStartAlarm;
	}

	public String getTimeEndAlarm() {
		return timeEndAlarm;
	}

	public void setTimeEndAlarm(String TimeEndAlarm) {
		this.timeEndAlarm = TimeEndAlarm;
	}
	
	
}
