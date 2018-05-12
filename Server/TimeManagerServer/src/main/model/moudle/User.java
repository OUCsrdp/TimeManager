package main.model.moudle;


public class User 
{
	private int id;
	private String numStu;
	private String school;
	private String major;
	private float GPA;
	private String name;//用户名？账户名？
	private String pwd;
	private String image;
	private String timeRegister;
	
	public User(int id, String numStu, String school, String major, float GPA, String name, String pwd, String image,String timeRegister)
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
	
	public int getId() {return id;}
	
	public void setNumStu(String numStu) {this.numStu = numStu;}
	public String getNumStu() {return numStu;}
	
	public void setSchool(String school) {this.school = school;}
	public String getSchool() {return school;}
	
	public void setMajor(String major) {this.major = major;}
	public String getMajor() {return major;}
	
	public void setGPA(float GPA) {this.GPA = GPA;}
	public float getGPA() {return GPA;}
	
	public void setName(String name) {this.name = name;}
	public String getName() {return name;}
	
	public void setPwd(String pwd) {this.pwd = pwd;}
	public String getPwd() {return pwd;}
	
	public void setImage(String image) {this.image = image;}
	public String getImage() {return image;}
	
	public String getTimeRegister(){return timeRegister;}

	public void setId(int id) {
		this.id = id;
	}

	public void setTimeRegister(String timeRegister) {
		this.timeRegister = timeRegister;
	}
}
