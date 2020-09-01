package hr.fer.zemris.ooup.lab3.texteditor;

import java.lang.reflect.InvocationTargetException;

public class PluginFactory {
	
	@SuppressWarnings("unchecked")
	public static Plugin newInstance(String pluginName) {
		Class<Plugin> clazz = null;
		try {
			clazz = (Class<Plugin>)Class.forName("hr.fer.zemris.ooup.lab3.texteditor.plugins." + pluginName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Plugin plugin = null;
		try {
			plugin = (Plugin)clazz.getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		
		return plugin;
	}
	
}
