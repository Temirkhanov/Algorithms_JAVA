import java.util.Random;

public class Commuters extends Thread{
	
	public static long time = System.currentTimeMillis();
	public boolean ownEZPass;
	public String payMethod;
	Random random = new Random();
	CityRoutine nyc;
	
	public Commuters(String name, boolean ez, CityRoutine city){
		super(name);
		nyc = city;
		ownEZPass = ez;
		if(ownEZPass)
			payMethod = "EZ-Pass.";
		else payMethod = "cash.";
	}
	
	
	@Override
	public void run() {
		try {
			startTraveling();
			if(ownEZPass)
				nyc.ezPassLane();
			else
				nyc.cashLane();
			msg("Paid the toll. Driving to garage.");
			sleep(random.nextInt(4000)+2000);
			if(!nyc.garageOpen) nyc.openGarage();
			nyc.useGarage();
			if(!nyc.subwayOpen) nyc.callMTA();
			sleep(100);
			nyc.getTheTrain();
			nyc.finish();
		
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void startTraveling() throws InterruptedException {
		msg("On the way to work.");
		sleep(random.nextInt(3000)+1000);
	}
	
	public void msg(String m) { 
		System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+": "+m);
	}
	
}
