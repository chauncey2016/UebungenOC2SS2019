import java.util.Arrays;
import java.util.Vector;

public class Emergence {
	Vector<Double> entropyPosition;
	Vector<Double> entropyPheromon;
	Vector<Double> entropyRoute;

	public Emergence(){
		entropyPosition = new Vector<Double>();
		entropyPheromon = new Vector<Double>();
		entropyRoute    = new Vector<Double>();
	}
	
	public void addPheromon(double[] tau){
		double[] tmp = tau.clone();
		double steps = 1000;
		for(int i=0; i<tmp.length; i++)
			tmp[i] = ((int)(tmp[i]*steps))/steps;
		
		double[] p = calcPropabilities(tmp, (int)steps);
		double shannonEntropy = shannonEntropy(p);
		entropyPosition.add(shannonEntropy);
		GraphComponent.pheromon.addValue(shannonEntropy);
	}
	
	public void addRoute(int[][] tours){
		int[][] tmp = tours.clone();
		for(int i=0; i<tmp.length; i++){
			tmp[i] = tours[i].clone();
			normieren(tmp[i]);
		}
		double[] p = calcPropabilities(tmp);
		double shannonEntropy = shannonEntropy(p);
		entropyRoute.add(shannonEntropy);
		GraphComponent.route.addValue(shannonEntropy);
	}

	private void normieren(int[] arr) {
		int index0 = -1;
		for(int i=0; i<arr.length; i++)
			if(arr[i] == 0)
				index0 = i;
		int[] arr2 = new int[arr.length];
		
		for(int i = 0; i < arr.length - index0; i++)
			arr2[i] = arr[i + index0];
		
		for(int i = arr.length - index0; i < arr.length; i++)
			arr2[i] = arr[i- arr.length + index0];

		for(int i=0; i<arr.length; i++)
			arr[i] = arr2[i];
	}

	public void addPositions(int[][] tours, int index){
		int m = tours.length;
		int n = tours[0].length;
		int[] positions = new int[m];
		for(int i=0; i<m; i++)
			positions[i] = tours[i][index];
		
		double[] p = calcPropabilities(positions, n);
		double shannonEntropy = shannonEntropy(p);
		entropyPosition.add(shannonEntropy);
		//if(entropyPosition.size() > 1)
		//	System.out.println(shannonEntropy - entropyPosition.get(entropyPosition.size()-2));
		GraphComponent.position.addValue(shannonEntropy);
	}

	private double shannonEntropy(double[] p) {
		double sum = 0;
		for(int i=0; i<p.length; i++)
			if(p[i] != 0)
				sum += p[i] * Math.log(p[i]);
		return -sum;
	}

	private double[] calcPropabilities(int[] positions, int n) {
		double[] p = new double[n];
		for(int i=0; i<n; i++){
			int count = 0;
			for(int j=0; j < positions.length; j++)
				if(positions[j] == i)
					count++;
			p[i] = (count + 0.0) / n;
		}
		return p;
	}
	private double[] calcPropabilities(double[] positions, int n) {
		double[] p = new double[n];
		for(int i=0; i<n; i++){
			int count = 0;
			for(int j=0; j < positions.length; j++)
				if(positions[j] == i)
					count++;
			p[i] = (count + 0.0) / n;
		}
		return p;
	}
	private double[] calcPropabilities(int[][] tmp) {
		boolean[] visited = new boolean[tmp.length];
		Vector<Double> vec = new Vector<Double>();
		for(int i=0; i<tmp.length; i++){
			if(visited[i])
				continue;
			int count = 1;
			for(int j=i+1; j<tmp.length; j++){
				if(arrEquals(tmp[i], tmp[j])){
					count++;
					visited[j] = true;
				}
			}
			double p = (count+0.0) / tmp.length;
			vec.add(p);
		}
		double[] res = new double[vec.size()];
		for(int i=0; i<res.length; i++){
			res[i] = vec.get(i);
		}
		return res;
	}
	private boolean arrEquals(int[] a, int[] b){
		if(a.length != b.length)
			return false;
		for(int i=0; i<a.length; i++)
			if(a[i] != b[i])
				return false;
		return true;
	}
	private void normalize(double[] tmp) {
		double min = calcMin(tmp);
		double max = calcMax(tmp);
		for(int i=0; i<tmp.length; i++)
			tmp[i] = (tmp[i] - min) / (max - min);
	}
	private double calcMax(double[] vec) {
		double max = Integer.MIN_VALUE;
		for(int i=0; i<vec.length; i++)
			if(vec[i] > max)
				max = vec[i];
		return max;
	}
	private double calcMin(double[] vec) {
		double min = Integer.MAX_VALUE;
		for(int i=0; i<vec.length; i++)
			if( vec[i] < min)
				min = vec[i];
		return min;
	}
}
