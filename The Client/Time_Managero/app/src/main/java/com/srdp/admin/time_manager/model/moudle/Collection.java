package com.srdp.admin.time_manager.model.moudle;

public class Collection 
{
	private int id;
	private int idUser;
	private int idTS;
	
	public Collection(int id, int idUser, int idTS)
	{
		this.id = id;
		this.idUser = idUser;
		this.idTS = idTS;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public int getIdTS() {
		return idTS;
	}

	public void setIdTS(int idTS) {
		this.idTS = idTS;
	}

	public int getId() {
		return id;
	}
	
}
