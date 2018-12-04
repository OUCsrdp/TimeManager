```json
method=getSTDetail
request
{
    "idTS":122
}

response
{
"id":,
"idUser":,
"date":,
"weekday":,
"affair":[{},{}] ,
"saffair":[{},{}] ,
"status":"success"||"fail"||"unlogin" ,
}

```

```json
使用Intent跨activity传值(从list页面传到详情页面)
{
    “name”:”爱吃肉的兔子”,
	“userId”://分享的时间分配表用户id
	“image”://头像地址
	“school”:”中国海洋大学”,
	“major”:”计算机系”,
    “summary”:”今天超超级充实的”,
    "gpa":3.7,
    “idTS”://时间分配表的id,
	“idST”://已分享的时间分配表的id
}


method=getSTDetail
request
{
    "idTS":122
}
response
{
    "status":"success"||"fail"||"unlogin",
    "date":"2018年11月5日",
    "affairs":[
        {
            "name"：//事务名称
            "idLabel":
            "timeStart":
            "timeEnd":
        },
        {
            "name"：
            "idLabel":
            "timeStart":
            "timeEnd":
        }
    ]
}
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

