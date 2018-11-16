import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class HuffCodingMain {

	public static void main(String[] args) throws IOException {

		Scanner inFile = new Scanner(new FileReader(args[0]));
		BufferedWriter out1 = new BufferedWriter(new FileWriter(args[1]));
		BufferedWriter out2 = new BufferedWriter(new FileWriter(args[2]));
		BufferedWriter out3 = new BufferedWriter(new FileWriter(args[3]));
		BufferedWriter out4 = new BufferedWriter(new FileWriter(args[4]));
		HuffmanLListTree hc = new HuffmanLListTree(inFile, out1, out2, out3, out4);
		hc.constructLinkedList();
		hc.constructBinTree();
		hc.constructCode(hc.root, "");
		
		out4.write("Pre-Order:"); 
		out4.newLine();
		hc.preOrder(hc.root); 	
		out4.newLine();
		out4.write("In-Order:"); 	
		out4.newLine();
		hc.inOrder(hc.root); 		
		out4.newLine();
		out4.write("Post-Order:"); 	
		out4.newLine();
		hc.postOrder(hc.root); 	
		out4.newLine();
		
		out1.close();
		out2.close();
		out3.close();
		out4.close();
	}

}
