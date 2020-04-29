package hr.fer.zemris.ooup.lab02.zad04;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.ooup.lab02.zad04.numbergenerators.WholeNumberGenerator;
import hr.fer.zemris.ooup.lab02.zad04.percentilecalculators.PercentileCalculator;

public class DistributionTester {
	
	private WholeNumberGenerator generator;
	private PercentileCalculator calculator;
	
	public DistributionTester(WholeNumberGenerator generator, PercentileCalculator calculator) {
		this.generator = generator;
		this.calculator = calculator;
	}
	
	public List<Double> processPercentiles(List<Integer> preGeneratedNumbers) {
		List<Double> percentiles = new ArrayList<>();
		
		List<Integer> numbers = (preGeneratedNumbers == null) ? generator.generate() : preGeneratedNumbers;
		for (int i = 10; i < 100; i += 10) {
			percentiles.add(calculator.calculatePthPercentile(numbers, i));
		}
		
		return percentiles;
	}
	
	public WholeNumberGenerator getGenerator() {
		return generator;
	}

	public void setGenerator(WholeNumberGenerator generator) {
		this.generator = generator;
	}

	public PercentileCalculator getCalculator() {
		return calculator;
	}

	public void setCalculator(PercentileCalculator calculator) {
		this.calculator = calculator;
	}
	
}
