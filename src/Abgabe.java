import java.util.Arrays;

public class Abgabe {

	public static void main(String[] args) {
		int n = 5;
		int[] matrix = makePaths(n);
		print(matrix, n);
		
		int[] path = new int[n-1];
		int[] minP = new int[n-1];
		
		int minDistance = Integer.MAX_VALUE;
		
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
		
		System.out.println();
		System.out.println(minDistance);
		System.out.println(Arrays.toString(path));
		
		//System.out.println("hallo :"+dst(2, 3, matrix, n));
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
	
	private static int length(int[] path, int n, int[] matrix) {
		boolean[] visited = new boolean[n];
		
		int nr_1 = path[0];
		int counter = 0;
		for(int j=0; j<n-1; j++) {
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
			for(int j=0; j<n-1; j++) {
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
		for(int j=0; j<n-1; j++) {
			if(visited[j] != true)
				nr_2 = j;
		}
		distance += dst(nr_1, nr_2, matrix, n);
		return distance;
	}
	
	private static int dst(int nr_1, int nr_2, int[] matrix, int n) {
		return matrix[ nr_1*n + nr_2 ];
	}

	private static int[] makePaths(int n) {
		int length = (n*(n-1))/2;
		int[] arr = new int[n*n];
		for(int i=0; i<n*n; i++) {
			if(arr[i] != 0 || i%n == i/n)
				continue;
			arr[ i ] = (int)(Math.random()*100) + 1;
			arr[ (i%n)*n + i/n ] = arr[i];
		}
		return arr;
	}
	
	private static void print(int[] arr, int n) {
		int i = 0;
		for(int x=0; x<n; x++) {
			System.out.println();
			for(int y=0; y<n; y++) {
				System.out.print(arr[i++]+"\t");
			}
		}
	}

}
