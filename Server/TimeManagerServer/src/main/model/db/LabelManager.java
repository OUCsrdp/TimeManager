package main.model.db;

import java.util.ArrayList;

import main.model.moudle.*;

public class LabelManager {
	
	public LabelManager() {;}
	
	public static boolean add(Label label) {return true;}

	public static boolean delete(Label label) {return true;}
	
	public static Label findWithId(long id) {return null;}
	public static ArrayList<Label> findWithName(String name) {return null;}
	public static ArrayList<Label> findWithColor(int color) {return null;}
	
	public static boolean change() {return true;}
}
