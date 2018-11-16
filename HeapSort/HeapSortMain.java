import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class HeapSortMain {

	public static void main(String[] args) {
		HeapSort heap = new HeapSort();
		
		try {
			heap.inFile = new Scanner(new FileReader(args[0]));
			heap.outFile1 =  new BufferedWriter(new FileWriter(args[1]));
			heap.outFile2 = new BufferedWriter(new FileWriter(args[2]));
			
			heap.numItems = heap.countData();
			heap.heapArray = new int[heap.numItems + 1];
			
			heap.inFile = new Scanner(new FileReader(args[0]));
			heap.buildHeap();
			heap.deleteHeap();
			
			heap.inFile.close();
			heap.outFile1.close();
			heap.outFile2.close();
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}//main

}
