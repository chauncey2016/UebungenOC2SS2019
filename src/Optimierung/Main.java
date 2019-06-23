package Optimierung;
import java.io.DataInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Exception{
		System.out.println("Main Optimierung");
		DockerAdapter adapter = DockerAdapter.instance();
		//max Iterations
		int testMax = 1000;
		
		double[] min1 = HillClimbing.randSearch(3, testMax);
		double m1 = adapter.nextVal(min1);
		System.out.println("randSearch: \t\t\tmin1="+m1);
		
		double[] min2 = HillClimbing.EazyHillClimbing(3, testMax);
		double m2 = adapter.nextVal(min2);
		System.out.println("EazyHillClimbing: \t\tmin2="+m2);
		
		double[] min3 = HillClimbing.SteepestAscentHillClimbing(3, testMax);
		double m3 = adapter.nextVal(min3);
		System.out.println("SteepestAscentHillClimbing: \tmin3="+m3);
		
		double[] min4 = HillClimbing.randomRestartHillClimbing(3, testMax);
		double m4 = adapter.nextVal(min4);
		System.out.println("RandomRestartHillClimbing: \tmin4="+m4);
		
		double[] min5; new SimulatedAnnealing(3, testMax, (min5 = new double[3]), true);
		double m5 = adapter.nextVal(min5);
		System.out.println("SimulatedAnnealing t1: \t\tmin5="+m5);
		
		double[] min6; new SimulatedAnnealing(3, testMax, (min6 = new double[3]), false);
		double m6 = adapter.nextVal(min5);
		System.out.println("SimulatedAnnealing t2: \t\tmin6="+m6);
	}
	
	
}
