package hr.fer.zemris.ooup.lab02.zad04.numbergenerators;

import java.util.ArrayList;
import java.util.List;

public class SerialGenerator implements WholeNumberGenerator {
	
	private int start;
	private int end;
	private int step;
	
	public SerialGenerator(int start, int end, int step) {
		this.start = start;
		this.end = end;
		this.step = step;
	}
	
	@Override
	public List<Integer> generate() {
		List<Integer> numbers = new ArrayList<>();
		for (int i = start; i < end; i += step) {
			numbers.add(i);
		}
		
		return numbers;
	}
	
}
