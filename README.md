# TimeManager
* 客户端的文件在Client下，服务器端文件在Server下（其中的Time_ManagerO和TimeManagerServerO文件是前后端交互重构前的备份）

* 服务器端所需要的jar包已经放在项目的文件夹下，**无需**再次引入，如果还是报错，可以把项目jar文件夹中的包放在tomcat本地目录的lib文件夹里

* **前端**主要修改内容：增加行为模式分析图表，修改计时功能，修改交互时的URL（增加method，便于servlet处理),token和user信息初始化的处理

* **后端**主要修改内容：修改servlet（增加session拦截部分，编写继承httpservlet基类的baseservlet类，修改service代码处理拼接token和userId）

