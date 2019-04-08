JSONObject getDailySheet( userId, date)
{
	定义JSONObject back 用于返回当天时间分配和日程安排的相关信息;
	定义TimeSharing类动态数组 timeSharings 获取该用户时间分配表;
	if 获取不到用户分享的时间分配表
		函数中断执行，返回空;
	定义TimeSharing timeSharing 用来甄别用户的事件分配表是否为所选日期;
	for timeSharing : timeSharings
	{
		如果是所选日期的时间分配表则使用timeSharing获取，并中断循环执行;
	}
	if 没有所选日期的时间分配表
			函数中断执行，返回空;
	定义Affair类动态数组 affairs 用来获取当天的时间分配事件;
	if 当天没有时间分配事件 affairs 赋为一个空的动态数组;
	定义S_Affair类动态数组 s_Affairs 用来获取当天的时间分配事件;
	if 当天没有日程 s_Affairs 赋为一个空的动态数组;
	声明一个字符串 weekday_s 用来获取当天的日期;
	switch timeSharing.getWeekday()
	{
		获取日期，赋值给weekday_s;
	}
	向 back 中加入日期
		
	定义Label类动态数组 labels 用来获取当天的时间分配事件和日程的标签
	定义JSONArray affairArray 用来获取当天所有时间分配事件的相关消息;
	定义int minutesAll 来获取当天时间分配的总时间;
	for affair : affairs
	{
		将minutesAll中逐项加上时间分配事件的时间;
	}
	for label : labels
	{
		定义获取当天各个标签的事件所占的时间比所需变量
		for affair : affairs
		{
			逐项统计每个标签的事件的平均时间，所占时间在总时间中的比例以及满意度
		}
		向 affairArray 中加入得到的相关变量的字符串
	}
	向 back 中加入当天的时间分配表的事件的相关信息 affairArray;
	
	定义JSONArray s_AffairArray 用来获取当天所有时间分配事件的相关消息;
	for s_Affair : s_Affairs
	{
		将 minutesAll 中逐项加上日程的时间;
	}
	for label : labels
	{
		定义获取当天各个标签的日程所占的时间比所需变量
		for s_Affair : s_Affairs
		{
			逐项统计每个标签的日程的平均时间，所占时间在总时间中的比例
		}
		向 s_AffairArray 中加入得到的相关变量的字符串
	}
	向 back 中加入当天的时间分配表的事件的相关信息 s_AffairArray;

	返回 back;
}

JSONObject getWeeklySheet(userId, date, labelid)
{
	定义JSONObject back 用于返回指定周的时间分配和日程安排的相关信息;
		
	定义TimeSharing类动态数组 timeSharings 获取该用户时间分配表;
		
	定义Calendar类变量 timeOfDate 获取指定日期的年月日;
	定义Calendar类变量 timeRegister 获取用户注册的年月日
	定义int型变量 week，使用timeOfDate和timeRegister获取当前是用户使用的第几周
       
    将 week 添加到 back;

    定义String类动态数组 durationArray 用来获取当天;
    将timeOfDate回到上周日;
    for i = 0; i < 7; i++
    {
       	将timeOfDate向前推动一天
       	定义TimeSharing类变量 timeSharing 获取 timeOfDate 的时间分配表;
   		for timeSharing : timeSharings
   		{
      			选取 timeOfDate 的时间分配表。
       	}
       	if 这一天没有时间分配
       	{
       		将durationArray当天的平均时间赋值为00分00秒并跳过统计当天的信息;
       	}
      	获取当天的平均时间并放入durationArray;
    }
    将durationArray放入back;
   	返回 back;
}

JSONObject getWeeklySheet(userId, date)
{
  	定义JSONObject back 用于返回指定周的时间分配和日程安排的相关信息;
	
	定义TimeSharing类动态数组 timeSharings 获取该用户时间分配表;
		
	定义Calendar类变量 timeOfDate 获取指定日期的年月日;
	定义Calendar类变量 timeRegister 获取用户注册的年月日
	定义int型变量 week，使用timeOfDate和timeRegister获取当前是用户使用的第几周
		
    将 week 添加到 back;
        
   
    定义Label类的动态数组 labels 获取所有的标签;
        
    定义int minutesAll 来获取当天时间分配的总时间;
    for i = 0; i < 7; i++
    {
        定义TimeSharing类变量 timeSharing 用来获取timeOfDate日期的时间分配表;
        for timeSharing : timeSharings
        {
        	如果是所选日期的时间分配表则使用timeSharing获取，并中断循环执行;
        }
        if 这一天没有时间分配
        {
        	跳过当天的信息统计;
        }
    	定义Affair类动态数组 affairs 获取当天的时间分配事件;
    	for affair affairs
    	{
    		将minutesAll中逐项加上时间分配事件的时间;
        }
        timeOfDate 回到七天前的时间;
        
        定义JSONArray affairArray 用来获取当天所有时间分配事件的相关消息;
        for label : labels
        {
        	定义JSONObject labelAffair 获取label指定标签的事件的先关信息;
        	定义获取当天各个标签的事件所占的时间比所需变量;
        	for j = 0; j < 7; j++
            {
            	将 timeOfDate 的时间延后一天;
            	定义TimeSharing类变量 timeSharing 获取 timeOfDate 的时间分配表;
            	if 当天没有时间分配表;
            		continue;
            	定义Affair动态数组 affairs 获取当天的时间分配事件;
            	逐项统计每个标签的事件的平均时间，所占时间在总时间中的比例以及满意度;
            }
        	向 labelAffair 中加入当天的时间分配表的事件的相关信息;
			向 affairArray 中加入 labelAffair;
        	timeOfDate 回到7天前;
        }
        向 back 中加入 affairArray;
        
        定义JSONArray s_AffairArray 用来获取当天所有时间分配事件的相关消息;
        for label : labels
        {
        	定义JSONObject labelAffair 获取label指定标签的事件的先关信息;
        	定义获取当天各个标签的日程所占的时间比所需变量;
        	for j = 0; j < 7; j++
            {
            	将 timeOfDate 的时间延后一天;
            	定义TimeSharing类变量 timeSharing 获取 timeOfDate 的时间分配表;
            	if 当天没有时间分配表;
            		continue;
            	定义S_Affair动态数组 s_Affairs 获取当天的日程表中的日程;
            	逐项统计每个标签的事件的平均时间，所占时间在总时间中的比例;
            }
        	向 labelAffair 中加入当天日程表日程的相关信息;
			向 s_AffairArray 中加入 labelAffair;
        	timeOfDate 回到7天前;
        }
        向 back 中加入 s_AffairArray;
        
        return back;
  	}