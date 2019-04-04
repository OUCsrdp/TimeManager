### Analysis Servlet

#### 1.1 获取用户使用总日期

method=GetDays

```json
{
    "token":,
}
//后端返回形式：jsonobject
{
    "days":1234
}
```

#### 1.2 获取平均推迟时间

method=GetDelayedTime

```json
{
    "token":,
    "weekday":"true"||"false"//true表示工作日
}
//后端返回形式：jsonobject
{
    "delayedtime":"3时12分"   
}
```

#### 1.3 获取未完成事件所占比例

method=GetUnfinishedPercent

```json
{
    "token":,
    "weekday":"true"||"false"//true表示工作日
}
//后端返回形式：jsonobject
{
    "unfininshedPercent":"45%" 
}

```

#### 1.4 获取统计图

method=GetChart

```json
{
    "token":,
    "weekday":"true"||"false"//true表示工作日,
    "type":"SimpleAnalysis"||"detailedAnalysis"||"densityAnalysis"
}
//后端返回形式：jsonobject
//如果前端请求是"type":"SimpleAnalysis"
{

    "chartInfor":[{
        "labelId":1,

        "labelName":"学习"，//标签的名字

        "percents":45, //超时或节约比例，45%该项为45，最多保留到小数点后2位

        "type":"timeSaving"||"timeOut"//如果相比日程安排节约时间该项为timeSaving，如果超   时为timeOut

    },

    {
		"labelId":2,
        
        "labelName":"休息" ，//标签的名字

        "percents":43.72,//45%该项为45，最多保留到小数点后2位，

        "type":"timeSaving"||"timeOut"//如果相比日程安排节约时间该项为timeSaving，如果超时为timeOut

    }...]
}
//如果前端请求是"type":"detailedAnalysis"
{
​
    "chartInfor":[{
    	"labelId":1,
​
        "labelName":"学习"，//标签的名字
​
        "percents":[0.45,0.13,0.42], //浮点型数组形式 分别表示少于，符合，超过预计时间的比例
​
    },
​
    {
        "labelId":2,
​
        "labelName":"休息" ，//标签的名字
​
        "percents":[0.45,0.13,0.42], //浮点型数组形式 分别表示少于，符合，超过预计时间的比例
​
    }...]
​
}
//如果前端请求是"type":"densityAnalysis"
{
​
    "density":6.88， //表示迄今为止，平均每小时完成的事件数,保留到小数点后2位
​
    “chartInfor”:[{   //数组总共7项，对应7个时间段的行为密集程度
​
        "period":"凌晨"，//时间段的名称
​
        "percents":5.50, //表示该时间段每小时平均做的事件数，保留到小数点后2位
​
    },
​
    {
​
    "period":"早晨"，//时间段的名称
​
    "percents":5.50, //表示该时间段每小时平均做的事件数，保留到小数点后2位
​
    }..]
​
}
```

