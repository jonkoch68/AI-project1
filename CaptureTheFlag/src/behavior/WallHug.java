package behavior;

import core.Behavior;
import core.Boid;
import core.World;
import processing.core.PVector;
/**
 * @author chase
 *
 */
public class WallHug extends Behavior{

	/**
	 * @param c
	 */
	public WallHug ( int c ) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see core.Behavior#getSteeringForce(core.Boid, core.World)
	 */
	@Override
	public PVector getSteeringForce ( Boid boid, World world ) {
		// TODO Auto-generated method stub
		PVector bpos=boid.getPosition();
		PVector wall=new PVector(world.getApplet().width/2 ,world.getApplet().height);
		
		if(bpos.y>world.getApplet().width/2) {
			if(bpos.y>world.getApplet().width-30) {
				PVector target = PVector.sub(new PVector(bpos.x,world.getApplet().height),bpos);
				PVector steering = PVector.sub(target,boid.getVelocity());
				steering.limit(boid.getMaxForce());
				return steering;
			}
			else return new PVector(0,0);
		}
		else {
			if(bpos.y>30) {
				PVector target = PVector.sub(new PVector(bpos.x,0),bpos);
				PVector steering = PVector.sub(target,boid.getVelocity());
				steering.limit(boid.getMaxForce());
				return steering;
			}
			else return new PVector(0,0);
		}
	}

}
