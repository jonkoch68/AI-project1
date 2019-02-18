package behavior;

import core.Behavior;
import core.Boid;
import core.World;
import geometry.RectangularRegion;
import processing.core.PVector;

/**
 * @author Jonko
 */
public class GotoSide extends Behavior {

	private RectangularRegion region_;

	/**
	 * @param c
	 */
	public GotoSide ( RectangularRegion region, int c ) {
		super(c);
		region_ = region;
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * @see core.Behavior#getSteeringForce(core.Boid, core.World)
	 */
	@Override
	public PVector getSteeringForce ( Boid boid, World world ) {

		if ( region_.inRegion(boid) ) {
			return new PVector(0,0);
		} else {
			PVector p = boid.getPosition();
			PVector t = region_.getCenter();
			PVector target = PVector.sub(t,p);
			PVector steering = PVector.sub(target,boid.getVelocity());
			steering.limit(boid.getMaxForce());
			return steering;

		}
	}

}
