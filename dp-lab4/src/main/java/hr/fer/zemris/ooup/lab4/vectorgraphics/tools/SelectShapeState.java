package hr.fer.zemris.ooup.lab4.vectorgraphics.tools;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.ooup.lab4.vectorgraphics.geometry.CompositeShape;
import hr.fer.zemris.ooup.lab4.vectorgraphics.geometry.Point;
import hr.fer.zemris.ooup.lab4.vectorgraphics.geometry.Rectangle;
import hr.fer.zemris.ooup.lab4.vectorgraphics.interfaces.GraphicalObject;
import hr.fer.zemris.ooup.lab4.vectorgraphics.interfaces.Renderer;
import hr.fer.zemris.ooup.lab4.vectorgraphics.models.DocumentModel;

/**
 * 
 * 
 * @author Ivan Skorupan
 */
public class SelectShapeState extends IdleState {
	
	private static final int HOTPOINT_WIDTH = 10;
	
	private static final int HOTPOINT_HEIGHT = 10;
	
	public SelectShapeState(DocumentModel model) {
		super(model);
	}
	
	@Override
	public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
		GraphicalObject selectedObject = model.findSelectedGraphicalObject(mousePoint);
		List<GraphicalObject> selectedObjects = new ArrayList<>(model.getSelectedObjects());
		
		if (selectedObject != null) {
			if (!ctrlDown) {
				if (!selectedObjects.isEmpty()) {
					deselectObjects(selectedObjects);
				}
			}
			
			selectedObject.setSelected(true);
		} else {
			deselectObjects(selectedObjects);
		}
	}
	
	private void deselectObjects(List<GraphicalObject> selectedObjects) {
		for (GraphicalObject object : selectedObjects) {
			object.setSelected(false);
		}
	}

	@Override
	public void mouseDragged(Point mousePoint) {
		List<GraphicalObject> selectedObjects = model.getSelectedObjects();
		if (selectedObjects.size() == 1) {
			GraphicalObject object = selectedObjects.get(0);
			int selectedHotPointIndex = model.findSelectedHotPoint(object, mousePoint);
			
			Point hotPoint = (selectedHotPointIndex != -1) ? object.getHotPoint(selectedHotPointIndex) : null;
			if (hotPoint != null) {
				object.setHotPoint(selectedHotPointIndex, hotPoint.translate(mousePoint.difference(hotPoint)));
			}
		}
	}

	@Override
	public void keyPressed(int keyCode) {
		List<GraphicalObject> selectedObjects = new ArrayList<>(model.getSelectedObjects());
		
		if (keyCode == KeyEvent.VK_G) {
			List<GraphicalObject> children = new ArrayList<>(selectedObjects);
			for (GraphicalObject child : children) {
				model.removeGraphicalObject(child);
			}
			
			GraphicalObject composite = new CompositeShape(children);
			model.addGraphicalObject(composite);
			composite.setSelected(true);
		} else if (keyCode == KeyEvent.VK_U) {
			if (selectedObjects.size() == 1 && selectedObjects.get(0) instanceof CompositeShape) {
				GraphicalObject composite = selectedObjects.get(0);
				model.removeGraphicalObject(composite);
				
				List<GraphicalObject> children = ((CompositeShape)composite).getChildren();
				for (GraphicalObject child : children) {
					model.addGraphicalObject(child);
					child.setSelected(true);
				}
			}
		}
		
		for (GraphicalObject object : selectedObjects) {
			if (keyCode == KeyEvent.VK_UP) {
				object.translate(new Point(0, -1));
			} else if (keyCode == KeyEvent.VK_DOWN) {
				object.translate(new Point(0, 1));
			} else if (keyCode == KeyEvent.VK_LEFT) {
				object.translate(new Point(-1, 0));
			} else if (keyCode == KeyEvent.VK_RIGHT) {
				object.translate(new Point(1, 0));
			} else if (keyCode == KeyEvent.VK_PLUS) {
				model.increaseZ(object);
			} else if (keyCode == KeyEvent.VK_MINUS) {
				model.decreaseZ(object);
			}
		}
	}

	@Override
	public void afterDraw(Renderer r) {
		List<GraphicalObject> selectedObjects = model.getSelectedObjects();
		for (GraphicalObject object : selectedObjects) {
			drawRectangle(r, object.getBoundingBox());
		}
		
		if (selectedObjects.size() == 1) {
			GraphicalObject selectedObject = selectedObjects.get(0);
			for (int i = 0; i < selectedObject.getNumberOfHotPoints(); i++) {
				Point hotPoint = selectedObject.getHotPoint(i);
				drawRectangle(r, new Rectangle(hotPoint.getX() - 5, hotPoint.getY() - 5, HOTPOINT_WIDTH, HOTPOINT_HEIGHT));
			}
		}
	}
	
	/**
	 * 
	 * 
	 * @param r
	 * @param rec
	 */
	private void drawRectangle(Renderer r, Rectangle rec) {
		Point upperLeft = new Point(rec.getX(), rec.getY());
		Point upperRight = new Point(upperLeft.getX() + rec.getWidth(), rec.getY());
		Point bottomLeft = new Point(upperLeft.getX(), upperLeft.getY() + rec.getHeight());
		Point bottomRight = new Point(bottomLeft.getX() + rec.getWidth(), bottomLeft.getY());
		
		r.drawLine(upperLeft, upperRight);
		r.drawLine(upperRight, bottomRight);
		r.drawLine(bottomRight, bottomLeft);
		r.drawLine(bottomLeft, upperLeft);
	}

	@Override
	public void onLeaving() {
		deselectObjects(new ArrayList<GraphicalObject>(model.getSelectedObjects()));
	}
	
}
