import lejos.nxt.LightSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;

   /* 
   This class will allow the robot to follow a line in a grid
   and detect whether a junction is ahead. When it senses a 
   junction, it will execute a move from the script.
   */
public class Junction {

	/* Creating private variables for the differential pilots
	and sensors as well as the counter and the threshold for the
	light sensor. The threshold can't be changed. It represents the
	light level that the light sensor detects when it is pointed at
	a black surface (which is the colour of the lines in the grid)
	*/
	
	private DifferentialPilot pilot;
	private LightSensor left;
	private LightSensor right;
	private int count = 0;
	private static final int threshold = 43;

	/*Constructor*/
	public Junction(DifferentialPilot pilot, LightSensor left, LightSensor right) {
		super();
		this.pilot = pilot;
		this.left = left;
		this.right = right;
	}

	/* This method will set the speed of the robot, and then the robot
	will start moving by doing the follow method*/
	public void run() {
		
		pilot.setTravelSpeed(pilot.getMaxTravelSpeed()/2);
			
		
		while (true) {
			follow();
		}
	}

	/* 
	The robot will continuosly move forward.
	The robot is reading the light levels from the
	left and right light sensors everytime it loops.
	The main idea is that the line its following
	is between the light sensors such that the light
	sensors are reading a value that is higher than the 
	threshold.
	*/
	private void follow() {
	
		int lightl = left.getLightValue();
		int lightr = right.getLightValue();

			while (lightl < threshold) {           //If the left light sensor reads a value that is lower 
				pilot.rotate(-1);	       //than the threshold, then it will rotate left so
				lightl = left.getLightValue(); //that it can place the line back between the sensors.
							       //It will keep looping until it achieves this.
				check();                       //It will also run the check function everytime it loops.
				
			}
			
			while (lightr < threshold) {           //If the right light sensor reads a value that is lower
				pilot.rotate(1);               //than the threshold, then it will rotate right so
				lightr = right.getLightValue();//that it can place the line back in between the sensors.
				                               //It will keep looping until it achieves this.
				check(); 		       //It will also run the check function everytime it loops.
			}

		pilot.forward();  //If it skips the while statements above, then it will simply move forward

	}

	/*This method will check whether or not the robot is at a junction by 
	checking whether or not both light sensors are pointing towards the black line.
	*/
	private void check() {
		if ((left.getLightValue() < threshold)                    //If both light sensors are reading a value that
				&& (right.getLightValue() < threshold)) { //is lower than the threshold then the 
			count = count + 1;                                //count is incremented by 1,
			pilot.travel(1.5);                                //the robot travels forward a bit
			script();                                         //and runs a action from the script.
		}            //The reason why the robot travels forward a bit is because if it does a turn, then the light sensors
		             //are positioned such that they are either side of the new line its following. Also if the action is
	}                    //that it ignores the junction, then it will not accidently read the junction again and increment the
                             //the count once more, thus doing another action straight away.
                             
        
        /*This method is a list of actions that the robot does when
        it detects a junction. The action it does depends on the value
        of the counter which increments by 1 everytime it detects a junction
        */
	private void script() {
	
		
		if (count == 1) {           //turn right   
			pilot.stop();      
			Delay.msDelay(200);
			pilot.rotate(90);
			pilot.travel(1);
		} else if (count == 2) {    //skip junction
			Delay.msDelay(200);
		} else if (count == 3) {    //turn left
			pilot.stop();
			Delay.msDelay(200);
			pilot.rotate(-90);
			pilot.travel(1);
		} else if (count == 4) {    //turn left
			pilot.stop();
			Delay.msDelay(200);
			pilot.rotate(-90);
			pilot.travel(1);
		} else if (count == 5) {    //skip junction
			Delay.msDelay(200);
		} else if (count == 6) {    //turn right
			pilot.stop();
			Delay.msDelay(200);
			pilot.rotate(90);
			pilot.travel(1);
		} else if (count == 7) {    //turn right
			pilot.stop();
			Delay.msDelay(200);
			pilot.rotate(90);
			pilot.travel(1);
		} else if (count == 8) {    //skip junction
			Delay.msDelay(200);
		} else if (count == 9) {    //spin once and then stop
			pilot.stop();       //This action indicates that it had
			Delay.msDelay(200); //reached the end of the script
			pilot.rotate(360);
			while (true) {
				pilot.stop();
			}
		}
	}

	/**
	 * @param args
	 * Creating the robot in the code by creating a new Differential
	 * pilot and left and right Light sensors, then putting all this
	 * into a object of type junction based on the constructor above.
	 * Then running the main program with this robot after pressing a button.
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
