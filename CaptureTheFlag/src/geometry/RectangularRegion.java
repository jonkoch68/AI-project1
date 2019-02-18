package geometry;

import java.awt.Color;

import core.Boid;
import core.World;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * @author Jonko
 */
public class RectangularRegion extends Region {

	/**
	 * @param world
	 */
	private float x_, y_;
	private int r_, g_, b_, id_;

	/**
	 * @param world
	 * @param x
	 * @param y
	 * @param r
	 * @param g
	 * @param b
	 * @param id
	 *          This is how the teams will be able to identify there location and
	 *          whether or not they run the rish of being eliminated
	 */
	public RectangularRegion ( World world, float x, float y, int r, int g, int b,
	                           int id ) {
		super(world);
		x_ = x;
		y_ = y;
		r_ = r;
		g_ = g;
		b_ = b;
		id_ = id;

		// TODO Auto-generated constructor stub
	}

	/**
	 * @return int id of the region
	 */
	public int getRegionID () {
		return id_;
	}

	/**
	 * @return Where the region begins and ends by the x axis
	 */
	public float[] getXBounds () {
		float[] bounds = { x_, x_ + world_.getApplet().width / 2.0f };
		return bounds;
	}

	/**
	 * @param me
	 * @return true if boid is currently in this region
	 */
	public boolean inRegion ( Boid me ) {
		if ( this.getXBounds()[0] < me.getPosition().x
		    && getXBounds()[1] > me.getPosition().x ) {
			return true;
		}
		return false;
	}

	/**
	 * @return the center of the region
	 */
	public PVector getCenter () {
		float x = x_ + world_.getApplet().width / 2;
		float y = world_.getApplet().height / 2;
		return new PVector(x,y);
	}

	public void render () {
		PApplet applet = world_.getApplet();
		applet.fill(r_,g_,b_);
		applet.stroke(200);
		applet.strokeWeight(5);
		applet.rect(x_,y_,applet.width / 2.0f,applet.height);
		applet.strokeWeight(1);
	}

}
