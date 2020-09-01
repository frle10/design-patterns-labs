package hr.fer.zemris.ooup.lab3.model;

public class Main {

	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++) {
			Animal animal = AnimalFactory.newInstance(args[i], "Papiga");
			animal.animalPrintGreeting();
			animal.animalPrintMenu();
		}
	}

}
