package com.srdp.admin.time_manager.model.moudle;

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
	
	public void setToken(String token) {this.token = token;}//��ʱδ����Ƿ���Ҫ
	public String getToken() {return token;}
}
