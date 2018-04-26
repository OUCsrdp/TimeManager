package main.model.moudle;



public class S_Affair extends Affair
{
	private long idS;
	
	private boolean isImportant;
	
	private String timeStartPlan;
	private String timeStartAlarm;
	private String timeEndAlarm;
	
	public S_Affair(long id, long idTS, long idS, long idLabel, int satisfaction, boolean isImportant, String name, String tips, String timeStart, String timeEnd, String timeStartPlan, String timeEndPlan, String timeStartAlarm, String timeEndAlarm) 
	{
		super(id, idTS, idLabel, satisfaction, name, tips, timeStart, timeEnd, timeEndPlan);
		this.idS = idS;
		this.isImportant = isImportant;
		this.timeStartPlan = timeStartPlan;
		this.timeStartAlarm = timeStartAlarm;
		this.timeEndAlarm = timeEndAlarm;
		// TODO Auto-generated constructor stub
	}

	public long getIdS() {
		return idS;
	}

	public void setIdS(long idS) {
		this.idS = idS;
	}

	public boolean isImportant() {
		return isImportant;
	}

	public void setImportant(boolean isImportant) {
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

	public String gettimeEndAlarm() {
		return timeEndAlarm;
	}

	public void setTimeEndAlarm(String TimeEndAlarm) {
		this.timeEndAlarm = TimeEndAlarm;
	}
	
	
}
