package jacks_brain;

import core.Boid;
import core.Brain;
import core.World;
import geometry.CircularTarget;
import geometry.RectangularRegion;
import processing.core.PVector;

/**
 * @author Jonko
 *
 */
public class Neutral_brain implements Brain{

	/* (non-Javadoc)
	 * @see core.Brain#getNetSteeringForce(core.Boid, core.World)
	 */
	
	private String state_;
	private RectangularRegion region_;
	private CircularTarget goal_;
	
	public Neutral_brain(RectangularRegion region, CircularTarget goal) {
		region_ = region;
		goal_ = goal;
		state_ = "Bait";
	}
	@Override
	public PVector getNetSteeringForce ( Boid boid, World world ) {
		// TODO Auto-generated method stub
		switch(state_) {
		case "Bait":{
			
		}
		case "Defend":{
			
		}
		case "Attack":
		}
		return new PVector(0,0);
	}

}
 