package hr.fer.zemris.ooup.lab02.zad05.numbersources;

import java.util.Scanner;

public class KeyboardSource implements NumberSource {
	
	private Scanner scanner;
	
	public KeyboardSource(Scanner scanner) {
		this.scanner = scanner;
	}
	
	@Override
	public int nextNumber() {
		if(scanner.hasNext()) {
			try {
				int number = Integer.parseInt(scanner.next());
				return number;
			} catch (NumberFormatException ex) {
				return -1;
			}
		}
		
		return -1;
	}
	
}
