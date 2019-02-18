import arbitrator.OneChoice;
import behavior.Seek;
import brain.SimpleBrain;
import core.Behavior;
import core.Boid;
import processing.core.PApplet;
import processing.core.PVector;
import target.MouseTarget;

/**
 * Boids which seek the mouse position.
 */
public class SeekBoids extends BoidsCore {

	/*
	 * (non-Javadoc)
	 * @see Boids#makeBoid()
	 */
	@Override
	public void makeBoid () {
		// behaviors
		Behavior seek = new Seek(new MouseTarget(this),color(255,0,0));

		// an arbitrator to combine behaviors
		OneChoice arbitrator = new OneChoice(seek);

		// a brain for action selection
		SimpleBrain brain = new SimpleBrain(arbitrator);

		// make the boid and add it to the world
		Boid boid = new Boid(world_,new PVector(mouseX,mouseY),1,PVector.random2D(),
		                     0.05f,2,60,radians(125),brain,0);
		world_.addBoid(boid);
	}

	public static void main ( String[] args ) {
		PApplet.main("SeekBoids");
	}

}
