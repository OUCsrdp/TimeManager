### 1 SheetServlet

#### 1.1 获取日报表基本信息（饼状图）

前端请求格式

method = GetDailySheet

{

```json
"token":

"date":"2018年7月28日"
```

}

后端返回格式：JSONObject

{

     "status":"success",
    
     "weekday": "星期六",
    
     "TimeSharing": [{
    
                            "labelid": "1",
    
                            "duration": "03:33", //该天所有该标签的事件总时间
    
                            “percent”:0.5,//所占时间比，以浮点数表示
    
                            "satisfaction": 3.8 //该天所有该标签的事件平均满意程度，取值范围为1-5
    
                   }, {
    
                            "labelid": "2", //标签的id
    
                            "duration": "03:29"， //该天所有该标签的事件总时间
    
                            “percent”:0.5,//所占时间比，以浮点数表示
    
                            "satisfaction": 4.8 //该天所有该标签的事件平均满意程度，取值范围为1-5
    
                   } .......],
    
       "Schedule": [{
    
                            "labelid": "1",
    
                            "duration": "03:50", //该天所有该标签的事件总时间
    
                            “percent”:0.5,//所占时间比，以浮点数表示
    
                   }, {
    
                            "labelid": "2", //标签的id
    
                            "duration": "01:29"， //该天所有该标签的事件总时间
    
                            “percent”:0.5,//所占时间比，以浮点数表示
    
                   } .......]

}

#### 1.2 获取周报表信息饼状图

类似于1.1

method = GetWeeklySheet

{

	"token":
	
	"date":"2018年7月28日"，

}

{

    "status":"success",
    
    "week": "第三周",//从使用APP到现在第几周
    
    "TimeSharing": [{
    
                            "labelid": "1",
    
                            "duration": "03:33", //该周所有该标签的事件总时间
    
                            “percent”:0.5,//所占时间比，以浮点数表示
    
                            "satisfaction": 3.8 //该周所有该标签的事件平均满意程度，取值范围为1-5
    
                   }, {
    
                            "labelid": "2", //标签的id
    
                            "duration": "03:29"， //该周所有该标签的事件总时间
    
                            “percent”:0.5,//所占时间比，以浮点数表示
    
                            "satisfaction": 4.8 //该周所有该标签的事件平均满意程度，取值范围为1-5
    
                   } .......],
    
       "Schedule": [{
    
                            "labelid": "1",
    
                            "duration": "03:50", //该周计划该标签的事件总时间
    
                            “percent”:0.5,//所占时间比，以浮点数表示
    
                   }, {
    
                            "labelid": "2", //标签的id
    
                            "duration": "01:29"， //该周计划该标签的事件总时间
    
                            “percent”:0.5,//所占时间比，以浮点数表示
    
                   } .......]

}

#### 1.3 获取周报表折线图

前端请求格式

method = GetWeeklyChange

{

	"token":
	
	"date":"2018年7月28日"，
	
	“labelId”:1//用户查看的标签的id（比如用户要查看一周学习时间的折线图报表）

}

后端返回格式

{

    "status":"success",
    
    "week": "第五周", //从使用APP到现在第几周
    
    “durationArray”:[“03:20”, “03:20”, “03:20”, “03:20”, “03:20”, “03:20”, “03:20”]//一周进行该标签事				   件所进行的时间的数组，从周一到周日

}

