package week3;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.nxt.addon.OpticalDistanceSensor;

public class KeepAway {

	private static final double max = 1000;
	private static final double min = 200;
	private DifferentialPilot pilot;
	private OpticalDistanceSensor u;
	private double speed;

	public KeepAway(DifferentialPilot pilot, OpticalDistanceSensor u) {
		super();
		this.pilot = pilot;
		this.u = u;
	}

	public void start() {
		pilot.forward();
		while (true) {
			double curr = u.getDistance();
		//	pilot.forward();
 
			if (curr >= max) {
				speed = pilot.getMaxTravelSpeed();
				pilot.forward();
			} else {
				
				speed = pilot.getMaxTravelSpeed() * ((curr-min) / (max-min));
				
				if(speed<0)
				{
					pilot.backward();
				}else
				{
					pilot.forward();
				}
			} 

			
			pilot.setTravelSpeed(speed);
			System.out.println(speed);

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
