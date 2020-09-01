package hr.fer.zemris.ooup.lab4.vectorgraphics.tools;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.ooup.lab4.vectorgraphics.geometry.LineSegment;
import hr.fer.zemris.ooup.lab4.vectorgraphics.geometry.Point;
import hr.fer.zemris.ooup.lab4.vectorgraphics.interfaces.GraphicalObject;
import hr.fer.zemris.ooup.lab4.vectorgraphics.interfaces.Renderer;
import hr.fer.zemris.ooup.lab4.vectorgraphics.models.DocumentModel;

public class EraserState extends IdleState {
	
	private static final double ERASE_DELTA = 1.0;
	
	private List<Point> linePoints = new ArrayList<>();
	
	private List<GraphicalObject> segments = new ArrayList<>();
	
	private List<GraphicalObject> objectsToRemove = new ArrayList<>();
	
	public EraserState(DocumentModel model) {
		super(model);
	}
	
	@Override
	public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
		linePoints.add(mousePoint);
		testForRemoval(mousePoint);
	}

	@Override
	public void mouseUp(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
		for (GraphicalObject object : objectsToRemove) {
			model.removeGraphicalObject(object);
		}
		
		objectsToRemove.clear();
		linePoints.clear();
		for (GraphicalObject segment : segments) {
			model.removeGraphicalObject(segment);
		}
		
		segments.clear();
	}

	@Override
	public void mouseDragged(Point mousePoint) {
		linePoints.add(mousePoint);
		LineSegment segment = new LineSegment(linePoints.get(linePoints.size() - 2), mousePoint);
		segments.add(segment);
		model.addGraphicalObject(segment);
		testForRemoval(mousePoint);
	}
	
	private void testForRemoval(Point point) {
		for (GraphicalObject object : model.list()) {
			if (object.selectionDistance(point) < ERASE_DELTA) {
				objectsToRemove.add(object);
			}
		}
	}
	
	@Override
	public void afterDraw(Renderer r) {
		for (int i = 0; i < linePoints.size() - 1; i++) {
			r.drawLine(linePoints.get(i), linePoints.get(i + 1));
		}
	}
	
}
