package hr.fer.zemris.ooup.lab3.texteditor;

import java.util.Stack;

import hr.fer.zemris.ooup.lab3.texteditor.actions.EditAction;

public interface UndoRedoObserver {
	
	void stacksChanged(Stack<EditAction> undoStack, Stack<EditAction> redoStack);
	
}
