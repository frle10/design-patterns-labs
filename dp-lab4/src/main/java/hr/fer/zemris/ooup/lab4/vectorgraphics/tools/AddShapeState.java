package hr.fer.zemris.ooup.lab4.vectorgraphics.tools;

import hr.fer.zemris.ooup.lab4.vectorgraphics.geometry.Point;
import hr.fer.zemris.ooup.lab4.vectorgraphics.interfaces.GraphicalObject;
import hr.fer.zemris.ooup.lab4.vectorgraphics.models.DocumentModel;

public class AddShapeState extends IdleState {
	
	private GraphicalObject prototype;
	
	public AddShapeState(DocumentModel model, GraphicalObject prototype) {
		super(model);
		this.prototype = prototype;
	}

	@Override
	public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
		GraphicalObject copy = prototype.duplicate();
		copy.translate(mousePoint);
		model.addGraphicalObject(copy);
	}
	
}
