package hr.fer.zemris.ooup.lab02.zad04.percentilecalculators;

import java.util.List;

@FunctionalInterface
public interface PercentileCalculator {
	double calculatePthPercentile(List<Integer> numbers, int percentile);
}
