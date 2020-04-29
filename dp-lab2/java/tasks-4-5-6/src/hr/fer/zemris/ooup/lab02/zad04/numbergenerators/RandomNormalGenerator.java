package hr.fer.zemris.ooup.lab02.zad04.numbergenerators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomNormalGenerator implements WholeNumberGenerator {
	
	private double mean;
	private double variance;
	private int nElem;
	
	public RandomNormalGenerator(double mean, double variance, int nElem) {
		this.mean = mean;
		this.variance = variance;
		this.nElem = nElem;
	}
	
	@Override
	public List<Integer> generate() {
		List<Integer> numbers = new ArrayList<>();
		Random random = new Random();
		
		for (int i = 0; i < this.nElem; i++) {
			double randomNum = this.mean + random.nextGaussian() * this.variance;
			numbers.add((int)Math.round(randomNum));
		}
		
		return numbers;
	}
	
}
