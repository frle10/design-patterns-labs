package hr.fer.zemris.ooup.lab02.zad04.percentilecalculators;

import java.util.ArrayList;
import java.util.List;

public class LinearInterpolationPercentileCalculator implements PercentileCalculator {
	
	@Override
	public double calculatePthPercentile(List<Integer> numbers, int percentile) {
		List<Double> percentRanks = new ArrayList<>();
		int N = numbers.size();
		for (int i = 0; i < N; i++) {
			double percentRank = 100. / N * ((i + 1) - 0.5);
			
			if (i == 0 && percentile < percentRank) return numbers.get(i);
			else if (i == N - 1 && percentile > percentRank) return numbers.get(i);
			else if (percentile == (int)Math.round(percentRank)) return numbers.get(i);
			
			percentRanks.add(percentRank);
		}
		
		double v = 0.0;
		for (int i = 0; i < N - 1; i++) {
			if (percentile > percentRanks.get(i) && percentile < percentRanks.get(i + 1)) {
				v = numbers.get(i) + N / 100.
						* (percentile - percentRanks.get(i))
						* (numbers.get(i + 1) - numbers.get(i));
			}
		}
		
		return v;
	}
	
}
