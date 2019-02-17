import arbitrator.WeightedAverage;
import behavior.Separation;
import brain.SimpleBrain;
import core.Behavior;
import core.Boid;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * Flocking.
 */
public class FlockBoids extends BoidsCore {

	/*
	 * (non-Javadoc)
	 * @see Boids#makeBoid()
	 */
	@Override
	public void makeBoid () {
		// behaviors
		Behavior separation = new Separation(color(255,0,0));

		// an arbitrator to combine behaviors
		WeightedAverage arbitrator = new WeightedAverage();
		arbitrator.addBehavior(separation,1.0f);

		// a brain for action selection
		SimpleBrain brain = new SimpleBrain(arbitrator);

		// make the boid and add it to the world
		Boid boid = new Boid(world_,new PVector(mouseX,mouseY),1,PVector.random2D(),
		                     0.05f,2,60,radians(125),brain);
		world_.addBoid(boid);
	}

	public static void main ( String[] args ) {
		PApplet.main("FlockBoids");
	}

}
