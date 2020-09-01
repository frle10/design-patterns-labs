package hr.fer.zemris.ooup.lab3.texteditor.actions;

import hr.fer.zemris.ooup.lab3.texteditor.Location;
import hr.fer.zemris.ooup.lab3.texteditor.LocationRange;
import hr.fer.zemris.ooup.lab3.texteditor.TextEditorModel;

public class InsertTextAction implements EditAction {
	
	private Location textStart;
	
	private Location textEnd;
	
	private String text;
	
	private TextEditorModel model;
	
	public InsertTextAction(Location textStart, Location textEnd, String text, TextEditorModel model) {
		this.textStart = textStart;
		this.textEnd = textEnd;
		this.text = text;
		this.model = model;
	}

	@Override
	public void executeDo() {
		model.setCursorLocation(new Location(textStart));
		model.insert(text);
	}

	@Override
	public void executeUndo() {
		model.deleteRange(new LocationRange(textStart, textEnd));
	}

	public void setTextStart(Location textStart) {
		this.textStart = textStart;
	}

	public void setTextEnd(Location textEnd) {
		this.textEnd = textEnd;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
