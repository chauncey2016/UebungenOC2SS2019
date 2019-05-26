import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.JComponent;

public class GraphComponent extends JComponent{
	static GraphComponent position;
	static GraphComponent route;
	static GraphComponent pheromon;
	Vector<Double> values;
	public GraphComponent() {
		setPreferredSize(new Dimension(400, 400));
		values = new Vector<Double>();
	}
	@Override
	public void paint(Graphics g){
		super.paint(g);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		double minVal = calcMin(values);
		double maxVal = calcMax(values);
		int size = values.size();
		
		int h = getHeight();
		int w = getWidth();
		
		int off = 30;
		int steps = 10;
		int x, y;
		double value;
		double v1, v2, x1, x2, y1, y2;
		g.setColor(Color.GRAY);
		
		String name = "";
		if( this == position){
			g.setColor(Color.RED);
			name = "position";
		}
		if( this == pheromon){
			g.setColor(Color.BLUE);
			name = "pheromon";
		}
		if(this == route){
			g.setColor(Color.GREEN);
			name = "route";
		}
		
		for(int i=1; i<size; i++){
			x1 = (w-off)*(i-1)/size + off;
			x2 = (w-off)*i/size + off;
			v1 = values.get(i-1);	
			v2 = values.get(i);	
			y1 = (int)(h * (v1-minVal) / (maxVal - minVal));
			y2 = (int)(h * (v2-minVal) / (maxVal - minVal));
			g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
			//g.fillOval(x-2, y-2, 4, 4);
		}
		g.setColor(Color.BLACK);
		for(int i=0; i<steps; i++){
			x = 0;
			y = h * i / steps;
			value = minVal + (maxVal-minVal)*i/steps;
			String str = new DecimalFormat("0.0#").format(value);
			g.drawString(str, x, y);
		}
		g.drawString(name, getWidth()-50, getHeight());
		//System.out.println(size);
	}
	public void addValue(double val){
		values.add(val);
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
