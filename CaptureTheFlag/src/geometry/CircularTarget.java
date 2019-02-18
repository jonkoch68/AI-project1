package geometry;

import target.FixedTarget;
import core.Boid;
import core.World;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * @author Jonko
 *This is another region with the target functionality baked into the region itself
 */
public class CircularTarget extends Region{

	private float radius_,x_,y_;
	private int id_,color_;
	private FixedTarget target_;


	public CircularTarget ( float x, float y, int id,World world, float radius, int color ) {
		super(world);
		radius_ = radius;
		x_ = x;
		y_ = y;
		id_ = id;
		target_ = new FixedTarget(x,y);
		color_ = color;
	}

	
	public FixedTarget getTarget() {
		return target_;
	}
	public int getId() {
		return id_;
	}
	public boolean inRegion(Boid boid) {
		PVector p = boid.getPosition();
		PVector t = target_.getPosition();
		if(world_.getApplet().dist(p.x,p.y,t.x,t.y) < radius_) {
				return true;
				}
	else {
		return false;
	}
	}
	
	/*
	 * (non-Javadoc)
	 * @see core.Renderable#render()
	 */
	@Override
	public void render () {
		PApplet applet = world_.getApplet();
		applet.fill(color_);
		applet.stroke(200);
		applet.strokeWeight(5);
		applet.ellipseMode(PApplet.CENTER);
		applet.ellipse(x_,y_,2 * radius_,2 * radius_);
	
		applet.strokeWeight(1);
	}

}
