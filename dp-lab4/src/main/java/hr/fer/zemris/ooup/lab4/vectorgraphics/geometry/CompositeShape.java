package hr.fer.zemris.ooup.lab4.vectorgraphics.geometry;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.ooup.lab4.vectorgraphics.interfaces.GraphicalObject;
import hr.fer.zemris.ooup.lab4.vectorgraphics.interfaces.Renderer;

public class CompositeShape extends AbstractGraphicalObject {
	
	private List<GraphicalObject> children = new ArrayList<>();
	
	public CompositeShape(List<GraphicalObject> children) {
		this.children = children;
	}
	
	public List<GraphicalObject> getChildren() {
		return children;
	}

	@Override
	public void translate(Point point) {
		for (GraphicalObject object : children) {
			object.translate(point);
		}
	}
	
	@Override
	public Rectangle getBoundingBox() {
		Rectangle union = children.get(0).getBoundingBox();
		for (int i = 1; i < children.size(); i++) {
			union = union.union(children.get(i).getBoundingBox());
		}
		
		return union;
	}

	@Override
	public double selectionDistance(Point mousePoint) {
		double minDistance = children.get(0).selectionDistance(mousePoint);
		for (int i = 1; i < children.size(); i++) {
			double distance = children.get(i).selectionDistance(mousePoint);
			if (distance < minDistance) {
				minDistance = distance;
			}
		}
		
		return minDistance;
	}

	@Override
	public void render(Renderer r) {
		for (GraphicalObject object : children) {
			object.render(r);
		}
	}

	@Override
	public String getShapeName() {
		return "Kompozit";
	}

	@Override
	public GraphicalObject duplicate() {
		return new CompositeShape(new ArrayList<>(children));
	}

	@Override
	public String getShapeID() {
		return "@COMP";
	}

	@Override
	public void save(List<String> rows) {
		for (GraphicalObject child : children) {
			child.save(rows);
		}
		
		String row = getShapeID() + " " + children.size();
		rows.add(row);
	}

	@Override
	public void load(Stack<GraphicalObject> stack, String data) {
		List<GraphicalObject> children = new ArrayList<>();
		int numOfObjs = Integer.parseInt(data);
		for (int i = 0; i < numOfObjs; i++) {
			children.add(0, stack.pop());
		}
		
		GraphicalObject composite = new CompositeShape(children);
		stack.push(composite);
	}
	
}
