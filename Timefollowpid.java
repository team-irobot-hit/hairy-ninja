import java.util.Timer;
import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;

public class LineFollow {

	private DifferentialPilot pilot;
	private LightSensor sensor;
	private static final int threshold = 43;//this value seperates the values of the light sensor when on the tape when it is off the tape


	public LineFollow(DifferentialPilot pilot, LightSensor sensor) {
		super();
		this.pilot = pilot;
		this.sensor = sensor;
	}

	private void run() {
		pilot.setTravelSpeed(pilot.getMaxTravelSpeed()/4);
		while(true) {
			int lightval = sensor.getLightValue();
			
			if(lightval < threshold) {
				//this starts measuring time the moment the lightsensor is on the black line
				long lStartTime = System.currentTimeMillis();
				
				
				while(sensor.getLightValue() < threshold)
				{
					long lEndTime = System.currentTimeMillis();
					long difference = lEndTime - lStartTime;
					
					//so as the time spent on the black line increases the arc value increases meaning initially it will almost rotate to then moving quite straight a few seconds later
					pilot.arcForward(((difference/2000)));
				}
				//this allows the robot to follow the black line even when it runs perpendicular to it because it will rotate very greatly initially


			}
			else if(lightval >= threshold) {
				//starts measuring time the moment the lightsensor is off the black line
				long lStartTime = System.currentTimeMillis();
				
				
				while(sensor.getLightValue() >threshold)
				{	
					long lEndTime = System.currentTimeMillis();
					long difference = lEndTime - lStartTime;
				
					//so as the time off the track increases the value off the arc will decreases until after a second or two it is almost rotating
					pilot.arcForward(-(5/ (5*(difference/1000) + 0.5) ) );
				}
				// this allows the robot to make really sharp turns (like the V shape one in the lab) because the robot realises that it is off the track and quickly rotates itself to meet the track again
				
			}                                                                                                                                                                                                                                                                                                                                                          
		
		}
		
		

	}

	public static void main(String[] args) {
		DifferentialPilot pilot = new DifferentialPilot(2.1f, 4.4f, Motor.B,
				Motor.C, false);

		Timefollowpid f = new Timefollowpid(pilot, new LightSensor(
				SensorPort.S2));

		f.run();

		Button.waitForAnyPress();

	}

}
