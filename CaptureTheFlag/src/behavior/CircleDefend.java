package behavior;

import core.Behavior;
import core.Boid;
import core.World;
import geometry.CircularTarget;
import processing.core.PVector;

/**
 * @author chase
 */
public class CircleDefend extends Behavior {
	private float radius_;
	private CircularTarget target_;

	public CircleDefend ( float rad, CircularTarget destination ) {
		super(0);
		radius_ = rad;
		target_ = destination;

	}

	/*
	 * (non-Javadoc)
	 * @see core.Behavior#getSteeringForce(core.Boid, core.World)
	 */
	@Override
	public PVector getSteeringForce ( Boid boid, World world ) {
		// TODO Auto-generated method stub
		PVector bpos = boid.getPosition();
		PVector tpos = target_.getTarget().getPosition();
		if ( PVector.dist(bpos,tpos) > radius_ ) {
			PVector diff = PVector.sub(tpos,bpos);
			PVector steering = PVector.sub(diff,boid.getVelocity());
			steering.limit(boid.getMaxForce());
			return steering;
		}
		return new PVector(0,0);
	}
}
