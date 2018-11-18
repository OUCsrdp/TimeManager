package main.model.services;

import java.util.ArrayList;
import main.model.db.*;
import main.model.moudle.*;

public class AnalysisService {
	String getDelayedTime(int userId,boolean weekday)
	{
		
		ArrayList<Schedule> schedules = ScheduleManager.findWithIdUser(userId);
		for(int i = 0; i < schedules.size(); i++)
		{
			if(weekday && schedules.get(i).getWeekday() <= 7 && schedules.get(i).getWeekday() > 5)
			{
				ArrayList<S_Affair> s_Affairs = S_AffairManager.findWithIdS(schedules.get(i).getId());
				for(int j = 0; j < s_Affairs.size(); j++)
				{
					
				}
			}
			else
			{
				
			}
		}
		return null;
	}
}
