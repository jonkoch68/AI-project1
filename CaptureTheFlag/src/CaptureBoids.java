import java.awt.Color;

import java.util.ArrayList;
import arbitrator.OneChoice;
import behavior.Seek;
import brain.SimpleBrain;
import core.Behavior;
import core.Boid;
import core.Brain;
import custom_brain.Defensive_brain;
import custom_brain.Neutral_brain;
import custom_brain.Offensive_brain;
import processing.core.PApplet;
import processing.core.PVector;
import target.MouseTarget;
import geometry.RectangularRegion;
import geometry.CircularTarget;

/**
 * author Jonathan Koch and Charles Brown
 */
public class CaptureBoids extends BoidsCore {
	private ArrayList<Boid> teamOneBoids_; // boids in the world
	private ArrayList<Boid> teamTwoBoids_; // boids in the world
	private RectangularRegion areaOne_;
	private RectangularRegion areaTwo_;
	private CircularTarget targetOne_;
	private CircularTarget targetTwo_;
	private int score0_, score1_;

	public void setup () {
		super.setup();
		// Score Keeping
		score0_ = 0;
		score1_ = 0;
		// Lists for team 1
		teamOneBoids_ = new ArrayList<Boid>();
		teamTwoBoids_ = new ArrayList<Boid>();
		// Area that will divide the teams and determine friendly and enemy
		// territory
		areaOne_ = new RectangularRegion(world_,0.0f,0.0f,255,0,0,0);
		areaTwo_ = new RectangularRegion(world_,world_.getApplet().width / 2,0.0f,0,
		                                 0,255,1);
		// These are the endzones in which the other team is trying to reach
		targetOne_ = new CircularTarget(900.0f,world_.getApplet().height / 2,0,
		                                world_,50.0f,color(255,0,255));
		targetTwo_ = new CircularTarget(100.0f,world_.getApplet().height / 2,1,
		                                world_,50.0f,color(255,0,255));

		world_.addThing(areaOne_);
		world_.addThing(areaTwo_);
		world_.addThing(targetOne_);
		world_.addThing(targetTwo_);
		// Makes team 1
		this.makeBoid(randFloat(areaOne_.getXBounds()[0],areaOne_.getXBounds()[1]),
		              randFloat(0,world_.getApplet().height),0,0);
		this.makeBoid(randFloat(areaOne_.getXBounds()[0],areaOne_.getXBounds()[1]),
		              randFloat(0,world_.getApplet().height),0,0);
		this.makeBoid(randFloat(areaOne_.getXBounds()[0],areaOne_.getXBounds()[1]),
		              randFloat(0,world_.getApplet().height),0,1);
		this.makeBoid(randFloat(areaOne_.getXBounds()[0],areaOne_.getXBounds()[1]),
		              randFloat(0,world_.getApplet().height),0,1);
		this.makeBoid(randFloat(areaOne_.getXBounds()[0],areaOne_.getXBounds()[1]),
		              randFloat(0,world_.getApplet().height),0,2);
		this.makeBoid(randFloat(areaOne_.getXBounds()[0],areaOne_.getXBounds()[1]),
		              randFloat(0,world_.getApplet().height),0,2);
		// makes team 2
		this.makeBoid(randFloat(areaTwo_.getXBounds()[0],areaTwo_.getXBounds()[1]),
		              randFloat(0,world_.getApplet().height),1,0);
		this.makeBoid(randFloat(areaTwo_.getXBounds()[0],areaTwo_.getXBounds()[1]),
		              randFloat(0,world_.getApplet().height),1,0);
		this.makeBoid(randFloat(areaTwo_.getXBounds()[0],areaTwo_.getXBounds()[1]),
		              randFloat(0,world_.getApplet().height),1,0);
		this.makeBoid(randFloat(areaTwo_.getXBounds()[0],areaTwo_.getXBounds()[1]),
		              randFloat(0,world_.getApplet().height),1,1);
		this.makeBoid(randFloat(areaTwo_.getXBounds()[0],areaTwo_.getXBounds()[1]),
		              randFloat(0,world_.getApplet().height),1,1);
		this.makeBoid(randFloat(areaTwo_.getXBounds()[0],areaTwo_.getXBounds()[1]),
		              randFloat(0,world_.getApplet().height),1,2);

	}

	/*
	 * (non-Javadoc)
	 * @see BoidsCore#makeBoid()
	 */
	/**
	 * @param x
	 *          determines where the x value on the map the boid will spawn
	 * @param y
	 *          determines where the y value on the map the boid will spawn
	 * @param id
	 *          Gives an id to which team the boid belongs to
	 * @param agression
	 *          on a scale from 1 to 3 will determine how aggressive the boid will
	 *          be 1 being defensive and 3 being aggresive This will make a boid
	 *          with the parameters for our game of invaiders.
	 */
	protected void makeBoid ( float x, float y, int id, int agression ) {
		Brain brain = null;
		// Int id is passed to ensure the boid is given the proper friendly
		// territory and endzone to go after
		if ( id == 0 ) {
			if ( agression == 0 ) {
				brain = new Defensive_brain(areaOne_,targetTwo_,targetOne_);// 0 for
				                                                            // defensive
			} else if ( agression == 1 ) {
				brain = new Neutral_brain(areaOne_,targetOne_,targetTwo_);// 1 for
				                                                          // Neutral
			} else {
				brain = new Offensive_brain(areaOne_,targetOne_); // 2 or greater for
				                                                  // offensive
			}
		} else {
			if ( agression == 0 ) {
				brain = new Defensive_brain(areaTwo_,targetOne_,targetTwo_);
			} else if ( agression == 1 ) {
				brain = new Neutral_brain(areaTwo_,targetTwo_,targetOne_);
			} else {
				brain = new Offensive_brain(areaTwo_,targetTwo_);
			}
		}
		Boid boid = new Boid(world_,new PVector(x,y),1,PVector.random2D(),0.05f,2,
		                     100,radians(360),brain,color(255 * id),id);
		world_.addBoid(boid);

		// Boids are added to there list for tracking
		if ( id == 0 ) {
			teamOneBoids_.add(boid);
		} else {
			teamTwoBoids_.add(boid);
		}

	}

	public void draw () {
		super.draw();

		if ( !teamOneBoids_.isEmpty() && !teamTwoBoids_.isEmpty() ) {// ensures that
		                                                             // an error
		                                                             // won't be
		                                                             // thrown for
		                                                             // uninitialized
		                                                             // arraylists
			outerloop: for ( Boid toCheck : teamOneBoids_ ) {
				for ( Boid other : teamTwoBoids_ ) {
					if ( PVector.dist(other.getPosition(),toCheck.getPosition()) <= 10 ) {// overlapping
					                                                                      // coordinates
						if ( areaOne_.inRegion(toCheck) && areaOne_.inRegion(other) ) {// both
						                                                               // in
						                                                               // teamOne's
						                                                               // region
							kill(other);
							break;
						} else if ( areaTwo_.inRegion(toCheck)
						    && areaTwo_.inRegion(other) ) {// both in teamTwo's region
							kill(toCheck);
							break outerloop;
						}
						// otherwise do nothing
					}

				}
			}
			for ( Boid boids : teamOneBoids_ ) {
				if ( targetOne_.inRegion(boids) ) {
					score(boids);
					System.out.printf("The score is Team 1: %d || Team 2: %d\n",score0_,
					                  score1_);
					if ( score0_ == 5 ) {
						System.out.println("TEAM ONE WINS");
						paused_ = true;
					}
					break;
				}
			}
			for ( Boid boids : teamTwoBoids_ ) {
				if ( targetTwo_.inRegion(boids) ) {
					score(boids);
					System.out.printf("The score is Team 1: %d || Team 2: %d\n",score0_,
					                  score1_);
					if ( score1_ == 5 ) {
						System.out.println("TEAM TWO WINS");
						paused_ = true;
					}
					break;
				}
			}
			if ( teamOneBoids_.isEmpty() && score0_ != 0
			    || teamTwoBoids_.isEmpty() && score1_ != 0 ) {
				if ( score0_ > score1_ ) {
					System.out.println("TEAM ONE WINS");
				}
				if ( score0_ < score1_ ) {
					System.out.println("TEAM TWO WINS");
				}
				paused_ = true;
			}
		}
	}

	/**
	 * Boid is removed from the world list as well as its team list
	 * 
	 * @param victim
	 */
	public void kill ( Boid victim ) {
		if ( teamTwoBoids_.contains(victim) ) {
			teamTwoBoids_.remove(victim);
		} else {
			teamOneBoids_.remove(victim);
		}
		world_.killBoid(victim);

	}

	/**
	 * If a boid reaches its endzone it will be removed and a score will be
	 * incremented
	 * 
	 * @param boid
	 */
	public void score ( Boid boid ) {
		if ( boid.getId() == 1 ) {
			world_.killBoid(boid);
			teamTwoBoids_.remove(boid);
			score1_++;
		} else {
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

	/**
	 * Generates a random float value in a given range
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	protected float randFloat ( float min, float max ) {
		return min + (int) (Math.random() * ((max - min) + 1));
	}

}
