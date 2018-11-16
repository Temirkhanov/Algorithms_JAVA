import java.util.ArrayList;

public class TheCommuteMain {

	public static void main(String[] args) {
		ArrayList<Thread> commuter = new ArrayList<Thread>();
		int numCommuters = 15;
		boolean ezPass;
		
		CityRoutine city= new CityRoutine(numCommuters); 
		
		for(int i = 0; i < numCommuters; i++) {
			ezPass = false;
			if(i % 2 == 0) ezPass = true;	//Every second commuter in town owns EZ-Pass
			commuter.add(new Commuters("Commuter - " + i, ezPass, city));
			commuter.get(i).start();
		}

		
	}

}
