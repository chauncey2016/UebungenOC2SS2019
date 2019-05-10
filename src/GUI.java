import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class GUI extends JComponent{
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setSize(500, 500);
		GUI canvas = new GUI();
		f.add(canvas);
		//JPanel panel = new JPanel();
		f.setVisible(true);
		canvas.init();
	}

	String[] names = {"Brisbane", "Venice", "Prague", "London", 
			"Edinburgh", "Paris", "Florence", "Rome", 
			"Sydney", "Seville", "Rio de Janeiro"};
	
	int n = -1;
	Point[] points = null;
	Point[] path = null;
	
	public GUI() {
		
	}
	
	public void init() {
		n = 9; //max 11 oder 10
		points = makeRandomPoints();
		points = makCirclePoints(70);
		int[] a = Abgabe.solve(n, makeMatrix());
		path = new Point[n];
		for(int i=0; i < a.length; i++) {
			path[i] = points[ a[i] ];
		}
		repaint();
	}
	
	private Point[] makeRandomPoints() {
		Point[] points = new Point[n];
		int off = 20;
		for(int i=0; i<n; i++) {
			int x = (int) (Math.random()*(getWidth() -2*off)) + off;
			int y = (int) (Math.random()*(getHeight() -2*off)) + off;
			points[i] = new Point(x, y);
		}
		System.out.println(getWidth());
		return points;
	}
	private Point[] makCirclePoints(int degree) {
		Point[] points = new Point[n];
		int x, y;
		int r = 100;
		int w = getWidth();
		int h = getHeight();
		for(int i=0; i<n; i++) {
			x = (int) (r * Math.cos(degree * i * Math.PI / 180));
			y = (int) (r * Math.sin(degree * i * Math.PI / 180));
			points[i] = new Point(x + w/2, y + h/2);
		}
		return points;
	}
	
	private int[] makeMatrix() {
		int[] arr = new int[n*n];
		for(int i=0; i<n*n; i++) {
			int x = i%n;
			int y = i/n;
			int dst = calcDst(x, y);
			arr[x*n+y] = (arr[y*n + x] = dst);
		}
		return arr;
	}
	private int calcDst(int a, int b) {
		return (int) Point.distance(points[a].x, points[a].y, points[b].x, points[b].y);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if(n == -1)
			return;
		for(int i=0; i<points.length; i++) {
			for(int j=i+1; j<points.length; j++) {
				Point p1 = points[i];
				Point p2 = points[j];
				if(p1 == p2)
					continue;
				//g.drawLine(p1.x, p1.y, p2.x, p2.y);
			}
		}
		int r = 5;
		for(int i=0; i<points.length; i++) {
			Point p = points[i];
			g.fillOval(p.x-r/2, p.y-r/2, r, r);
			g.drawString(names[i], p.x, p.y-r/2);
		}
		if(path == null)
			return;
		for(int i=1; i<path.length; i++) {
			Point p1 = points[i];
			Point p2 = points[i-1];
			g.drawLine(p1.x, p1.y, p2.x, p2.y);
		}
	}
	


}
