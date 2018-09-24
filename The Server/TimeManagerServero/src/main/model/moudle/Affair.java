package main.model.moudle;

public class Affair 
{
	private int id;
	private int idTS;
	private int idLabel;
	
	private int satisfaction;
	
	private String name;
	private String tips;
	
	private String timeStart;
	private String timeEnd;
	private String timeEndPlan;
	public Affair() {}
	public Affair(int id, int idTS, int idLabel, int satisfaction, String name, String tips, String timeStart, String timeEnd, String timeEndPlan)
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

	public int getIdTS() {
		return idTS;
	}

	public void setIdTS(int idTS) {
		this.idTS = idTS;
	}

	public int getIdLabel() {
		return idLabel;
	}

	public void setIdLabel(int idLabel) {
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

	public String getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	public String getTimeEndPlan() {
		return timeEndPlan;
	}

	public void setTimeEndPlan(String timeEndPlan) {
		this.timeEndPlan = timeEndPlan;
	}

	public int getId() {
		return id;
	}
	public void setId(int id){
		this.id=id;
	}
}
