package hr.fer.zemris.ooup.lab4.vectorgraphics.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;
import java.util.function.Supplier;

import javax.swing.JComponent;

import hr.fer.zemris.ooup.lab4.vectorgraphics.geometry.G2DRendererImpl;
import hr.fer.zemris.ooup.lab4.vectorgraphics.geometry.Point;
import hr.fer.zemris.ooup.lab4.vectorgraphics.interfaces.DocumentModelListener;
import hr.fer.zemris.ooup.lab4.vectorgraphics.interfaces.GraphicalObject;
import hr.fer.zemris.ooup.lab4.vectorgraphics.interfaces.Renderer;
import hr.fer.zemris.ooup.lab4.vectorgraphics.interfaces.Tool;
import hr.fer.zemris.ooup.lab4.vectorgraphics.models.DocumentModel;

/**
 * Models a custom component on which a user can draw with the mouse.
 * <p>
 * The canvas has an attributed {@link DrawingModel} which it uses
 * for geometric object management.
 * 
 * @author Ivan Skorupan
 */
public class DrawingCanvas extends JComponent implements DocumentModelListener {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Drawing model upon which this component operates.
	 */
	private DocumentModel model;
	
	/**
	 * A supplier of currently selected drawing tool.
	 */
	private Supplier<Tool> toolSupplier;
	
	/**
	 * Constructs a new {@link DrawingCanvas} object with provided
	 * tool supplier and a drawing model on which to operate.
	 * 
	 * @param model - drawing model to work with
	 * @param toolSupplier - supplier of currently selected drawing tool
	 * @throws NullPointerException if any of the arguments is <code>null</code>
	 */
	public DrawingCanvas(DocumentModel model, Supplier<Tool> toolSupplier) {
		setFocusable(true);
		this.model = Objects.requireNonNull(model);
		this.toolSupplier = Objects.requireNonNull(toolSupplier);
		model.addDocumentModelListener(this);
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DrawingCanvas.this.requestFocusInWindow();
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				toolSupplier.get().mouseDown(convertMouseEventToPoint(e), e.isShiftDown(), e.isControlDown());
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				toolSupplier.get().mouseUp(convertMouseEventToPoint(e), e.isShiftDown(), e.isControlDown());
			}
		});
		
		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				toolSupplier.get().mouseDragged(convertMouseEventToPoint(e));
			}
		});
		
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				toolSupplier.get().keyPressed(e.getKeyCode());
			}
		});
	}
	
	private Point convertMouseEventToPoint(MouseEvent e) {
		return new Point(e.getPoint().x, e.getPoint().y);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Renderer r = new G2DRendererImpl(g2d);
		for (GraphicalObject go : model.list()) {
			go.render(r);
			toolSupplier.get().afterDraw(r, go);
		}
		
		toolSupplier.get().afterDraw(r);
	}
	
	/**
	 * Getter for reference to the drawing model.
	 * 
	 * @return drawing model this component operates upon
	 */
	public DocumentModel getModel() {
		return model;
	}
	
	/**
	 * Getter for currently selected drawing tool supplier.
	 * 
	 * @return supplier for currently selected drawing tool
	 */
	public Supplier<Tool> getToolSupplier() {
		return toolSupplier;
	}

	@Override
	public void documentChange() {
		repaint();
	}
	
}
