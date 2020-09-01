package hr.fer.zemris.ooup.lab4.vectorgraphics.geometry;

/**
 * 
 * 
 * @author Ivan Skorupan
 */
public class Rectangle {
	
	private int x;
	
	private int y;
	
	private int width;
	
	private int height;
	
	public Rectangle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public Rectangle union(Rectangle other) {
		int x = Math.min(this.x, other.x);
		int y = Math.min(this.y, other.y);
		int width = Math.max(this.x + this.width, other.x + other.width) - x;
		int height = Math.max(this.y + this.height, other.y + other.height) - y;
		
		return new Rectangle(x, y, width, height);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	@Override
	public String toString() {
		return "Rectangle [x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + "]";
	}
	
}
