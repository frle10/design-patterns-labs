package hr.fer.zemris.ooup.lab3.texteditor.actions;

import hr.fer.zemris.ooup.lab3.texteditor.Location;
import hr.fer.zemris.ooup.lab3.texteditor.LocationRange;
import hr.fer.zemris.ooup.lab3.texteditor.TextEditorModel;

public class RemoveRangeAction implements EditAction {
	
	private String removedText;
	
	private TextEditorModel model;
	
	private LocationRange range;
	
	public RemoveRangeAction(String removedText, TextEditorModel model, LocationRange range) {
		this.removedText = removedText;
		this.model = model;
		this.range = range;
	}

	@Override
	public void executeDo() {
		model.deleteRange(new LocationRange(range));
	}

	@Override
	public void executeUndo() {
		model.setCursorLocation(new Location(range.getRangeBegin()));
		model.insert(removedText);
	}

}
