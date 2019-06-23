import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class EmergenceGUI extends JComponent{
	static EmergenceGUI c;
	public static void main(String[] args){
		JFrame f = new JFrame();
		c = new EmergenceGUI();
		f.setSize(500, 500);
		f.add(c);
		f.setVisible(true);
		new Thread(new Runnable(){
			public void run(){
				double value = Math.random();
				c.setCurrentValue("ab", value);
			}
		}).start();
	}
	
	class TimedValue{
		long time;
		double value;
		public TimedValue(long t, double v){
			time = t;
			value = v;
		}
	}
	
	Map<String, Vector<TimedValue>> map;
	public EmergenceGUI(){
		map = new HashMap<String, Vector<TimedValue>>();
	}

	void setCurrentValue(String attributeName, double value){
		long t = System.currentTimeMillis();
		TimedValue tv = new TimedValue(t, value);
		Vector<TimedValue> vec = null;
		if( map.get(attributeName) == null ){
			vec = new Vector<TimedValue>();
			map.put(attributeName, vec);
		}else{
			vec = map.get(attributeName);
		}
		vec.add(tv);
	}
}
