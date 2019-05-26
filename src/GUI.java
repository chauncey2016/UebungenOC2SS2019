import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class GUI extends JComponent{
	JTextField out;
	long seed = 9L;
	Random random;
	public static void main(String[] args) {
		JFrame f = new JFrame("Problem des Handelsreisenden");
		f.setSize(700, 480);
		
		JPanel panel2 = new JPanel();
		GUI canvas = new GUI();
		panel2.add(canvas);
		
		GraphComponent canvas2 = new GraphComponent();
		panel2.add(canvas2);
		
		f.add(panel2);
		
		JPanel panel = new JPanel();
		
		JRadioButton r1=new JRadioButton("Brute Force"); 
		r1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(r1.isSelected()){
					canvas.brute_force = true;
					canvas.init();
				}
					
			}
		});
		JRadioButton r2=new JRadioButton("ACS");
		r2.setSelected(true);
		r2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(r2.isSelected()){
					canvas.brute_force = false;
					canvas.init();
				}
			}
		});
		ButtonGroup bg=new ButtonGroup();    
		bg.add(r1);
		bg.add(r2);
		panel.add(r1);
		panel.add(r2);
		
		JButton button1 = new JButton("reset");
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.init();
			}
		});
		panel.add(button1);
		
		JButton button = new JButton("random/ circle");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.useRandom = !canvas.useRandom;
				canvas.init();
			}
		});
		panel.add(button);
		
		JLabel label_seed = new JLabel("Random Seed");
		panel.add(label_seed);
		JTextField field3 = new JTextField();
		field3.setText("9");
		field3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				long seed = Long.parseLong(field3.getText());
				canvas.seed = seed;
				canvas.init();
			}
		});
		field3.setPreferredSize(new Dimension(40, 20));
		panel.add(field3);
		
		JLabel label_n = new JLabel("number");
		panel.add(label_n);
		JTextField field2 = new JTextField();
		field2.setText("9");
		field2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int n = Integer.parseInt(field2.getText());
				if(n > canvas.names.length) {
					JOptionPane.showMessageDialog(null, "Max n = "+canvas.names.length+ " out of bounds");
				}else {
					canvas.n = n;
					canvas.init();
				}
			}
		});
		field2.setPreferredSize(new Dimension(40, 20));
		panel.add(field2);
		
		JLabel label_input = new JLabel("Degree");
		panel.add(label_input);
		JTextField field = new JTextField();
		field.setText("40");
		field.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.degree = Integer.parseInt(field.getText());
				canvas.init();
			}
		});
		field.setPreferredSize(new Dimension(40, 20));
		panel.add(field);
		
		canvas.out = new JTextField();
		canvas.out.setEditable(false);
		canvas.out.setPreferredSize(new Dimension(300, 20));
		panel.add(canvas.out);
		
		f.add(panel, BorderLayout.SOUTH);
		f.setVisible(true);
		canvas.init();
	}

	String[] names = {"Brisbane", "Venice", "Prague", "London", 
			"Edinburgh", "Paris", "Florence", "Rome", 
			"Sydney", "Seville", "Rio de Janeiro", "Berlin", "Washington",
			"Stockholm", "NY", "Madrid", "Barcelona", "Hong Kong", "Moskau", "St Petersburg", "Hamburg"};
	
	int n = 0;
	int degree = 40;
	Point[] points = null;
	Point[] path = null;
	boolean useRandom = false;
	boolean brute_force = false;
	
	public GUI() {
		setPreferredSize(new Dimension(400, 400));
		n = 9; //max 11 oder 10
	}
	
	public void init() {
		GraphComponent.unique.values.clear();
		
		if(useRandom)
			points = makeRandomPoints();
		else{
			random = new Random(seed);
			points = makCirclePoints(degree);
		}
			
		
		int[] matrix =  makeMatrix();
		Abgabe.print(matrix, n);
		long t0 = System.currentTimeMillis();
		
		int[] a    = null;
		int length = -1;
		
		if(brute_force){
			a = Abgabe.solve(n,matrix);
			length = Abgabe.length2(a, n, matrix);
		}else{
			ACS acs = new ACS(matrix, n, random);
			a = acs.solve();
			length = acs.globalBestTourLength;
		}
		
		GraphComponent.unique.repaint();
		
		long t1 = System.currentTimeMillis();
		if( (t1-t0) < 1000)
			out.setText((t1-t0)+"ms for calc with "+n+" Elements "+length+" pixels");
		else
			out.setText((t1-t0)/1000+"sec for calc with "+n+" Elements"+length+" pixels");
		
		path = new Point[n];
		
		for(int i=0; i < n; i++)
			path[i] = points[ a[i] ];
		
		/*
		System.out.println();
		for(int i=0; i < n; i++)
			System.out.print(names[a[i]]+"\t");
		System.out.println();
		for(int i=0; i < n; i++)
			System.out.print(a[i]+"\t");
		*/
		
		repaint();
	}
	
	private Point[] makeRandomPoints() {
		random = new Random(seed);
		Point[] points = new Point[n];
		int off = 20;
		for(int i=0; i<n; i++) {
			//int x = (int) (Math.random()*(getWidth() -2*off)) + off;
			//int y = (int) (Math.random()*(getHeight() -2*off)) + off;
			int w = 400;
			int h = 400;
			int x = random.nextInt(w - 2* off) + off;
			int y = random.nextInt(h - 2* off) + off;
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
		Graphics2D g2 = (Graphics2D)g;
		 g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
		if(n == -1)
			return;
		g.setColor(Color.GRAY);
		for(int i=0; i<points.length; i++) {
			for(int j=i+1; j<points.length; j++) {
				Point p1 = points[i];
				Point p2 = points[j];
				if(p1 == p2)
					continue;
				g.drawLine(p1.x, p1.y, p2.x, p2.y);
			}
		}
		if(path == null)
			return;
		g.setColor(Color.RED);
		g2.setStroke(new BasicStroke(3));
		for(int i=1; i<path.length; i++) {
			Point p1 = path[i-1];
			Point p2 = path[i];
			g.drawLine(p1.x, p1.y, p2.x, p2.y);
		}
		g2.setStroke(new BasicStroke(1));
		g.setColor(Color.BLACK);
		int r = 5;
		for(int i=0; i<points.length; i++) {
			Point p = points[i];
			g.fillOval(p.x-r/2, p.y-r/2, r, r);
			g.drawString(names[i], p.x, p.y-r/2);
		}
	}
	


}
