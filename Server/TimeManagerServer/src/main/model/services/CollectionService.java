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
		CollectionManager managerC = new CollectionManager();
		ArrayList<Collection> findList = managerC.findWithIdTS(idTs);
		int count = 0;
		for(Collection a:findList) {
			if(a.getIdUser() == userId) {
				count ++;
				return "fail"; //�Ѿ��ղع��������ٴ��ղ�
			}
		}
		if(count == 0) {
			if(managerC.add(userId, idTs) == 1) return "success";
			else return "fail";
		}
		else return "fail";
		
	}
	//����success��fail

	JSONObject getCollection(int userId) {
		UserManager managerU = new UserManager();
		User user = managerU.findWithId(userId);
		JSONObject back = new JSONObject(); //����װ���ص����ݵ�
		if(user != null) {
			CollectionManager managerC = new CollectionManager();
			ArrayList<Collection> listC = managerC.findWithIdUser(userId);
			for (Collection curC: listC) {
				int idST = curC.getIdTS(); //����Ӧ�õõ�idST
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