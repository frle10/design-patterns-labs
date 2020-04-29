package hr.fer.zemris.ooup.lab02.zad05;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

public class FileAllElementsTimestamp implements Consumer<List<Integer>> {
	
	private OutputStream outputStream;
	
	public FileAllElementsTimestamp(Path path) {
		try {
			this.outputStream = new BufferedOutputStream(
							new FileOutputStream(path.toFile()));
		} catch (FileNotFoundException e) {
			System.err.println("File not found!");
		}
	}
	
	@Override
	public void accept(List<Integer> list) {
		String timestamp = DateFormat.getInstance().format(new Date().getTime()) + " ";
		try {
			outputStream.write(timestamp.getBytes());
			
			for (int i = 0; i < list.size(); i++) {
				String line = list.get(i).toString() + " ";
				outputStream.write(line.getBytes());
			}
			
			outputStream.write("\n".getBytes());
			outputStream.flush();
		} catch (IOException e) {
			System.err.println("Error while writing to file!");
		}
	}

	public OutputStream getOutputStream() {
		return outputStream;
	}
}
