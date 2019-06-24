package Optimierung;

public class HillClimbing {
	static Statistics st = null;
	static double[] randSearch(int dimensions, int testMax) throws Exception{
		DockerAdapter adapter = DockerAdapter.instance();
		double vmin = 10000, v;
		double[] values = new double[dimensions];
		double[] minValues = null;
		for(int i=0; i<testMax; i++){
			for(int j=0; j<dimensions; j++)
				values[j] = Math.random()*100;
			v = adapter.nextVal(values);
			if(v<vmin && (vmin=v) == v)
				minValues = values.clone();
		}
		return minValues;
	}
	static double[] EazyHillClimbing(int dimensions, int testMax, double s) throws Exception{
		DockerAdapter adapter = DockerAdapter.instance();
		double vmin = 10000, v;
		
		//Random init
		double[] values = new double[dimensions];
		for(int j=0; j<dimensions; j++)
			values[j] = Math.random();
		
		//Max Interations
		boolean k_found = true;
		
		//Steps size
		//double s = 1;
		
		double localMin = 1000;
		double[] tmp_values;
		double tmp_out;
		double[] nextValues = null;
		
		for(int i=0; k_found && i<testMax; i++){
			k_found = false;
			for(int j=0; j<dimensions; j++){
				tmp_values = values.clone();
				tmp_values[j] += s;
				tmp_out = adapter.nextVal(tmp_values);
				if(tmp_out < localMin){
					localMin = tmp_out;
					nextValues = tmp_values;
					k_found = true;
					break;
				}
			}
			if(st != null)
				st.push(i, adapter.nextVal(nextValues));
			values = nextValues;
		}
		return values;
	}
	static double[] SteepestAscentHillClimbing(int dimensions, int testMax, double s) throws Exception{
		DockerAdapter adapter = DockerAdapter.instance();
		double vmin = 10000, v;
		
		//Random init
		double[] values = new double[dimensions];
		for(int j=0; j<dimensions; j++)
			values[j] = Math.random();
		
		//Condition
		boolean k_found = true;
		
		//Steps size
		//double s = 1;
		
		double localMin = 1000;
		double[] tmp_values;
		double tmp_out;
		double[] nextValues = null;
		
		for(int i=0; k_found && i<testMax; i++){
			k_found = false;
			for(int j=0; j<dimensions; j++){
				tmp_values = values.clone();
				tmp_values[j] += s;
				tmp_out = adapter.nextVal(tmp_values);
				if(tmp_out < localMin){
					localMin = tmp_out;
					nextValues = tmp_values;
					k_found = true;
				}
			}
			if(st != null)
				st.push(adapter.nextVal(nextValues));
			values = nextValues;
		}
		return values;
	}
	
	static double[] randomRestartHillClimbing(int dimensions, int testMax, double s) throws Exception{
		DockerAdapter adapter = DockerAdapter.instance();
		double[] O = null, o;
		int i_max = 100;
		for(int i=0; i<testMax / i_max; i++){
			o = SteepestAscentHillClimbing(dimensions, i_max, s);
			if(O == null || adapter.nextVal(o)  < adapter.nextVal(O))
				O = o;
		}
		return O;
	}
}
