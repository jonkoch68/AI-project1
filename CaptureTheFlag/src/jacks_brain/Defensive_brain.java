package jacks_brain;

import behavior.Defend;
import behavior.GotoSide;
import behavior.Pursue;
import core.Boid;
import core.Brain;

import core.World;
import geometry.CircularTarget;
import geometry.RectangularRegion;
import processing.core.PVector;
import target.BoidTarget;

import java.awt.Color;
import java.util.ArrayList;

/**
 * @author Jonko
 *
 */
public class Defensive_brain implements Brain{

	/* (non-Javadoc)
	 * @see core.Brain#getNetSteeringForce(core.Boid, core.World)
	 */

	private String state_;
	private RectangularRegion region_;
	private CircularTarget circ_;
	public Defensive_brain(RectangularRegion region, CircularTarget circ) {

		region_ = region;
		circ_ = circ;
		state_ = "Defend";
	}
	@Override
	public PVector getNetSteeringForce ( Boid boid, World world ) {
		switch(state_) {
		case "Defend":{
			if(!region_.inRegion(boid)) {
				return new GotoSide(region_,boid.getId()).getSteeringForce(boid,world); 
			}else if(boid.getEnemies().size() != 0) {
				state_ = "pursue";
			}else {
				return new Defend(150,circ_,0).getSteeringForce(boid,world);
			}
		}
		case "pursue":{
			System.out.println("now pursuing");
			if(!region_.inRegion(boid)) {
				state_ = "Defend";
			}
			if(boid.getEnemies().size() == 0) {
				state_ = "Defend";
			}
			else {
				return new Pursue(new BoidTarget(boid.getEnemies().get(0)),0).getSteeringForce(boid,world);
			}
		}

		}
		return new PVector(0,0);
	}
	

}
