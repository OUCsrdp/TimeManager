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
		User user=UserManager.findWithId(2);
		System.out.println("name: "+user.getName());
		// TODO Auto-generated method stub
		/*int idUser1 = UserManager.add("16020031030", "中国海洋大学", "计算机科学与技术", 4.0f, "User_a", "C://", "123456", "1970-01-01");
		int idUser2 = UserManager.add("16020031031", "中国海洋大学", "计算机科学与技术", 3.0f, "User_b", "C://", "123456", "2018-05-17");
		int idUser3 = UserManager.add("16020031032", "中国海洋大学", "计算机科学与技术", 2.0f, "User_c", "C://", "123456", "2009-03-12");
		int idUser4 = UserManager.add("16020031033", "中国海洋大学", "计算机科学与技术", 3.5f, "User_d", "C://", "123456", "2012-04-01");
		int idUser5 = UserManager.add("16020031034", "中国海洋大学", "计算机科学与技术", 2.8f, "User_e", "C://", "123456", "1999-11-21");
		int idUser6 = UserManager.add("16020031035", "中国海洋大学", "计算机科学与技术", 3.9f, "User_f", "C://", "123456", "2014-05-01");

		int idTS1 = TimeSharingManager.add(idUser1, "2010-05-01", 2);
		int idTS2 = TimeSharingManager.add(idUser2, "2010-05-02", 2);
		int idTS3 = TimeSharingManager.add(idUser3, "2010-05-03", 2);
		int idTS4 = TimeSharingManager.add(idUser4, "2010-05-04", 2);
		int idTS5 = TimeSharingManager.add(idUser5, "2010-05-05", 2);
		int idTS6 = TimeSharingManager.add(idUser6, "2010-05-06", 2);
		
		int idST1 = SharedTableManager.add(idUser1, idTS1, "2010-05-01", "nin gen wo ya me ru zo,JoJo!", 666);
		int idST2 = SharedTableManager.add(idUser2, idTS2, "2010-05-02", "nin gen wo ya me ru zo,JoJo!", 666);
		int idST3 = SharedTableManager.add(idUser3, idTS3, "2010-05-03", "nin gen wo ya me ru zo,JoJo!", 666);
		int idST4 = SharedTableManager.add(idUser4, idTS4, "2010-05-04", "nin gen wo ya me ru zo,JoJo!", 666);
		int idST5 = SharedTableManager.add(idUser5, idTS5, "2010-05-05", "nin gen wo ya me ru zo,JoJo!", 666);
		int idST6 = SharedTableManager.add(idUser6, idTS6, "2010-05-06", "nin gen wo ya me ru zo,JoJo!", 666);
		
		int idS1 = ScheduleManager.add(idUser1, "2010-05-01", 2);
		int idS2 = ScheduleManager.add(idUser2, "2010-05-02", 2);
		int idS3 = ScheduleManager.add(idUser3, "2010-05-03", 2);
		int idS4 = ScheduleManager.add(idUser4, "2010-05-04", 2);
		int idS5 = ScheduleManager.add(idUser5, "2010-05-05", 2);
		int idS6 = ScheduleManager.add(idUser6, "2010-05-06", 2);
		
		int idLabel = LabelManager.add("学习", "C://", "red");*/
		
		//int idSA = S_AffairManager.add(idTS1, idS1, idLabel, satisfaction, isImportant, name, tips, timeStart, timeEnd, timeStartPlan, timeEndPlan, timeStartAlarm, timeEndAlarm)
		
	}
}
