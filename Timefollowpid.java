import java.util.Timer;
import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;

public class LineFollow {

	private DifferentialPilot pilot;
	private LightSensor sensor;
	private static final int threshold = 43;
	private static final int maxlight = 60;


	public LineFollow(DifferentialPilot pilot, LightSensor sensor) {
		super();
		this.pilot = pilot;
		this.sensor = sensor;
	}

	private void run() {
		pilot.setTravelSpeed(pilot.getMaxTravelSpeed()/4);
		//pilot.setTravelSpeed(pilot.getMaxTravelSpeed()/3);
		while(true) {
			
			
			
			int lightval = sensor.getLightValue();
			
			if(lightval < threshold) {
				long lStartTime = System.currentTimeMillis();
				while(sensor.getLightValue() < threshold)
				{
					long lEndTime = System.currentTimeMillis();
					long difference = lEndTime - lStartTime;
					pilot.arcForward(((difference/2000)));
				}
				//			pilot.arcForward(15 * (lightval/threshold));

							
			}
			else if(lightval >= threshold) {
				long lStartTime = System.currentTimeMillis();
				while(sensor.getLightValue() >threshold)
				{	
				long lEndTime = System.currentTimeMillis();
				long difference = lEndTime - lStartTime;
				pilot.arcForward(-(5/ (5*(difference/1000) + 0.5) ) );
				}
				
				
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
