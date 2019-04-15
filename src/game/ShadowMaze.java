package game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.zalinius.architecture.GameContainer;
import com.zalinius.architecture.GameObject;
import com.zalinius.architecture.Graphical;
import com.zalinius.architecture.Logical;
import com.zalinius.architecture.input.Button;
import com.zalinius.architecture.input.Inputtable;
import com.zalinius.geometry.Rectangle;
import com.zalinius.physics.Point2D;
import com.zalinius.physics.UnitVector;
import com.zalinius.physics.Vector2D;
import com.zalinius.physics.collisions.Collision;
import com.zalinius.utilities.Debug;
import com.zalinius.utilities.ZMath;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.FillRule;
import javafx.stage.Stage;
import math.Line;
import player.Player;

public class ShadowMaze extends GameContainer implements GameObject {

	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		super.start(primaryStage);
		Collection<Inputtable> controls = new ArrayList<>();
		controls.add(exitAction());
		controls.addAll(player.keyboardControls());
		addControls(controls, new ArrayList<>());
	}
	

	
	private int width = 1920, height = 1080;
	private Player player;
	private Collection<Wall> walls;
	
	public ShadowMaze() {
		player = new Player(new Point2D(width/2, height/2));
		walls = new ArrayList<>();
		walls.add(new Wall(300, 300, 100, 300));
	}


	@Override
	public Point2D windowSize() {
		return new Point2D(width, height);
	}

	@Override
	public String windowTitle() {
		return "Shadow Maze";
	}


	@Override
	public void render(GraphicsContext gc) {
		gc.setFill(Color.AQUA);
		gc.fillRect(0, 0, width, height);
		Wall wall = null;
		for (Iterator<Wall> wallIt = walls.iterator(); wallIt.hasNext();) {
			wall = wallIt.next();
			wall.render(gc);
		}
		player.render(gc);
		gc.setFill(Color.BLACK);
		gc.beginPath();
		gc.setFillRule(FillRule.EVEN_ODD);
		gc.moveTo(0, 0);
		Line ray = new Line(player.position(), new UnitVector(new Vector2D(player.position(), wall.tlCorner())));
		gc.lineTo(0, ray.solveForY(0));
		gc.lineTo(wall.leftSide(), wall.upSide());
		gc.lineTo(wall.rightSide(), wall.downSide());
		ray = new Line(player.position(), new UnitVector(new Vector2D(player.position(), wall.brCorner())));
		gc.lineTo(ray.solveForX(0), 0);
		gc.lineTo(0, 0);
		gc.closePath();
		gc.fill();
	}

	@Override
	public void update(double delta) {
		player.update(delta);
		for (Iterator<Wall> wallIt = walls.iterator(); wallIt.hasNext();) {
			Wall wall = wallIt.next();
			if(Collision.isOverlapping(player, wall)) {
				Point2D playerPos = player.position();
				double newX = playerPos.x, newY = playerPos.y;
				if(ZMath.isStrictlyBetween(wall.downSide(), wall.upSide(), playerPos.y)) {
					newX = ZMath.xClamp(playerPos.x, wall.leftSide() - player.size(), wall.rightSide() + player.size());
				}
				if(ZMath.isStrictlyBetween(wall.leftSide(), wall.rightSide(), playerPos.x))				{
					newY = ZMath.xClamp(playerPos.y, wall.downSide() - player.size(), wall.upSide() + player.size());
				}
				player.forceMove(new Point2D(newX, newY));
			}
		}		
	}
	
	private Inputtable exitAction() {
		return new Inputtable() {
			
			@Override
			public Button button() {
				return Button.ESCAPE;
			}
			
			@Override
			public void pressed() {
				Platform.exit();
			}
		};
	}
	
	
	@Override
	public Logical gameLogic()      {return this;}
	@Override
	public Graphical gameGraphics() {return this;}
}
