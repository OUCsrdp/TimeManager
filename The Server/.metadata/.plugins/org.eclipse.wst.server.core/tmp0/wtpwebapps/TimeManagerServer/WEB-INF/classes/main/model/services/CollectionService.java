package main.model.services;

import main.model.moudle.*;

import java.util.ArrayList;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import main.model.db.*;

public class CollectionService{
	
	String collection(int idTs,int userId) {
		//userid��ָ��ǰ�û�id,����ָ�ղصķ�����û�id
		//�Ƿ������ղع����ж�
		//��ӹ�ϵCollection��
		ArrayList<Collection> findList = CollectionManager.findWithIdTS(idTs);
		int count = 0;
		for(Collection a:findList) {
			if(a.getIdUser() == userId) {
				count ++;
				return "fail"; //�Ѿ��ղع��������ٴ��ղ�
			}
		}
		if(count == 0) {
			if(CollectionManager.add(userId, idTs) == -1) return "success";
			else return "fail";
		}
		else return "fail";
		
	}
	//����success��fail

	JSONObject getCollection(int userId) {
		User user = UserManager.findWithId(userId);
		JSONObject back = new JSONObject(); //����װ���ص����ݵ�
		if(user != null) {
			ArrayList<Collection> listC = CollectionManager.findWithIdUser(userId);
			for (Collection curC: listC) {
				int idST = curC.getIdTS(); //����Ӧ�õõ�idST
				SharedTable curST = SharedTableManager.findWithId(idST);
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
					
					back.put("", array); //???û��ָʾô
				}
				else return null;
			}
		}
		else return null;
	}
	//���ص�jsonObjectͬ��
	//��Ҫ������ʱ��������

}