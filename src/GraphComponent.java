import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.JComponent;

public class GraphComponent extends JComponent{
	Vector<Double> values;
	public GraphComponent() {
		setPreferredSize(new Dimension(400, 400));
		values = new Vector<Double>();
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
		double label;
		for(int i=0; i<steps; i++){
			x = 0;
			y = h - h*i/steps;
			label = minVal + (maxVal - minVal)*i/steps;
			g.drawString(makeLabel(label), x, y);
		}
	}
	private String makeLabel(double label) {
		return label + "";
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
