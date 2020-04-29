package hr.fer.zemris.ooup.lab02.zad05;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

import hr.fer.zemris.ooup.lab02.zad05.numbersources.FileSource;

public class TaskFive {
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		FileAllElementsTimestamp faet = new FileAllElementsTimestamp(Paths.get("output.txt"));
		Path input = Paths.get("input.txt");
		
		List<Consumer<List<Integer>>> actions = new ArrayList<>();
		actions.add(faet);
		actions.add(ConsumersUtil.average);
		NumberSequence ns = new NumberSequence(new FileSource(input), actions);
		ns.start();
		
		scanner.close();
		try {
			faet.getOutputStream().close();
		} catch (IOException e) {
			System.err.println("Error while closing the output stream!");
		}
	}
	
}
