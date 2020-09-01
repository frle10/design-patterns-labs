package hr.fer.zemris.ooup.lab3.texteditor;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ClipboardStack {
	
	private Stack<String> texts = new Stack<>();
	
	private List<ClipboardObserver> clipboardObservers = new ArrayList<>();
	
	public void addClipboardObserver(ClipboardObserver observer) {
		clipboardObservers.add(observer);
	}
	
	public void removeClipboardObserver(ClipboardObserver observer) {
		clipboardObservers.remove(observer);
	}
	
	public void notifyClipboardObservers() {
		clipboardObservers.forEach(co -> co.updateClipboard());
	}
	
	public void pushText(String text) {
		texts.push(text);
		notifyClipboardObservers();
	}
	
	public String popText() {
		String text = texts.pop();
		notifyClipboardObservers();
		return text;
	}
	
	public String peekText() {
		return texts.peek();
	}
	
	public boolean isEmpty() {
		return texts.isEmpty();
	}
	
	public void clear() {
		texts.clear();
		notifyClipboardObservers();
	}
	
}
