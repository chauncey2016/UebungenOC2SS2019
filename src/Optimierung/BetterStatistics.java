package Optimierung;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class BetterStatistics extends Statistics{

	double[][][] values;
	int currentAlgo;
	
	int numAlgo, tries, testMax;

	String[] names;
	public BetterStatistics(int numAlgo, int tries, int testMax) {
		super(tries, testMax);
		values = new double[numAlgo][testMax][tries];
		this.numAlgo 	= numAlgo;
		this.tries 		= tries;
		this.testMax 	= testMax;
		names = new String[numAlgo];
	}

	public void setAlgoName(int indexAlgo, String name){
		names[indexAlgo] = name;
	}
	public void setCurrentTest(int test){
		currentTest = test;
		iteration = 0;
	}
	
	public void setCurrentAlgo(int algo){
		currentAlgo = algo;
		iteration = 0;
	}
	
	public void push(double value){
		values[currentAlgo][iteration++][currentTest] = value;
	}
	
	public void print() throws Exception{
		double[][] out = new double[numAlgo][testMax];
		
		for(int i=0; i<values.length; i++){//numAlgo
			for(int j=0; j<values[i].length; j++){//testMax
				double val = 0;
				int count = 0;
				for(int k=0; k<values[i][j].length; k++){//tries
					if(values[i][j][k] != 0){
						count++;
						val += values[i][j][k];
					}
				}
				if(count != 0){
					val = val / count; //avg
					out[i][j] = val;
				}
			}
		}
		
		for(int i=0; i<numAlgo; i++){
			System.out.println("Algo "+i + " "+names[i]);
			for(int j=0; j<testMax; j++){
				if(out[i][j] == 0)
					continue;
				System.out.println(out[i][j]);
			}
		}
	
		
		String filename = "statisticsHillClimbing.csv";
		File file = new File(filename);
		PrintWriter out2 = new PrintWriter(
							new OutputStreamWriter(
									new FileOutputStream(file)));
		String head = "";
		for(int i=0; i<numAlgo; i++){
			head += names[i];
			if(i+1 < numAlgo)
				head += "\t";
		}
		out2.println(head);
		String line = "";
		for(int i=0; i<testMax; i++){
			line = "";
			for(int j=0; j<numAlgo; j++){
				if(out[j][i] == 0)
					out[j][i] = out[j][i-1];
				line += out[j][i];
				if(j+1 < numAlgo)
					line += "\t";
			}
			out2.println(line);
		}
		out2.close();
		System.out.println(file.getAbsolutePath()+" saved");
	}
}
