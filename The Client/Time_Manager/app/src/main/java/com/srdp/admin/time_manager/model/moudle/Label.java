package com.srdp.admin.time_manager.model.moudle;

import org.litepal.crud.LitePalSupport;

public class Label extends LitePalSupport
{
	private int id;
	
	private String name;
	
	private String image;
	
	private String color;

	public Label(){}
	public Label(int id, String name, String image, String color)
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id=id;
	}
}
