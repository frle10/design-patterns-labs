package hr.fer.zemris.ooup.lab02.zad04;

import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.ooup.lab02.zad04.numbergenerators.FibonacciGenerator;
import hr.fer.zemris.ooup.lab02.zad04.numbergenerators.RandomNormalGenerator;
import hr.fer.zemris.ooup.lab02.zad04.numbergenerators.SerialGenerator;
import hr.fer.zemris.ooup.lab02.zad04.percentilecalculators.LinearInterpolationPercentileCalculator;
import hr.fer.zemris.ooup.lab02.zad04.percentilecalculators.NearestRankPercentileCalculator;

public class TaskFour {
	
	public static void main(String[] args) {
		List<Integer> numbers = Arrays.asList(1, 10, 50);
		DistributionTester dt = new DistributionTester(new SerialGenerator(1, 10, 1),
				new NearestRankPercentileCalculator());
		
		List<Double> percentiles = dt.processPercentiles(numbers);
		System.out.println(percentiles);
		
		dt.setCalculator(new LinearInterpolationPercentileCalculator());
		percentiles = dt.processPercentiles(numbers);
		System.out.println(percentiles);
		
		percentiles = dt.processPercentiles(null);
		System.out.println(percentiles);
		
		dt.setGenerator(new RandomNormalGenerator(100, 5, 15));
		percentiles = dt.processPercentiles(null);
		System.out.println(percentiles);
		
		dt.setGenerator(new FibonacciGenerator(20));
		percentiles = dt.processPercentiles(null);
		System.out.println(percentiles);
	}
	
}
