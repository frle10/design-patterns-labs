package hr.fer.zemris.ooup.lab02.zad04.numbergenerators;

import java.util.ArrayList;
import java.util.List;

public class FibonacciGenerator implements WholeNumberGenerator {
	
	private int nElem;
	
	public FibonacciGenerator(int nElem) {
		this.nElem = nElem;
	}
	
	@Override
	public List<Integer> generate() {
		List<Integer> numbers = new ArrayList<>();
		if (this.nElem == 0) return numbers;
		
		
		if (this.nElem == 1) {
			numbers.add(1);
		} else {
			numbers.add(1);
			numbers.add(1);
			for (int i = 2; i < nElem; i++) {
				numbers.add(numbers.get(i - 1) + numbers.get(i - 2));
			}
		}
		
		return numbers;
	}

}
