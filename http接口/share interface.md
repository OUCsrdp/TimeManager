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
//gpafail��ʾgpa����
}
```

#### 2.collection
```json
{
	"userId"://��ǰ�û�id
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
	"userId"://��ǰ�û�id
	"idST"://�����ʱ������id
	"token":	
}
{
	"status":"success"||"fail"||"likedfail"||"unlogin"
	//likedfail��ʾ�Ѿ������
}
```

#### 4.getlist
```json
{
	"operation":"getList"
	"major":
	//Major����Ϊĳ��רҵ����,all��ʾ����רҵ
	"token":
}
{
	"status":"success"||"fail"||"unlogin",
	"jsonArray":
	[{
	��name��:������������ӡ�,
	��userId��://�����ʱ�������û�id
	��image��://ͷ���ַ
	��school��:���й������ѧ��,
	��major��:�������ϵ��,
	��summary��:�����쳬������ʵ�ġ�,
	��timeShared��:��2017��5��4�ա�,//ʱ������ָ�ʽ��
	��thumbup��:333
	��idTS��://ʱ�������id
	��idST��://�ѷ����ʱ�������id
	},
	{
	��name��:������������ӡ�,
	��userId��://�����ʱ�������û�id
	��image��://ͷ���ַ
	��school��:���й������ѧ��,
	��major��:�������ϵ��,
	��summary��:�������ҡ�,
	��timeShared��:��2017��5��5�ա�,//ʱ������ָ�ʽ��
	��thumbup��:233
	},
	]
}
```

#### 5.��ȡĳ�û��������ղ�
```json
{
	"operation":"getCollection"
	"userId":
	"token":
}
��������
```

#### 6. ��ȡ��̬����רҵ�б�

```json
method=getSearchMajors
{
    "majorKeyword":"����"
}
{
    "status":"success"||"fail"||"likedfail"||"unlogin"
    "majorKeyword":"����",
    "majors":[
    {"major":"������Ϣ"}
    ,{ "major":"������Ϣ����"}
    ]
}
```

