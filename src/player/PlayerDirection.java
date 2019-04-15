package player;

import java.util.ArrayList;
import java.util.Collection;
import com.zalinius.architecture.input.Button;
import com.zalinius.architecture.input.Inputtable;
import com.zalinius.physics.UnitVector;
import com.zalinius.physics.Vector2D;

public class PlayerDirection {
	
	private boolean up, down, left, right;
	private UnitVector lastDirection;

	public PlayerDirection() {
		this(UnitVector.up());
	}
	
	public PlayerDirection(UnitVector initialDirection) {
		up = false;
		down = false;
		left = false;
		right = false;
		lastDirection = initialDirection;
	}
	
	public UnitVector direction() {
		if( (!up && !down && !left && !right) || (up && down && left && right) ) {
			return lastDirection;
		}
		else {
			Vector2D rawDirection = new Vector2D();
			if(up) {
				rawDirection = rawDirection.add(UnitVector.up());
			}
			if(down) {
				rawDirection = rawDirection.add(UnitVector.down());
			}
			if(left) {
				rawDirection = rawDirection.add(UnitVector.left());
			}
			if(right) {
				rawDirection = rawDirection.add(UnitVector.right());
			}
			
			if(rawDirection.isZeroVector()) {
				return lastDirection;
			}
			else {
				UnitVector direction = new UnitVector(rawDirection);
				lastDirection = direction;
				return direction;
			}			
		}
	}
	
	public boolean shouldBeMoving() {
		Vector2D rawDirection = new Vector2D();
		if(up) {
			rawDirection = rawDirection.add(UnitVector.up());
		}
		if(down) {
			rawDirection = rawDirection.add(UnitVector.down());
		}
		if(left) {
			rawDirection = rawDirection.add(UnitVector.left());
		}
		if(right) {
			rawDirection = rawDirection.add(UnitVector.right());
		}
		
		return !rawDirection.isZeroVector();
	}
	
	public Collection<Inputtable> keyboardControls(){
		Collection<Inputtable> inputs = new ArrayList<>();
		
		inputs.add(new Inputtable() {
			@Override
			public Button button() {return Button.UP;}
			@Override
			public void pressed() {up = true;}
			@Override
			public void released() {up = false;}
		});

		inputs.add(new Inputtable() {
			@Override
			public Button button() {return Button.DOWN;}
			@Override
			public void pressed() {down = true;}
			@Override
			public void released() {down = false;}
		});

		inputs.add(new Inputtable() {
			@Override
			public Button button() {return Button.LEFT;}
			@Override
			public void pressed() {left = true;}
			@Override
			public void released() {left = false;}
		});

		inputs.add(new Inputtable() {
			@Override
			public Button button() {return Button.RIGHT;}
			@Override
			public void pressed() {right = true;}
			@Override
			public void released() {right = false;}
		});
		
		return inputs;
	}

}
