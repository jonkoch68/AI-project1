package jacks_brain;

import behavior.Defend;
import behavior.GotoSide;
import core.Boid;
import core.Brain;

import core.World;
import geometry.CircularTarget;
import geometry.RectangularRegion;
import processing.core.PVector;
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
	private int persuers_,id_;
	private String state_;
	private RectangularRegion region_;
	private CircularTarget circ_;
	public Defensive_brain(int id,RectangularRegion region, CircularTarget circ) {
		persuers_ = 0;		
		id_ = id;
		region_ = region;
		circ_ = circ;
		state_ = "Defend";
	}
	@Override
	public PVector getNetSteeringForce ( Boid boid, World world ) {
		switch(state_) {
		case "Defend":{
			if(!region_.inRegion(boid)) {
				return new GotoSide(region_,0).getSteeringForce(boid,world); 
			}else if(boid.getNeighbors().size() != 0) {
				
			}else {
				return new Defend(75,circ_,0).getSteeringForce(boid,world);
			}
		}
		case "pursue":{
			
		}

		}
		return null;
	}
	

}
