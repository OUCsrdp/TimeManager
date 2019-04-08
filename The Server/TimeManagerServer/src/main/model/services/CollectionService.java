package main.model.services;

import main.model.moudle.*;

import java.util.ArrayList;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import main.model.db.*;

public class CollectionService{
	
	public String collection(int idTs,int userId) {
		//userid��ָ��ǰ�û�id,����ָ�ղصķ�����û�id
		//�Ƿ������ղع����ж�
		//��ӹ�ϵCollection��
		CollectionManager managerC = new CollectionManager();
		ArrayList<Collection> findList = managerC.findWithIdTS(idTs);
		int count = 0;
		if(findList!=null)
		{
			for(Collection a:findList) {
				if(a.getIdUser() == userId) {
					count ++;
					return "fail"; //�Ѿ��ղع��������ٴ��ղ�
				}
			}
		}
		if(count == 0) {
			if(managerC.add(userId, idTs) != -1) return "success";
			else return "fail";
		}
		else return "fail";
		
	}
	//����success��fail

	public JSONObject getCollection(int userId) {
		
		User user = UserManager.findWithId(userId);
		JSONObject back = new JSONObject(); //����װ���ص����ݵ�
		if(user != null) {
			
			ArrayList<Collection> listC = CollectionManager.findWithIdUser(userId);
			JSONArray array = new JSONArray();
			for (Collection curC: listC) {
				int idTS = curC.getIdTS(); //�õ�TimeSharing
				TimeSharing curTS = TimeSharingManager.findWithId(idTS);
				
				SharedTable curST = SharedTableManager.findWithIdTS(idTS); //�Ժ������������
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
	//���ص�jsonObjectͬ��
	//��Ҫ������ʱ��������

}