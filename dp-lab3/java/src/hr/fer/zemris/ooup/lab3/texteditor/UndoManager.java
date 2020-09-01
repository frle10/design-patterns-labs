package hr.fer.zemris.ooup.lab3.texteditor;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.ooup.lab3.texteditor.actions.EditAction;

public class UndoManager {
	
	private Stack<EditAction> undoStack = new Stack<>();
	
	private Stack<EditAction> redoStack = new Stack<>();
	
	private static final UndoManager undoManager = new UndoManager();
	
	private List<UndoRedoObserver> undoRedoObservers = new ArrayList<>();
	
	private UndoManager() {
	}
	
	public static UndoManager getInstance() {
		return undoManager;
	}
	
	public void addUndoRedoObserver(UndoRedoObserver observer) {
		undoRedoObservers.add(observer);
	}
	
	public boolean removeUndoRedoObserver(UndoRedoObserver observer) {
		return undoRedoObservers.remove(observer);
	}
	
	public void notifyUndoRedoObservers() {
		undoRedoObservers.forEach(uro -> uro.stacksChanged(undoStack, redoStack));
	}
	
	public void undo() {
		if (!undoStack.isEmpty()) {
			EditAction action = undoStack.pop();
			action.executeUndo();
			redoStack.push(action);
			notifyUndoRedoObservers();
		}
	}
	
	public void redo() {
		if (!redoStack.isEmpty()) {
			EditAction action = redoStack.pop();
			action.executeDo();
			undoStack.push(action);
			notifyUndoRedoObservers();
		}
	}
	
	public void push(EditAction c) {
		redoStack.clear();
		undoStack.push(c);
		notifyUndoRedoObservers();
	}
	
}
