package main.model.moudle;


public class SharedTable 
{
	private long id;
	private String timeShared;
	private String summary;
	private int thumbup;
	
	public SharedTable(long id, String timeShared, String summary, int thumbup)
	{
		this.id = id;
		this.timeShared = timeShared;
		this.summary = summary;
		this.thumbup = thumbup;
	}
	
	public long getId() {return id;}
	
	public void settimeShared(String timeShared) {this.timeShared = timeShared;}
	public String gettimeShared() {return timeShared;}
	
	public void setSummary(String summary) {this.summary = summary;}
	public String getSummary() {return summary;}
	
	public void setThumbup(int thumbup) {this.thumbup = thumbup;}
	public int getThumbup() {return thumbup;}
}
