package hr.fer.zemris.ooup.lab4.vectorgraphics.interfaces;

/**
 * Models objects that listen to changes on a geometric object.
 * 
 * @author Ivan Skorupan
 */
public interface GraphicalObjectListener {
	
	/**
	 * Gets called when a property change occurs on a geometric object
	 * <code>o</code>.
	 * 
	 * @param o - geometric object whose properties changed
	 */
	void graphicalObjectChanged(GraphicalObject o);
	
	/**
	 * 
	 * 
	 * @param o
	 */
	void graphicalObjectSelectionChanged(GraphicalObject o);
	
}
