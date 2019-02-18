package jacks_brain;

import behavior.Pursue;
import behavior.GotoSide;
import behavior.Evade;
import arbitrator.PrioritizedDithering;
import core.Boid;
import core.Brain;
import core.World;
import geometry.CircularTarget;
import geometry.RectangularRegion;
import processing.core.PVector;
import target.BoidTarget;


/**
 * @author Jonko
 *
 */
public class Offensive_brain implements Brain {

	/* (non-Javadoc)
	 * @see core.Brain#getNetSteeringForce(core.Boid, core.World)
	 */
	
	

	private String state_;
	private RectangularRegion region_;
	private CircularTarget goal_;
	
	public Offensive_brain(RectangularRegion region, CircularTarget goal) {

		region_ = region;
		goal_ = goal;
		state_ = "Attack";
	}
	@Override
	public PVector getNetSteeringForce ( Boid boid, World world ) {
		switch(state_){
		case "Attack":{
			if(boid.getEnemies().size() >= 1) {
				state_ = "Evade";
			}
			else {
				return new Pursue(goal_.getTarget(),world.getApplet().color(255,0,0)).getSteeringForce(boid,world);
			}
		}
		case "Evade":{
			if(boid.getEnemies().size() > 2) {
				state_ = "Retreat";
			}
			else if(boid.getEnemies().size() == 0){
				state_ = "Attack";
			}
			else {
			Pursue pursuit = new Pursue(goal_.getTarget(),world.getApplet().color(255,0,0));
			BoidTarget predator = new BoidTarget(boid.getEnemies().get(0));
			Evade evade = new Evade(predator,world.getApplet().color(255,0,0));
			PrioritizedDithering arbitrator = new PrioritizedDithering();
			arbitrator.addBehavior(evade,1,0.45f);
			arbitrator.addBehavior(pursuit,0,.65f);
			return arbitrator.getNetSteeringForce(boid,world);
			}
		}
		case "Retreat":{
			if(region_.inRegion(boid)) {
				state_ = "Attack";
			}else {
			return new GotoSide(region_,world.getApplet().color(125,255,0)).getSteeringForce(boid,world);
		}
		}
		}
		return new PVector(0,0);
	}

}
