package hr.fer.zemris.ooup.lab3.texteditor;

import javax.swing.JLabel;

public class CursorLocationLabel extends JLabel implements CursorObserver {
	
	private static final long serialVersionUID = 1L;
	
	public CursorLocationLabel(TextEditorModel model) {
		model.addCursorObserver(this);
		updateCursorLocation(model.getCursorLocation(), false);
	}

	@Override
	public void updateCursorLocation(Location loc, boolean select) {
		setText("X: " + (loc.getX() + 1) + " Y: " + (loc.getY() + 1));
	}
	
}
