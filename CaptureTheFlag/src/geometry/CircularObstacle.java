package geometry;

import core.World;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * @author ssb
 */
public class CircularObstacle extends Obstacle {

	private PVector position_; // center of obstacle
	private float radius_; // obstacle's radius

	/**
	 * Create a circular obstacle with the specified position and radius.
	 * 
	 * @param position
	 *          position of the center of the obstacle
	 * @param radius
	 *          obstacle's radius
	 */

	public CircularObstacle ( World world, PVector position, float radius ) {
		super(world);
		position_ = position;
		radius_ = radius;
	}

	/*
	 * (non-Javadoc)
	 * @see geometry.Obstacle#getNearestPoint(processing.core.PVector)
	 */
	@Override
	public PVector getNearestPoint ( PVector p ) {
		if ( PVector.dist(position_,p) < radius_ ) {
			return null;
		}

		PVector v = PVector.sub(p,position_);
		v.setMag(radius_);
		return PVector.add(position_,v);
	}

	/*
	 * (non-Javadoc)
	 * @see geometry.Obstacle#getNormal(processing.core.PVector)
	 */
	@Override
	public PVector getNormal ( PVector p ) {
		PVector v = PVector.sub(p,position_);
		v.normalize();
		return v;
	}

	/*
	 * (non-Javadoc)
	 * @see geometry.Obstacle#render()
	 */
	@Override
	public void render () {
		PApplet applet = world_.getApplet();
		applet.fill(50);
		applet.stroke(50);
		applet.ellipseMode(PApplet.CENTER);
		applet.ellipse(position_.x,position_.y,2 * radius_,2 * radius_);
	}

	/*
	 * (non-Javadoc)
	 * @see geometry.Obstacle#getCenter()
	 */
	@Override
	public PVector getCenter () {
		return position_;
	}

	/*
	 * (non-Javadoc)
	 * @see geometry.Obstacle#getRadius()
	 */
	@Override
	public float getRadius () {
		return radius_;
	}

}
