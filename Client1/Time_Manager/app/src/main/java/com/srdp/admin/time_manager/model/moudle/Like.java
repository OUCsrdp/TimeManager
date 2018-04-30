package com.srdp.admin.time_manager.model.moudle;

public class Like 
{
	private long id;
	private long idUser;
	private long idTS;
	
	public Like(long id, long idUser, long idTS)
	{
		this.id = id;
		this.idUser = idUser;
		this.idTS = idTS;
	}

	public long getIdUser() {
		return idUser;
	}

	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}

	public long getIdTS() {
		return idTS;
	}

	public void setIdTS(long idTS) {
		this.idTS = idTS;
	}

	public long getId() {
		return id;
	}
	
	
}
