package Optimierung;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class Statistics {
	double[][] values;
	int currentTest = 0;
	Statistics(int tries, int testMax){
		values = new double[tries][testMax];
	}
	public void push(int iteration, double value){
		values[currentTest][iteration] = value;
	}
	/**
	 * @deprecated
	 * @param test
	 * @param iteration
	 * @param value
	 */
	public void push(int test, int iteration, double value){
		values[test][iteration] = value;
	}
	public void print() throws Exception{
		System.out.println("Filename:");
		Scanner scan = new Scanner(System.in);
		String in = scan.nextLine();
		String filename = in+".csv";
		scan.close();
		File file = new File(filename);
		PrintWriter out = new PrintWriter(
							new OutputStreamWriter(
									new FileOutputStream(file)));
		String head = "";
		for(int i=0; i<values.length; i++){
			head += "test_"+i;
			if(i+1 < values.length)
				head += "\t";
		}
		out.println(head);
		String line = "";
		for(int i=0; i<values[0].length; i++){
			line = "";
			for(int j=0; j<values.length; j++){
				line += values[j][i];
				if(j+1 < values.length)
					line += "\t";
			}
			out.println(line);
		}
		out.close();
		System.out.println(file.getAbsolutePath()+" saved");
	}
}
