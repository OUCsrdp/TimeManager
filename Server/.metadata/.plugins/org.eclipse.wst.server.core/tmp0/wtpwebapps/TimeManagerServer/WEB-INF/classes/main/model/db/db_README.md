# DateBaseManager
[TOC]
## SqlServerManager
* \- url(static String)
* \- username(static String)
* \- password(static String)
* \- con(static Connection)
* \- stmt(static Statement)
* \- rs(static ResultSet)
* \+ Connect():连接数据库
* \+ Create():创建数据表
* \+ Close():关闭数据库

##AffairManager
* \+ add():增加Affair,成功时返回true
* \+ delete():删除Affair,成功时返回true
* \+ change():更改Affair,成功时返回true
* \+ findWithId():使用Id进行查找,返回一个变量,没有时返回null
* \+ findWithIdLabel():使用Label的Id进行查找,返回动态数组,没有时返回null
* \+ findWithSatisfaction():使用满意度进行查找,返回动态数组,没有时返回null
* \+ findWithName():使用用户名进行查找,返回一个动态数组,没有时返回null

##CollectionManager
* \+ add():增加Collection,成功时返回true
* \+ delete():删除Collection,成功时返回true
* \+ change():更改Collection,成功时返回true
*  \+ findWithId():使用Id进行查找,返回一个变量,没有时返回null
* \+ findWithIdUser():使用User的Id进行查找,返回动态数组,没有时返回null
* \+ findWithIdTS():使用时间分配表的Id进行查找,返回动态数组,没有时返回null

##EventHourManager
* \+ add():增加EventHour,成功时返回true
* \+ delete():删除EventHour,成功时返回true
* \+ change():更改EventHour,成功时返回true
*  \+ findWithId():使用Id进行查找,返回一个变量,没有时返回null
*  \+ findWithIsWorkday:通过判断是否为工作日进行查找,返回动态数组,没有时返回null
*  \+ findWithTimeArea():通过时间段编号进行查找,返回动态数组,没有时返回null

##LabelManager
* \+ add():增加Label,成功时返回true
* \+ delete():删除Label,成功时返回true
* \+ change():更改Label,成功时返回true
* \+ findWithId():使用Id进行查找,返回一个变量,没有时返回null
* \+ findWithName():使用用户名进行查找,返回一个动态数组,没有时返回null
* \+ findWithColor():通过标签的颜色（即种类）进行查找,返回动态数组,没有时返回null

##LikeManager
* \+ add():增加Like,成功时返回true
* \+ delete():删除Like,成功时返回true
* \+ change():更改Like,成功时返回true
*  \+ findWithId():使用Id进行查找,返回一个变量,没有时返回null
* \+ findWithIdUser():使用User的Id进行查找,返回动态数组,没有时返回null
* \+ findWithIdTS():使用时间分配表的Id进行查找,返回动态数组,没有时返回null


##S_AffairManager
* \+ add():增加S_Affair,成功时返回true
* \+ delete():删除S_Affair,成功时返回true
* \+ change():更改S_Affair,成功时返回true
* \+ findWithId():使用Id进行查找,返回一个变量,没有时返回null
* \+ findWithIdLabel():使用Label的Id进行查找,返回动态数组,没有时返回null
* \+ findWithIdTS():使用时间分配表的Id进行查找,返回动态数组,没有时返回null
* \+ findWithIdS():使用日程表的Id进行查找,返回动态数组,没有时返回null
* \+ findWithSatisfaction():使用满意度进行查找,返回动态数组,没有时返回null
* \+ findWithName():使用用户名进行查找,返回一个动态数组,没有时返回null
*  \+ findWithIsImportant:通过判断是否为重要事件进行查找,返回动态数组,没有时返回null


##ScheduleManager
* \+ add():增加Schedule,成功时返回true
* \+ delete():删除Schedule,成功时返回true
* \+ change():更改Schedule,成功时返回true
* \+ findWithId():使用Id进行查找,返回一个变量,没有时返回null
* \+ findWithIdUser():使用User的Id进行查找,返回动态数组,没有时返回null
* \+ findWithDate():使用日期进行查找,返回动态数组,没有时返回null
* \+ findWithWeekday():使用星期几的编号进行查找,返回当前周的动态数组,没有时返回null(另一种按日期查找)

##SharedTableManager
* \+ add():增加Schedule,成功时返回true
* \+ delete():删除Schedule,成功时返回true
* \+ change():更改Schedule,成功时返回true
* \+ findWithId():使用Id进行查找,返回一个变量,没有时返回null
* \+ findWithTimeShared():通过分享的时间查找,返回一个人变量，没有时返回null
* \+ findWithTimeShared():通过点赞数查找,返回动态数组,没有时返回null

##TimeSharingManager
* \+ add():增加TimeSharing,成功时返回true
* \+ delete():删除TimeSharing,成功时返回true
* \+ change():更改TimeSharing,成功时返回true
*  \+ findWithId():使用Id进行查找,返回一个变量,没有时返回null
* \+ findWithIdUser():使用User的Id进行查找,返回动态数组,没有时返回null
* \+ findWithDate():使用日期进行查找,返回动态数组,没有时返回null
* \+ findWithWeekday():使用星期几的编号进行查找,返回当前周的动态数组,没有时返回null(另一种按日期查找)

##TokenManager
* \+ add():增加Token,成功时返回true
* \+ delete():删除Token,成功时返回true
* \+ change():更改Token,成功时返回true
*  \+ findWithId():使用Id进行查找,返回一个变量,没有时返回null
*  \+ findWithToken():使用Token进行查找,返回一个变量,没有时返回null

##UserManager
* \+ add():增加User,成功时返回true
* \+ delete():删除User,成功时返回true
* \+ change():更改User,成功时返回true
* \+ findWithId():使用Id进行查找,返回一个变量,没有时返回null
* \+ findWithNumStu():使用学号进行查找（在思考是否需要传入学校）,返回一个变量,没有时返回null
* \+ findWithSchool:通过学校进行查找,返回动态数组,没有时返回null
* \+ findWithMajor:通过专业进行查找(在思考是否需要传入学校),返回动态数组,没有时返回null
* \+ findWithName():使用用户名进行查找,返回一个动态数组,没有时返回null
* \+ findWithTimeRegister():通过注册时间查找,返回动态数组,没有时返回null