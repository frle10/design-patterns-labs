package hr.fer.zemris.ooup.lab4.vectorgraphics.geometry;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * 
 * 
 * @author Ivan Skorupan
 */
public class GeometryUtil {
	
	public static final double DELTA = 1e-6;
	
	public static double distanceFromPoint(Point point1, Point point2) {
		return sqrt(pow(point1.getX() - point2.getX(), 2.) + pow(point1.getY() - point2.getY(), 2));
	}

	public static double distanceFromLineSegment(Point s, Point e, Point p) {
		int x = p.getX();
		int y = p.getY();
		int x1 = s.getX();
		int y1 = s.getY();
		int x2 = e.getX();
		int y2 = e.getY();
		
		double A = x - x1;
        double B = y - y1;
        double C = x2 - x1;
        double D = y2 - y1;

        double dot = A * C + B * D;
        double len_sq = C * C + D * D;
        double param = -1;
        if (len_sq != 0) {
        	param = dot / len_sq;
        }
        
        double xx, yy;

        if (param < 0) {
          xx = x1;
          yy = y1;
        }
        else if (param > 1) {
          xx = x2;
          yy = y2;
        }
        else {
          xx = x1 + param * C;
          yy = y1 + param * D;
        }

        double dx = x - xx;
        double dy = y - yy;
        return Math.sqrt(dx * dx + dy * dy);
	}

}
