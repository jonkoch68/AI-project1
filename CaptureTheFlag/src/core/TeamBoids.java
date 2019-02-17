package core;

import processing.core.PVector;

/**
 * @author Jonko
 *
 */
public class TeamBoids extends Boid{

	/**
	 * @param world
	 * @param position
	 * @param mass
	 * @param velocity
	 * @param maxforce
	 * @param maxspeed
	 * @param neighborRadius
	 * @param neighborAngle
	 * @param brain
	 * @param color
	 */
	
	private int team_;
	
	public TeamBoids ( World world, PVector position, float mass,
	                   PVector velocity, float maxforce, float maxspeed,
	                   float neighborRadius, float neighborAngle, Brain brain,
	                   int color,int team ) {
		super(world,position,mass,velocity,maxforce,maxspeed,neighborRadius,
		      neighborAngle,brain,color);
		team_ = team;
		// TODO Auto-generated constructor stub
	}
	
	public int getTeam() {
		return team_;
	}

}
