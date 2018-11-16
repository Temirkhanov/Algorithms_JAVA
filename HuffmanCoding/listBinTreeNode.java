import java.io.BufferedWriter;
import java.io.IOException;

public class listBinTreeNode {
	public String code = " ";
	private String chStr = " ";
	private int prob = 0;
	private listBinTreeNode next = null,
							left = null,
							right = null; 
	
	public listBinTreeNode() {
	}
	
	public listBinTreeNode(String ch, int pr) {
		chStr = ch;
		prob = pr;
	}
	
	public boolean hasNext() {
		if(next != null)
			return true;
		return false;
	}
	
	public boolean isLeaf() {
		if(left == null && right == null) {
			return true;
		}
		return false;
	}
	
	public String getCh() {
		return chStr;
	}
	
	public int getProb() {
		return prob;
	}
	
	public listBinTreeNode getNext() {
		return next;
	}
	
	public listBinTreeNode getLeft() {
		return left;
	}
	
	public listBinTreeNode getRight() {
		return right;
	}
	
	public void setNext(listBinTreeNode n) {
		next = n;
	}
	
	public void setLeft(listBinTreeNode l) {
		left = l;
	}
	
	public void setRight(listBinTreeNode r) {
		right = r;
	}
	
	public void setCh(String st) {
		chStr = st;
	}
	
	public void setProb(int p) {
		prob = p;
	}
	
	public void printNode(BufferedWriter outFile) throws IOException {
		outFile.write("(" + chStr + ", " + prob + ", " + code + ", ");
		if(next == null)
			outFile.write("null, ");
		else
			outFile.write(next.getCh() + ", ");
		if(left == null)
			outFile.write("null, ");
		else
			outFile.write(left.getCh() + ", "); 
		if(right == null)
			outFile.write("null)   ");
		else
			outFile.write(right.getCh() + ")   ");
		outFile.newLine();
	}
		
}//class
