import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import arbitrator.OneChoice;
import behavior.Seek;
import brain.SimpleBrain;
import core.Behavior;
import core.Boid;
import core.Brain;
import core.TeamBoids;
import processing.core.PApplet;
import processing.core.PVector;
import target.MouseTarget;

import geometry.RectangularRegion;

/**
 * Boids which seek the mouse position.
 */
public class CaptureBoids extends BoidsCore {
	private List<Boid> teamOneBoids_; // boids in the world
	private List<Boid> teamTwoBoids_; // boids in the world
	private RectangularRegion areaRed_;
	private RectangularRegion areaBlue_;

	public void setup () {
		super.setup();
		teamOneBoids_ = new ArrayList<Boid>();
		teamTwoBoids_ = new ArrayList<Boid>();
		areaRed_ =
		    new RectangularRegion(world_,0.0f,0.0f,255,0,0,0);
		areaBlue_ =
		    new RectangularRegion(world_,world_.getApplet().width / 2,0.0f,0,0,255,
		                          1);
		world_.addThing(areaRed_);
		world_.addThing(areaBlue_);
		this.makeBoid(10,10,0);
		this.makeBoid(750,600,1);
		this.makeBoid(750,600,1);

	}
	/*
	 * (non-Javadoc)
	 * @see BoidsCore#makeBoid()
	 */

	protected void makeBoid ( float x, float y, int id ) {
		// behaviors
		// behaviors
		Behavior seek = new Seek(new MouseTarget(this),color(255,0,0));

		// an arbitrator to combine behaviors
		OneChoice arbitrator = new OneChoice(seek);

		// a brain for action selection
		SimpleBrain brain1 = new SimpleBrain(arbitrator);

		// make the boid and add it to the world
		TeamBoids boid =
		    new TeamBoids(world_,new PVector(x,y),1,PVector.random2D(),
		                  0.05f,2,60,radians(125),brain1,color(255*id),id);
		world_.addBoid(boid);

		if ( id == 0 ) {
			teamOneBoids_.add(boid);
		} else {
			teamTwoBoids_.add(boid);
		}

	}

	public void draw () {
		super.draw();
		
		if ( !teamOneBoids_.isEmpty() && !teamTwoBoids_.isEmpty() ) {
			outerloop : for ( Boid toCheck : teamOneBoids_ ) {
				for ( Boid other : teamTwoBoids_ ) {
					if ( PVector.dist(other.getPosition(),toCheck.getPosition()) <= 10 ) {// overlapping
					                                                                      // coordinates
								
						if ( areaRed_.inRegion(toCheck) && areaRed_.inRegion(other) ) {// both
						                                                               // in
						                                                               // teamOne's
						                                                               // region
							kill(other);
							break;
						} else if ( areaBlue_.inRegion(toCheck)
						    && areaBlue_.inRegion(other) ) {// both in teamTwo's region
							kill(toCheck);
							break outerloop;
						}
						// otherwise do nothing
					}

				}
			}
		}
	}

	public void kill ( Boid victim ) {
		if ( teamTwoBoids_.contains(victim) ) {
			teamTwoBoids_.remove(victim);
		} else {
			teamOneBoids_.remove(victim);
		}
		world_.killBoid(victim);

	}

	public static void main ( String[] args ) {
		PApplet.main("CaptureBoids");
	}

	/*
	 * (non-Javadoc)
	 * @see BoidsCore#makeBoid()
	 */
	@Override
	protected void makeBoid () {

	}

}
