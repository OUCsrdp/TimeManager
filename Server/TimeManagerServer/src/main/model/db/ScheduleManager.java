package main.model.db;

import java.util.ArrayList;

import main.model.moudle.*;
public class ScheduleManager {

	public ScheduleManager() {;}
	
	public static boolean add(Schedule schdule) {return true;}

	public static boolean delete(Schedule schdule) {return true;}
	
	public static Schedule findWithId(long id) {return null;}
	public static ArrayList<Schedule> findWithIdUser(long idUser) {return null;}
	public static ArrayList<Schedule> findWithDate(String date) {return null;}
	public static ArrayList<Schedule> findWithWeekday(int weekday) {return null;}
	
	public static boolean change(Schedule schdule) {return true;}
	
}
