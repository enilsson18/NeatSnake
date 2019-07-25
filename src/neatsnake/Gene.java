package neatsnake;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Gene {
	private ArrayList<Integer> out;
	private ArrayList<Integer> in;
	private Double[] values;
	
	private int inPos = 0;
	private double value = 0;
	private String nodeType;
	private int ID;
	private boolean active = true;

	public Gene(String type, int id) {
		nodeType = type;
		ID = id;
	}

	// processing init
	public void startEval(ArrayList<Integer> in, ArrayList<Integer> out) {
		values = new Double[in.size()];
		inPos = 0;
		value = 0;
		this.in = in;
		this.out = out;
	}
	
	//calculates the values
	public double eval(double num) {
		if (values.length > inPos) {
			values[inPos] = num;
		}
		inPos += 1;
		value += num;
		if (inPos >= in.size()) {
			int temp = 0;
			Arrays.sort(values, Comparator.comparing((s) -> s));
			for (int i = 0; i < values.length; i++) {
				temp += (values[i]/values[0])/(values[values.length-1]-values[0]);
			}
			return value;
		} else {
			return -1;
		}
	}

	// getters, setters, and toString
	
	public void setActivity(boolean a) {
		active = a;
	}
	
	public ArrayList<Integer> getOut(){
		return out;
	}
	
	public ArrayList<Integer> getIn(){
		return in;
	}
	
	public boolean getActivity() {
		return active;
	}

	public int getID() {
		return ID;
	}

	public double getValue() {
		return value;
	}

	public String getType() {
		return nodeType;
	}

	public String toString() {
		return ID + ": Enabled: " + active + ", Node Type:" + nodeType + " node, ID: " + ID;
	}
}
