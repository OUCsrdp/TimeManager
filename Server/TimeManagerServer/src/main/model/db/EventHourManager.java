package main.model.db;

import java.util.ArrayList;

import main.model.moudle.*;

public class EventHourManager {
	
	public EventHourManager() {;}
	
	public static boolean add(EventHour eventHour) {return true;}

	public static boolean delete(EventHour eventHour) {return true;}
	
	public static EventHour findWithId(long id) {return null;}
	public static ArrayList<EventHour> findWithIsWorkday(boolean isWorkday) {return null;}
	public static ArrayList<EventHour> findWithTimeArea(int timeArea) {return null;}
	
	public static boolean change() {return true;}
}
