package Optimierung;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class Statistics {
	public static void main(String[] args) throws Exception{
		
	}
	double[][] values;
	Statistics(int tries, int testMax){
		values = new double[tries][testMax];
	}
	public void push(int test, int iteration, double value){
		values[test][iteration] = value;
	}
	public void print() throws Exception{
		System.out.println("Filename:");
		Scanner scan = new Scanner(System.in);
		String in = scan.nextLine();
		String filename = in+".csv";
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
		String line = "";
		for(int i=0; i<values[0].length; i++){
			line = "";
			for(int j=0; j<values.length; i++){
				line += values[j][i];
				if(j+1 < values.length)
					line += "\t";
			}
		}
		out.close();
		System.out.println(file.getAbsolutePath()+" saved");
	}
}
