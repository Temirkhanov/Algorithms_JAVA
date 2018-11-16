import java.util.Random;

public class Trains extends Thread{
	
	final String name;
	public int trainCap, availSeats, random;
	public boolean trainRunning, okToBoard, arrived;
	public static long time = System.currentTimeMillis();
	Random rand;
	CityRoutine city;
	
	public Trains(String nameTrain, CityRoutine city1) {
		super(nameTrain);
		city = city1;
		rand = new Random();
		trainCap = 10;
		availSeats = rand.nextInt(10);
		name = nameTrain;
		trainRunning = true;
	}

	
	@Override
	public void run() {
		while(city.subwayOpen == true) {
			try {
				availSeats = rand.nextInt(8);
				arrived = true;
				city.subway();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	 
	public void msg(String m) { 
		System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+": "+m);
	}
	
}
