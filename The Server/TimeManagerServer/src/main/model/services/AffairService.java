package main.model.services;

import main.model.moudle.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import main.model.db.*;


public class AffairService{
	
	//ͨ��id���޸�һ��affair��sAffair�Ŀ�ʼʱ�䣬����ʱ��
	//isAffairΪ1��ʾAffair,Ϊ0��ʾs_affair
	//�ɹ�����true��ʧ�ܷ���false
	boolean changeAffairById(int isAffair,int id,String startTime,String endTime) {
		if(isAffair == 1) {
			Affair affair = AffairManager.findWithId(id);
			affair.setTimeStart(startTime);
			affair.setTimeEnd(endTime);
			if(AffairManager.change(affair)) return true;
			else return false;
		}
		else {
			S_Affair sAffair = S_AffairManager.findWithId(id);
			sAffair.setTimeStart(startTime);
			sAffair.setTimeEnd(endTime);
			if(S_AffairManager.change(sAffair)) return true;
			else return false;
		}
	}
	
	//����Ϊɶ��ҪisAffair?????
	//ChangeΪ1��ʾ�޸ģ�Ϊ0��ʾ������isAffairΪ1��ʾAffair,Ϊ0��ʾs_affair
	//�����Ƿ�ɹ����ɹ�����1�����ɹ�����0��
	public int changeAffair(int change,int isAffair,Affair affair,String date,int idU) {
		Calendar calendar = Calendar.getInstance();
		int week = calendar.get(Calendar.DAY_OF_WEEK);
		int weekday;
		if(week == 0)weekday = 7;
		else weekday = week - 1;
		AffairManager managerA = new AffairManager();
		
		//�½��¼�
		if(change == 0) {
			if(affair.getIdTS() == 0) {
				if(TimeSharingManager.findWithDate(idU,date) != null) {} //���ݿ�findWithDate����Ӧ�÷���TimeSharing������list
				else {
					
					int idts = TimeSharingManager.add(idU, date, weekday);
					if(idts != -1) affair.setIdTS(idts);
					else return 0;
				}
			}
			
			
			
			int idTS = affair.getIdTS();
			int idLabel = affair.getIdLabel();
			int satisfaction = affair.getSatisfaction();
			String name = affair.getName();
			String tips = affair.getTips();
			String timeStart = affair.getTimeStart();
			String timeEnd = affair.getTimeEnd();
			String timeEndPlan = affair.getTimeEndPlan();
			
			int add_result = managerA.add(idTS, idLabel, satisfaction, name, tips, timeStart, timeEnd,timeEndPlan);
			if( add_result!= -1) return add_result;
			else return 0;
		}
		else { //�޸��¼�
			
			int id = affair.getId();
			if(managerA.findWithId(id) != null) {
				//���¼�����
				if(managerA.change(affair)) return id;
				else return 0;
			}
			else return 0;
		}
	}
	public int changeSAffair(int change,int isAffair,S_Affair affair,String date,int idU) {
		S_AffairManager managerSA = new S_AffairManager();
		Calendar calendar = Calendar.getInstance();
		int week = calendar.get(Calendar.DAY_OF_WEEK);
		int weekday;
		if(week == 0)weekday = 7;
		else weekday = week - 1;
		
		
		//�½��ճ�
		if(change == 0) {
			if(affair.getIdS() == 0) {
				if(ScheduleManager.findWithDate(idU,date) != null) {
					affair.setIdS(ScheduleManager.findWithDate(idU,date).getId());
				} //���ݿ�findWithDate����Ӧ�÷���TimeSharing������list
				else {
					
					int ids = ScheduleManager.add(idU, date, weekday);
					if(ids != -1) affair.setIdS(ids);
					else return 0;
				}
			}
			if(affair.getTimeStart() != null) {
				if(affair.getIdTS() == 0) {
					if(TimeSharingManager.findWithDate(idU,date) != null) {} //���ݿ�findWithDate����Ӧ�÷���TimeSharing������list
					else {
						
						int idts = TimeSharingManager.add(idU, date, weekday);
						if(idts != -1) affair.setIdTS(idts);
						else return 0;
					}
				}
			}
			
			
			
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
			
			
			ArrayList<S_Affair> SAlist = S_AffairManager.findWithIdS(idS); //�õ���ǰ�ճ̱��µ������ճ�
			if(SAlist != null)
			{
				for(S_Affair SA :SAlist) {
					if(SortService.compareTime(timeStartPlan, SA.getTimeStartPlan()) && SortService.compareTime(timeEndPlan, SA.getTimeEndPlan())) {}
					else if(SortService.compareTime(SA.getTimeStartPlan(), timeStartPlan) && SortService.compareTime(SA.getTimeEndPlan(),timeEndPlan)) {}
					else return 0;
				}
			}
			
			int add_result = managerSA.add(idTS, idS, idLabel, satisfaction, isImportant, name, tips, timeStart, timeEnd, timeStartPlan, timeEndPlan, timeStartAlarm, timeEndAlarm);
			if(add_result != -1) 
				return add_result;
			else return 0;
		}
		else { //�޸��ճ�
			if(affair.getTimeStart() != null) {
				if(affair.getIdTS() == 0) {
					if(TimeSharingManager.findWithDate(idU, date) == null) {
						int idts = TimeSharingManager.add(idU, date, weekday);
						if(idts != -1) affair.setIdTS(idts);
						else return 0;
					}
				}
			}
			int id = affair.getId();
			System.out.println("id:"+Integer.toString(id));
			if(managerSA.findWithId(id) != null) {
				//���ճ̴���
				if(managerSA.change(affair)) return id;
				else return 0;
			}
			else return 0;	
		}
	}

	public int DeleteAffair(int id,int isAffair) {	//ɾ���¼�������Ϊ�¼���id,isAffairΪ1��ʾAffair,Ϊ0��ʾSAffair
		if(isAffair == 1) { //�������¼������ճ�
			AffairManager managerA = new AffairManager();
			Affair affair = managerA.findWithId(id);
			if(affair != null) { //�����û��Ƿ����
				if(managerA.delete(id)) return 1;
				else return 0;
			}
			else return 0;
		}
		else {
			S_AffairManager managerSA = new S_AffairManager();
			S_Affair affair = managerSA.findWithId(id);
			if(affair != null) {
				if(managerSA.delete(id)) return 1;
				else return 0;
			}
			else return 0;
		}
	}
	//ɾ���¼�������Ϊ�¼���id,isAffairΪ1��ʾAffair,Ϊ0��ʾSAffair
	
	public S_Affair  guessAffair(String time,String date,String name) {
		ArrayList<S_Affair> list1 = S_AffairManager.findWithName(name);
		ArrayList<S_Affair> list = new ArrayList<S_Affair>();
		if(list1!=null)
		{
			for(S_Affair s : list1) {
				Schedule ts = ScheduleManager.findWithId(s.getIdS());
				if(ts.getDate().equals(date)) list.add(s);
			}
		}
		if(list.isEmpty()) return null; //������ͬ���ճ�
		else {
			S_Affair t = list.get(0); //��ʼ��СΪ��һ��
			for(S_Affair s: list) {
				String timeStart = s.getTimeStartPlan();
				if(SortService.timeCloser(timeStart, t.getTimeStartPlan(), time)) t = s; //��ǰ�ĸ��ӽ������¸�ֵ
			}
			return t;
		}
	}
	//����Ϊ��������ڣ�2018��5��12�գ�������ʱ�䣨����10:00�����¼����ƣ����ص���������ʱ�������ͬ��S_Affair,����������򷵻�null

}