
public class Valet extends Thread{

	CityRoutine city;

	public Valet(String name, CityRoutine city1) {
		super(name);

		city = city1;
	}
	
	public void run(){
		try {
		
	
				city.parkTheCar();

		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}



	
