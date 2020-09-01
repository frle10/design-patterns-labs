package hr.fer.zemris.ooup.lab4.vectorgraphics;

import static hr.fer.zemris.ooup.lab4.vectorgraphics.Util.*;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.ooup.lab4.vectorgraphics.geometry.LineSegment;
import hr.fer.zemris.ooup.lab4.vectorgraphics.geometry.Oval;
import hr.fer.zemris.ooup.lab4.vectorgraphics.geometry.SVGRendererImpl;
import hr.fer.zemris.ooup.lab4.vectorgraphics.gui.DrawingCanvas;
import hr.fer.zemris.ooup.lab4.vectorgraphics.interfaces.GraphicalObject;
import hr.fer.zemris.ooup.lab4.vectorgraphics.interfaces.Tool;
import hr.fer.zemris.ooup.lab4.vectorgraphics.models.DocumentModel;
import hr.fer.zemris.ooup.lab4.vectorgraphics.tools.AddShapeState;
import hr.fer.zemris.ooup.lab4.vectorgraphics.tools.EraserState;
import hr.fer.zemris.ooup.lab4.vectorgraphics.tools.IdleState;
import hr.fer.zemris.ooup.lab4.vectorgraphics.tools.SelectShapeState;

/**
 * This class contains the main method and is therefore the core class
 * of this GUI application. The program starts by simply initializing
 * the GUI thread and it then does the work necessary to make the app
 * work. 
 * 
 * @author Ivan Skorupan
 */
public class VectorGraphics extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Currently selected drawing tool.
	 */
	private Tool currentTool;

	/**
	 * The drawing model upon which this drawing application operates
	 * and with which it manages objects drawn inside its canvas.
	 */
	private DocumentModel model = new DocumentModel();

	/**
	 * An instance of {@link JFileChooser} used throughout this program
	 * to create message dialogues (used in several action implementations
	 * so is instantiated here for convenience).
	 */
	private JFileChooser jfc = new JFileChooser();

	/**
	 * Canvas on which all elements are drawn.
	 */
	private DrawingCanvas canvas;

	/**
	 * Constructs a new {@link VectorGraphics} object.
	 */
	public VectorGraphics(List<GraphicalObject> initialObjects) {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setTitle("JVDraw");
		setSize(800, 600);
		setLocationRelativeTo(null);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exit.actionPerformed(null);
			}
		});
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					setTool(new IdleState(model));
				}
			}
		});
		initGUI(initialObjects);
		currentTool = new IdleState(model);
	}

	/**
	 * Initializes and places GUI components on the window.
	 */
	private void initGUI(List<GraphicalObject> initialObjects) {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		JPanel editorPanel = new JPanel(new BorderLayout());
		cp.add(editorPanel);

		canvas = new DrawingCanvas(model, () -> {return currentTool;});
		editorPanel.add(canvas, BorderLayout.CENTER);

		createToolbarAndStatusBar(editorPanel, initialObjects);
		createMenuBar();
		createActions();
	}
	
	/**
	 * Helper method which configures all actions defined in this class.
	 */
	private void createActions() {
		select.putValue(Action.NAME, "Selektiraj");
		select.putValue(Action.SHORT_DESCRIPTION, "Select objects");
		select.setEnabled(true);
		
		erase.putValue(Action.NAME, "Brisalo");
		erase.putValue(Action.SHORT_DESCRIPTION, "Erase objects");
		erase.setEnabled(true);
		
		save.putValue(Action.NAME, "Pohrani");
		save.putValue(Action.SHORT_DESCRIPTION, "Save document");
		save.setEnabled(true);
		
		open.putValue(Action.NAME, "Učitaj");
		open.putValue(Action.SHORT_DESCRIPTION, "Open document");
		open.setEnabled(true);
		
		export.putValue(Action.NAME, "SVG Export");
		export.putValue(Action.SHORT_DESCRIPTION, "Export document as SVG");
		export.setEnabled(true);
		
		exit.putValue(Action.NAME, "Exit");
		exit.putValue(Action.SHORT_DESCRIPTION, "Exit application");
		exit.setEnabled(true);
	}

	/**
	 * Helper method that creates and places this GUI application's
	 * toolbar and status bar.
	 * 
	 * @param editorPanel - a panel on which to place the toolbar and status bar
	 */
	private void createToolbarAndStatusBar(JPanel editorPanel, List<GraphicalObject> initialObjects) {
		JToolBar toolbar = new JToolBar("Tools");
		toolbar.setFloatable(true);
		
		JButton openButton = new JButton(open);
		toolbar.add(openButton);
		
		JButton saveButton = new JButton(save);
		toolbar.add(saveButton);
		
		JButton exportButton = new JButton(export);
		toolbar.add(exportButton);
		
		ButtonGroup drawingToolsGroup = new ButtonGroup();
		
		for (int i = 0; i < initialObjects.size(); i++) {
			GraphicalObject object = initialObjects.get(i);
			Action objectAction = new AbstractAction() {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					setTool(new AddShapeState(model, object));
				}
			};
			
			objectAction.putValue(Action.NAME, object.getShapeName());
			objectAction.putValue(Action.SHORT_DESCRIPTION, object.getShapeName() + " Tool");
			objectAction.setEnabled(true);
			
			JToggleButton button = new JToggleButton(objectAction);
			button.setFont(new Font("Arial", Font.ITALIC, 15));
			drawingToolsGroup.add(button);
			toolbar.add(button);
		}
		
		JToggleButton selectButton = new JToggleButton(select);
		selectButton.setFont(new Font("Arial", Font.ITALIC, 15));
		drawingToolsGroup.add(selectButton);
		toolbar.add(selectButton);
		
		JToggleButton eraseButton = new JToggleButton(erase);
		eraseButton.setFont(new Font("Arial", Font.ITALIC, 15));
		drawingToolsGroup.add(eraseButton);
		toolbar.add(eraseButton);
		
		editorPanel.add(toolbar, BorderLayout.PAGE_START);
	}

	/**
	 * Helper method that creates and sets this GUI application's menu bar.
	 */
	private void createMenuBar() {
		JMenuBar menu = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		
		menu.add(fileMenu);
		fileMenu.add(open);
		fileMenu.add(save);
		fileMenu.add(export);
		fileMenu.add(exit);

		setJMenuBar(menu);
	}
	
	private void setTool(Tool tool) {
		currentTool.onLeaving();
		currentTool = tool;
	}
	
	private final Action select = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			setTool(new SelectShapeState(model));
		}
	};
	
	private final Action erase = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			setTool(new EraserState(model));
		}
	};
	
	/**
	 * This action performs file saving regardless of whether or not there
	 * were any changes to the document.
	 */
	private final Action save = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(jfc.showSaveDialog(VectorGraphics.this) == JFileChooser.APPROVE_OPTION) {
				Path selectedPath = jfc.getSelectedFile().toPath();
				performSaving(selectedPath);
			}
		}
	};

	/**
	 * This action asks the user to save changes if document was modified and
	 * asks the user to choose a file to be opened, after which the selected
	 * file is open.
	 */
	private final Action open = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(jfc.showOpenDialog(VectorGraphics.this) == JFileChooser.APPROVE_OPTION) {
				Path selectedPath = jfc.getSelectedFile().toPath();
				try {
					openFile(selectedPath, model);
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(VectorGraphics.this, ex.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	};

	/**
	 * This action asks the user for a file export location and then runs
	 * the export process where it creates an image from the document and
	 * exports it to the selected location in JPG, PNG or GIF format.
	 */
	private final Action export = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			FileNameExtensionFilter filter = new FileNameExtensionFilter("SVG images", "svg");
			jfc.setFileFilter(filter);
			if(jfc.showSaveDialog(VectorGraphics.this) == JFileChooser.APPROVE_OPTION) {
				if(jfc.getFileFilter().accept(jfc.getSelectedFile())) {
					Path selectedPath = jfc.getSelectedFile().toPath();
					SVGRendererImpl r = new SVGRendererImpl(selectedPath.toString());
					for (GraphicalObject object : model.list()) {
						object.render(r);
					}
					
					try {
						r.close();
					} catch (IOException e1) {
						System.out.println("There was an IO exception while exporting the file.");
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(VectorGraphics.this, "The file must have SVG extension!",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}			
			jfc.setFileFilter(null);
			jfc.resetChoosableFileFilters();
		}
	};

	/**
	 * This action exits the application but first asks the user to save their work.
	 */
	private final Action exit = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	};

	/**
	 * This method performs the saving operation on given <code>documentPath</code>
	 * and runs as a "save as" operation if <code>saveAs</code> is <code>true</code>.
	 * 
	 * @param documentPath - document to be saved
	 * @param saveAs - flag that indicates if method should act as save as or normal save
	 */
	private void performSaving(Path documentPath) {
		try {
			saveFile(documentPath, model);
			JOptionPane.showMessageDialog(VectorGraphics.this, "The document was sucessfully saved!",
					"Saved", JOptionPane.INFORMATION_MESSAGE);
		} catch(IOException ex) {
			JOptionPane.showMessageDialog(VectorGraphics.this, ex.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Starting point of this program.
	 * 
	 * @param args - command line arguments (not used)
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			List<GraphicalObject> objects = new ArrayList<>();
			
			objects.add(new LineSegment());
			objects.add(new Oval());
			
			new VectorGraphics(objects).setVisible(true);
		});
	}

}
