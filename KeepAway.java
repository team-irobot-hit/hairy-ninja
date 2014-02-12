package week3;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.nxt.addon.OpticalDistanceSensor;

public class KeepAway {
	
	
	private static final double max = 1000; //the distance where the robot will start to slow down from
	private static final double min = 200; //the ideal distance the robot wants to be at
	private DifferentialPilot pilot;
	private OpticalDistanceSensor u;
	private double speed;

	//standard constructor for the pilot and optical sensor
	public KeepAway(DifferentialPilot pilot, OpticalDistanceSensor u) {
		super();
		this.pilot = pilot;
		this.u = u;
	}

	//the method where all the calculations and actions of the robot take place
	public void start() {
		pilot.forward(); 
		
		while (true) {
			
			double curr = u.getDistance();

			if (curr >= max) {//in this case the current distance is too far away so it just runs at max speed
				speed = pilot.getMaxTravelSpeed();
				pilot.forward();
			} else {//now that the distance is less than the max it will proportionately change based on its value
				
				speed = pilot.getMaxTravelSpeed() * ((curr-min) / (max-min));
				//even though a speed can be negative, we discovered that that doesn't set the robot to reverse so we have to use this if statement to tell the robot when to reverse
				if(speed<0)
				{
					pilot.backward();
				}else
				{
					pilot.forward();
				}
			} 
			
			pilot.setTravelSpeed(speed);
		}
	}

	public static void main(String[] args) {
		OpticalDistanceSensor sensor = new OpticalDistanceSensor(SensorPort.S3);
		DifferentialPilot pilot = new DifferentialPilot(2.1f, 4.4f, Motor.B,
				Motor.C, true);
		KeepAway k = new KeepAway(pilot, sensor);
		Button.waitForAnyPress();
		k.start();
		Button.waitForAnyPress();

	}
}
