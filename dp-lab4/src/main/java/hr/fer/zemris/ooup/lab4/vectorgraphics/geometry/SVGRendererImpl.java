package hr.fer.zemris.ooup.lab4.vectorgraphics.geometry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.ooup.lab4.vectorgraphics.interfaces.Renderer;

public class SVGRendererImpl implements Renderer {
	
	private List<String> lines = new ArrayList<>();
	
	private String fileName;
	
	public SVGRendererImpl(String fileName) {
		this.fileName = fileName;
		String svgHeader = "<svg xmlns=\"http://www.w3.org/2000/svg\"" + 
				" xmlns:xlink=\"http://www.w3.org/1999/xlink\">";
		lines.add(svgHeader);
	}
	
	public void close() throws IOException {
		lines.add("</svg>");
		StringBuilder sb = new StringBuilder();
		for (String line : lines) {
			sb.append(line + "\n");
		}
		
		Files.writeString(Paths.get(fileName), sb.toString());
	}
	
	@Override
	public void drawLine(Point s, Point e) {
		StringBuilder sb = new StringBuilder();
		sb.append("<line x1=\"" + s.getX() + "\" y1=\"" + s.getY() + "\" x2=\"" + e.getX() + "\" y2=\"" + e.getY() + "\"/>");
		lines.add(sb.toString());
	}

	@Override
	public void fillPolygon(List<Point> points) {
		StringBuilder sb = new StringBuilder();
		sb.append("<polygon points=\"");
		
		for (Point point : points) {
			sb.append(point.getX() + "," + point.getY() + " ");
		}
		
		sb.deleteCharAt(sb.length() - 1);
		sb.append("\"/>");
		lines.add(sb.toString());
	}
	
}
