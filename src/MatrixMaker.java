import java.util.Random;

public class MatrixMaker {
	static long seed = 11;
	
	public static int[] makePaths(int n) {
		Random random = new Random(seed);
		int length = (n*(n-1))/2;
		int[] arr = new int[n*n];
		for(int i=0; i<n*n; i++) {
			if(arr[i] != 0 || i%n == i/n)
				continue;
			//arr[ i ] = (int)(Math.random()*100) + 1
			arr[ i ] = random.nextInt() % 100 + 1;
			arr[ (i%n)*n + i/n ] = arr[i];
		}
		return arr;
	}
}
