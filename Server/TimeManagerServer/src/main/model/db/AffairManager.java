package main.model.db;

import java.util.ArrayList;

import main.model.moudle.*;

public class AffairManager
{
	public AffairManager() {;}
	
	public static boolean add(Affair affair) {return true;}
	
	public static boolean delete(Affair affair) {return true;}
	
	public static Affair findWithId(long id) {return null;}
	public static ArrayList<Affair> findWithIdTS(long idTS) {return null;}
	public static ArrayList<Affair> findWithIdLabel(long idLabel) {return null;}
	public static ArrayList<Affair> findWithSatisfaction(int satisfaction) {return null;}
	public static ArrayList<Affair> findWithName(String name){return null;}
	
	public static boolean change() {return true;}
}
