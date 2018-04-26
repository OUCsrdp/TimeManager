package main.model.moudle;

public class TimeSharing 
{
	private long id;
	private long idUser;
	
	private String date;
    
    private int weekday;
    
    public TimeSharing(long id, long idUser, String date, int weekday)
    {
    	this.id = id;
    	this.idUser = idUser;
    	this.date = date;
    	this.weekday = weekday;
    }
    
    public long getId() {return id;}
    
    public void setIdUser(long idUser) {this.idUser = idUser;}
    public long getIdUser() {return idUser;}
    
    public void setString(String date) {this.date = date;}
    public String getString() {return date;}
    
    public void setWeekday(int weekday) {this.weekday = weekday;}
    public int getWeekday()
    {
    	return weekday;
    }
}
