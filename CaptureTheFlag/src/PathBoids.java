import geometry.Path;
import geometry.PolylinePath;
import processing.core.PApplet;

/**
 * Path following.
 */
public class PathBoids extends BoidsCore {

	private boolean definepath_; // in define-a-path mode
	private PolylinePath curpath_; // current path being drawn

	protected Path path_; // the path to use

	public void setup () {
		super.setup();
		definepath_ = false;
		curpath_ = null;
	}

	/*
	 * (non-Javadoc)
	 * @see Boids#makeBoid()
	 */
	@Override
	public void makeBoid () {
	}

	public static void main ( String[] args ) {
		PApplet.main("PathBoids");
	}

	public void draw () {
		super.draw();
		if ( definepath_ ) {
			stroke(255,0,0);
			noFill();
			strokeWeight(5);
			rect(0,0,width,height);
			strokeWeight(1);
		}
	}

	public void mousePressed () {
		if ( definepath_ ) {
			if ( mouseButton == LEFT ) {
				if ( curpath_ == null ) {
					curpath_ = new PolylinePath(world_,20);
					world_.addThing(curpath_);
				}
				curpath_.addVertex(mouseX,mouseY);
			} else {
				definepath_ = false;
				path_ = curpath_;
				curpath_ = null;
			}
		} else {
			super.mousePressed();
		}
	}

	public void keyPressed () {
		if ( key == 'P' ) {
			if ( definepath_ ) {
				definepath_ = false;
				world_.addThing(curpath_);
				path_ = curpath_;
				curpath_ = null;
			} else {
				definepath_ = true;
				curpath_ = null;
			}
		} else {
			super.keyPressed();
		}

	}

}
