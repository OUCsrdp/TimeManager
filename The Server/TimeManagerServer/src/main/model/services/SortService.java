package main.model.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import main.model.moudle.*;
import main.model.db.*;
import java.math.*;

public class SortService{
	public static boolean timeCloser(String time1,String time2,String time) {//time1比time2更接近time返回true
		int index1 = time1.indexOf(":");
		int index2 = time2.indexOf(":");
		int index3 = time.indexOf(":");
		String hour3 = time.substring(0,index3-1);
		String hour1 = time1.substring(0, index1-1);
		String hour2 = time2.substring(0, index2-1);
		String minute1 = time1.substring(index1+1);
		String minute2 = time2.substring(index2+1);
		String minute3 = time.substring(index3+1);

		int h1 = Integer.parseInt(hour1);
		int h2 = Integer.parseInt(hour2);
		int h3 = Integer.parseInt(hour3);

		int m1 = Integer.parseInt(minute1);
		int m2 = Integer.parseInt(minute2);
		int m3 = Integer.parseInt(minute3);
		
		if(Math.abs(h1-h3) < Math.abs(h2-h3)) return true;
		else if(Math.abs(h1-h3) == Math.abs(h2-h3)) {
			if(Math.abs(m1-m3) <= Math.abs(m2-m3)) return true;
			else return false;
		}
		return false;

	}
	
	public static boolean compareTime(String time1,String time2) { //time1比time2早那么就放返回true
		int index1 = time1.indexOf(":");
		int index2 = time2.indexOf(":");
		String hour1 = time1.substring(0, index1);
		String hour2 = time2.substring(0, index2);
		String minute1 = time1.substring(index1+1);
		String minute2 = time2.substring(index2+1);
		int h1 = Integer.parseInt(hour1);
		int h2 = Integer.parseInt(hour2);
		int m1 = Integer.parseInt(minute1);
		int m2 = Integer.parseInt(minute2);
		
		if(h1 < h2) return true;
		else if(h1 > h2) return false;
		else {
			if(m1 < m2) return true;
			if(m1 >= m2) return false;
		}
		return false;
	}
	
	public static ArrayList<Affair> sortAByTime(ArrayList<Affair> list){
		Collections.sort(list, new Comparator<Affair>() {  
			@Override
			public int compare(Affair arg0, Affair arg1) {
				// TODO Auto-generated method stub
				if(compareTime(arg0.getTimeStart(),arg1.getTimeStart())) return -1; //开始时早的放前面
				else return 1;
			}  
        });  
		
		return list;
	}
	
	public static ArrayList<S_Affair> sortSByTime(ArrayList<S_Affair> list){
		Collections.sort(list, new Comparator<S_Affair>() {  
			@Override
			public int compare(S_Affair arg0, S_Affair arg1) {
				// TODO Auto-generated method stub
				if(compareTime(arg0.getTimeStartPlan(),arg1.getTimeStartPlan())) return -1; //开始时早的放前面
				else return 1;
			}  
        });  
		
		return list;
	}
}