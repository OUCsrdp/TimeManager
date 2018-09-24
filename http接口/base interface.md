### 1、userServlet

#### 1.1 register

请求：
{
	"operation":"register",
	"verify":"4635",
	"id":233,
	"numStu":"13467"//user的其他基本信息	
}
返回
{
	"status":"success"||"usernamefail"||"verifyfail"
}

#### 1.2 login

{
	"operation":"login",
	"name":"lajfla",
	"pwd":"fdlvnv",
	"verify":"4353",
	"token":"gprejgp"
}
{
	"status":"success"||”usernamefail"||"passwordfail","verifyfail",
	"token":"rgegg"
	 //user的基本信息
}

#### 1.3 changeInfor

{
	"operation":"infor"
	"token",
	"id":233,
	"nameStu":"5240",
}
{
	"status":"success"||"fail"||"unlogin"
}

#### 1.4 getUser

{
	"operation":"getUser",
	"token":,
	"name":"爱吃肉的兔子"
}
{
	user转化成的json字符串加上
	"status":"success"||"fail"||"unlogin"
}

#### 1.5 logout

{
	"operation":"logout",
	"token":,
	"id":13555
}
{
	"status":"success"||"fail"||"unlogin"
}



### 2、TimeSharingServlet

?token userId year month day
{
	"id":
	"idUser":
	"date":
	"weekday":
	"affair":[{},{}]
	"saffair":[{},{}]
	"status":"success"||"fail"||"unlogin"
}

### 3、affairServlet

#### 3.1 创建修改

{
	"token":
	"sign1"://1 means modify,0 means create
	"sign2"://1 means affair,0 means saffair
	"date":
	"username":
	//各种affair消息
	
}
{
	"status":"success"||"fail"||"unlogin"
}

#### 3.2 删除

{
	"token":
	"sign1":-1,
	"id":
	"isAffair":
}
{
	"status":"success"||"fail"||"unlogin"
}

#### 3.3 猜日程

{
	"token":
	"name":
	"date":
	"time":
}
{
	"status":"success"||"fail"||"unlogin"
	"hasSAffair":1表示有同名日程，0表示没有
	//各种s_affair信息
}

#### 3.4 修改时间

{
	"token":
	"sign2"://1 means affair,0 means saffair
	"id"://表示要修改的affair或者saffair的id

​	"timeEnd"://什么时候结束该事务

}

{
	"status":"success"||"fail"||"unlogin"
}