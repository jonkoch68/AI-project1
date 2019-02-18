package custom_brain;

import arbitrator.PrioritizedDithering;
import behavior.Defend;
import behavior.Evade;
import behavior.GotoSide;
import behavior.Pursue;
import core.Boid;
import core.Brain;

import core.World;
import geometry.CircularTarget;
import geometry.RectangularRegion;
import processing.core.PVector;
import target.BoidTarget;

/**
 * @author Jonko
 */
public class Defensive_brain implements Brain {

	/*
	 * (non-Javadoc)
	 * @see core.Brain#getNetSteeringForce(core.Boid, core.World)
	 */

	private String state_;
	private RectangularRegion region_;
	private CircularTarget guard_, goal_;

	/**
	 * @param region
	 * @param guard
	 * @param goal
	 *          Creates defensive brain with a friendly region, desired guarding
	 *          area, and desired target area
	 */
	public Defensive_brain ( RectangularRegion region, CircularTarget guard,
	                         CircularTarget goal ) {

		region_ = region;
		guard_ = guard;
		state_ = "Defend";
		goal_ = goal;
	}

	@Override
	public PVector getNetSteeringForce ( Boid boid, World world ) {
		switch ( state_ ) {
		case "Defend": {
			if ( boid.getFriends().size() < 3 ) {
				state_ = "Score";
			} else if ( boid.getEnemies().size() != 0 ) {
				state_ = "Pursue";
			} else if ( !region_.inRegion(boid) ) {
				return new GotoSide(region_,boid.getId()).getSteeringForce(boid,world);

			} else {

				return new Defend(125,guard_,0).getSteeringForce(boid,world);
			}
		}
		case "Pursue": {
			if ( !region_.inRegion(boid) ) {
				state_ = "Defend";
			}
			if ( boid.getEnemies().size() == 0 ) {
				state_ = "Defend";
			} else {
				return new Pursue(new BoidTarget(boid.getEnemies().get(0)),0)
				    .getSteeringForce(boid,world);
			}
		}
		case "Score": {
			if ( boid.getEnemies().size() == 0 ) {
				return new Pursue(goal_.getTarget(),world.getApplet().color(255,0,0))
				    .getSteeringForce(boid,world);
			} else {
				Pursue pursuit =
				    new Pursue(goal_.getTarget(),world.getApplet().color(255,0,0));
				BoidTarget predator = new BoidTarget(boid.getEnemies().get(0));
				Evade evade = new Evade(predator,world.getApplet().color(255,0,0));
				PrioritizedDithering arbitrator = new PrioritizedDithering();
				arbitrator.addBehavior(evade,1,0.45f);
				arbitrator.addBehavior(pursuit,0,.65f);
				return arbitrator.getNetSteeringForce(boid,world);
			}
		}
		}
		return new PVector(0,0);
	}

}
