package Optimierung;
import java.io.DataInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Exception{
		System.out.println("Main Optimierung By author");
		
		DockerAdapter.setModus(5);
		int dimensions = DockerAdapter.getDimensions();
		DockerAdapter adapter = DockerAdapter.instance();
		
		//max Iterations
		int testMax = 1000;
		
		double[] min1 = HillClimbing.randSearch(dimensions, testMax);
		double m1 = adapter.nextVal(min1);
		System.out.println("randSearch: \t\t\tmin1="+m1+" "+Arrays.toString(min1));
		
		double[] min2 = HillClimbing.EazyHillClimbing(dimensions, testMax);
		double m2 = adapter.nextVal(min2);
		System.out.println("EazyHillClimbing: \t\tmin2="+m2);
		
		double[] min3 = HillClimbing.SteepestAscentHillClimbing(dimensions, testMax);
		double m3 = adapter.nextVal(min3);
		System.out.println("SteepestAscentHillClimbing: \tmin3="+m3);
		
		double[] min4 = HillClimbing.randomRestartHillClimbing(dimensions, testMax);
		double m4 = adapter.nextVal(min4);
		System.out.println("RandomRestartHillClimbing: \tmin4="+m4);
		
		double[] min5; new SimulatedAnnealing(dimensions, testMax, (min5 = new double[dimensions]), true);
		double m5 = adapter.nextVal(min5);
		System.out.println("SimulatedAnnealing t1: \t\tmin5="+m5);
		
		double[] min6; new SimulatedAnnealing(dimensions, testMax, (min6 = new double[dimensions]), false);
		double m6 = adapter.nextVal(min5);
		System.out.println("SimulatedAnnealing t2: \t\tmin6="+m6);
	}
	
	
}