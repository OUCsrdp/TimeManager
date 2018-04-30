package main.model.services;

import main.model.moudle.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import main.model.db.*;


public class AffairService{//����Ϊɶ��ҪisAffair?????
	//ChangeΪ1��ʾ�޸ģ�Ϊ0��ʾ������isAffairΪ1��ʾAffair,Ϊ0��ʾs_affair
	//�����Ƿ�ɹ����ɹ�����1�����ɹ�����0��
	public int changeAffair(int change,int isAffair,Affair affair) {
		AffairManager managerA = new AffairManager();
		//�½��¼�
		if(change == 0) {
			
			int idTS = affair.getIdTS();
			int idLabel = affair.getIdLabel();
			int satisfaction = affair.getSatisfaction();
			String name = affair.getName();
			String tips = affair.getTips();
			String timeStart = affair.getTimeStart();
			String timeEnd = affair.getTimeEnd();
			String timeEndPlan = affair.getTimeEndPlan();
			
			if(managerA.add(idTS, idLabel, satisfaction, name, tips, timeStart, timeEnd,timeEndPlan)) return 1;
			else return 0;
		}
		else { //�޸��¼�
			
			int id = affair.getId();
			if(managerA.findWithId(id) != null) {
				//���¼�����
				if(managerA.change(affair)) return 1;
				else return 0;
			}
			else return 0;
		}
	}
	public int changeSAffair(int change,int isAffair,S_Affair affair) {
		S_AffairManager managerSA = new S_AffairManager();
		//�½��ճ�
		if(change == 0) {
			
			int idTS = affair.getIdTS();
			int idS = affair.getIdS();
			int idLabel = affair.getIdLabel();
			int satisfaction = affair.getSatisfaction();
			String isImportant = affair.getIsImportant();
			String name = affair.getName();
			String tips = affair.getTips();
			String timeStart = affair.getTimeStart();
			String timeEnd = affair.getTimeEnd();
			String timeStartPlan = affair.getTimeStartPlan();
			String timeEndPlan = affair.getTimeEndPlan();
			String timeStartAlarm = affair.getTimeStartAlarm();
			String timeEndAlarm = affair.getTimeEndAlarm();
			
			if(add(idTS, idS, idLabel, satisfaction, isImportant, name, tips, timeStart, timeEnd, timeStartPlan, timeEndPlan, timeStartAlarm, timeEndAlarm)) 
				return 1;
			else return 0;
		}
		else { //�޸��ճ�
			int id = affair.getId();
			if(managerSA.findWithId(id) != null) {
				//���ճ̴���
				if(managerSA.change(affair)) return 1;
				else return 0;
			}
			else return 0;	
		}
	}

}