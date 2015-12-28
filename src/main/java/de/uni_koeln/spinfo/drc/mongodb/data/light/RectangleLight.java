package de.uni_koeln.spinfo.drc.mongodb.data.light;

import de.uni_koeln.spinfo.drc.mongodb.data.Rectangle;

public class RectangleLight {

	private int x;
	private int y;
	private int width;
	private int height;
	private String id;


	public RectangleLight(final String id, Rectangle rectangle) {
		this.id = id;
		this.x = rectangle.getX();
		this.y = rectangle.getY();
		this.width = rectangle.getWidth();
		this.height = rectangle.getHeight();
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

}
