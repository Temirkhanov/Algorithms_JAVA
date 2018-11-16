import java.util.Random;
import java.util.Vector;
import java.util.concurrent.locks.ReentrantLock;

public class CityRoutine {
	
	public static long time = System.currentTimeMillis();
	public boolean ezBoothBusy, cashBoothBusy, garageOpen, subwayOpen;
	public int numServed, numCommuters, numCust, numValets, availSeats, availSeats2;
	ReentrantLock okEZ, okCash, okToPark, cust, okToBoard, trainAdropOff, trainBdropOff;
	Vector<Thread> ezLane, cashLane;
	Random random;
	Trains trainA, trainB;
	Valet v1, v2;
	
	public CityRoutine(int numCom) {
		numCommuters = numCom;
		init();
	}

	
	public void finish() {
		numCommuters--;
		if(numCommuters <=0) {
			garageOpen = false;
			subwayOpen = false;
			System.out.println("============================== END OF STRUGGLES ============================");
		}
	}
	
	public void init() {
		ezLane = new Vector<Thread>();
		cashLane = new Vector<Thread>();
		okEZ = new ReentrantLock();
		okCash = new ReentrantLock();
		okToPark = new ReentrantLock();
		cust = new ReentrantLock();
		okToBoard = new ReentrantLock();
		trainAdropOff = new ReentrantLock();
		trainBdropOff = new ReentrantLock();
		ezBoothBusy = false;
		cashBoothBusy = false;
		numCust = 0;
		numValets = 0;
		garageOpen = false;
		subwayOpen = false;
		numServed = 0;
		random = new Random();
		v1 = new Valet("Joseph Svitak", this);
		v2 = new Valet("Kent Boklan", this);
		trainA = new Trains("Train A", this);
		trainB = new Trains("Train B", this);

	}
	
	public void openGarage(){
		garageOpen = true;
		v1.start();
		v2.start();
	}
	
	public void callMTA() {
		subwayOpen = true;
		trainA.start();
		trainB.start();
	}
	
	
	public void getTheTrain() throws InterruptedException {
		synchronized(okToBoard) {
			okToBoard.wait();
			if(availSeats-1 > 0 || availSeats2-1 > 0)
				okToBoard.notify();
		}
			if(availSeats > 0 && trainA.arrived) {
				availSeats--;
				synchronized(trainAdropOff) {
					trainAdropOff.wait();
					msg("Got to work on A train.");
				}
			}
			else {
				availSeats2--;
				synchronized(trainBdropOff) {
					trainBdropOff.wait();
					msg("Got to work on B train.");
				}
			}
				
	}

	
	public void subway() throws InterruptedException {
		synchronized(okToBoard) {
			msg("Arrived.");
			if(Thread.currentThread() == trainA)
				availSeats = trainA.availSeats;
			else
				availSeats2 = trainB.availSeats;
			if(subwayOpen == false)
				return;
			okToBoard.notify();
			Thread.sleep(2000);
			if(Thread.currentThread() == trainA) {
				availSeats = 0;
				msg("Departing.");
				Thread.sleep(1000);
				synchronized(trainAdropOff) {
					msg("Dropping people off");
					trainAdropOff.notifyAll();
				}
			}
			else {
				synchronized(trainBdropOff) {
					availSeats2 = 0;
					msg("Departing.");
					Thread.sleep(1000);
					msg("Dropping people off");
					trainBdropOff.notifyAll();
				}
			}
		}//synch okToBoard
	}
	
	
	public void parkTheCar() throws InterruptedException {
		while(numServed != numCommuters-1) {
			synchronized(cust) {
				numValets++;
				if(numCust == 0)
					cust.wait();
				numValets--;
				numServed++;
				System.out.println("["+(System.currentTimeMillis()-time)+"] "+Thread.currentThread().getName()+": "+"Is parking someone's car.");
				Thread.sleep(random.nextInt(1000)+500);
			}
			synchronized(okToPark) {
				okToPark.notify();
			}
		}//while
		
	}
	
	public void useGarage() throws InterruptedException {
		System.out.println("["+(System.currentTimeMillis()-time)+"] "+Thread.currentThread().getName()+": "+"Arrived at the parking garage.");
		numCust++;
		synchronized(cust) {
			if(numValets > 0)
				cust.notify();
		}
		synchronized(okToPark) {
			okToPark.wait();
		}
		numCust--;
		System.out.println("["+(System.currentTimeMillis()-time)+"] "+Thread.currentThread().getName()+": "+"Leaving garage...");
		
	}
	
	
	public void ezPassLane() throws InterruptedException{
		System.out.println("["+(System.currentTimeMillis()-time)+"] "+Thread.currentThread().getName()+": "+"Using EZ-Pass lane.");
		synchronized(okEZ) {
			ezLane.add(Thread.currentThread());
			if(ezBoothBusy) {
				okEZ.wait();
			}
			ezBoothBusy = true;
			Thread.sleep(1000);
			ezBoothBusy = false;
			okEZ.notify();
		}
	}
	
	public synchronized void cashLane() throws InterruptedException {
		System.out.println("["+(System.currentTimeMillis()-time)+"] "+Thread.currentThread().getName()+": "+"Using cash lane.");
		synchronized(okCash) {
			cashLane.add(Thread.currentThread());
			if(cashBoothBusy) {
				okCash.wait();
			}
			cashBoothBusy = true;
			Thread.sleep(1500);
			cashBoothBusy = false;
			okCash.notify();
		}
	}
	
	public void msg(String m) { 
		System.out.println("["+(System.currentTimeMillis()-time)+"] "+Thread.currentThread().getName()+": "+m);
	}

}
