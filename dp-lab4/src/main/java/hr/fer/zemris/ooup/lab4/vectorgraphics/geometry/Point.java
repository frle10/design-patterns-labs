package hr.fer.zemris.ooup.lab4.vectorgraphics.geometry;

/**
 * This class models a point in 2D space with integer
 * coordinates.
 * 
 * @author Ivan Skorupan
 */
public class Point {
	
	/**
	 * Coordinate x of this point.
	 */
	private int x;
	
	/**
	 * Coordinate y of this point.
	 */
	private int y;

	/**
	 * Constructs a new {@link Point} object with initialized
	 * coordinates.
	 * 
	 * @param x - x coordinate
	 * @param y - y coordinate
	 */
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns a new point that represents this point translated by point <code>dp</code>.
	 * 
	 * @param dp - point to translate this point by
	 * @return new point (this translated by <code>dp</code>)
	 */
	public Point translate(Point dp) {
		return new Point(x + dp.x, y + dp.y);
	}
	
	/**
	 * Returns a new point representing this point subtracted by point <code>p</code>.
	 * 
	 * @param p - point to subtract this point by
	 * @return subtracted point
	 */
	public Point difference(Point p) {
		return new Point(x - p.x, y - p.y);
	}
	
	public Point copy() {
		return new Point(x, y);
	}
	
	/**
	 * Getter for this point's x coordinate.
	 * 
	 * @return x coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * Getter for this point's y coordinate.
	 * 
	 * @return y coordinate
	 */
	public int getY() {
		return y;
	}
	
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
	
}
