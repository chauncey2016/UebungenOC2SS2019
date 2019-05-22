import java.util.Arrays;
import java.util.Random;

public class Abgabe {
	
	public static void main(String[] args) {
		int n = 10;
		int[] matrix = MatrixMaker.makePaths(n);
		print(matrix, n);
		
		int[] path = new int[n-1];
		int[] minP = new int[n-1];
		
		int minDistance = Integer.MAX_VALUE;
		
		long t0 = System.currentTimeMillis();
		while( hasNext(path) ) {
			int d = length(path, n, matrix);
			//System.out.println(Arrays.toString(path));
			//System.out.println(d);
			if(d < minDistance && d != 0){
				minDistance = d;
				for(int i=0; i<path.length; i++)
					minP[i] = path[i];
			}
			path = next(path);
		}
		long t1 = System.currentTimeMillis();
		System.out.println();
		System.out.println(minDistance);
		System.out.println(Arrays.toString(minP));
		System.out.println(Arrays.toString(decode(minP, n)));
		System.out.println((t1-t0)+"ms for calculation with "+n+" Elements");
		
		//System.out.println("hallo :"+dst(2, 3, matrix, n));
	}
	
	public static int[] solve(int n, int[] matrix) {
		long t0 = System.currentTimeMillis();
		int[] path = new int[n-1];
		int[] minP = new int[n-1];
		
		int[] path2;
		
		int minDistance = Integer.MAX_VALUE;
		
		while( hasNext(path) ) {
			path2 = decode(path, n);
			int d = length2(path2, n, matrix);
			//int d = length(path, n, matrix); //decode and calc in one function bad
			if(d < minDistance && d != 0){
				minDistance = d;
				for(int i=0; i<path.length; i++)
					minP[i] = path[i];
			}
			path = next(path);
		}
		
		long t1 = System.currentTimeMillis();
		
		System.out.println((t1-t0)+"ms for calculation with "+n+" Elements");
		
		System.out.println("Length: " + length(minP, n, matrix));
		
		return decode(minP, n);
	}

	private static int[] next(int[] path) {
		for(int i=path.length-1; i>=0; i--) {
			if(path[i] < path.length - i) {
				path[i]++;
				break;
			}else {
				path[i] = 0;
			}
		}
		return path;
	}
	private static boolean hasNext(int[] path) {
		for(int i=path.length-1; i>=0; i--)
			if(path[i] < path.length - i)
				break;
			else 
				if(i == 0)
					return false;
		return true;
	} 
	
	private static int[] decode(int[] path, int n) {
		boolean[] visited = new boolean[n];
		int counter = 0;
		int[] path2 = new int[n];
		for(int i=0; i<n-1; i++) {
			int nr_2 = path[i];
			counter = 0;
			for(int j=0; j<n; j++) {
				if(visited[j] == true)
					continue;
				if(counter == nr_2) {
					path2[i] = j;
					visited[j] = true;
					break;
				}
				counter++;
			}
		}
		for(int j=0; j<n; j++)
			if(visited[j] != true)
				path2[n-1] = j;
		return path2;
	}
	
	public static int length2(int[] path, int n, int[] matrix) {
		int d = 0;
		for(int i=1; i<n; i++) {
			int a = path[i-1];
			int b = path[i];
			d += matrix[a*n + b];
		}
		return d;
	}
	
	private static int length(int[] path, int n, int[] matrix) {
		boolean[] visited = new boolean[n];
		
		int nr_1 = path[0];
		int counter = 0;
		for(int j=0; j<n; j++) {
			if(counter == nr_1) {
				nr_1 = j;
				visited[j] = true;
				break;
			}
			counter++;
		}
		
		int distance = 0;
		//System.out.println("--------------------------------------");
		int nr_2 = -1;
		for(int i=1; i<path.length; i++) {
			nr_2 = path[i];
			counter = 0;
			for(int j=0; j<n; j++) {
				if(visited[j] == true)
					continue;
				if(counter == nr_2) {
					nr_2 = j;
					visited[j] = true;
					break;
				}
				counter++;
			}
			
			distance += dst(nr_1, nr_2, matrix, n);
			//System.out.println("distanz von " + nr_1 + " nach " + nr_2 + " insgesamt " + distance);
			nr_1 = nr_2;
		}
		for(int j=0; j<n; j++) {
			if(visited[j] != true)
				nr_2 = j;
		}
		distance += dst(nr_1, nr_2, matrix, n);
		return distance;
	}
	
	private static int dst(int nr_1, int nr_2, int[] matrix, int n) {
		return matrix[ nr_1*n + nr_2 ];
	}
	
	public static void print(int[] arr, int n) {
		int i = 0;
		System.out.println();
		System.out.print("\t");
		for(int j=0; j<n; j++) {
			System.out.print(j+".\t");
		}
		for(int x=0; x<n; x++) {
			System.out.println();
			System.out.print(x+".\t");
			for(int y=0; y<n; y++) {
				System.out.print(arr[i++]+"\t");
			}
		}
		System.out.println();
	}

}
