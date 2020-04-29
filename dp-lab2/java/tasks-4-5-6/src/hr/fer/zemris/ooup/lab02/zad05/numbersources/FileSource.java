package hr.fer.zemris.ooup.lab02.zad05.numbersources;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileSource implements NumberSource {
	
	private List<String> lines = new ArrayList<>();
	private int currentIndex;
	
	public FileSource(Path path) {
		try {
			this.lines = Files.readAllLines(path);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error while reading all lines from input file!");
		}
	}
	
	@Override
	public int nextNumber() {
		if (currentIndex >= lines.size()) return -1;
		
		int number = 0;
		try {
			number = Integer.parseInt(lines.get(currentIndex++));
		} catch (NumberFormatException ex) {
			return -1;
		}
		
		return number;
	}
	
}
