package hr.fer.zemris.ooup.lab3.texteditor.actions;

import hr.fer.zemris.ooup.lab3.texteditor.Location;
import hr.fer.zemris.ooup.lab3.texteditor.TextEditorModel;

public class InsertNewlineAction implements EditAction {
	
	private Location breakLocation;
	
	private TextEditorModel model;
	
	public InsertNewlineAction(Location breakLocation, TextEditorModel model) {
		this.breakLocation = breakLocation;
		this.model = model;
	}

	@Override
	public void executeDo() {
		model.setCursorLocation(new Location(breakLocation));
		model.insertNewline();
	}

	@Override
	public void executeUndo() {
		model.setCursorLocation(new Location(breakLocation));
		model.removeNewline();
	}
	
}
