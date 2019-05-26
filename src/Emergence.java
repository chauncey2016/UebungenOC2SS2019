import java.util.Arrays;
import java.util.Vector;

public class Emergence {
	Vector<Double> entropyPosition;
	
	public Emergence(){
		entropyPosition = new Vector<Double>();
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
		if(entropyPosition.size() > 1)
			System.out.println(shannonEntropy - entropyPosition.get(entropyPosition.size()-2));
		GraphComponent.unique.addValue(shannonEntropy);
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
}
