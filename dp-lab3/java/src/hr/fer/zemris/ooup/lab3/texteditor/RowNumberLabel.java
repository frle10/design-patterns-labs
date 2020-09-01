package hr.fer.zemris.ooup.lab3.texteditor;

import javax.swing.JLabel;

public class RowNumberLabel extends JLabel implements TextObserver {
	
	private static final long serialVersionUID = 1L;
	
	private TextEditorModel model;
	
	public RowNumberLabel(TextEditorModel model) {
		this.model = model;
		model.addTextObserver(this);
		updateText();
	}

	@Override
	public void updateText() {
		setText("Rows: " + model.getLines().size());
	}

}
