package hr.fer.zemris.ooup.lab3.texteditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

public class TextEditorCanvas extends JComponent implements CursorObserver, TextObserver {
	
	private static final long serialVersionUID = 1L;
	
	private static final int PADDING_LEFT = 5;
	private static final int PADDING_TOP = 5;
	
	private TextEditorModel model;
	
	private ClipboardStack clipboard = new ClipboardStack();
	
	private boolean isShiftPressed;
	
	public TextEditorCanvas() {
		setFocusable(true);
		model = new TextEditorModel("Ja sam Ivan\nGle ovo radi\nBravooo");
		model.addCursorObserver(this);
		model.addTextObserver(this);
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				TextEditorCanvas.this.requestFocusInWindow();
			}
		});
		
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_SHIFT) {
					isShiftPressed = false;
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if (Character.isAlphabetic(keyCode)) {
					boolean ctrl = e.isControlDown();
					String character = Character.toString(keyCode);
					if (ctrl && character.equals("C")) {
						copy();
					} else if (ctrl && character.equals("X")) {
						cut();
					} else if (ctrl && character.equals("V")) {
						paste(e.isShiftDown());
					} else if (ctrl && character.equals("Z")) {
						model.getUndoManager().undo();
					} else if (ctrl && character.equals("Y")) {
						model.getUndoManager().redo();
					} else {
						model.insert(e.isShiftDown() ? character : character.toLowerCase());
					}
				} else if (keyCode == KeyEvent.VK_SPACE) {
					model.insert(' ');
				} else if (keyCode == KeyEvent.VK_UP) {
					model.moveCursorUp();
				} else if (keyCode == KeyEvent.VK_DOWN) {
					model.moveCursorDown();
				} else if (keyCode == KeyEvent.VK_LEFT) {
					model.moveCursorLeft();
				} else if (keyCode == KeyEvent.VK_RIGHT) {
					model.moveCursorRight();
				} else if (keyCode == KeyEvent.VK_ENTER) {
					model.insertNewline();
				} else if (keyCode == KeyEvent.VK_BACK_SPACE) {
					if (model.selectionExists()) {
						model.deleteRange(model.getSelectionRange());
					} else {
						model.deleteBefore();
					}
				} else if (keyCode == KeyEvent.VK_DELETE) {
					if (model.selectionExists()) {
						model.deleteRange(model.getSelectionRange());
					} else {
						model.deleteAfter();
					}
				} else if (keyCode == KeyEvent.VK_SHIFT) {
					if (!isShiftPressed) {
						isShiftPressed = true;
						Location cursorLocation = model.getCursorLocation();
						Location left = new Location(cursorLocation.getX(), cursorLocation.getY());
						Location right = new Location(cursorLocation.getX(), cursorLocation.getY());
						model.setSelectionRange(new LocationRange(left, right));
					}
				}
			}
		});
	}
	
	public void copy() {
		if (model.selectionExists()) {
			clipboard.pushText(model.getSelectionText());
		}
	}
	
	public void cut() {
		if (model.selectionExists()) {
			clipboard.pushText(model.getSelectionText());
			model.deleteRange(model.getSelectionRange());
		}
	}
	
	public void paste(boolean shift) {
		if (shift && !clipboard.isEmpty()) {
			model.insert(clipboard.popText());
		} else if (!clipboard.isEmpty()) {
			model.insert(clipboard.peekText());
		}
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Location cursorLocation = model.getCursorLocation();
		drawSelection(g2d);
		model.setCursorLocation(new Location(0, 0));
		model.allLines().forEachRemaining(l -> writeLine(g2d, l));
		model.setCursorLocation(cursorLocation);
		drawCursor(g2d);
	}
	
	private void writeLine(Graphics2D g2d, String line) {
		Location cursorLocation = model.getCursorLocation();
		int height = getFontHeight();
		int stringX = PADDING_LEFT;
		int stringY = PADDING_TOP + (cursorLocation.getX() + 1) * height;
		g2d.setColor(Color.BLACK);
		g2d.drawString(line, stringX, stringY);
		
		int x = cursorLocation.getX();
		Location newCursorLocation = new Location((x == model.getLines().size() - 1) ? x : x + 1,
				line.length());
		model.setCursorLocation(newCursorLocation);
	}
	
	private void drawCursor(Graphics2D g2d) {
		Location cursorLocation = model.getCursorLocation();
		String line = model.getLines().get(cursorLocation.getX());
		String preCursorSubstring = line.substring(0, cursorLocation.getY());
		
		int x = PADDING_LEFT + getFontMetrics(getFont()).stringWidth(preCursorSubstring);
		int y = PADDING_TOP + (cursorLocation.getX() + 1) * getFontHeight();
		g2d.drawLine(x, y + getFontMetrics(getFont()).getDescent() / 2, x,
				y - getFontMetrics(getFont()).getAscent());
	}
	
	private void drawSelection(Graphics2D g2d) {
		int x1 = model.getSelectionRange().getRangeBegin().getX();
		int x2 = model.getSelectionRange().getRangeEnd().getX();
		int y1 = model.getSelectionRange().getRangeBegin().getY();
		int y2 = model.getSelectionRange().getRangeEnd().getY();
		
		g2d.setColor(Color.CYAN);
		for (int i = x1; i <= x2; i++) {
			String line = model.getLines().get(i);
			int x1Rect = PADDING_LEFT;
			if (x1 == x2) {
				x1Rect += getFontMetrics(getFont()).stringWidth(line.substring(0, y1));
				line = line.substring(y1, y2);
			} else if (i == x1) {
				x1Rect += getFontMetrics(getFont()).stringWidth(line.substring(0, y1));
				line = line.substring(y1);
			} else if (i == x2) {
				line = line.substring(0, y2);
			}
			
			int y1Rect = PADDING_TOP + (i + 1) * getFontHeight() - getFontMetrics(getFont()).getAscent();
			int width = getFontMetrics(getFont()).stringWidth(line);
			int height = getFontMetrics(getFont()).getDescent() / 2 + getFontMetrics(getFont()).getAscent();
			
			g2d.fillRect(x1Rect, y1Rect, width, height);
		}
	}
	
	private int getFontHeight() {
		return getFontMetrics(getFont()).getHeight();
	}
	
	public TextEditorModel getModel() {
		return model;
	}

	public void setModel(TextEditorModel model) {
		this.model = model;
	}

	public ClipboardStack getClipboard() {
		return clipboard;
	}

	@Override
	public void updateCursorLocation(Location loc, boolean select) {
		if (isShiftPressed && select) {
			model.getSelectionRange().setNewRangeSide(loc);
		} else {
			model.getSelectionRange().setRangeBegin(loc);
			model.getSelectionRange().setRangeEnd(loc);
		}
		repaint();
	}

	@Override
	public void updateText() {
		repaint();
	}
	
}
