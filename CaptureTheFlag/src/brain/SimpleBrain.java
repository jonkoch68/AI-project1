package brain;

import core.Arbitrator;
import core.Boid;
import core.Brain;
import core.World;
import processing.core.PVector;

/**
 * A simple boid brain - carries out a single action.
 */
public class SimpleBrain implements Brain {

	private Arbitrator action_; // the action

	/**
	 * Create a simple boid brain - carries out a single action.
	 * 
	 * @param action
	 *          the action
	 */
	public SimpleBrain ( Arbitrator action ) {
		action_ = action;
	}

	/*
	 * (non-Javadoc)
	 * @see core.Brain#getNetSteeringForce(core.Boid, core.World)
	 */
	@Override
	public PVector getNetSteeringForce ( Boid boid, World world ) {
		return action_.getNetSteeringForce(boid,world);
	}

}
