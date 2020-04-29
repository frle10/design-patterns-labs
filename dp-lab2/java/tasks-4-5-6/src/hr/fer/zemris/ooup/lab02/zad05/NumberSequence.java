package hr.fer.zemris.ooup.lab02.zad05;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import hr.fer.zemris.ooup.lab02.zad05.numbersources.NumberSource;

public class NumberSequence {
	
	private List<Integer> numbers = new ArrayList<>();
	private List<Consumer<List<Integer>>> actions;
	private NumberSource source;
	
	public NumberSequence(NumberSource source, List<Consumer<List<Integer>>> actions) {
		this.source = source;
		this.actions = actions;
	}
	
	public void start() {
		while(true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.err.println("The thread was interrupted!");
			}
			int number = source.nextNumber();
			if (number > 0) {
				numbers.add(number);
				runActions();
			}
			else break;
		}
	}
	
	private void runActions() {
		for (Consumer<List<Integer>> action : actions) {
			action.accept(numbers);
		}
	}
	
	public NumberSource getSource() {
		return source;
	}
	
	public void setSource(NumberSource source) {
		this.source = source;
	}
	
}
