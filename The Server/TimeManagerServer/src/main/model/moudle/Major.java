package main.model.moudle;

public class Major {

	private int id;
	private String name;
	
	public Major(int id, String name)
	{
		this.id = id;
		this.name = name;
	}
	
	public int getId() {return id;}
	
	public void setMajor(String name) {this.name = name;}//��ʱδ����Ƿ���Ҫ
	public String getMajor() {return name;}

	public void setId(int id) {
		this.id = id;
	}
}
