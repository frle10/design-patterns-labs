package hr.fer.zemris.ooup.lab4.vectorgraphics.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.ooup.lab4.vectorgraphics.geometry.Point;
import hr.fer.zemris.ooup.lab4.vectorgraphics.interfaces.DocumentModelListener;
import hr.fer.zemris.ooup.lab4.vectorgraphics.interfaces.GraphicalObject;
import hr.fer.zemris.ooup.lab4.vectorgraphics.interfaces.GraphicalObjectListener;

/**
 * 
 * 
 * @author Ivan Skorupan
 */
public class DocumentModel {
	
	public final static double SELECTION_PROXIMITY = 10;
	
	private List<GraphicalObject> objects = new ArrayList<>();
	
	private List<GraphicalObject> roObjects = Collections.unmodifiableList(objects);
	
	private List<DocumentModelListener> listeners = new ArrayList<>();
	
	private List<GraphicalObject> selectedObjects = new ArrayList<>();
	
	private List<GraphicalObject> roSelectedObjects = Collections.unmodifiableList(selectedObjects);
	
	private final GraphicalObjectListener goListener = new GraphicalObjectListener() {

		@Override
		public void graphicalObjectChanged(GraphicalObject o) {
			notifyListeners();
		}

		@Override
		public void graphicalObjectSelectionChanged(GraphicalObject o) {
			if (!o.isSelected()) {
				selectedObjects.remove(o);
			} else if (o.isSelected() && !selectedObjects.contains(o)) {
				selectedObjects.add(o);
			}
			notifyListeners();
		}
	};
	
	public DocumentModel() {
	}
	
	public void clear() {
		objects.clear();
		selectedObjects.clear();
		notifyListeners();
	}
	
	public void addGraphicalObject(GraphicalObject obj) {
		objects.add(obj);
		obj.addGraphicalObjectListener(goListener);
		notifyListeners();
	}
	
	public void removeGraphicalObject(GraphicalObject obj) {
		if (objects.remove(obj)) {
			selectedObjects.remove(obj);
			notifyListeners();
		}
	}
	
	public List<GraphicalObject> list() {
		return roObjects;
	}
	
	public void addDocumentModelListener(DocumentModelListener l) {
		listeners.add(l);
	}
	
	public void removeDocumentModelListener(DocumentModelListener l) {
		listeners.remove(l);
	}
	
	public void notifyListeners() {
		for (DocumentModelListener l : listeners) {
			l.documentChange();
		}
	}
	
	public List<GraphicalObject> getSelectedObjects() {
		return roSelectedObjects;
	}
	
	public void increaseZ(GraphicalObject go) {
		int oldIndex = objects.indexOf(go);
		if (oldIndex < objects.size() - 1) {
			objects.remove(oldIndex);
			objects.add(oldIndex + 1, go);
			notifyListeners();
		}
	}
	
	public void decreaseZ(GraphicalObject go) {
		int oldIndex = objects.indexOf(go);
		if (oldIndex > 0) {
			objects.remove(oldIndex);
			objects.add(oldIndex - 1, go);
			notifyListeners();
		}
	}
	
	public GraphicalObject findSelectedGraphicalObject(Point mousePoint) {
		if (objects.size() == 0) return null;
		
		GraphicalObject selectedObject = objects.get(0);
		double minDistance = objects.get(0).selectionDistance(mousePoint);
		
		for (int i = 1; i < objects.size(); i++) {
			double distance = objects.get(i).selectionDistance(mousePoint);
			if (distance < minDistance) {
				minDistance = distance;
				selectedObject = objects.get(i);
			}
		}
		
		if (minDistance > SELECTION_PROXIMITY) return null;
		return selectedObject;
	}
	
	public int findSelectedHotPoint(GraphicalObject object, Point mousePoint) {
		double minDistance = object.getHotPointDistance(0, mousePoint);
		int minIndex = 0;
		
		for (int i = 1; i < object.getNumberOfHotPoints(); i++) {
			double distance = object.getHotPointDistance(i, mousePoint);
			if (distance < minDistance) {
				minDistance = distance;
				minIndex = i;
			}
		}
		
		if (minDistance > SELECTION_PROXIMITY) return -1;
		return minIndex;
	}

}
