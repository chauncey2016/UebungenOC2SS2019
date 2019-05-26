import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.JComponent;

public class GraphComponent extends JComponent{
	static GraphComponent unique;
	Vector<Double> values;
	public GraphComponent() {
		setPreferredSize(new Dimension(400, 400));
		values = new Vector<Double>();
		unique = this;
	}
	@Override
	public void paint(Graphics g){
		super.paint(g);
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		double minVal = calcMin(values);
		double maxVal = calcMax(values);
		int size = values.size();
		
		int h = getHeight();
		int w = getWidth();
		
		g.setColor(Color.BLACK);
		int steps = 10;
		int x, y;
		double value;
		for(int i=0; i<size; i++){
			x = w*i/steps;
			value = values.get(i);	
			y = (int)(h * (value-minVal) / (maxVal - minVal));
			g.fillOval(x-2, y-2, 4, 4);
		}
	}
	public void addValue(double val){
		values.addElement(val);
	}
	private double calcMax(Vector<Double> vec) {
		double max = Integer.MIN_VALUE;
		for(int i=0; i<vec.size(); i++)
			if(vec.get(i) > max)
				max = vec.get(i);
		return max;
	}
	private double calcMin(Vector<Double> vec) {
		double min = Integer.MAX_VALUE;
		for(int i=0; i<vec.size(); i++)
			if( ((double)vec.get(i)) < min)
				min = vec.get(i);
		return min;
	}
	
	
}
