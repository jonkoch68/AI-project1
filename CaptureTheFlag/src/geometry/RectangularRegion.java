package geometry;

import java.awt.Color;

import core.World;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * @author Jonko
 *
 */
public class RectangularRegion extends Region{

	/**
	 * @param world
	 */
	private float x_,y_;
	private int r_,g_,b_,id_;

	
	public RectangularRegion ( World world,float x, float y, int r, int g, int b,int id) {
		super(world);
		x_ = x;
		y_ = y;
		r_ = r;
		g_ = g;
		b_ = b;
		id_ = id;

		// TODO Auto-generated constructor stub
	}
	public int getRegionID() {
		return id_;
	}
	
	public void render () {
		PApplet applet = world_.getApplet();
		applet.fill(r_,g_,b_);
		applet.stroke(200);
		applet.strokeWeight(5);
		applet.rect(x_,y_,applet.width/2.0f,applet.height);
		applet.strokeWeight(1);
	}

}
