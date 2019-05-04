package main;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import main.model.db.*;
import main.model.moudle.*;
import main.model.services.*;

public class Main {

	@SuppressWarnings("null")
	public static void main(String[] args) {
		AnalysisService aService=new AnalysisService();
		String dt=aService.getDelayedTime(2,true);
		System.out.println("dt"+dt);
		String dtf=aService.getDelayedTime(2,false);
		System.out.println("dtf"+dt);
		//int a=aService.getUnfinishedPercent(2,true);
		//System.out.println("uf"+a);
		//JSONObject simple=aService.getChart(2, true, "SimpleAnalysis");
		//JSONObject detailed=aService.getChart(2, true, "detailedAnalysis");
		//JSONObject density=aService.getChart(2, true, "densityAnalysis");
		//SheetService s=new SheetService();
		/*JSONObject x=s.getWeeklySheet(2,"2019年4月18日");
		System.out.println(x.toString());
		JSONObject x2=s.getWeeklyChange(2,"2019年4月18日",1);
		System.out.println(x2.toString());*/
		//JSONObject x3=s.getDailySheet(2, "2019年4月18日");
		//System.out.println(x3.toString());
		/*TimeSharingService tService=new TimeSharingService();
		JSONObject j=tService.getSTDetails(2);
		System.out.println(j);*/
		
	}
}
