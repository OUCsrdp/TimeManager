package main;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import main.model.db.*;
import main.model.moudle.*;
import main.model.services.*;

public class Main {

	@SuppressWarnings("null")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*User rabbit = UserManager.findWithName("������");
		System.out.println("rabbit�� "+rabbit.getGPA() + " " + rabbit.getMajor());*/
		/*TimeSharingService ts=new TimeSharingService();
		JSONObject json=ts.getSharingTable("2", "2018��5��5��");
		System.out.println(json.toString());*/
		ScheduleService s=new ScheduleService();
		JSONObject j=s.getScheduleTable("2", "2018��5��8��");
		System.out.println(j.toString());
	}

}
