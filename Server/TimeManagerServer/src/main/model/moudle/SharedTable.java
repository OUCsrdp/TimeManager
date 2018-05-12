package main.model.moudle;

public class SharedTable 
{
	private int id;
	private int idUser;
	private int idTS;
	private String timeShared;
	private String summary;
	private int thumbup;
	
	public SharedTable(int id, int idUser,int idTS, String timeShared, String summary, int thumbup)
	{
		this.id = id;
		this.idUser = idUser;
		this.idTS = idTS;
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

	public int getIdTS() {
		return idTS;
	}

	public void setIdTS(int idTS) {
		this.idTS = idTS;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
}
