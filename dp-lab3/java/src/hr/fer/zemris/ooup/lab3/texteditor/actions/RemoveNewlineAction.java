package hr.fer.zemris.ooup.lab3.texteditor.actions;

import hr.fer.zemris.ooup.lab3.texteditor.Location;
import hr.fer.zemris.ooup.lab3.texteditor.TextEditorModel;

public class RemoveNewlineAction implements EditAction {
	
	private Location breakpoint;
	
	private TextEditorModel model;
	
	public RemoveNewlineAction(Location breakpoint, TextEditorModel model) {
		this.breakpoint = breakpoint;
		this.model = model;
	}
	
	@Override
	public void executeDo() {
		model.setCursorLocation(new Location(breakpoint));
		model.removeNewline();
	}

	@Override
	public void executeUndo() {
		model.setCursorLocation(new Location(breakpoint));
		model.insertNewline();
	}

}
