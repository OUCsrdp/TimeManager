package main.model.services;

import main.model.moudle.*;

import java.util.ArrayList;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import main.model.db.*;

public class CollectionService{
	
	String collection(int idTs,int userId) {
		//userid是指当前用户id,不是指收藏的分配表用户id
		//是否曾经收藏过的判断
		//添加关系Collection表
		CollectionManager managerC = new CollectionManager();
		ArrayList<Collection> findList = managerC.findWithIdTS(idTs);
		int count = 0;
		for(Collection a:findList) {
			if(a.getIdUser() == userId) {
				count ++;
				return "fail"; //已经收藏过，不能再次收藏
			}
		}
		if(count == 0) {
			if(managerC.add(userId, idTs) == 1) return "success";
			else return "fail";
		}
		else return "fail";
		
	}
	//返回success或fail

	JSONObject getCollection(int userId) {
		UserManager managerU = new UserManager();
		User user = managerU.findWithId(userId);
		JSONObject back = new JSONObject(); //用于装返回的数据的
		if(user != null) {
			CollectionManager managerC = new CollectionManager();
			ArrayList<Collection> listC = managerC.findWithIdUser(userId);
			for (Collection curC: listC) {
				int idST = curC.getIdTS(); //这里应该得到idST
				SharedTableManager managerST = new SharedTableManager();
				SharedTable curST = managerST.findWithId(idST);
				if(curST != null) {
					JSONObject object = new JSONObject();
					JSONArray array = new JSONArray();
					
					TimeSharing curTS = 
					String name = 
					int userId
					String image
					String school
					String major
					String summary
					String timeShared
					int thumbup
					
					back.put("", array); //???没有指示么
				}
				else return null;
			}
		}
		else return null;
	}
	//返回的jsonObject同上
	//需要按分享时间排序吗？

}