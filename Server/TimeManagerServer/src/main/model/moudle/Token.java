package main.model.moudle;

public class Token 
{
	private int id;
	private String token;
	
	public Token(int id, String token)
	{
		this.id = id;
		this.token = token;
	}
	
	public int getId() {return id;}
	
	public void setToken(String token) {this.token = token;}//暂时未想好是否需要
	public String getToken() {return token;}

	public void setId(int id) {
		this.id = id;
	}
}
