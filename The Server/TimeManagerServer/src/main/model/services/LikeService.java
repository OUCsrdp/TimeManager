package main.model.services;

import main.model.moudle.*;

import java.util.ArrayList;

import main.model.db.*;

public class LikeService{
	public int thumb(int userId,int idTs) {
		//�û���ָ��ǰ�û�id
		//������1.��ӵ�like��2.sharedTable�е���һ��thumbupҪ��һ
		//Ҫ�Ȳ鿴�Ƿ��Ѿ�������ϵ��������ֻ��һ��
		LikeManager managerL = new LikeManager();
		ArrayList<Like> findList = managerL.findWithIdTS(idTs);
		int count = 0;
		for(Like a:findList) {
			if(a.getIdUser() == userId) {
				count ++;
				return -1; //�Ѿ��޹��������ٴ���
			}
		}
		if(count == 0) {
			SharedTableManager managerTS =  new SharedTableManager();
			SharedTable curTS = managerTS.findWithIdTS(idTs); //��Ҫ���°��࣬idTS
			int thumb = curTS.getThumbup();
			thumb = thumb + 1;
			curTS.setThumbup(thumb); //��������һ
			if(managerL.add(userId, idTs) != -1) {
				if(managerTS.change(curTS)) return 1;
			}
			else  return -1;
		}
		else return -1;
		return -1;
		
	}
	//ע��һ���û�ֻ�ܸ�һ��ʱ���������һ�Σ������û����޺�ĵ�������ʧ�ܷ���-1

}