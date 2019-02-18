package chases_brain;

import java.util.ArrayList;

import behavior.CircleDefend;
import behavior.GetBetween;
import behavior.GotoSide;
import behavior.Pursue;
import behavior.Seek;
import behavior.Wander;
import core.Boid;
import core.Brain;
import core.World;
import geometry.CircularTarget;
import geometry.RectangularRegion;
import geometry.Region;
import processing.core.PVector;
import target.BoidTarget;

/**
 * @author chase
 */
public class Defender implements Brain {
	private RectangularRegion region_;// home region
	private CircularTarget goal_;// goal being defended
	private int stance_;// passive vs active defense
	private int id_;// the boid's team id
	private ArrayList<Boid> teamMates_;

	public Defender ( RectangularRegion region, CircularTarget goal, int id,
	                  ArrayList<Boid> teamMates ) {
		region_ = region;
		goal_ = goal;
		stance_ = 1;
		id_ = id;
		teamMates_ = teamMates;
	}

	/*
	 * (non-Javadoc)
	 * @see core.Brain#getNetSteeringForce(core.Boid, core.World)
	 */
	@Override
	public PVector getNetSteeringForce ( Boid boid, World world ) {
		// TODO Auto-generated method stub
		if ( region_.inRegion(boid) ) {//In Home Region
			if ( boid.getEnemies().size() == 0 ) {//no enemies nearby

				if ( teamMates_.size() >= 5 ) {//5+teammates
					return new Wander(0).getSteeringForce(boid,world);
				} else if ( teamMates_.size() >= 1 ) {//last standing
					return new CircleDefend(50,goal_).getSteeringForce(boid,world);
				} else {//should be impossible to get here
					System.out.println("Shouldn't get here: teammates<1");
					return new PVector(0,0);
				}

			} else if ( boid.getEnemies().size() == 1 ) {//if there is only one enemy
				return new GetBetween(boid.getEnemies().get(0),goal_,0)
				    .getSteeringForce(boid,world);
			} else {//if there are multiple enemies
				return new Pursue(new BoidTarget(boid.getEnemies().get(0)),0)
				    .getSteeringForce(boid,world);
			}
		} else {
			return new GotoSide(region_,0).getSteeringForce(boid,world);
		}

	}

}
