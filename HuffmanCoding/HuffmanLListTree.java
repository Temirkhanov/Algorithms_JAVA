import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

public class HuffmanLListTree {

	static BufferedWriter outFile1, outFile2, outFile3, outFile4;
	static Scanner inFile;	
	public static listBinTreeNode listHead;
	public listBinTreeNode root;
	
	public HuffmanLListTree(Scanner in, BufferedWriter out1, BufferedWriter out2, BufferedWriter out3, BufferedWriter out4) {
		listHead = new listBinTreeNode("dummy", 0);
		inFile = in;
		outFile1 = out1;
		outFile2 = out2;
		outFile3 = out3;
		outFile4 = out4;
	}

	//step 2
	public void constructLinkedList() {
		String c;
		int p;
		while(inFile.hasNext()) {
			c = inFile.next();
			p = inFile.nextInt();
			listBinTreeNode newNode = new listBinTreeNode(c, p);
			insertNewNode(findSpot(p), newNode);
			printList(outFile1);
		}
		inFile.close();
	}

	public static void printList(BufferedWriter outFile) {
		listBinTreeNode node = listHead;
		try {
			outFile.write("listHead --> ");
			while (node.getNext() != null) {
				outFile.write("(" + node.getCh() + ", " + node.getProb() 
				+ ", " + node.getNext().getCh() + ")" + " --> ");
				node = node.getNext();
			}
			outFile.write(
					"(" + node.getCh() + ", " + node.getProb() 
					+ ", " + "NULL" + ")" + " --> " + "NULL");
			outFile.newLine();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static listBinTreeNode findSpot(int p) {
		listBinTreeNode spot = listHead;
		while(spot.getNext() != null && spot.getNext().getProb() < p) {
			spot = spot.getNext();
		}
		return spot;
	}

	public static void insertNewNode(listBinTreeNode spot, listBinTreeNode newNode){
		newNode.setNext(spot.getNext());
		spot.setNext(newNode);
	}

	//step 3
	public void constructBinTree() throws IOException {
		listBinTreeNode spot;
		listHead = listHead.getNext();
		while(listHead.getNext() != null) {
			String ch = listHead.getCh() + listHead.getNext().getCh();
			int pr = listHead.getProb() + listHead.getNext().getProb();
			listBinTreeNode newNode = new listBinTreeNode(ch, pr);
			newNode.setLeft(listHead);
			newNode.setRight(listHead.getNext());
			newNode.printNode(outFile2);
			spot = findSpot(newNode.getProb());
			insertNewNode(spot, newNode);
			if(listHead.getNext().getNext() == null) {
				listHead = listHead.getNext();
				printList(outFile2);
				break;
			}
			listHead = listHead.getNext().getNext();
			printList(outFile2);
		}//while
		root = listHead;
	}//constructBinTree

	//step 4
	public void constructCode(listBinTreeNode node, String c) throws IOException {
		if(node == null)
			System.out.println("this is an empty tree");
		else if(node.isLeaf()) {
			node.code = c;
			outFile3.write(node.getCh() + ", " + node.code);
			outFile3.newLine();
		}
		else {
			constructCode(node.getLeft(), c + "0");
			constructCode(node.getRight(), c + "1");
		}
	}

	//Order prints
	public void preOrder(listBinTreeNode n) throws IOException{
		if(n == null);
		else {
			n.printNode(outFile4);
			preOrder(n.getLeft());
			preOrder(n.getRight());
		}
	}

	public void inOrder(listBinTreeNode n) throws IOException {
		if(n == null);
		else {
			inOrder(n.getLeft());
			n.printNode(outFile4);
			inOrder(n.getRight());
		}
	}

	public void postOrder(listBinTreeNode n) throws IOException {
		if(n == null);
		else {
			postOrder(n.getLeft());
			postOrder(n.getRight());
			n.printNode(outFile4);
		}
	}
}
