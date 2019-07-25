package neatsnake;

public class Connection {
	private int in, out, id;
	private double weight;
	private boolean active;
	
	public Connection() {
		
	}
	
	public Connection(int in, int out, double weight, int id) {
		this.in = in;
		this.out = out;
		this.weight = weight;
		this.id = id;
		active = true;
	}
	
	public double eval(double num) {
		return num*weight;
	}
	
	public void setConnection(Connection c) {
		in = c.getIn();
		out = c.getOut();
		weight = c.getWeight();
	}
	
	public void setActivity(boolean a) {
		active = a;
	}
	
	public void setWeight(double w) {
		weight = w;
	}
	
	public boolean getActivity() {
		return active;
	}
	
	public int getIn() {
		return in;
	}
	
	public int getOut() {
		return out;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public int getId() {
		return id;
	}
}
