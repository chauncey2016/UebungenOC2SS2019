import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class ACS {
	
	int n;
	int[] delta;
	
	int m;
	double[] tau;
	double tau0;
	
	boolean[][] visited;
	int[][] tours;
	int[] L;
	
	int[] globalBestTour;
	int globalBestTourLength;
	
	double alpha;
	double beta;
	double q0;
	double rho;
	
	Random rand;
	
	Emergence emergence;
	
	boolean debug = false;
	
	public ACS(int[] matrix, int n, Random rand){
		emergence = new Emergence();
		
		delta = matrix;
		this.n = n;
		
		/*
		 * Geratene Werte
		beta 	= 0.5;
		alpha 	= 0.1;
		rho		= 0.1;
		q0 		= 0.5;
		tau0	= 0.3;
		*/
		//Paper params
		beta	= 2;
		q0		= 0.9;
		alpha	= rho	= 0.1;
		tau0	= 1.0 / (n * Lnn());
		m = 10;
		
		visited = new boolean[m][n];
		tours = new int[m][n];
		//globalBestTour = new int[n];
		//globalBestTourLength = Integer.MAX_VALUE;
		
		tau = new double[n*n];
		L = new int[m];
		this.rand = rand;
		
		initTau();
	}
	public int[] solve(){
		for(int i=0; i<1000; i++){
			resetAnts();
			emergence.addPositions(tours, 0);
			for(int j=1; j<n; j++){
				for(int k=0; k<m; k++){
					int r = tours[k][j-1];
					int s = stateTransitionRule(k, r);
					tours[k][j] = s;
					visited[k][s] = true;
					L[k] += delta(r, s);
				}
				emergence.addPositions(tours, j);
				emergence.addPheromon(tau);
			}
			for(int k=0; k<m; k++){
				if(debug && L[k] == 0)
					System.out.println("H "+k);
				if( L[k] < globalBestTourLength){
					globalBestTourLength = L[k];
					globalBestTour = tours[k].clone();
				}
			}
			//all ants have built a complete solution
			checktau();
			globalUpdateRule4();
		}
		return globalBestTour;
	}
	void checktau(){
		double max = Integer.MIN_VALUE;
		double min = Integer.MAX_VALUE;
		for(int i=0; i<tau.length; i++){
			if(tau[i] < min)
				min = tau[i];
			if(tau[i] > max)
				max = tau[i];
		}
		if(debug)
			System.out.println(min+", "+max);
	}
	void resetAnts(){
		for(int a=0; a<m; a++){
			for(int i=0; i<visited[a].length; i++)
				visited[a][i] = false;
			for(int i=0; i<tours[a].length; i++)
				tours[a][i] = -1;
			L[a] = 0;
			int startNode = rand.nextInt(n);
			tours[a][0] = startNode;
			visited[a][startNode] = true;
		}
	}
	/**
	 * Closest City heuristic
	 * @return
	 */
	double Lnn(){
		int length = 0;
		boolean[] visited = new boolean[n];
		int[] path = new int[n];
		path[0] 	= 0;
		visited[0] 	= true;
		for(int i=1; i<n; i++){
			int r = path[i-1];
			int min = Integer.MAX_VALUE;
			int s = -1;
			for(int j=0; j<n; j++){
				if(visited[j])continue;
				int val = delta(r, j);
				if(val < min){
					min = val;
					s = j;
				}
			}
			visited[s] = true;
			path[i] = s;
			length += min;
		}
		globalBestTour 			= path;
		globalBestTourLength 	= length;
		if(debug)
			System.out.println("G "+length+" "+Arrays.toString(path));
		return length;
	}
	void initTau(){
		//javax.swing.JOptionPane.showMessageDialog(null, tau0+"");
		if(debug)
			System.out.println(tau0);
		for(int i=0; i< tau.length; i++)
			tau[i] = tau0;
	}
	int delta(int x, int y){
		return delta[x*n + y];
	}
	double tau(int x, int y){
		return tau[x*n + y];
	}
	void setTau(int x, int y, double val){
		tau[x*n + y] = val;
		tau[y*n + x] = val;
	}
	double eta(int x, int y){
		return 1.0 / delta(x, y);
	}
	double p(int k, int r, int s){
		if(visited[k][s])
			return 0;
		double sum = 0;
		for(int u=0; u<n; u++){
			if(visited[k][u]) continue;
			sum += tau(r, u) * Math.pow(eta(r, u), beta);
		}
		double res = tau(r, s) * Math.pow(eta(r, s), beta)/sum;
		if(Double.isNaN(res)){
			//System.out.println("C: "+tau(r, s)+", "+Math.pow(eta(r, s), beta)+", "+sum);
			if(debug && sum == 0){
				System.out.println("C");
				for(int u=0; u<n; u++)
					System.out.println(tau(r, u)+","+eta(r, u)+","+ beta);
			}
		}
		return res;
	}
	double deltaTau(int k, int r, int s){
		boolean visited_rs = false;
		int[] tour = tours[k];
		for(int i=0; i<n-1 && tour[i] != -1 && tour[i+1] != -1; i++){
			boolean left = tour[i] == r && tour[i+1] == s;
			boolean rght = tour[i] == s && tour[i+1] == r;
			if(left || rght){
				visited_rs = true;
				break;
			}
		}
		if(visited_rs == false)
			return 0;
		return 1.0 / L[k];
	}
	double deltaTau(int r, int s){
		boolean visited_rs = false;
		int[] tour = globalBestTour;
		for(int i=0; i<n-1 && tour[i] != -1 && tour[i+1] != -1; i++){
			boolean left = tour[i] == r && tour[i+1] == s;
			boolean rght = tour[i] == s && tour[i+1] == r;
			if(left || rght){
				visited_rs = true;
				break;
			}
		}
		if(visited_rs == false)
			return 0;
		if(debug && globalBestTourLength == 0){
			System.out.println("F");
		}
		return 1.0 / globalBestTourLength;
	}
	void globalUpdateRule2(){
		double val;
		double sum;
		for(int r=0; r<n; r++){//NAJA
			for(int s=r+1; s<n; s++){//Naja
				
				sum = 0;
				for(int k=0; k<m; k++)
					sum += deltaTau(k, r, s);
				val = (1.0 - alpha)*tau(r, s) + sum;
				if(Double.isInfinite(val)){
					System.out.println("D "+val);
				}
				setTau(r, s, val);
				
			}
		}
	}
	void globalUpdateRule4(){
		double val;
		for(int r=0; r<n; r++){
			for(int s=r+1; s<n; s++){
				val = (1.0 - alpha) * tau(r, s) + deltaTau(r, s);
				if(debug && Double.isInfinite(val))
					System.out.println("E "+val+", "+tau(r, s)+", "+deltaTau(r, s));
				setTau(r, s, val);
			}
		}
	}
	void localUpdatingRule(int r, int s){
		double val = (1.0-rho)*tau(r, s) + rho * tau0;
		setTau(r, s, val);
	}
	/**
	 * Pseudo Random proportional rule
	 * @param k
	 * @param r
	 * @return
	 */
	int stateTransitionRule(int k, int r){
		double q = rand.nextDouble();
		if(q <= q0){
			double max = Integer.MIN_VALUE;
			int s = -1;
			for(int u=0; u<n; u++){
				if(visited[k][u])continue;
				double val = tau(r, u)*Math.pow(eta(r, u), beta);
				if(val > max){
					max = val;
					s = u;
				}
			}
			if(debug && s == -1){
				String str = "";
				for(boolean[] a: visited)
					str += Arrays.toString(a);
				System.out.println("A ");
			}
			return s;
		}else{
			/*
			double max = Double.MIN_VALUE;
			int s = -1;
			for(int u=0; u<n; u++){
				double val = p(k, r, u);
				if(val > max){
					val = max;
					s = u;
				}
			}
			*/
			q = rand.nextDouble();
			double sum = 0;
			for(int u=0; u<n; u++){
				double val = p(k, r, u);
				sum += val;
				if(q <= sum)
					return u;
			}
			if(debug)
				System.out.println("B: sum="+sum+", q="+q);
			return -1;
		}
	}
}
