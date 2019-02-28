package main.model.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.math.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
import main.model.services.moudle.*;
import main.model.services.db.*;

public class SortService{
	public static boolean timeCloser(String time1,String time2,String time) {//time1��time2���ӽ�time,��ͬ�Ƚӽ�ʱ����true
		
		SimpleDateFormat simple = new SimpleDateFormat("HH:mm");
		long t1,t2,t;
		try {
			t1 = simple.parse(time1).getTime();
			t2 = simple.parse(time2).getTime();
			t = simple.parse(time).getTime();
		} catch (ParseException e) {
			return false;
		}
		
		if(Math.abs(t1-t) <= Math.abs(t2-t)) return true;
		else return false;

	}
	
	public static boolean compareTime(String time1,String time2) { //time1��time2����ô�ͷŷ���true
		
		
		SimpleDateFormat sdf=new SimpleDateFormat("hh:mm");
	
		Date a =null,b = null;
		try {
			a = sdf.parse(time1);
			b = sdf.parse(time2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		if(a.before(b))
			return true;
		else
			return false;
	}
	
	public static ArrayList<Affair> sortAByTime(ArrayList<Affair> list){
		Collections.sort(list, new Comparator<Affair>() {  
			@Override
			public int compare(Affair arg0, Affair arg1) {
				// TODO Auto-generated method stub
				if(compareTime(arg0.getTimeStart(),arg1.getTimeStart())) return -1; //��ʼʱ��ķ�ǰ��
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
				if(compareTime(arg0.getTimeStart(),arg1.getTimeStart())) return 1; //��ʼʱ��ķ�ǰ��
				else return -1;
			}  
        });  
		
		return list;
	}
}