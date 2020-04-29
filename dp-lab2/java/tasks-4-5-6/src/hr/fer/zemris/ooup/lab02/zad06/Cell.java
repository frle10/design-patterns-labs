package hr.fer.zemris.ooup.lab02.zad06;

import java.util.HashSet;
import java.util.Set;

public class Cell {
	
	private String exp = "0";
	private int value;
	
	private Set<Cell> users = new HashSet<>();
	
	public Cell() {}
	
	public Cell(String exp) {
		this.exp = exp;
	}
	
	public String getExp() {
		return exp;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}

	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}

	public Set<Cell> getUsers() {
		return users;
	}
	
}
