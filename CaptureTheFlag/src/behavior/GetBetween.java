package behavior;

import core.Behavior;
import core.Boid;
import core.Target;
import core.World;
import geometry.CircularRegion;
import geometry.CircularTarget;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * @author chase
 *
 */
public class GetBetween extends Behavior{
	private CircularTarget goal_;
	private Boid target_;
	public GetBetween ( Boid Etarget, CircularTarget goal, int c ) {
		super(c);
		target_=Etarget;
		goal_=goal;
	}

	/* (non-Javadoc)
	 * @see core.Behavior#getSteeringForce(core.Boid
	 * , core.World)
	 */
	@Override
	public PVector getSteeringForce ( Boid boid, World world ) {
		PVector bpos=boid.getPosition();
		PVector gpos=goal_.getTarget().getPosition();
		PVector tpos=target_.getPosition();
		PVector line=PVector.add(gpos,tpos);
		PVector.div(line,(3/4),line);
		PVector destination=PVector.sub(line,bpos);
		PVector steering = PVector.sub(destination,boid.getVelocity());
		steering.limit(boid.getMaxForce());
		return steering;
	}

	/**
	 * Get the magnitude of the maximum steering force that can result from this
	 * behavior.
	 * 
	 * @param boid
	 *          the boid
	 * @param world
	 *          the world containing the boid
	 * @return the magnitude of the maximum steering force that can result from
	 *         this behavior
	 */
	public float getMaxSteeringForce ( Boid boid, World world ) {
		return 2 * boid.getMaxSpeed();
	}

}
