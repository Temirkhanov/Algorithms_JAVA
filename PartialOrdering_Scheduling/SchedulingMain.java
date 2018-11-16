import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SchedulingMain {


	public static void debuggingPrint(Scheduling s, int c) {
		int n = s.numNodes;
		System.out.println("\n *******************************");
		System.out.println("Current Time: " + c);
		System.out.print("Jobs Marked: ");
		for(int i = 1; i <= n; i++)
			if(s.jobMarked[i] == 1)
				System.out.print(i + ", ");
		System.out.print("\nProcessing Time: ");
		for(int i = 1; i < s.procGiven; i++)
			System.out.print("P(" + i + ") = " + s.processTime[i] + ", ");
		System.out.print("\nProcessing Jobs: ");
		for(int i = 1; i < s.procGiven; i++)
			if(s.processJob[i] != 0)
				System.out.print("P(" + i + ")->" + s.processJob[i] + ", ");
		System.out.print("\nDone Jobs: ");
		for(int i = 1; i <= n; i++)
			if(s.jobDone[i] == 1)
				System.out.print(i + ", ");
		System.out.println("\n *******************************");
	}

	public static void main(String[] args) {

		try {
			Scanner inFile1 = new Scanner(new FileReader(args[0]));
			Scanner inFile2 = new Scanner(new FileReader(args[1]));
			BufferedWriter outFile = new BufferedWriter(new FileWriter(args[2]));

			Scheduling schedules = new Scheduling();
			schedules.loadMatrix(inFile1);
			schedules.computeTotalJobTimes(inFile2);

			System.out.print("Enter number of processors: ");
			Scanner sc = new Scanner(System.in);
			int procNum = sc.nextInt();
			if(procNum <= 0)
				System.out.println("Invalid number of processors!");
			if(procNum > schedules.numNodes)
				procNum = schedules.numNodes;
			procNum++;
			sc.close();
			schedules.procGiven = procNum;
			schedules.init();
			int procUsed = 0, currentTime = 0;
			boolean allJobsAreDone = false;
			
			while(!allJobsAreDone) {
				while(true) {
					int orphenNode = schedules.getUnMarkOrphen();
					if(orphenNode == -1) break;
					schedules.jobMarked[orphenNode] = 1;
					Node node = new Node(orphenNode, schedules.jobTimeAry[orphenNode]);
					schedules.insertOpen(node);
				}
				schedules.printList();
				
				while(schedules.open.next != null && procUsed < schedules.procGiven) {
					int availProc = schedules.findProcessor();
					if(availProc > 0) {
						procUsed++;
						Node newJob = schedules.open.next;
						schedules.open.next = schedules.open.next.next;
						newJob.next = null;
						schedules.processJob[availProc] = newJob.jobID;
						schedules.processTime[availProc] = newJob.jobTime;
						schedules.updateTable(availProc, newJob, currentTime);
					}//if
					else if(availProc == -1) break;
				}

				if(schedules.checkCycle() >= 1) {
					System.out.println("Cycle...");
					inFile1.close();
					inFile2.close();
					outFile.close();
					return; 
				}
		
				schedules.printTable(outFile);
				currentTime++;
				
				int count = 0;
				for(int i = 1; i < schedules.procGiven; i++)
					if(schedules.processTime[i] == 1)
						count++;
				procUsed = count;
				
				for(int i = 1; i < schedules.procGiven; i++)
					schedules.processTime[i]--;
				
				int job = schedules.findDoneJob(currentTime);
				while(job != -1) {
					schedules.deleteNode(job);
					schedules.deleteEdge(job);
					job = schedules.findDoneJob(currentTime);
				}
				debuggingPrint(schedules, currentTime);
				
				allJobsAreDone = true;
				for(int i = 1; i <= schedules.numNodes; i++)
					if(schedules.jobDone[i] == 0)
						allJobsAreDone = false;
			//if(currentTime == 5) break;
			}//while

			schedules.printTable(outFile);
			inFile1.close();
			inFile2.close();
			outFile.close();

		} 
		catch (IOException e) {
			e.printStackTrace();
		}//catch





	}//main

}
