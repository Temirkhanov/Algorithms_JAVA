import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

public class Scheduling {
	
	int numNodes, totalJobTimes, procGiven;
	int[][] adjMatrix, scheduleTable; 
	int[] jobTimeAry, processJob, processTime, 
		parentCount, jobDone, jobMarked;
	Node open = new Node(0, 0);
	
	public Scheduling() {
	}
	
	public void init() {
		scheduleTable = new int[procGiven][totalJobTimes+1];
		processJob = new int[procGiven];
		processTime = new int[procGiven];
		jobDone = new int[numNodes+1];
		jobMarked = new int[numNodes+1];
	}
	
	public void loadMatrix(Scanner inFile) {
		numNodes = inFile.nextInt();
		adjMatrix = new int[numNodes+1][numNodes+1];
		parentCount = new int[numNodes+1];
		int i, j;
		while(inFile.hasNextInt()) {
			i = inFile.nextInt();
			j = inFile.nextInt();
			adjMatrix[i][j] = 1;
			++parentCount[j];
		}
		
	}
	
	public void computeTotalJobTimes(Scanner inFile) {
		jobTimeAry = new int[numNodes+1];
		totalJobTimes = 0;
		int i, t;
		inFile.nextInt();
		while(inFile.hasNextInt()) {
			i = inFile.nextInt();
			t = inFile.nextInt();
			totalJobTimes += t;
			jobTimeAry[i] = t;
		}
	}
	
	public int getUnMarkOrphen() {
		for(int i = 1; i <= numNodes; i++)
			if(jobMarked[i] == 0 && parentCount[i] == 0)
				return i;
		return -1;
	}
	
	public void insertOpen(Node node) {
		Node spot = open;
		while(spot.next != null && spot.next.jobTime < node.jobTime)
			spot = spot.next;
		node.next = spot.next;
		spot.next = node;
	}
	
	public int findProcessor() {
		for(int i = 1; i < procGiven; i++)
			if(processTime[i] <= 0)
				return i;
		return -1;
	}
	
	public void deleteNode(int id) {
		jobDone[id] = 1;
		/*Node iter = open;
		while(iter.next.jobID != node.jobID)
			iter = iter.next;
		iter.next = node.next;
		node.next = null; */
	}
	
	public void printList() {
		Node node = open.next;
		System.out.print("\n");
		System.out.print("OPEN: ");
		while(node != null) {
			System.out.print("(" + node.jobID + ", " + 
							node.jobTime + ")" + " -> ");
			node = node.next;
		}
		System.out.print("\n");
	}
	
	public void updateTable(int availProc, Node newJob, int currentTime) {
		if(newJob.jobTime == 1)
			scheduleTable[availProc][currentTime] = newJob.jobID;
		else {
			int t = newJob.jobTime;
			while(t != 0) {
				scheduleTable[availProc][currentTime++] = newJob.jobID;
				--t;
			}
		}//else
	}

	public int findDoneJob(int cTime) {
		cTime--;
		for(int i = 1; i < procGiven; i++)
			if(processTime[i] == 0) {      
				if(jobDone[scheduleTable[i][cTime]] != 1) {
					jobDone[scheduleTable[i][cTime]] = 1;	   	
					processJob[i] = 0;				
					return scheduleTable[i][cTime];
				}//else
			}//if
		return -1;
	}
	
	public void printTable(BufferedWriter outFile) {
		try {
			outFile.write("---" + "-" + 0);
			for(int i = 1; i <= totalJobTimes; i++) {
				if(i > 9)
					outFile.write("---" + i);
				else 
					outFile.write("----" + i);
			}
			outFile.newLine();
			for(int i = 1; i < scheduleTable.length; i++) {
				outFile.write("P(" + i + ")" + "|");
				for(int j = 0; j < scheduleTable[i].length; j++) {
					if(scheduleTable[i][j] == 0)
						outFile.write(" -  |");
					else {
						if(scheduleTable[i][j]/10 < 1)
							outFile.write(" " + scheduleTable[i][j] + " " + " |");
						else
							outFile.write(" " + scheduleTable[i][j] + " |");
					}
				}
				outFile.newLine();
				outFile.write("--------------------------------------------------------------------------");
				outFile.write("--------------------------------------------------------------------------");
				outFile.newLine();
				
			}
			outFile.newLine();
		
				
		}//try 
		catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}//catch
		
	}
	
	public int checkCycle() {
		boolean allNodesDone = true;
		boolean allProcFree = true;
		for(int i = 1; i <= numNodes; i++)
			if(jobDone[i] == 0)
				allNodesDone = false;
		for(int i = 1; i < procGiven; i++)
			if(processTime[i] > 0)
				allProcFree = false;
		
		if(open.next == null && !allNodesDone && allProcFree)
			return 1;
		return 0;
	}
	
	public void deleteEdge(int id) {
		for(int kid = 1; kid <= numNodes; kid++)
			if(adjMatrix[id][kid] > 0)
				parentCount[kid]--;	
	}

}//class
