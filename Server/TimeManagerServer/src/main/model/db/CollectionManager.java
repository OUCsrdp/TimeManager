package main.model.db;

import java.util.ArrayList;

import main.model.moudle.*;
public class CollectionManager {
	
	public CollectionManager() {;}
	
	public static boolean add(Collection collection) {return true;}
	
	public static boolean delete(Collection collection) {return true;}
	
	public static Collection findWithId(long id) {return null;}
	public static ArrayList<Collection> findWithIdUser(long idUser) {return null;}
	public static ArrayList<Collection> findWithIdTS(long idTS) {return null;}
	
	public static boolean change(Collection collection) {return true;}
}
