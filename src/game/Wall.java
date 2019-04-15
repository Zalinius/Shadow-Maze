package game;

import com.zalinius.architecture.Collidable;
import com.zalinius.architecture.GameObject;
import com.zalinius.geometry.Rectangle;
import com.zalinius.geometry.Shape;
import com.zalinius.physics.Point2D;
import com.zalinius.physics.Vector2D;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Wall implements GameObject, Collidable{
	private Point2D center;
	private Vector2D dimensions;
	
	public Wall(double x, double y, double w, double h) {
		this(new Point2D(x, y), new Vector2D(w, h));
	}
	
	public Wall(Point2D center, Vector2D dimensions) {
		this.center = center;
		this.dimensions = dimensions;
	}

	@Override
	public void render(GraphicsContext gc) {
		gc.setFill(Color.BLACK);
		gc.fillRect(center.x - dimensions.x/2, center.y - dimensions.y/2, dimensions.x, dimensions.y); 
	}

	@Override
	public void update(double delta) {
	}

	@Override
	public Shape getCollisionBox() {
		return new Rectangle(Point2D.add(center, dimensions.scale(-0.5)),Point2D.add(center, dimensions.scale(0.5)));
	}
	
	
	public double leftSide() {
		return center.x - dimensions.x/2;
	}	
	public double rightSide() {
		return center.x + dimensions.x/2;
	}
	public double upSide() {
		return center.y + dimensions.y/2;
	}
	public double downSide() {
		return center.y - dimensions.y/2;
	}

}
