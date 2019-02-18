package chases_brain;

import java.util.ArrayList;

import arbitrator.SimplePrioritized;
import arbitrator.WeightedAverage;
import behavior.Evade;
import behavior.Flee;
import behavior.GotoSide;
import behavior.Seek;
import behavior.WallHug;
import core.Boid;
import core.Brain;
import core.World;
import geometry.CircularTarget;
import geometry.RectangularRegion;
import processing.core.PVector;
import target.BoidTarget;

/**
 * @author chase
 */
public class Attacker implements Brain {
	private RectangularRegion region_;// enemy region
	private RectangularRegion home_;
	private CircularTarget goal_;// goal being Attacked
	private int id_;// the boid's team id
	private ArrayList<Boid> teamMates_;
	private SimplePrioritized action_ = new SimplePrioritized();
	private int stance_;

	public Attacker ( RectangularRegion region, RectangularRegion home,
	                  CircularTarget goal, int id, ArrayList<Boid> teamMates ) {
		region_ = region;
		home_ = home;
		goal_ = goal;
		id_ = id;
		teamMates_ = teamMates;
		action_.addBehavior(new Seek(goal_.getTarget(),0),1);
		stance_ = (int) (Math.random() * 5.0)+1;
	}

	/*
	 * (non-Javadoc)
	 * @see core.Brain#getNetSteeringForce(core.Boid, core.World)
	 */
	@Override
	public PVector getNetSteeringForce ( Boid boid, World world ) {
		// TODO Auto-generated method stub
		// In/near the enemy region
		if ( region_.inRegion(boid) ) {
			switch ( stance_ ) {
			case 2:
			case 3:
			case 4:
				// unimpeded attack
				int numEnemies = boid.getEnemies().size();
				if ( numEnemies == 0 ) {
					return new Seek(goal_.getTarget(),0).getSteeringForce(boid,world);
				} else if ( numEnemies < 3 ) {// impeded attack
					action_
					    .addBehavior(new Evade(new BoidTarget(boid.getEnemies().get(0)),
					                           0),
					                 2);
					return action_.getNetSteeringForce(boid,world);
				} else {// there are 3 or more enemies, retreat
					action_.addBehavior(new GotoSide(home_,0),2);
					action_
					    .addBehavior(new Flee(new BoidTarget(boid.getEnemies().get(0)),0),
					                 3);
					return action_.getNetSteeringForce(boid,world);
				}
			case 5:// wallhug
				System.out.println("Wall hugger");
				float distance =
				    PVector.dist(boid.getPosition(),goal_.getTarget().getPosition());
				if ( distance <350) {// if boid is 1/3
				                                               // screen width
				                                               // from the goal it
				                                               // seeks
					return new Seek(goal_.getTarget(),0).getSteeringForce(boid,world);

				} else {
					return new WallHug(0).getSteeringForce(boid,world);
				}
			case 1:
			
				return new PVector(0,0);
			}
			
		}

		// moving to the enemy region
		return new GotoSide(region_,0).getSteeringForce(boid,world);
	}

}
