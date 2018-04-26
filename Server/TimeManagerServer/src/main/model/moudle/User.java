package main.model.moudle;

public class User 
{
	private long id;
	private String numStu;
	private String school;
	private String major;
	private float GPA;
	private String name;//用户名？账户名？
	private String pwd;
	private String image;
	private String timeRegister;
	
	public User(long id, String numStu, String school, String major, float GPA, String name, String pwd, String image, String timeRegister)
	{
		this.id = id;
		this.numStu = numStu;
		this.school = school;
		this.major = major;
		this.GPA = GPA;
		this.name = name;
		this.pwd = pwd;
		this.image = image;
		this.timeRegister = timeRegister;
	}
	
	public long getid() {return id;}
	
	public void setnumStu(String numStu) {this.numStu = numStu;}
	public String getnumStu() {return numStu;}
	
	public void setSchool(String school) {this.school = school;}
	public String getSchool() {return school;}
	
	public void setMajor(String major) {this.major = major;}
	public String getMajor() {return major;}
	
	public void setgpa(float GPA) {this.GPA = GPA;}
	public float getGPA() {return GPA;}
	
	public void setname(String name) {this.name = name;}
	public String getname() {return name;}
	
	public void setpwd(String pwd) {this.pwd = pwd;}
	public String getpwd() {return pwd;}
	
	public void setImage(String image) {this.image = image;}
	public String getImage() {return image;}
	
	public String getTimeRegister(){return timeRegister;}
}
