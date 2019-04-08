获得验证码请求数据时加入：

"operation":"getVerify"

检查一下日程表，时间分配表的URL参数名 比如userId等



affairService

changeAffair和changeSAffair函数，成功返回affairId,失败返回0

增加一个函数

boolean changeAffairById(int isAffair,int id,string startTime,string endTime)

通过id来修改一个Affair的开始时间，结束时间

成功返回true，失败返回false



userService

token拼接用户Id后加密



