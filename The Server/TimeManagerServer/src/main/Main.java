package main;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
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
<<<<<<< HEAD
	public static void main(String[] args) throws ParseException {
		String delayTime = AnalysisService.getDelayedTime(238, true);
		System.out.println(delayTime);
=======
	public static void main(String[] args) {
		AnalysisService aService=new AnalysisService();
		//JSONObject simple=aService.getChart(2, true, "SimpleAnalysis");
		//JSONObject detailed=aService.getChart(2, true, "detailedAnalysis");
		JSONObject density=aService.getChart(2, true, "densityAnalysis");
		/*TimeSharingService tService=new TimeSharingService();
		JSONObject j=tService.getSTDetails(2);
		System.out.println(j);*/
		
>>>>>>> 8342845777e737e912c6b21b32707e1561d43aea
	}
}
