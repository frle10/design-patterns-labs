package hr.fer.zemris.ooup.lab02.zad04.percentilecalculators;

import java.util.List;

public class NearestRankPercentileCalculator implements PercentileCalculator {
	
	@Override
	public double calculatePthPercentile(List<Integer> numbers, int percentile) {
		numbers.sort(null);
		double nP = percentile * numbers.size() / 100. + 0.5;
		return numbers.get((int)Math.round(nP) - 1);
	}
	
}
