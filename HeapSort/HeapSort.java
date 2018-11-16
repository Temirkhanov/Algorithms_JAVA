import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

public class HeapSort {

	public int 	rootIndex, fatherIndex, 
				leftKidIndex, rightKidIndex, 
				minKidIndex, numItems = 0;
	public int[] heapArray;
	public BufferedWriter outFile1, outFile2;
	public Scanner inFile;
	
	
	public HeapSort() {
	}
	
	public int countData() {
		int count = 0;
		while(inFile.hasNextInt()) {
			inFile.nextInt();
			count++;
		}
		inFile.close();
		return count;
	}
	
	public void buildHeap() {
		rootIndex = 1;
		
		while(inFile.hasNextInt()) {
			int data = inFile.nextInt();
			insertOneDataItem(data);
			int kidIndex = heapArray[0];
			bubbleUp(kidIndex);
			bubbleDown(fatherIndex);
			printHeap();
		}
	}
	
	public void replaceRoot() {
		heapArray[rootIndex] = heapArray[heapArray[0]];
		heapArray[0]--;	
	}
	
	public void insertOneDataItem(int data) {
		
		if(heapArray[0] == heapArray.length - 1) {
			System.out.println("Heap is full");
			return;
		}
		heapArray[++heapArray[0]] = data;
	}
	
	public int getRoot() {
		return heapArray[rootIndex];
	}
	
	public void deleteHeap() {
		try {
			while(heapArray[0] != 0) {
				int data = getRoot();
				outFile2.write(data + ", ");
				replaceRoot();
				fatherIndex = rootIndex;
				bubbleDown(fatherIndex);
				printHeap();
				
			}//while
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void bubbleUp(int kidIndex) {
		if(isRoot(kidIndex))
			return;
		else {
			fatherIndex = kidIndex / 2;
			if(heapArray[kidIndex] >= heapArray[fatherIndex])
				return;
			else {
				swap(kidIndex, fatherIndex);
				bubbleUp(fatherIndex);
			}//inner else
		}//else
	}
	
	public void swap(int n1, int n2) {
		int temp = heapArray[n1];
		heapArray[n1] = heapArray[n2];
		heapArray[n2] = temp;
	}

	public void bubbleDown(int fatherIndex) {
		if(isLeaf(fatherIndex))
			return;
		else {
			leftKidIndex = fatherIndex * 2;
			rightKidIndex = fatherIndex * 2 + 1;
			int minIndex = findMinKidIndex(leftKidIndex, rightKidIndex);
			if(heapArray[minIndex] >= heapArray[fatherIndex])
				return;
			else {
				swap(minIndex, fatherIndex);
				bubbleDown(minIndex);
			}
		}
				
	}
	
	public boolean isLeaf(int index) {
		if((index * 2) > heapArray[0])
			return true;
		else return false;
	}
	
	public boolean isRoot(int index) {
		return index == rootIndex;
	}
	
	public int findMinKidIndex(int i, int j) {
		if(i > heapArray[0])
			return j;
		else if(j > heapArray[0])
			return i;
		else if(heapArray[i] > heapArray[j])
			return j;
		else return i;
	}
	
	public boolean isHeapEmpty() {
		return heapArray[0] == 0;
	}
	
	public boolean isHeapFull() {
		return heapArray[0] == heapArray.length+1;
	}

	public void printHeap() {
		try {
			for(int i = 0; i <= heapArray[0]; i++)
				outFile1.write(heapArray[i] + ", ");
			outFile1.newLine();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}

	}
	
}//class
