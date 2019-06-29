package Optimierung;

import Optimierung.GeneticAlgorithm.BlackBox;
import Optimierung.GeneticAlgorithm.GeneticAlgorithm;
import Optimierung.GeneticAlgorithm.Logger;

import java.io.DataInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
public class Main {

	public static void main(String[] args) throws Exception{
		DockerAdapter.setModus(4);
		int dimensions = DockerAdapter.getDimensions();
		DockerAdapter adapter = DockerAdapter.instance();

		for(int i = 0; i < 10; i++){
			BlackBox bb = new BlackBox(-10, 10, 0.1, dimensions);
			GeneticAlgorithm ga = new GeneticAlgorithm(100, i, bb);
			String[] data = ga.train(adapter, 500, 10, 0.05, "simple");
			Logger.write("output_ga/bb4_" + i + ".csv", data);
		}

		System.out.println("bye");
	}

	public static void main6(String[] args) throws Exception{
		DockerAdapter.setModus(5);
		int dimensions = DockerAdapter.getDimensions();
		DockerAdapter adapter = DockerAdapter.instance();
		double[] values; 
		int testMax = 500;
		Statistics st = new Statistics(10, testMax);
		SimulatedAnnealing.st = st;
//		SimulatedAnnealing.alpha = 0.993;
		SimulatedAnnealing.alpha = 0.987;
//		SimulatedAnnealing.eta = 1;
//		SimulatedAnnealing.eta = 0.5;
		for(int i=0; i<10; i++){
			st.currentTest = i;
			values = new double[dimensions];
			new SimulatedAnnealing(dimensions, testMax, values, false);
			double value = adapter.nextVal(values);
			System.out.println(i+": "+value+" "+Arrays.toString(values));
		}
		//st.print();
		//BB5_SimulatedAnnealing_t1_eta_1
		//BB5_SimulatedAnnealing_t1_eta_0.5
		//BB5_SimulatedAnnealing_t2_alpha_0.993
		//BB5_SimulatedAnnealing_t2_alpha_0.987
		System.out.println("bye");
	}
	public static void main5(String[] args) throws Exception{
		DockerAdapter.setModus(5);
		int dimensions = DockerAdapter.getDimensions();
		DockerAdapter adapter = DockerAdapter.instance();
		
		int testMax = 500;
		
		Statistics st = new Statistics(10, testMax);
		HillClimbing.st = st;
		
		System.out.println("Startet");
		
//		double stepsSize = 1;
		double stepsSize = 0.5;
		
		for(int i=0; i<10; i++){
			st.setCurrentTest(i);
			double[] min2 = HillClimbing.randomRestartHillClimbing(dimensions, testMax, stepsSize);
			double m2 = adapter.nextVal(min2);
			System.out.println(i+": "+m2+" "+Arrays.toString(min2));
		}
		
		//st.print();
		//BB5_RandomRestartHillClimbing_s_0.5
		System.out.println("bye");
	}
	public static void main2(String[] args) throws Exception{
		System.out.println("Main Optimierung By author");
		
		double stepsSize = 1;
		
		DockerAdapter.setModus(5);
		int dimensions = DockerAdapter.getDimensions();
		DockerAdapter adapter = DockerAdapter.instance();
		
		//max Iterations
		int testMax = 500;
		
		double[] min1 = HillClimbing.randSearch(dimensions, testMax);
		double m1 = adapter.nextVal(min1);
		System.out.println("randSearch: \t\t\tmin1="+m1+" "+Arrays.toString(min1));
		
		double[] min2 = HillClimbing.EazyHillClimbing(dimensions, testMax, stepsSize);
		double m2 = adapter.nextVal(min2);
		System.out.println("EazyHillClimbing: \t\tmin2="+m2+Arrays.toString(min2));
		
		double[] min3 = HillClimbing.SteepestAscentHillClimbing(dimensions, testMax, stepsSize);
		double m3 = adapter.nextVal(min3);
		System.out.println("SteepestAscentHillClimbing: \tmin3="+m3+Arrays.toString(min3));
		
		double[] min4 = HillClimbing.randomRestartHillClimbing(dimensions, testMax, stepsSize);
		double m4 = adapter.nextVal(min4);
		System.out.println("RandomRestartHillClimbing: \tmin4="+m4+Arrays.toString(min4));
		
		double[] min5; new SimulatedAnnealing(dimensions, testMax, (min5 = new double[dimensions]), true);
		double m5 = adapter.nextVal(min5);
		System.out.println("SimulatedAnnealing t1: \t\tmin5="+m5+Arrays.toString(min5));
		
		double[] min6; new SimulatedAnnealing(dimensions, testMax, (min6 = new double[dimensions]), false);
		double m6 = adapter.nextVal(min5);
		System.out.println("SimulatedAnnealing t2: \t\tmin6="+m6+Arrays.toString(min6));
	}
	
	
}
