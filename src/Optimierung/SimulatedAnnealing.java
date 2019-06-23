package Optimierung;

public class SimulatedAnnealing {
	SimulatedAnnealing(int dimensions, int iMax, double[] result, boolean useT1) throws Exception{
		DockerAdapter b = DockerAdapter.instance();
		
		double[] o = initRandom(dimensions);
		double[] o_tmp;
		double[] o_best = o;
		double T;
		double s = 1;
		double bo, bo_t;
		for(int i=0; i<iMax; i++){
			T = t(iMax, i+1, useT1);
			
			bo = b.nextVal(o);
			
			o_tmp = randomNeighbor(o, s);
			bo_t =  b.nextVal(o_tmp);
			
			if( bo_t < bo ){
				o = o_tmp;
			}else if( Math.random() < E(bo_t - bo, T) ){
				o = o_tmp;
			}
			
			if(bo < b.nextVal(o_best))
				o_best = o;
		}
		for(int i=0; i<result.length; i++)
			result[i] = o_best[i];
	}
	private double[] randomNeighbor(double[] o, double s) {
		double[] res = new double[o.length];
		double d_v ;
		for(int i=0; i<o.length; i++){
			d_v = Math.random()*2*s;
			res[i] = o[i] + d_v;
		}
		return res;
	}
	double[] initRandom(int dimensions){
		double[] values = new double[dimensions];
		for(int j=0; j<dimensions; j++)
			values[j] = Math.random();
		return values;
	}
	double b(double[] in){
		return 1;
	}
	double E(double delta, double T){
		return Math.pow(Math.E, -delta / T);
	}
	 
	double t(int a, int b, boolean useT1){
		if(useT1)
			return t1(a, b);
		else
			return t2(a, b);
	}
	double eta = 1.0; //iMax to 0
	double alpha = 0.993;
	double t1(int iMax, int i){
		return iMax - eta * i;
	}
	double t2(int iMax, int i){
		return iMax * Math.pow(alpha, i);
	}
	
	
}
