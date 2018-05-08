package main.model.services;

import main.model.moudle.*;

import java.util.ArrayList;

import main.model.db.*;

public class SharedtableService{
	
	String share(int idTs) {
		//判断gpa；
		//判断是否重复分享
		//对于SharedTables的初始化
		
		SharedTableManager managerST = new SharedTableManager();
		ArrayList<SharedTable> findList = managerST.findWithIdTS(idTs); //需要新版本类
		int count = 0;
		for(SharedTable a:findList) {
			if(a.getIdTS() == idTs) { //需要新版本类
				count ++;
				return "fail"; //已经分享过，不能再次分享
			}
		}
		if(count == 0) {
			TimeSharingManager managerTS = new TimeSharingManager();
			TimeSharing curTS = managerTS.findWithId(idTs);
			int idUser = curTS.getIdUser();
			UserManager managerU = new UserManager();
			User curUser = managerU.findWithId(idUser);
			float gpa = curUser.getGPA();
			if(gpa >= 3) {
				if(managerST.add(timeShare, summary, 0)) return "success";
				else return "fail";
			}
			else return "gpafail";
		}
		else return "fail";
	}
	//gpa不足返回gpafail 操作失败返回fail

	JSONObject getTSList(int userId,String major) {
		SharedTableManager managerST = new SharedTableManager();
		 if(major.equals("all")) { //不限专业
			 
			 
		 }
		 else { //本专业
			 
		 }
	}
	/*Userid为当前用户id,Major可能为某个专业名称，”original”表示本专业,”all”表示不限专业
	返回jsonObject*/
	//排序

}