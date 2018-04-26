package main.model.db;

import java.util.ArrayList;

import main.model.moudle.*;

public class LikeManager {

	public LikeManager() {;}
	
	public static boolean add(Like like) {return true;}

	public static boolean delete(Like like) {return true;}
	
	public static Like findWithId(long id) {return null;}
	public static ArrayList<Like> findWithIdUser(long idUser) {return null;}
	public static ArrayList<Like> findWithIdTS(long idTS) {return null;}
	
	public static boolean change(Like like) {return true;}
}
