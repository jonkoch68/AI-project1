import java.awt.Color;

import java.util.ArrayList;
import arbitrator.OneChoice;
import behavior.Seek;
import brain.SimpleBrain;
import core.Behavior;
import core.Boid;
import core.Brain;
import processing.core.PApplet;
import processing.core.PVector;
import target.MouseTarget;
import geometry.RectangularRegion;
import geometry.CircularTarget;
import jacks_brain.Defensive_brain;
import jacks_brain.Offensive_brain;
/**
 * Boids which seek the mouse position.
 */
public class CaptureBoids extends BoidsCore {
	private ArrayList<Boid> teamOneBoids_; // boids in the world
	private ArrayList<Boid> teamTwoBoids_; // boids in the world
	private RectangularRegion areaOne_;
	private RectangularRegion areaTwo_;
	private CircularTarget targetOne_;
	private CircularTarget targetTwo_;
	private int score0_,score1_;

	public void setup () {
		super.setup();
		score0_ = 0;
		score1_ = 0;
		teamOneBoids_ = new ArrayList<Boid>();
		teamTwoBoids_ = new ArrayList<Boid>();
		
		areaOne_ =
		    new RectangularRegion(world_,0.0f,0.0f,255,0,0,0);
		areaTwo_ =
		    new RectangularRegion(world_,world_.getApplet().width / 2,0.0f,0,0,255,
		                          1);
		targetOne_ = new CircularTarget(900.0f, world_.getApplet().height/2,0,world_,50.0f,color(255,0,255));
		targetTwo_ = new CircularTarget(100.0f, world_.getApplet().height/2,1,world_,50.0f,color(255,0,255));
		
		world_.addThing(areaOne_);
		world_.addThing(areaTwo_);
		world_.addThing(targetOne_);
		world_.addThing(targetTwo_);
		this.makeBoid(10,10,0,0);
		this.makeBoid(10,10,0,0);
		this.makeBoid(10,10,0,0);
		
		this.makeBoid(990,10,1,2);
		this.makeBoid(990,10,1,2);
		this.makeBoid(990,10,1,2);


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
		Brain brain = null;

		if(id == 0) {
			if(agression == 0) {
				brain = new Defensive_brain(areaOne_,targetTwo_);
			}
			else if(agression == 1)
			{
				//insert code for neutral
			}
			else {
				brain = new Offensive_brain(areaOne_,targetOne_); 
			}
		}else {
			if(agression == 0) {
				brain = new Defensive_brain(areaTwo_,targetOne_);
			}
			else if(agression == 1)
			{
				//insert code for neutral
			}
			else {
				brain = new Offensive_brain(areaTwo_,targetTwo_); 
			}
		}
		Boid boid = new Boid(world_,new PVector(x,y),1,PVector.random2D(),
		                     0.05f,2,75,radians(360),brain, color(255*id), id);
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
		for(Boid boids : teamOneBoids_) {
			if(targetOne_.inRegion(boids)){
				score(boids);
				System.out.printf("The score is Team 1: %d || Team 2: %d\n", score0_,score1_);
				break;
			}
		}
		for(Boid boids : teamTwoBoids_) {
			if(targetTwo_.inRegion(boids)){
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
	public void score(Boid boid) {
		if(boid.getId() == 1) {
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
		// behaviors
		Behavior seek = new Seek(new MouseTarget(this),color(255,0,0));

		// an arbitrator to combine behaviors
		OneChoice arbitrator = new OneChoice(seek);

		// a brain for action selection
		SimpleBrain brain = new SimpleBrain(arbitrator);

		// make the boid and add it to the world
		Boid boid = new Boid(world_,new PVector(mouseX,mouseY),1,PVector.random2D(),
		                     0.05f,2,60,radians(125),brain,color(255),1);
		world_.addBoid(boid);
		teamTwoBoids_.add(boid);
	}
	

}
