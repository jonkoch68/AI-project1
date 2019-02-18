package chases_brain;

import core.Boid;
import core.Brain;
import core.World;
import geometry.CircularTarget;
import geometry.Region;
import processing.core.PVector;

/**
 * @author chase
 *
 */
public class Defender implements Brain{
private Region region_;//home region
private CircularTarget goal_;//goal being defended
private int stance_;//passive vs active defense
public Defender(Region region, CircularTarget goal) {
	region_=region;
	goal_=goal;
	stance_=1;
	
}
/* (non-Javadoc)
 * @see core.Brain#getNetSteeringForce(core.Boid, core.World)
 */
@Override
public PVector getNetSteeringForce ( Boid boid, World world ) {
	// TODO Auto-generated method stub
	if(region_.inRegion) {
		
	}
	switch(stance_) {
	case 1://defend
		
		break;
	}
	return null;
}

}
