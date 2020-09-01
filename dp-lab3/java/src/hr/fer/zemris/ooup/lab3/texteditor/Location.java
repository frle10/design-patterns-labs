package hr.fer.zemris.ooup.lab3.texteditor;

import java.util.Objects;

public class Location implements Comparable<Location> {
	
	private int x;
	private int y;
	
	public Location(Location other) {
		this.x = other.x;
		this.y = other.y;
	}
	
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Location))
			return false;
		Location other = (Location) obj;
		return x == other.x && y == other.y;
	}

	@Override
	public String toString() {
		return "Location [x=" + x + ", y=" + y + "]";
	}

	@Override
	public int compareTo(Location o) {
		if (x < o.x) return -1;
		else if (x > o.x) return 1;
		
		if (y < o.y) return -1;
		else if (y > o.y) return 1;
		return 0;
	}
	
}
