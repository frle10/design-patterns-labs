package hr.fer.zemris.ooup.lab02.zad04.numbergenerators;

import java.util.List;

@FunctionalInterface
public interface WholeNumberGenerator {
	List<Integer> generate();
}
