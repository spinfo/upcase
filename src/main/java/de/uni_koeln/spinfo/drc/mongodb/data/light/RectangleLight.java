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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + height;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + width;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RectangleLight other = (RectangleLight) obj;
		if (height != other.height)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (width != other.width)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RectangleLight [x=" + x + ", y=" + y + ", width=" + width
				+ ", height=" + height + "]";
	}
	
	

}
