### affairService

1. changeAffair和changeSAffair函数，成功返回affairId,失败返回0

2. 增加一个函数

   boolean changeAffairById(int isAffair,int id,string startTime,string endTime)

   通过id来修改一个affair或sAffair的开始时间，结束时间

   成功返回true，失败返回false



### TokenUtil

1. String encodeToken(string token,int userId)

   拼接token和userId,然后加密，把加密后的token存入数据库

2. int decodeToken(string token)

   解密出userId