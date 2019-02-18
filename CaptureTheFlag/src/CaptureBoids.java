import java.awt.Color;

import java.util.ArrayList;
import java.util.List;

import arbitrator.OneChoice;
import behavior.Seek;
import behavior.Defend;
import behavior.GotoSide;
import brain.SimpleBrain;
import core.Behavior;
import core.Boid;
import core.Brain;
import core.TeamBoids;
import processing.core.PApplet;
import processing.core.PVector;
import target.MouseTarget;

import geometry.RectangularRegion;
import geometry.CircularTarget;
/**
 * Boids which seek the mouse position.
 */
public class CaptureBoids extends BoidsCore {
	private List<TeamBoids> teamOneBoids_; // boids in the world
	private List<TeamBoids> teamTwoBoids_; // boids in the world
	private RectangularRegion areaRed_;
	private RectangularRegion areaBlue_;
	private CircularTarget target0_;
	private CircularTarget target1_;
	private int score0_,score1_;

	public void setup () {
		super.setup();
		score0_ = 0;
		score1_ = 0;
		teamOneBoids_ = new ArrayList<TeamBoids>();
		teamTwoBoids_ = new ArrayList<TeamBoids>();
		areaRed_ =
		    new RectangularRegion(world_,0.0f,0.0f,255,0,0,0);
		areaBlue_ =
		    new RectangularRegion(world_,world_.getApplet().width / 2,0.0f,0,0,255,
		                          1);
		target0_ = new CircularTarget(900.0f, world_.getApplet().height/2,0,world_,50.0f,color(255,0,255));
		target1_ = new CircularTarget(100.0f, world_.getApplet().height/2,1,world_,50.0f,color(255,0,255));
		
		world_.addThing(areaRed_);
		world_.addThing(areaBlue_);
		world_.addThing(target0_);
		world_.addThing(target1_);
		this.makeBoid(10,10,0,0);


	}
	/*
	 * (non-Javadoc)
	 * @see BoidsCore#makeBoid()
	 */
	/**
	 * 
	 * @param x determines where the x value on the map the boid will spawn
	 * @param y determines where the y value on the map the boid will spawn
	 * @param id Gives an id to which team the boid belongs to
	 * @param agression on a scale from 1 to 3 will determine how aggressive the boid will be 1 being defensive and 3 being aggresive
	 * This will make a boid with the parameters for our game of invaiders.
	 * 
	 */
	protected void makeBoid ( float x, float y, int id, int agression  ) {
		GotoSide friend = new GotoSide(areaBlue_,0);

		// an arbitrator to combine behaviors
		OneChoice arbitrator = new OneChoice(friend);

		// a brain for action selection
		SimpleBrain brain = new SimpleBrain(arbitrator);

		// make the boid and add it to the world
		TeamBoids boid = new TeamBoids(world_,new PVector(x,y),1,PVector.random2D(),
		                     0.05f,2,60,radians(125),brain, color(255*id), id);
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
		for(TeamBoids boids : teamOneBoids_) {
			if(target0_.inRegion(boids)){
				score(boids);
				System.out.printf("The score is Team 1: %d || Team 2: %d\n", score0_,score1_);
				break;
			}
		}
		for(TeamBoids boids : teamTwoBoids_) {
			if(target1_.inRegion(boids)){
				score(boids);
				System.out.printf("The score is Team 1: %d || Team 2: %d\n", score0_,score1_);
				break;
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
	public void score(TeamBoids boid) {
		if(boid.getTeam() == 1) {
			world_.killBoid(boid);
			teamTwoBoids_.remove(boid);
			score1_++;
		}else {
			world_.killBoid(boid);
			teamOneBoids_.remove(boid);
			score0_++;
		}
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
