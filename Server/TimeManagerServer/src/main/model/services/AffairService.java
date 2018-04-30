package main.model.services;

import main.model.moudle.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import main.model.db.*;


public class AffairService{//这里为啥需要isAffair?????
	//Change为1表示修改，为0表示创建，isAffair为1表示Affair,为0表示s_affair
	//返回是否成功（成功返回1，不成功返回0）
	public int changeAffair(int change,int isAffair,Affair affair) {
		AffairManager managerA = new AffairManager();
		//新建事件
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
		else { //修改事件
			
			int id = affair.getId();
			if(managerA.findWithId(id) != null) {
				//该事件存在
				if(managerA.change(affair)) return 1;
				else return 0;
			}
			else return 0;
		}
	}
	public int changeSAffair(int change,int isAffair,S_Affair affair) {
		S_AffairManager managerSA = new S_AffairManager();
		//新建日程
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
		else { //修改日程
			int id = affair.getId();
			if(managerSA.findWithId(id) != null) {
				//该日程存在
				if(managerSA.change(affair)) return 1;
				else return 0;
			}
			else return 0;	
		}
	}

}