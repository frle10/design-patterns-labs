package hr.fer.zemris.ooup.lab3.texteditor.actions;

import hr.fer.zemris.ooup.lab3.texteditor.Location;
import hr.fer.zemris.ooup.lab3.texteditor.TextEditorModel;

public class RemoveCharAction implements EditAction {

	private String removedCharacter;

	private Location characterLocation;

	private TextEditorModel model;

	public RemoveCharAction(String removedCharacter, Location characterLocation, TextEditorModel model) {
		this.removedCharacter = removedCharacter;
		this.characterLocation = characterLocation;
		this.model = model;
	}

	@Override
	public void executeDo() {
		model.setCursorLocation(new Location(characterLocation));
		model.deleteAfter();
	}

	@Override
	public void executeUndo() {
		model.setCursorLocation(new Location(characterLocation));
		model.insert(removedCharacter);
	}

}
