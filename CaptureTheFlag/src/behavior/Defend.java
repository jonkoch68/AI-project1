package behavior;

import core.Behavior;
import core.Boid;
import core.Target;
import core.World;
import processing.core.PApplet;
import processing.core.PVector;
import geometry.CircularTarget;

/**
 * @author Jonko
 *
 */
public class Defend extends Behavior{

	
	private float radius_, lookahead_;
	private CircularTarget target_;
	/**
	 * @param c
	 */
	public Defend (float radius,CircularTarget target,  int c ) {
		super(c);
		radius_ = radius;
		target_ = target;

		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see core.Behavior#getSteeringForce(core.Boid, core.World)
	 */
	@Override
	public PVector getSteeringForce ( Boid boid, World world ) {

	PVector t = target_.getTarget().getPosition();
	PVector p = boid.getPosition();
	float dist = PApplet.dist(p.x,p.y,t.x,t.y);
	System.out.println(dist);
	if( dist < radius_) {
		return new PVector(0,0);
	}else {
		System.out.println("Redirecting");
		PVector target = PVector.sub(t,p);
		PVector steering = PVector.sub(target,boid.getVelocity());
		steering.limit(boid.getMaxForce());
		return steering;
	}


	}

}
