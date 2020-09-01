package hr.fer.zemris.ooup.lab4.vectorgraphics.interfaces.copy;

import hr.fer.zemris.ooup.lab4.vectorgraphics.geometry.Point;

/**
 * Models objects that act as drawing tools for geometric objects.
 * <p>
 * The tools perform actions on mouse events.
 * 
 * @author Ivan Skorupan
 */
public interface Tool {
	
	void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown);
	
	void mouseUp(Point mousePoint, boolean shiftDown, boolean ctrlDown);
	
	void mouseDragged(Point mousePoint);
	
	void keyPressed(int keyCode);
	
	void afterDraw(Renderer r, GraphicalObject go);
	
	void afterDraw(Renderer r);
	
	void onLeaving();
}
