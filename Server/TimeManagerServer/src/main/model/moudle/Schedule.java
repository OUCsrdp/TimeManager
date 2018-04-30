package main.model.moudle;

public class Schedule 
{
	private long id;
	private long idUser;
	
	private String date;
	    
    private int weekday;
    
    public Schedule(long id, long idUser, String date, int weekday)
    {
    	this.id = id;
    	this.idUser = idUser;
    	this.date = date;
    	this.weekday = weekday;
    }
    
    public long getId() {return id;}
    
    public void setIdUser(long idUser) {this.idUser = idUser;}
    public long getIdUser() {return idUser;}
    
   public void setDate(String date) {this.date = date;}
   public String getDate() {return date;}
    
    public void setWeekday(int weekday) {this.weekday = weekday;}
    public int getWeekday()
    {
    	return weekday;
    }
}
