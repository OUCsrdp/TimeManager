package main.model.db;

import java.util.ArrayList;

import main.model.moudle.*;

public class TimeSharingManager {

	public TimeSharingManager() {;}
	
	public static boolean add(TimeSharing timeSharing) {return true;}

	public static boolean delete(TimeSharing timeSharing) {return true;}
	
	public static TimeSharing findWithId(long id) {return null;}
	public static ArrayList<TimeSharing> findWithIdUser(long idUser) {return null;}
	public static ArrayList<TimeSharing> findWithDate(String date) {return null;}
	public static ArrayList<TimeSharing> findWithWeekday(int weekday) {return null;}
	
	public static boolean change(TimeSharing timeSharing) {return true;}
}
