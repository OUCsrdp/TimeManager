### ShareTableServlet
#### 1.share
```json
{
	"idTs":
	"operation":"share"
	"token":
}
{
	"status":"success"||"gpafail"||"fail"||"unlogin"
//gpafail表示gpa不足
}
```

#### 2.collection
```json
{
	"userId"://当前用户id
	"idST":
	"token":
}
{
	"status":"success"||"fail"||"unlogin"
}
```

#### 3.like
```json
{
	"userId"://当前用户id
	"idST"://分享的时间分配表id
	"token":	
}
{
	"status":"success"||"fail"||"likedfail"||"unlogin"
	//likedfail表示已经点过赞
}
```

#### 4.getlist
```json
{
	"operation":"getList"
	"major":
	//Major可能为某个专业名称,all表示不限专业
	"token":
}
{
	"status":"success"||"fail"||"unlogin",
	"jsonArray":
	[{
	“name”:”爱吃肉的兔子”,
	“userId”://分享的时间分配表用户id
	“image”://头像地址
	“school”:”中国海洋大学”,
	“major”:”计算机系”,
	“summary”:”今天超超级充实的”,
	“timeShared”:”2017年5月4日”,//时间就这种格式吧
	“thumbup”:333
	“idTS”://时间分配表的id
	“idST”://已分享的时间分配表的id
	},
	{
	“name”:”爱吃肉的兔子”,
	“userId”://分享的时间分配表用户id
	“image”://头像地址
	“school”:”中国海洋大学”,
	“major”:”计算机系”,
	“summary”:”又是我”,
	“timeShared”:”2017年5月5日”,//时间就这种格式吧
	“thumbup”:233
	},
	]
}
```

#### 5.获取某用户的所有收藏
```json
{
	"operation":"getCollection"
	"userId":
	"token":
}
返回如上
```

#### 6. 获取动态搜索专业列表

```json
method=getSearchMajors
{
    "majorKeyword":"电子"
}
{
    "status":"success"||"fail"||"likedfail"||"unlogin"
    "majorKeyword":"电子",
    "majors":[
    {"major":"电子信息"}
    ,{ "major":"电子信息工程"}
    ]
}
```

