package hr.fer.zemris.ooup.lab3.texteditor;

public interface CursorObserver {
	
	void updateCursorLocation(Location loc, boolean select);
	
}
