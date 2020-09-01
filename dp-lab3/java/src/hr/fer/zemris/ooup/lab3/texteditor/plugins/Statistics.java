package hr.fer.zemris.ooup.lab3.texteditor.plugins;

import java.util.List;

import javax.swing.JOptionPane;

import hr.fer.zemris.ooup.lab3.texteditor.ClipboardStack;
import hr.fer.zemris.ooup.lab3.texteditor.Plugin;
import hr.fer.zemris.ooup.lab3.texteditor.TextEditorModel;
import hr.fer.zemris.ooup.lab3.texteditor.UndoManager;

public class Statistics implements Plugin {

	@Override
	public String getName() {
		return "Statistics";
	}

	@Override
	public String getDescription() {
		return "Shows in a dialogue the number of rows, words and letters in the document.";
	}

	@Override
	public void execute(TextEditorModel model, UndoManager undoManager, ClipboardStack clipboardStack) {
		List<String> lines = model.getLines();
		int rows = lines.size();
		int words = 0;
		int characters = 0;

		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			char lastChar = ' ';
			for (int j = 0; j < line.length(); j++) {
				char current = line.charAt(j);
				characters++;
				if (Character.isWhitespace(current)) {
					lastChar = ' ';
					continue;
				} else if (Character.isAlphabetic(current)) {
					if (lastChar == ' ') {
						words++;
						lastChar = current;
					}
				}
			}
		}
		
		String message = "Rows: " + rows + " Words: " + words + " Characters: " + characters;
		JOptionPane.showOptionDialog(null,
				message,
				"Statistics",
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE, 
				null, null, null
		);
	}

}
