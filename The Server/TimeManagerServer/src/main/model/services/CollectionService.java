package main.model.services;

import main.model.moudle.*;

import java.util.ArrayList;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import main.model.db.*;

public class CollectionService{
	
	public String collection(int idTs,int userId) {
		//userid是指当前用户id,不是指收藏的分配表用户id
		//是否曾经收藏过的判断
		//添加关系Collection表
		CollectionManager managerC = new CollectionManager();
		ArrayList<Collection> findList = managerC.findWithIdTS(idTs);
		int count = 0;
		if(findList!=null)
		{
			for(Collection a:findList) {
				if(a.getIdUser() == userId) {
					count ++;
					return "fail"; //已经收藏过，不能再次收藏
				}
			}
		}
		if(count == 0) {
			if(managerC.add(userId, idTs) != -1) return "success";
			else return "fail";
		}
		else return "fail";
		
	}
	//返回success或fail

	public JSONObject getCollection(int userId) {
		
		User user = UserManager.findWithId(userId);
		JSONObject back = new JSONObject(); //用于装返回的数据的
		if(user != null) {
			
			ArrayList<Collection> listC = CollectionManager.findWithIdUser(userId);
			JSONArray array = new JSONArray();
			for (Collection curC: listC) {
				int idTS = curC.getIdTS(); //得到TimeSharing
				TimeSharing curTS = TimeSharingManager.findWithId(idTS);
				
				SharedTable curST = SharedTableManager.findWithIdTS(idTS); //以后会加上这个函数
				if(curST != null) {
					JSONObject object = new JSONObject();
					
					
					
					int userID = curTS.getIdUser();
					User curUser = UserManager.findWithId(userID);
					String name = curUser.getName();
					String image = curUser.getImage();
					String school = curUser.getSchool();
					String major = curUser.getMajor();
					String summary = curST.getSummary();
					String timeShared = curST.getTimeShared();
					int thumbup = curST.getThumbup();
					
					object.put("name", name);
					object.put("userId", userID);
					object.put("image", image);
					object.put("school", school);
					object.put("major", major);
					object.put("summary",summary);
					object.put("timeShared", timeShared);
					object.put("thumbup", thumbup);
					
					array.add(object);
					
				}
				else return null;
			}
			back.put("collections", array);
			return back;
		}
		else return null;
	}
	//返回的jsonObject同上
	//需要按分享时间排序吗？

}