package main.model.services;

import main.model.moudle.*;

import java.util.ArrayList;

import main.model.db.*;

public class SharedtableService{
	
	String share(int idTs) {
		//�ж�gpa��
		//�ж��Ƿ��ظ�����
		//����SharedTables�ĳ�ʼ��
		
		SharedTableManager managerST = new SharedTableManager();
		ArrayList<SharedTable> findList = managerST.findWithIdTS(idTs); //��Ҫ�°汾��
		int count = 0;
		for(SharedTable a:findList) {
			if(a.getIdTS() == idTs) { //��Ҫ�°汾��
				count ++;
				return "fail"; //�Ѿ�������������ٴη���
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
	//gpa���㷵��gpafail ����ʧ�ܷ���fail

	JSONObject getTSList(int userId,String major) {
		SharedTableManager managerST = new SharedTableManager();
		 if(major.equals("all")) { //����רҵ
			 
			 
		 }
		 else { //��רҵ
			 
		 }
	}
	/*UseridΪ��ǰ�û�id,Major����Ϊĳ��רҵ���ƣ���original����ʾ��רҵ,��all����ʾ����רҵ
	����jsonObject*/
	//����

}