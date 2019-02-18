package behavior;

import core.Boid;
import core.Target;
import core.World;
import geometry.CircularRegion;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * @author chase
 *
 */
public class GetBetween extends TargetBehavior{
	private CircularRegion goal_;
	private Target target_;
	public GetBetween ( Target Etarget, CircularRegion goal, int c ) {
		super(Etarget,c);
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
		PVector gpos=goal_.getCenter();
		PVector tpos=target_.getPosition();
		PVector line=PVector.add(gpos,tpos);
		PVector.div(line,2,line);
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
