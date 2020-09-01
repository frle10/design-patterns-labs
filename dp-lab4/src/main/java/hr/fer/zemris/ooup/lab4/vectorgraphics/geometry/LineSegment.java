package hr.fer.zemris.ooup.lab4.vectorgraphics.geometry;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.ooup.lab4.vectorgraphics.interfaces.GraphicalObject;
import hr.fer.zemris.ooup.lab4.vectorgraphics.interfaces.Renderer;

/**
 * This class models a line in 2D space defined by its
 * starting and ending {@link Point}.
 * 
 * @author Ivan Skorupan
 */
public class LineSegment extends AbstractGraphicalObject {
	
	public LineSegment() {
		this(new Point(0, 0), new Point(10, 0));
	}
	
	/**
	 * Constructs a new {@link LineSegment} object with initialized
	 * starting and ending point.
	 * 
	 * @param startPoint - start point of this line
	 * @param endPoint - end point of this line
	 * @throws NullPointerException if any of the arguments is <code>null</code>
	 */
	public LineSegment(Point startPoint, Point endPoint) {
		super(Arrays.asList(startPoint, endPoint));
	}

	@Override
	public Rectangle getBoundingBox() {
		Point startPoint = getHotPoint(0);
		Point endPoint = getHotPoint(1);
		
		int x = Math.min(startPoint.getX(), endPoint.getX());
		int y = Math.min(startPoint.getY(), endPoint.getY());
		
		int width = Math.abs(startPoint.getX() - endPoint.getX());
		int height = Math.abs(startPoint.getY() - endPoint.getY());
		
		return new Rectangle(x, y, width, height);
	}

	@Override
	public double selectionDistance(Point mousePoint) {
		return GeometryUtil.distanceFromLineSegment(getHotPoint(0), getHotPoint(1), mousePoint);
	}

	@Override
	public String getShapeName() {
		return "Linija";
	}

	@Override
	public GraphicalObject duplicate() {
		return new LineSegment(getHotPoint(0).copy(), getHotPoint(1).copy());
	}

	@Override
	public void render(Renderer r) {
		r.drawLine(getHotPoint(0), getHotPoint(1));
	}

	@Override
	public String getShapeID() {
		return "@LINE";
	}

	@Override
	public void save(List<String> rows) {
		Point s = getHotPoint(0);
		Point e = getHotPoint(1);
		String row = getShapeID() + " " + s.getX() + " " + s.getY() + " " + e.getX() + " " + e.getY();
		rows.add(row);
	}

	@Override
	public void load(Stack<GraphicalObject> stack, String data) {
		String[] tokens = data.split(" ");
		int x1 = Integer.parseInt(tokens[0]);
		int y1 = Integer.parseInt(tokens[1]);
		int x2 = Integer.parseInt(tokens[2]);
		int y2 = Integer.parseInt(tokens[3]);
		
		stack.push(new LineSegment(new Point(x1, y1), new Point(x2, y2)));
	}
}
