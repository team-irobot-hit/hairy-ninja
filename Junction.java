import lejos.nxt.LightSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;

public class Junction {

	private DifferentialPilot pilot;
	private LightSensor left;
	private LightSensor right;
	private int count = 0;
	private static final int threshold = 43;

	public Junction(DifferentialPilot pilot, LightSensor left, LightSensor right) {
		super();
		this.pilot = pilot;
		this.left = left;
		this.right = right;
	}

	public void run() {
		
		pilot.setTravelSpeed(pilot.getMaxTravelSpeed()/2);
			
		
		while (true) {
			follow();
		}
	}

	private void follow() {
	
		int lightl = left.getLightValue();
		int lightr = right.getLightValue();

			while (lightl < threshold) {
				pilot.rotate(-1);		
				lightl = left.getLightValue();
				
				check(); 
				
			}
			
			while (lightr < threshold) {
				pilot.rotate(1);
				lightr = right.getLightValue();
				
				check(); 
			}

		pilot.forward();

	}

	private void check() {
		if ((left.getLightValue() < threshold)
				&& (right.getLightValue() < threshold)) {
			count = count + 1;
			pilot.travel(1.5);
			script();
		}
	}

	private void script() {
	
		
		if (count == 1) {
			pilot.stop();
			Delay.msDelay(200);
			pilot.rotate(90);
			pilot.travel(1);
		} else if (count == 2) {
			Delay.msDelay(200);
		} else if (count == 3) {
			pilot.stop();
			Delay.msDelay(200);
			pilot.rotate(-90);
			pilot.travel(1);
		} else if (count == 4) {
			pilot.stop();
			Delay.msDelay(200);
			pilot.rotate(-90);
			pilot.travel(1);
		} else if (count == 5) {
			Delay.msDelay(200);
		} else if (count == 6) {
			pilot.stop();
			Delay.msDelay(200);
			pilot.rotate(90);
			pilot.travel(1);
		} else if (count == 7) {
			pilot.stop();
			Delay.msDelay(200);
			pilot.rotate(90);
			pilot.travel(1);
		} else if (count == 8) {
			Delay.msDelay(200);
		} else if (count == 9) {
			pilot.stop();
			Delay.msDelay(200);
			pilot.rotate(360);
			while (true) {
				pilot.stop();
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DifferentialPilot pilot = new DifferentialPilot(2.1f, 4.4f, Motor.B,
				Motor.C, false);
		LightSensor left = new LightSensor(SensorPort.S2);
		LightSensor right = new LightSensor(SensorPort.S1);
		Junction j = new Junction(pilot, left, right);
		Button.waitForAnyPress();
		j.run();
		Button.waitForAnyPress();
	}

}
