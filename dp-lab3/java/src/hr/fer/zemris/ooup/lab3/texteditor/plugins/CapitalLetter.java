package hr.fer.zemris.ooup.lab3.texteditor.plugins;

import java.util.List;

import hr.fer.zemris.ooup.lab3.texteditor.ClipboardStack;
import hr.fer.zemris.ooup.lab3.texteditor.Plugin;
import hr.fer.zemris.ooup.lab3.texteditor.TextEditorModel;
import hr.fer.zemris.ooup.lab3.texteditor.UndoManager;

public class CapitalLetter implements Plugin {

	@Override
	public String getName() {
		return "Capital Letter";
	}

	@Override
	public String getDescription() {
		return "Capitalizes the first letter of each word in the document.";
	}

	@Override
	public void execute(TextEditorModel model, UndoManager undoManager, ClipboardStack clipboardStack) {
		List<String> lines = model.getLines();
		for (int i = 0; i < lines.size(); i++) {
			StringBuilder sb = new StringBuilder(lines.get(i));
			char lastChar = ' ';
			for (int j = 0; j < sb.length(); j++) {
				char current = sb.charAt(j);
				if (Character.isWhitespace(current)) {
					lastChar = ' ';
					continue;
				} else if (Character.isAlphabetic(current)) {
					if (lastChar == ' ') {
						sb.replace(j, j + 1, String.valueOf(current).toUpperCase());
						lastChar = current;
					}
				}
			}
			
			lines.set(i, sb.toString());
		}
		
		model.notifyTextObservers();
	}

}
