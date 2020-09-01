package hr.fer.zemris.ooup.lab3.texteditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.ooup.lab3.texteditor.actions.EditAction;

public class TextEditor extends JFrame {

	private static final long serialVersionUID = 1L;

	private TextEditorCanvas canvas;

	private JFileChooser jfc = new JFileChooser();

	private List<Plugin> plugins = new ArrayList<>();

	public TextEditor() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Text Editor");
		setLocation(20, 20);
		setSize(800, 600);
		loadPlugins();
		initGUI();
	}

	private void loadPlugins() {
		List<Path> pluginPaths = null;
		try {
			pluginPaths = Files.list(Paths.get("src/hr/fer/zemris/ooup/lab3/texteditor/plugins")).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		List<String> pluginNames = pluginPaths.stream()
				.map(p -> {
					String pluginName = p.toFile().getName();
					int pointIndex = pluginName.lastIndexOf(".");
					return pluginName.substring(0, pointIndex);
				})
				.collect(Collectors.toList());
		
		pluginNames.forEach(pn -> plugins.add(PluginFactory.newInstance(pn)));
	}

	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		canvas = new TextEditorCanvas();
		cp.add(canvas, BorderLayout.CENTER);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JPanel statusBar = new JPanel(new GridLayout(0, 3));
		statusBar.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		statusBar.add(new CursorLocationLabel(canvas.getModel()));
		statusBar.add(new RowNumberLabel(canvas.getModel()));
		cp.add(statusBar, BorderLayout.PAGE_END);

		JToolBar toolbar = new JToolBar();
		cp.add(toolbar, BorderLayout.PAGE_START);
		toolbar.add(new JButton(undo));
		toolbar.add(new JButton(redo));
		toolbar.add(new JButton(cut));
		toolbar.add(new JButton(copy));
		toolbar.add(new JButton(paste));

		JMenu file = new JMenu("File");
		JMenu edit = new JMenu("Edit");
		JMenu move = new JMenu("Move");
		JMenu pluginsMenu = new JMenu("Plugins");
		menuBar.add(file);
		menuBar.add(edit);
		menuBar.add(move);
		menuBar.add(pluginsMenu);

		file.add(open);
		file.add(save);
		file.add(exit);

		edit.add(undo);
		edit.add(redo);
		edit.add(cut);
		edit.add(copy);
		edit.add(paste);
		edit.add(pasteAndTake);
		edit.add(deleteSelection);
		edit.add(clear);

		move.add(cursorToDocStart);
		move.add(cursorToDocEnd);
		
		plugins.forEach(p -> pluginsMenu.add(new AbstractAction(p.getName()) {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				TextEditorModel model = canvas.getModel();
				p.execute(model, model.getUndoManager(), canvas.getClipboard());
			}
		}));
		
		redo.setEnabled(false);
		undo.setEnabled(false);
		cut.setEnabled(false);
		copy.setEnabled(false);
		paste.setEnabled(false);
		pasteAndTake.setEnabled(false);
		canvas.getModel().getUndoManager().addUndoRedoObserver(new UndoRedoObserver() {
			@Override
			public void stacksChanged(Stack<EditAction> undoStack, Stack<EditAction> redoStack) {
				if (undoStack.isEmpty()) undo.setEnabled(false);
				else undo.setEnabled(true);
				
				if (redoStack.isEmpty()) redo.setEnabled(false);
				else redo.setEnabled(true);
			}
		});
		
		canvas.getModel().addCursorObserver(new CursorObserver() {
			@Override
			public void updateCursorLocation(Location loc, boolean select) {
				if (canvas.getModel().selectionExists()) {
					cut.setEnabled(true);
					copy.setEnabled(true);
				} else {
					cut.setEnabled(false);
					copy.setEnabled(false);
				}
			}
		});
		
		canvas.getClipboard().addClipboardObserver(new ClipboardObserver() {
			@Override
			public void updateClipboard() {
				if (canvas.getClipboard().isEmpty()) {
					paste.setEnabled(false);
					pasteAndTake.setEnabled(false);
				} else {
					paste.setEnabled(true);
					pasteAndTake.setEnabled(true);
				}
			}
		});
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				TextEditor textEditor = new TextEditor();
				textEditor.setVisible(true);
			}
		});
	}

	private final Action undo = new AbstractAction("Undo") {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			canvas.getModel().getUndoManager().undo();
		}
	};

	private final Action redo = new AbstractAction("Redo") {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			canvas.getModel().getUndoManager().redo();
		}
	};

	private final Action cut = new AbstractAction("Cut") {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			canvas.cut();
		}
	};

	private final Action copy = new AbstractAction("Copy") {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			canvas.copy();
		}
	};

	private final Action paste = new AbstractAction("Paste") {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			canvas.paste(false);
		}
	};

	private final Action pasteAndTake = new AbstractAction("Paste and Take") {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			canvas.paste(true);
		}
	};

	private final Action deleteSelection = new AbstractAction("Delete selection") {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			canvas.getModel().deleteRange(canvas.getModel().getSelectionRange());
		}
	};

	private final Action clear = new AbstractAction("Clear document") {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			canvas.getModel().setCursorLocation(new Location(0, 0));
			canvas.getModel().setSelectionRange(new LocationRange(new Location(0, 0), new Location(0, 0)));
			canvas.getModel().setLines("");
		}
	};

	private final Action open = new AbstractAction("Open") {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			jfc.setDialogTitle("Open file");
			if(jfc.showOpenDialog(TextEditor.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}

			Path openedFilePath = jfc.getSelectedFile().toPath();
			try {
				canvas.getModel().setLines(Files.readString(openedFilePath));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	};

	private final Action save = new AbstractAction("Save") {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			jfc.showSaveDialog(TextEditor.this);
			Path saveFile = jfc.getSelectedFile().toPath();

			if (saveFile != null) {
				List<String> lines = canvas.getModel().getLines();
				StringBuilder sb = new StringBuilder();
				lines.forEach(s -> sb.append(s + "\n"));
				try {
					Files.writeString(saveFile, sb.toString());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	};

	private final Action exit = new AbstractAction("Exit") {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			TextEditor.this.dispose();
		}
	};

	private final Action cursorToDocStart = new AbstractAction("Cursor to document start") {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			canvas.getModel().setCursorLocation(new Location(0, 0));
			canvas.getModel().notifyCursorObservers(false);
		}
	};

	private final Action cursorToDocEnd = new AbstractAction("Cursor to document end") {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			List<String> lines = canvas.getModel().getLines();
			int y = lines.get(lines.size() - 1).length();
			canvas.getModel().setCursorLocation(new Location(lines.size() - 1, y));
		}
	};

}
