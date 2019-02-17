import core.Boid;
import core.Renderable;
import core.World;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * Support code for boids main program.
 */
public abstract class BoidsCore extends PApplet {

	protected World world_; // the world containing the boids

	protected boolean paused_, step_; // status of running - paused or stepping

	public void settings () {
		size(1000,800);
	}

	public void setup () {
		world_ = new World(this);
		paused_ = false;
		step_ = false;
	}

	public void draw () {
		if ( paused_ && !step_ ) {
			return;
		}

		step_ = false;

		// clear background
		background(255);

		// draw all the non-boid things in the world
		for ( Renderable thing : world_.getThings() ) {
			thing.render();
		}

		// draw all the boids in their current positions
		for ( Boid boid : world_.getBoids() ) {
			boid.render();
		}

		// calculate steering forces and move all boids
		for ( Boid boid : world_.getBoids() ) {
			boid.update();

			// wrap boid at window edges - note that this changes the boid's position
			// because PVector is mutable
			PVector pos = boid.getPosition();
			if ( pos.x < 0 ) {
				pos.x += width;
			}
			if ( pos.y < 0 ) {
				pos.y += height;
			}
			if ( pos.x > width ) {
				pos.x -= width;
			}
			if ( pos.y > height ) {
				pos.y -= height;
			}
		}
	}

	public void mousePressed () {
		makeBoid();
	}

	public void keyPressed () {
		// pressing 'B' toggles boid debugging, 'b' toggles behavior debugging, 'a'
		// toggles arbitrator debugging
		if ( key == 'B' ) {
			world_.setDebug(World.DEBUG_BOID,!world_.getDebug(World.DEBUG_BOID));
		}
		if ( key == 'b' ) {
			world_.setDebug(World.DEBUG_BEHAVIOR,
			                !world_.getDebug(World.DEBUG_BEHAVIOR));
		}
		if ( key == 'a' ) {
			world_.setDebug(World.DEBUG_ARBITRATOR,
			                !world_.getDebug(World.DEBUG_ARBITRATOR));
		}

		if ( key == 'p' ) {
			paused_ = !paused_;
		}
		if ( key == 's' ) {
			step_ = true;
		}
	}

	protected abstract void makeBoid ();
}
