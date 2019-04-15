package math;

import com.zalinius.physics.Point2D;
import com.zalinius.physics.UnitVector;

/**
 * A parametric line
 */
public class Line implements ParametricCurve {

	public final Point2D source;
	public final UnitVector direction;

	public Line(Point2D source, UnitVector direction) {
		this.source = source;
		this.direction = direction;
	}
	public Line(UnitVector direction) {
		this(new Point2D(), direction);
	}
	public Line(double yIntercept, double slope) {
		this(new Point2D(0, yIntercept), new UnitVector(1, slope));
	}
	
	public double solveForX(double y) {
		double t = (y - source.y) / direction.y;
		return direction.x * t + source.x;
	}
	public double solveForY(double x) {
		double t = (x - source.x) / direction.x;
		return direction.y * t + source.y;
	}

	@Override
	public Point2D getPoint(double t) {
		return Point2D.add(source, direction.scale(t));
	}

}
