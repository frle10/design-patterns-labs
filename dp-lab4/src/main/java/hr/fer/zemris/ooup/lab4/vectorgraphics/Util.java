package hr.fer.zemris.ooup.lab4.vectorgraphics;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import hr.fer.zemris.ooup.lab4.vectorgraphics.geometry.CompositeShape;
import hr.fer.zemris.ooup.lab4.vectorgraphics.geometry.LineSegment;
import hr.fer.zemris.ooup.lab4.vectorgraphics.geometry.Oval;
import hr.fer.zemris.ooup.lab4.vectorgraphics.interfaces.GraphicalObject;
import hr.fer.zemris.ooup.lab4.vectorgraphics.models.DocumentModel;

/**
 * This class provides <code>public static</code> methods used
 * in {@link VectorGraphics} class to implement actions such as saving
 * a file or opening an existing file.
 * 
 * @author Ivan Skorupan
 */
public class Util {
	
	private static final Map<String, GraphicalObject> PROTOTYPES = new HashMap<>();
	
	static {
		PROTOTYPES.put("@LINE", new LineSegment());
		PROTOTYPES.put("@OVAL", new Oval());
		PROTOTYPES.put("@COMP", new CompositeShape(new ArrayList<>()));
	}
	
	/**
	 * This method performs a saving operation on a given <code>savePath</code> and
	 * <code>model</code>.
	 * 
	 * @param savePath - path to which to save the <code>model</code>
	 * @param model - drawing model to save
	 * @throws Exception if any kind of error occurs while saving
	 */
	public static void saveFile(Path savePath, DocumentModel model) throws IOException {
		List<String> rows = new ArrayList<>();
		for (GraphicalObject object : model.list()) {
			object.save(rows);
		}
		
		StringBuilder sb = new StringBuilder();
		for (String line : rows) {
			sb.append(line + "\n");
		}
		
		Files.writeString(savePath, sb.toString());
	}
	
	/**
	 * This method opens a .jvd file from <code>openPath</code> and loads it into
	 * the <code>model</code>.
	 * 
	 * @param openPath - path from which to load a .jvd file
	 * @param model - drawing model to load the opened file into
	 * @throws Exception if any kind of error occurs while opening the file
	 */
	public static void openFile(Path openPath, DocumentModel model) throws Exception {
		List<String> lines = Files.readAllLines(openPath);
		Stack<GraphicalObject> objectStack = new Stack<>();
		
		for (String line : lines) {
			String id = line.split(" ")[0];
			PROTOTYPES.get(id).load(objectStack, line.substring(id.length() + 1));
		}
		
		List<GraphicalObject> objects = new ArrayList<>();
		while(!objectStack.isEmpty()) {
			objects.add(0, objectStack.pop());
		}
		
		for (GraphicalObject object : objects) {
			model.addGraphicalObject(object);
		}
	}
	
}
