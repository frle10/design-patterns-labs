package hr.fer.zemris.ooup.lab02.zad06;

public class CircularDependencyException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public CircularDependencyException() {
		super();
	}
	
	public CircularDependencyException(String message) {
		super(message);
	}
	
}
