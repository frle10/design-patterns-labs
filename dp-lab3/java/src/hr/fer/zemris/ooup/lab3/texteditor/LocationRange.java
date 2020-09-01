package hr.fer.zemris.ooup.lab3.texteditor;

public class LocationRange {
	
	private Location rangeBegin;
	private Location rangeEnd;
	
	public LocationRange(LocationRange other) {
		this.rangeBegin = new Location(other.rangeBegin);
		this.rangeEnd = new Location(other.rangeEnd);
	}
	
	public LocationRange(Location leftRangeSide, Location rightRangeSide) {
		this.rangeBegin = leftRangeSide;
		this.rangeEnd = rightRangeSide;
	}
	
	public Location getRangeBegin() {
		return rangeBegin;
	}

	public void setRangeBegin(Location rangeBegin) {
		this.rangeBegin = rangeBegin;
	}

	public Location getRangeEnd() {
		return rangeEnd;
	}

	public void setRangeEnd(Location rangeEnd) {
		this.rangeEnd = rangeEnd;
	}

	public void setNewRangeSide(Location loc) {
		if (loc.compareTo(rangeEnd) < 0) {
			rangeBegin = loc;
		} else {
			rangeEnd = loc;
		}
	}
	
}
