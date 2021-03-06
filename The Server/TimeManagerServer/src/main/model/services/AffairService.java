package main.model.services;

import main.model.moudle.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import main.model.db.*;


public class AffairService{
	
	//通过id来修改一个affair或sAffair的开始时间，结束时间
	//isAffair为1表示Affair,为0表示s_affair
	//成功返回true，失败返回false
	public boolean changeAffairById(boolean isAffair,int id,String startTime,String endTime) {
		if(isAffair) {
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
	
	//这里为啥需要isAffair?????
	//Change为1表示修改，为0表示创建，isAffair为1表示Affair,为0表示s_affair
	//返回是否成功（成功返回1，不成功返回0）
	public int changeAffair(int change,int isAffair,Affair affair,String date,int idU) {
		Calendar calendar = Calendar.getInstance();
		int week = calendar.get(Calendar.DAY_OF_WEEK);
		int weekday;
		if(week == 0)weekday = 7;
		else weekday = week - 1;
		AffairManager managerA = new AffairManager();
		
		//新建事件
		if(change == 0) {
			if(affair.getIdTS() == 0) {
				TimeSharing ts=TimeSharingManager.findWithDate(idU,date);
				if(ts != null) {
					affair.setIdTS(ts.getId());
				} //数据库findWithDate函数应该返回TimeSharing而不是list
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
			System.out.println("affairdata");
			System.out.println(idTS);
			System.out.println(idLabel);
			System.out.println(satisfaction);
			System.out.println(name);
			System.out.println(tips);
			System.out.println(timeStart);
			System.out.println(timeEnd);
			System.out.println(timeEndPlan);
			int add_result = managerA.add(idTS, idLabel, satisfaction, name, tips, timeStart, timeEnd,timeEndPlan);
			if( add_result!= -1) return add_result;
			else return 0;
		}
		else { //修改事件
			
			int id = affair.getId();
			if(managerA.findWithId(id) != null) {
				//该事件存在
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
		
		
		//新建日程
		if(change == 0) {
			if(affair.getIdS() == 0) {
				if(ScheduleManager.findWithDate(idU,date) != null) {
					affair.setIdS(ScheduleManager.findWithDate(idU,date).getId());
				} //数据库findWithDate函数应该返回TimeSharing而不是list
				else {
					
					int ids = ScheduleManager.add(idU, date, weekday);
					if(ids != -1) affair.setIdS(ids);
					else return 0;
				}
			}
			if(affair.getTimeStart() != null) {
				if(affair.getIdTS() == 0) {
					if(TimeSharingManager.findWithDate(idU,date) != null) {} //数据库findWithDate函数应该返回TimeSharing而不是list
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
			
			
			ArrayList<S_Affair> SAlist = S_AffairManager.findWithIdS(idS); //得到当前日程表下的已有日程
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
		else { //修改日程
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
				//该日程存在
				if(managerSA.change(affair)) return id;
				else return 0;
			}
			else return 0;	
		}
	}

	public int DeleteAffair(int id,int isAffair) {	//删除事件，参数为事件的id,isAffair为1表示Affair,为0表示SAffair
		if(isAffair == 1) { //区分是事件还是日程
			AffairManager managerA = new AffairManager();
			Affair affair = managerA.findWithId(id);
			if(affair != null) { //看该用户是否存在
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
	//删除事件，参数为事件的id,isAffair为1表示Affair,为0表示SAffair
	
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
		if(list.isEmpty()) return null; //不存在同名日程
		else {
			S_Affair t = list.get(0); //初始最小为第一个
			for(S_Affair s: list) {
				String timeStart = s.getTimeStartPlan();
				if(SortService.timeCloser(timeStart, t.getTimeStartPlan(), time)) t = s; //当前的更接近则重新赋值
			}
			return t;
		}
	}
	//参数为今天的日期（2018年5月12日），现在时间（比如10:00），事件名称，返回当天离现在时间最近的同名S_Affair,如果不存在则返回null

}