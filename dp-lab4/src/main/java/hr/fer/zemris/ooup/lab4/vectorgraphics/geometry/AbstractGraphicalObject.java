package hr.fer.zemris.ooup.lab4.vectorgraphics.geometry;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.ooup.lab4.vectorgraphics.interfaces.GraphicalObject;
import hr.fer.zemris.ooup.lab4.vectorgraphics.interfaces.GraphicalObjectListener;

/**
 * Models an abstract geometric object.
 * <p>
 * Each such object has a foreground color and a list of listeners that listen
 * to the object's property changes.
 * 
 * @author Ivan Skorupan
 */
public abstract class AbstractGraphicalObject implements GraphicalObject {
	
	private List<Point> hotPoints = new ArrayList<>();
	
	private List<Boolean> hotPointSelected = new ArrayList<>();
	
	private boolean selected;
	
	/**
	 * List of listeners interested in this geometric object changes.
	 */
	List<GraphicalObjectListener> listeners = new ArrayList<>();
	
	public AbstractGraphicalObject() {
	}
	
	protected AbstractGraphicalObject(List<Point> hotPoints) {
		this.hotPoints = hotPoints;
	}
	
	public Point getHotPoint(int index) {
		return hotPoints.get(index);
	}
	
	public void setHotPoint(int index, Point hotPoint) {
		hotPoints.set(index, hotPoint);
		notifyListeners();
	}
	
	public int getNumberOfHotPoints() {
		return hotPoints.size();
	}
	
	public double getHotPointDistance(int index, Point point) {
		return GeometryUtil.distanceFromPoint(hotPoints.get(index), point);
	}
	
	public boolean isHotPointSelected(int index) {
		return hotPointSelected.get(index);
	}
	
	public void setHotPointSelected(int index, boolean selected) {
		hotPointSelected.set(index, selected);
		notifySelectionListeners();
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
		notifySelectionListeners();
	}
	
	public void translate(Point point) {
		for (int i = 0; i < hotPoints.size(); i++) {
			Point hotPoint = hotPoints.get(i);
			setHotPoint(i, hotPoint.translate(point));
		}
		notifyListeners();
	}
	
	/**
	 * Helper method that notifies all {@link GraphicalObjectListener}
	 * objects about this object's change.
	 */
	protected void notifyListeners() {
		for(GraphicalObjectListener l : listeners) {
			l.graphicalObjectChanged(this);
		}
	}
	
	/**
	 * Helper method that notifies all {@link GraphicalObjectListener}
	 * objects about this object's selection change.
	 */
	protected void notifySelectionListeners() {
		for(GraphicalObjectListener l : listeners) {
			l.graphicalObjectSelectionChanged(this);
		}
	}
	
	/**
	 * Adds a geometric object listener to this object.
	 * 
	 * @param l - listener to be added
	 */
	@Override
	public void addGraphicalObjectListener(GraphicalObjectListener l) {
		listeners.add(Objects.requireNonNull(l));
	}
	
	/**
	 * Removes a geometric object listener from this object.
	 * 
	 * @param l - listener to be removed
	 */
	@Override
	public void removeGraphicalObjectListener(GraphicalObjectListener l) {
		listeners.remove(l);
	}
	
}
