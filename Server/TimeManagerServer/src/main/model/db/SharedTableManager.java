package main.model.db;

import java.util.ArrayList;

import main.model.moudle.*;

public class SharedTableManager {

	public SharedTableManager() {;}
	
	public static boolean add(SharedTable sharedTable) {return true;}

	public static boolean delete(SharedTable sharedTable) {return true;}
	
	public static SharedTable findWithId(long id) {return null;}
	public static SharedTable findWithTimeShared(String timeShared) {return null;}
	public static ArrayList<SharedTable> findWithThumbup(int thumbup) {return null;}
	
	public static boolean change(SharedTable sharedTable) {return true;}
}
