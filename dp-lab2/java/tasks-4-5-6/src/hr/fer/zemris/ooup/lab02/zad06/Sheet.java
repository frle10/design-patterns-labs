package hr.fer.zemris.ooup.lab02.zad06;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Sheet {
	
	private Cell[][] sheet;
	private int rows;
	private int columns;
	
	public Sheet(int rows, int columns) {
		sheet = new Cell[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				sheet[i][j] = new Cell();
			}
		}
		
		this.rows = rows;
		this.columns = columns;
	}
	
	public Cell cell(String ref) {
		if(Pattern.matches("[A-Z][1-9]+", ref.toUpperCase())) {
			char rowLetter = ref.toUpperCase().charAt(0);
			
			int rowIndex = convertLetterToRowIndex(rowLetter);
			int columnIndex = Integer.parseInt(ref.substring(1)) - 1;
			
			return sheet[rowIndex][columnIndex];
		}
		
		return null;
	}
	
	public int evaluate(Cell cell) {
		return evaluate(cell, 0);
	}
	
	private int evaluate(Cell cell, int recursionLevel) {
		if (recursionLevel > rows * columns) {
			throw new CircularDependencyException("There are circular dependencies in the sheet!");
		}
		
		int cellValue = 0;
		try {
			cellValue = Integer.parseInt(cell.getExp());
		} catch (NumberFormatException ex) {
			List<Cell> refs = getRefs(cell);
			for (Cell ref : refs) {
				ref.getUsers().add(cell);
				cellValue += evaluate(ref, recursionLevel + 1);
			}
		}
		
		cell.setValue(cellValue);
		return cellValue;
	}
	
	public void set(String ref, String content) {
		Cell cell = cell(ref);
		
		if (cell != null) {
			cell.setExp(content);
			evaluate(cell);
			notify(cell);
		}
	}
	
	private void notify(Cell cell) {
		for (Cell parentCell : cell.getUsers()) {
			evaluate(parentCell);
		}
	}
	
	public List<Cell> getRefs(Cell cell) {
		String content = cell.getExp();
		String[] refs = content.split("[+-/%\\*]");
		
		List<Cell> cells = new ArrayList<>();
		for (String ref : refs) {
			cells.add(cell(ref));
		}
		
		return cells;
	}
	
	public void print() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				Cell currentCell = sheet[i][j];
				System.out.printf("%3d ", currentCell.getValue());
			}
			System.out.println();
		}
	}
	
	private int convertLetterToRowIndex(char letter) {
		return letter - 'A';
	}
	
}
