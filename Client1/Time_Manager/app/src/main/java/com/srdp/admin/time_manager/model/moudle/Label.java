package com.srdp.admin.time_manager.model.moudle;

import java.awt.Color;
import java.awt.Image;

public class Label 
{
	private long id;
	
	private String name;
	
	private Image image;
	
	private Color color;
	
	public Label(long id, String name, Image image, Color color)
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

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public long getId() {
		return id;
	}
}
