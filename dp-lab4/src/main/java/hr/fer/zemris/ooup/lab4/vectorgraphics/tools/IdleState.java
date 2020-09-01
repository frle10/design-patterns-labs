package hr.fer.zemris.ooup.lab4.vectorgraphics.tools;

import java.util.Objects;

import hr.fer.zemris.ooup.lab4.vectorgraphics.geometry.Point;
import hr.fer.zemris.ooup.lab4.vectorgraphics.interfaces.GraphicalObject;
import hr.fer.zemris.ooup.lab4.vectorgraphics.interfaces.Renderer;
import hr.fer.zemris.ooup.lab4.vectorgraphics.interfaces.Tool;
import hr.fer.zemris.ooup.lab4.vectorgraphics.models.DocumentModel;

/**
 * This class models an abstract tool for drawing geometric objects.
 * <p>
 * Since all our three supported tools share certain same behavior patterns,
 * we can have this abstract class to minimize code duplication.
 * 
 * @author Ivan Skorupan
 */
public class IdleState implements Tool {
	
	/**
	 * Drawing model to operate upon.
	 */
	protected DocumentModel model;
	
	/**
	 * Constructs a new {@link IdleState} object.
	 * 
	 * @param model - drawing model to add a new object to
	 * @param canvas - canvas on which to paint the objects
	 * @throws NullPointerException if any of the arguments is <code>null</code>
	 */
	public IdleState(DocumentModel model) {
		this.model = Objects.requireNonNull(model);
	}

	@Override
	public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
	}

	@Override
	public void mouseUp(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
	}

	@Override
	public void mouseDragged(Point mousePoint) {
	}

	@Override
	public void keyPressed(int keyCode) {
	}

	@Override
	public void afterDraw(Renderer r, GraphicalObject go) {
	}

	@Override
	public void afterDraw(Renderer r) {
	}

	@Override
	public void onLeaving() {
	}
	
}
