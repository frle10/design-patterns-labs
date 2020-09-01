package hr.fer.zemris.ooup.lab3.texteditor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import hr.fer.zemris.ooup.lab3.texteditor.actions.EditAction;
import hr.fer.zemris.ooup.lab3.texteditor.actions.InsertNewlineAction;
import hr.fer.zemris.ooup.lab3.texteditor.actions.InsertTextAction;
import hr.fer.zemris.ooup.lab3.texteditor.actions.RemoveCharAction;
import hr.fer.zemris.ooup.lab3.texteditor.actions.RemoveNewlineAction;
import hr.fer.zemris.ooup.lab3.texteditor.actions.RemoveRangeAction;

public class TextEditorModel {

	private List<String> lines = new ArrayList<>();

	private LocationRange selectionRange = new LocationRange(new Location(0, 0), new Location(0, 0));

	private Location cursorLocation = new Location(0, 0);

	private List<CursorObserver> cursorObservers = new ArrayList<>();

	private List<TextObserver> textObservers = new ArrayList<>();
	
	private UndoManager undoManager = UndoManager.getInstance();

	public TextEditorModel(String text) {
		setLines(text);
	}
	
	public boolean selectionExists() {
		return selectionRange.getRangeBegin().compareTo(selectionRange.getRangeEnd()) != 0;
	}

	public Iterator<String> allLines() {
		return lines.iterator();
	}

	public Iterator<String> linesRange(int index1, int index2) {
		return lines.subList(index1, index2).iterator();
	}
	
	public void insert(char c) {
		insert(String.valueOf(c));
	}
	
	public void insertNewline() {
		if (selectionExists()) {
			deleteRange(getSelectionRange());
		}
		
		int x = cursorLocation.getX();
		int y = cursorLocation.getY();
		StringBuilder sb = new StringBuilder(lines.get(x));
		lines.add(x + 1, sb.substring(y));
		sb.delete(y, sb.length());
		lines.set(x, sb.toString());
		
		cursorLocation.setX(x + 1);
		cursorLocation.setY(0);
		
		EditAction inla = new InsertNewlineAction(new Location(x, y), this);
		undoManager.push(inla);
		
		notifyCursorObservers(false);
		notifyTextObservers();
	}
	
	public void removeNewline() {
		int x = cursorLocation.getX();
		int y = cursorLocation.getY();
		
		String line = lines.get(x);
		if (y == 0 && x > 0) {
			cursorLocation.setX(x - 1);
			cursorLocation.setY(lines.get(x - 1).length());
			lines.set(x - 1, lines.get(x - 1) + line);
			lines.remove(x);
		} else if (y == line.length() && x < lines.size() - 1) {
			lines.set(x, line + lines.get(x + 1));
			lines.remove(x + 1);
		}
		
		EditAction rnla = new RemoveNewlineAction(new Location(cursorLocation.getX(), cursorLocation.getY()),
				this);
		undoManager.push(rnla);
		
		notifyCursorObservers(false);
		notifyTextObservers();
	}
	
	public void insert(String text) {
		if (selectionExists()) {
			deleteRange(getSelectionRange());
		}
		
		Location textStart = new Location(cursorLocation);
		List<String> indLines = text.lines().collect(Collectors.toList());
		for (int i = 0; i < indLines.size(); i++) {
			int x = cursorLocation.getX();
			int y = cursorLocation.getY();
			
			String indLine = indLines.get(i);
			StringBuilder sb = new StringBuilder(lines.get(x));
			sb.insert(y, indLine);
			if (i != indLines.size() - 1) {
				lines.add(x + 1, sb.substring(y + indLine.length()));
				lines.set(x, sb.substring(0, y + indLine.length()));
				cursorLocation.setX(x + 1);
				cursorLocation.setY(0);
			} else {
				cursorLocation.setY(y + indLine.length());
				lines.set(x, sb.toString());
			}
		}
		
		EditAction insertAction = new InsertTextAction(textStart,
				new Location(cursorLocation), text, this);
		undoManager.push(insertAction);
		
		notifyCursorObservers(false);
		notifyTextObservers();
	}
	
	public void deleteBefore() {
		int y = cursorLocation.getY();
		int x = cursorLocation.getX();
		if (y > 0) {
			String line = lines.get(x);
			StringBuilder sb = new StringBuilder(line);
			String removedChar = String.valueOf(sb.charAt(y - 1));
			sb.deleteCharAt(y - 1);
			lines.set(x, sb.toString());
			cursorLocation.setY(y - 1);
			
			EditAction rca = new RemoveCharAction(removedChar,
					new Location(cursorLocation), this);
			undoManager.push(rca);
			
			notifyTextObservers();
			notifyCursorObservers(false);
		} else {
			removeNewline();
		}
	}
	
	public void deleteAfter() {
		int y = cursorLocation.getY();
		int x = cursorLocation.getX();
		String line = lines.get(x);
		if (y < line.length()) {
			StringBuilder sb = new StringBuilder(line);
			String removedChar = String.valueOf(sb.charAt(y));
			sb.deleteCharAt(y);
			lines.set(x, sb.toString());
			
			EditAction rca = new RemoveCharAction(removedChar,
					new Location(cursorLocation), this);
			undoManager.push(rca);
			
			notifyTextObservers();
		} else {
			removeNewline();
		}
	}
	
	public void deleteRange(LocationRange r) {
		int x1 = r.getRangeBegin().getX();
		int x2 = r.getRangeEnd().getX();
		int y1 = r.getRangeBegin().getY();
		int y2 = r.getRangeEnd().getY();
		
		String selectionText = getSelectionText();
		StringBuilder sb = new StringBuilder(lines.get(x1));
		if (x1 != x2) {
			sb.delete(y1, sb.length());
			lines.set(x1, sb.toString());
			
			sb = new StringBuilder(lines.get(x2));
			sb.delete(0, y2);
			lines.set(x2, sb.toString());
			
			lines.set(x1, lines.get(x1) + lines.get(x2));
		} else {
			sb.delete(y1, y2);
			lines.set(x1, sb.toString());
		}
		
		lines.subList(x1 + 1, x2 + 1).clear();
		
		EditAction rra = new RemoveRangeAction(selectionText, this, new LocationRange(r));
		undoManager.push(rra);
		
		cursorLocation.setX(x1);
		cursorLocation.setY(y1);
		notifyTextObservers();
		notifyCursorObservers(false);
	}

	public void moveCursorLeft() {
		int x = cursorLocation.getX();
		int y = cursorLocation.getY();
		if (y > 0) {
			cursorLocation.setY(y - 1);
		} else {
			if (x > 0) {
				cursorLocation.setX(x - 1);
				cursorLocation.setY(lines.get(x - 1).length());
			}
		}
		
		notifyCursorObservers(true);
	}

	public void moveCursorRight() {
		int x = cursorLocation.getX();
		int y = cursorLocation.getY();
		String line = lines.get(x);
		if (y < line.length()) {
			cursorLocation.setY(y + 1);
		} else {
			if (x < lines.size() - 1) {
				cursorLocation.setX(x + 1);
				cursorLocation.setY(0);
			}
		}
		
		notifyCursorObservers(true);
	}

	public void moveCursorUp() {
		int x = cursorLocation.getX();
		if (x > 0) {
			cursorLocation.setX(x - 1);

			int y = cursorLocation.getY();
			String lineAbove = lines.get(x - 1);
			if (y > lineAbove.length()) {
				cursorLocation.setY(lineAbove.length());
			}

			notifyCursorObservers(true);
		}
	}

	public void moveCursorDown() {
		int x = cursorLocation.getX();
		if (x < lines.size() - 1) {
			cursorLocation.setX(x + 1);

			int y = cursorLocation.getY();
			String lineBelow = lines.get(x + 1);
			if (y > lineBelow.length()) {
				cursorLocation.setY(lineBelow.length());
			}

			notifyCursorObservers(true);
		}
	}
	
	public String getSelectionText() {
		int x1 = selectionRange.getRangeBegin().getX();
		int y1 = selectionRange.getRangeBegin().getY();
		int x2 = selectionRange.getRangeEnd().getX();
		int y2 = selectionRange.getRangeEnd().getY();
		
		StringBuilder sb = new StringBuilder();
		if (x1 == x2) {
			sb.append(lines.get(x1).substring(y1, y2));
		} else {
			sb.append(lines.get(x1).substring(y1) + "\n");
			for (int i = x1 + 1; i < x2; i++) {
				sb.append(lines.get(i) + "\n");
			}
			
			sb.append(lines.get(x2).substring(0, y2));
		}
		
		return sb.toString();
	}

	public void addCursorObserver(CursorObserver observer) {
		cursorObservers.add(observer);
	}

	public boolean removeCursorObserver(CursorObserver observer) {
		return cursorObservers.remove(observer);
	}

	public void notifyCursorObservers(boolean select) {
		cursorObservers.forEach(co -> co.updateCursorLocation(cursorLocation, select));
	}

	public void addTextObserver(TextObserver observer) {
		textObservers.add(observer);
	}

	public boolean removeTextObserver(TextObserver observer) {
		return textObservers.remove(observer);
	}
	
	public void notifyTextObservers() {
		textObservers.forEach(to -> to.updateText());
	}

	public List<CursorObserver> getCursorObservers() {
		return cursorObservers;
	}

	public List<TextObserver> getTextObservers() {
		return textObservers;
	}

	public List<String> getLines() {
		return lines;
	}

	public void setLines(String text) {
		lines = text.lines().collect(Collectors.toList());
		if (lines.isEmpty()) {
			lines.add("");
		}
		notifyTextObservers();
	}

	public LocationRange getSelectionRange() {
		return selectionRange;
	}

	public void setSelectionRange(LocationRange selectionRange) {
		this.selectionRange = selectionRange;
	}

	public Location getCursorLocation() {
		return cursorLocation;
	}

	public void setCursorLocation(Location cursorLocation) {
		this.cursorLocation = cursorLocation;
	}

	public UndoManager getUndoManager() {
		return undoManager;
	}

}
