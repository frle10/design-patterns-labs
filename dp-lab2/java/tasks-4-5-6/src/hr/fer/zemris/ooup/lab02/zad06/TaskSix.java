package hr.fer.zemris.ooup.lab02.zad06;

public class TaskSix {
	
	public static void main(String[] args) {
		Sheet s = new Sheet(5, 5);
		
		s.set("A1", "2");
		s.set("A2", "5");
		s.set("A3", "A1+A2");
		s.print();
		
		System.out.println();
		
		s.set("A1", "4");
		s.set("A4", "A1+A3");
		s.print();
		
		System.out.println();
		
		try {
			s.set("A1", "A3");
		} catch (CircularDependencyException ex) {
			System.err.println("Caught exception!");
		}
		
		// This line will cause an exception because of circular dependencies.
		// s.print();
	}
	
}
