package custom_brain;

import behavior.Pursue;
import behavior.Seek;
import arbitrator.PrioritizedDithering;
import behavior.Wander;
import core.Boid;
import core.Brain;
import core.World;
import geometry.CircularTarget;
import geometry.RectangularRegion;
import processing.core.PVector;
import target.BoidTarget;
import behavior.Separation;
import behavior.Defend;

/**
 * @author Jonko
 */
public class Neutral_brain implements Brain {

	/*
	 * (non-Javadoc)
	 * @see core.Brain#getNetSteeringForce(core.Boid, core.World)
	 */

	private String state_;
	private RectangularRegion region_;
	private CircularTarget goal_, guard_;
	private String prevState_;

	/**
	 * @param region
	 * @param goal
	 * @param guard
	 *          Much like defensive brain this brain will take inputs for friendly
	 *          region, goal and area to guard Neutral acts as a fluid brain
	 *          between offensive and defensive
	 */
	public Neutral_brain ( RectangularRegion region, CircularTarget goal,
	                       CircularTarget guard ) {
		region_ = region;
		goal_ = goal;
		state_ = "Bait";
		prevState_ = "Bait";
		guard_ = guard;
	}

	@Override
	public PVector getNetSteeringForce ( Boid boid, World world ) {
		switch ( state_ ) {
		case "Bait": {
			if ( region_.inRegion(boid) ) {
				return new Seek(goal_.getTarget(),world.getApplet().color(65,124,32))
				    .getSteeringForce(boid,world);
			} else if ( boid.getEnemies().size() < 1 ) {
				prevState_ = state_;
				state_ = "Attract";
			} else if ( boid.getFriends().size() <= 2 ) {
				prevState_ = state_;
				state_ = "Defend";
			} else if ( boid.getFriends().size() == 1 ) {
				state_ = "Score";
			} else {
				return new Wander(world.getApplet().color(255,45,63))
				    .getSteeringForce(boid,world);
			}
		}
		case "Attract": {
			if ( boid.getEnemies().size() >= 1 && region_.inRegion(boid) ) {
				prevState_ = state_;
				state_ = "Attack";
			} else {
				Wander wander = new Wander(world.getApplet().color(255,45,63));
				Separation seperate =
				    new Separation(world.getApplet().color(0,125,255));
				PrioritizedDithering arbitrator = new PrioritizedDithering();
				arbitrator.addBehavior(seperate,1,.6f);
				arbitrator.addBehavior(wander,0,.4f);
				return arbitrator.getNetSteeringForce(boid,world);
			}
		}
		case "Defend": {
			if ( boid.getFriends().size() > 2 ) {
				prevState_ = state_;
				state_ = "Bait";
			}
			Defend defend =
			    new Defend(100,guard_,world.getApplet().color(45,125,200));
			Wander wander = new Wander(world.getApplet().color(0,255,25));
			PrioritizedDithering arb = new PrioritizedDithering();
			arb.addBehavior(defend,1,.75f);
			arb.addBehavior(wander,0,.25f);
			return arb.getNetSteeringForce(boid,world);
		}
		case "Attack": {
			if ( region_.inRegion(boid) == false ) {
				state_ = prevState_;
			} else {
				return new Pursue(new BoidTarget(boid.getEnemies().get(0)),
				                  world.getApplet().color(125,125,255))
				                      .getSteeringForce(boid,world);
			}
		}
		case "Score": {
			return new Pursue(goal_.getTarget(),world.getApplet().color(255,0,0))
			    .getSteeringForce(boid,world);
		}
		}
		return new PVector(0,0);
	}

}
