package com.srdp.admin.time_manager.model.moudle;

import org.litepal.crud.LitePalSupport;

public class Label extends LitePalSupport
{
	private int id;
	
	private String name;
	
	private int image;
	
	private int color;

	public Label(){}
	public Label(int id, String name, int image, int color)
	{
		this.id = id;
		this.name = name;
		this.image = image;
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id=id;
	}
}
