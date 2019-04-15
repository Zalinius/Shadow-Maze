package player;

import java.util.Collection;

import com.zalinius.architecture.Collidable;
import com.zalinius.architecture.GameObject;
import com.zalinius.architecture.input.Inputtable;
import com.zalinius.geometry.Circle;
import com.zalinius.geometry.Rectangle;
import com.zalinius.geometry.Shape;
import com.zalinius.physics.Point2D;
import com.zalinius.physics.Vector2D;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Player implements GameObject, Collidable {

	public Player() {
		this(new Point2D());
	}
	public Player(Point2D startingPoint) {
		position = startingPoint;
		direction = new PlayerDirection();
	}

	private Point2D position;
	private PlayerDirection direction;

	@Override
	public void update(double delta) {
		if(direction.shouldBeMoving()) {
			Vector2D movement = direction.direction().scale(delta * 200);
			position = Point2D.add(position, movement);
		}
	}
	
	public double size() {
		return 20;
	}

	@Override
	public void render(GraphicsContext gc) {
		gc.setFill(Color.BLACK);
		gc.fillOval(position.x-20, position.y-20, 40, 40);

		Point2D front = Point2D.add(position, direction.direction().scale(10));
		gc.setFill(Color.WHITE);
		gc.fillOval(front.x-1, front.y-1, 2, 2);			
	}
	
	public void forceMove(Point2D newPosition) {
		position = newPosition;
	}

	public Point2D position() {
		return position;
	}

	public Collection<Inputtable> keyboardControls() {
		return direction.keyboardControls();
	}
	
	@Override
	public Shape getCollisionBox() {
		return new Rectangle(new Point2D(position.x-20, position.y-20), 40.0, 40.0);
	}





}
