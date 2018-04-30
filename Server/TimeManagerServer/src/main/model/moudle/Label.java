package main.model.moudle;

public class Label 
{
	private long id;
	
	private String name;
	
	private String image;
	
	private int color;
	
	public Label(long id, String name, String image, int color)
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

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public long getId() {
		return id;
	}
}
