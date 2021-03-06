package Optimierung;
import java.io.DataInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class DockerAdapter {
	private static int modus = 1;
	Scanner scan;
	Scanner in_scan;
	PrintWriter out;
	private static DockerAdapter unique = null;
	public static DockerAdapter instance() throws Exception{
		if(unique == null)
			unique = new DockerAdapter();
		return unique;
	}
	public static int getDimensions(){
		if(modus == 1) return 3;
		if(modus == 2) return 5;
		if(modus == 3) return 2;
		if(modus == 4) return 10;
		if(modus == 5) return 2;
		return -1;
	}
	public static void setModus(int i){
		modus = i;
	}
	private DockerAdapter() throws Exception{
		//Start Process
		Process p = Runtime.getRuntime().exec("docker run -i bb -b "+modus);
		
		//Init Streams
		scan = new Scanner(p.getErrorStream());
		in_scan = new Scanner(p.getInputStream());
		out = new PrintWriter((p.getOutputStream()));
		
		//Magic
		startCom();

		
		System.out.println("init done");
	}
	double nextVal(String str_in){
		out.println(str_in);
		out.flush();
		String str_out = in_scan.nextLine();
		return Double.parseDouble(str_out);
	}
	double nextVal(double x, double y, double z){
		String str_in = x+" "+y+" "+z;
		out.println(str_in);
		out.flush();
		String str_out = in_scan.nextLine();
		//System.out.println(str_in +"=>"+str_out);
		return Double.parseDouble(str_out);
	}
	public double nextVal(double[] values){
		String str_in = "";
		for(int i=0; i<values.length; i++){
			str_in += values[i];
			if(i+1<values.length)
				str_in += " ";
		}
		out.println(str_in);
		out.flush();
		String str_out = in_scan.nextLine();
		//System.out.println(str_in +"=>"+str_out);
		return Double.parseDouble(str_out);
	}
	void startCom() throws Exception {
		long dt = 100L;
		int n2 = 5;
		for(int i=0; i<n2; i++){
			Thread.currentThread().sleep(dt);
			String val = "1 1 1";
			out.println(val);
			out.flush();
		}
		for(int i=0; i<n2; i++){
			String val = in_scan.nextLine();
		}
	}
}
