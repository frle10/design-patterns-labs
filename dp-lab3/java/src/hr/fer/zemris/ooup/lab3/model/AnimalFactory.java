package hr.fer.zemris.ooup.lab3.model;

import java.lang.reflect.InvocationTargetException;

public class AnimalFactory {
	
	@SuppressWarnings("unchecked")
	public static Animal newInstance(String animalKind, String name) {
		Class<Animal> clazz = null;
		try {
			clazz = (Class<Animal>)Class.forName("hr.fer.zemris.ooup.lab3.model.plugins." + animalKind);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Animal animal = null;
		try {
			animal = (Animal)clazz.getConstructor(String.class).newInstance(name);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		
		return animal;
	}
	
}
