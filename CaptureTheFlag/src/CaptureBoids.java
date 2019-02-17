import arbitrator.OneChoice;
import behavior.Seek;
import brain.SimpleBrain;
import core.Behavior;
import core.Boid;
import core.Brain;
import processing.core.PApplet;
import processing.core.PVector;
import target.MouseTarget;

import geometry.RectangularRegion;
/**
 * Boids which seek the mouse position.
 */
public class CaptureBoids extends BoidsCore {


	
	
	public void setup() {
		super.setup();
		RectangularRegion areaRed = new RectangularRegion(world_,0.0f,0.0f,255,0,0,0);
		RectangularRegion areaBlue = new RectangularRegion(world_,world_.getApplet().width/2,0.0f,0,0,255,1);
		world_.addThing(areaRed);
		world_.addThing(areaBlue);
		

	}
	/* (non-Javadoc)
	 * @see BoidsCore#makeBoid()
	 */
	
	protected void makeBoid (float x, float y, int id, Brain brain) {
		// TODO Auto-generated method stub

	}
	
	
	
	
	
	
	
	public static void main ( String[] args ) {
		PApplet.main("CaptureBoids");
	}

}
