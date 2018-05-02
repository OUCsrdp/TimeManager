package main.model.moudle;

public class Schedule 
{
	private int id;
	private int idUser;
	
	private String date;
	    
    private int weekday;
    
    public Schedule(int id, int idUser, String date, int weekday)
    {
    	this.id = id;
    	this.idUser = idUser;
    	this.date = date;
    	this.weekday = weekday;
    }
    
    public int getId() {return id;}
    
    public void setIdUser(int idUser) {this.idUser = idUser;}
    public int getIdUser() {return idUser;}
    
   public void setDate(String date) {this.date = date;}
   public String getDate() {return date;}
    
    public void setWeekday(int weekday) {this.weekday = weekday;}
    public int getWeekday()
    {
    	return weekday;
    }
}
