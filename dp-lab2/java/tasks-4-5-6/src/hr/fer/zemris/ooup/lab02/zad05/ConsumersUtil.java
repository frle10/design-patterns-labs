package hr.fer.zemris.ooup.lab02.zad05;

import java.util.List;
import java.util.function.Consumer;

public class ConsumersUtil {
	
	public static Consumer<List<Integer>> sum = list -> {
		int sum = 0;
		for (int i = 0; i < list.size(); i++) {
			sum += list.get(i);
		}
		
		System.out.println(sum);
	};
	
	public static Consumer<List<Integer>> average = list -> {
		double average = 0.0;
		for (int i = 0; i < list.size(); i++) {
			average += list.get(i);
		}
		
		average /= list.size();
		System.out.println(average);
	};
	
	public static Consumer<List<Integer>> median = list -> {
		double median = 0.0;
		int N = list.size();
		
		if (N % 2 == 1) {
			median = list.get(N / 2);
		} else {
			median = (list.get(N / 2 - 1) + list.get(N / 2)) / 2.;
		}
		
		System.out.println(median);
	};
	
}
