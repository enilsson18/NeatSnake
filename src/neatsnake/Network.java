package neatsnake;

import java.util.ArrayList;

public class Network {
	private int inputs, outputs;
	int weightRandomness = 1000000;
	private int netID;
	private int overflowPrevention = 0, maxNodePath = 1000;

	private ArrayList<Gene> genes = new ArrayList<Gene>();
	private ArrayList<Connection> connections = new ArrayList<Connection>();
	
	//initialize
	public Network(int netID, int inputNum, int outputNum) {
		inputs = inputNum;
		outputs = outputNum;
		this.netID = netID;
		// makes initial inputs
		for (int i = 0; i < inputNum; i++) {
			genes.add(new Gene("input", genes.size()));
		}
		// makes initial outputs
		for (int i = 0; i < outputNum; i++) {
			genes.add(new Gene("output", genes.size()));
		}
		// sets all the connections
		for (int i = 0; i < inputNum; i++) {
			connections.add(new Connection(i, (i%outputNum)+inputNum, Math.random(),connections.size()));
		}
		if (inputNum < outputNum) {
			for (int i = 0; i < outputNum-inputNum; i++) {
				connections.add(new Connection(i%inputNum, i+inputNum+inputNum, Math.random(),connections.size()));
			}
		}
		// System.out.println("finished network setup");
	}

	//copy method so that it can keep the ID while changing the network
	public void setNetwork(Network network) {
		genes = network.getGenes();
		connections = network.getConnections();
		// System.out.println("finished network setup");
	}

	// main methods

	//process inputs through the network and returns response for snake
	public int process(int[] inputs) {
		/*
		 * for (int i = 0; i < inputs.length; i++) { System.out.println(inputs[i]); }
		 */

		// temp var

		// initialize things before calculations
		for (int i = 0; i < genes.size(); i++) {
			genes.get(i).startEval(getIns(i),getOuts(i));
		}
		// send all the
		for (int i = 0; i < inputs.length; i++) {
			overflowPrevention = 0;
			runNode(i, inputs[i]);
		}
		double[] temp = new double[outputs];

		for (int i = 0; i < outputs; i++) {
			temp[i] = genes.get(i).getValue();
		}

		// wee bit of stuff related to the snake change if ur using this for another
		// program
		// output sorting controls

		// printNodes();
		
		//return result of calculations
		int biggest = 0;
		for (int i = 0; i < outputs; i++) {
			if (temp[i] > temp[biggest]) {
				biggest = i;
			}
			//System.out.println(temp[i]);
		}
		return biggest + 1;
	}

	// recursion are u proud Mrs. Cingel?
	// this little thing traces through all of the nodes
	//sorry this is very hard to explain with words but I will write the comment later
	public void runNode(int node, double value) {
		for (int i = 0; i < genes.get(node).getOut().size(); i++) {
			//System.out.println(genes.get(node).getOut().get(i));
			//genes.get(genes.get(node).getOut().get(i));
			genes.get(connections.get(genes.get(node).getOut().get(i)).getIn()).eval(connections.get(genes.get(node).getOut().get(i)).eval(value));
			if (genes.get(connections.get(genes.get(node).getOut().get(i)).getOut()).eval(0) != -1 && connections.get(genes.get(node).getOut().get(i)).getActivity() != false) {
				runNode(connections.get(genes.get(node).getOut().get(i)).getOut(),genes.get(connections.get(genes.get(node).getOut().get(i)).getOut()).eval(0));
			}
		}
	}

	// setter methods
	//adds a node in a random position
	
	public void addRandomNode() {
		// set out
		int randNum;
		// set in
		int randNumTwo;

		do {
			randNum = (int) (Math.floor(Math.random() * genes.size()));
			randNumTwo = (int) (Math.floor(Math.random() * genes.size()));
		} while (genes.get(randNumTwo).getType() == "output" || genes.get(randNum).getType() == "input"
				|| !genes.get(randNum).getActivity() || !genes.get(randNumTwo).getActivity());

		genes.add(new Gene("hidden", genes.size()));
		connections.add(new Connection(randNumTwo, genes.size()-1,Math.floor(Math.random() * weightRandomness) / weightRandomness, connections.size()));
		connections.add(new Connection(genes.size()-1, randNum, Math.floor(Math.random() * weightRandomness) / weightRandomness, connections.size()));
	}
	
	//adds a specific node
	public void addNode(double weight, int in, int out) {
		genes.add(new Gene("hidden", genes.size()));
		connections.add(new Connection(in, genes.size()-1,Math.floor(Math.random() * weightRandomness) / weightRandomness, connections.size()));
		connections.add(new Connection(genes.size()-1, out, Math.floor(Math.random() * weightRandomness) / weightRandomness, connections.size()));
	}
	
	//removes a random hidden node 
	public void removeRandomNode() {
		int randNum;
		int counter = 0;
		boolean isValid;
		do {
			randNum = (int) (Math.floor(Math.random() * genes.size()));
			ArrayList<Integer> outList = getOuts(randNum);
			ArrayList<Integer> inList = getIns(randNum);
			isValid = true;

			for (int i = 0; i < outList.size(); i++) {
				if (outList.get(i) == -1 || getIns(connections.get(outList.get(i)).getOut()).size() <= 1) {
					isValid = false;
					break;
				}
			}
			for (int i = 0; i < inList.size(); i++) {
				if (inList.get(i) == -1 || getOuts(connections.get(inList.get(i)).getIn()).size() <= 1) {
					isValid = false;
					break;
				}
			}

			counter += 1;
		} while ((genes.get(randNum).getType() != "hidden" || !genes.get(randNum).getActivity() || !isValid) && counter <= genes.size() * 10);
		if (counter <= genes.size() * 10) {
			ArrayList<Integer> outList = getOuts(randNum);
			ArrayList<Integer> inList = getIns(randNum);
			for (int i = 0; i < outList.size(); i++) {
				connections.get(outList.get(i)).setActivity(false);
			}

			for (int i = 0; i < inList.size(); i++) {
				connections.get(inList.get(i)).setActivity(false);
			}

			genes.get(randNum).setActivity(false);
		}
	}
	
	//add random connection
	public void addRandomConnection() {
		int randNum;
		int randNum2;
		do {
			randNum = (int) (Math.floor(Math.random() * genes.size()));
			randNum2 = (int) (Math.floor(Math.random() * genes.size()));
		} while (randNum == randNum2 || genes.get(randNum).getType() == "output"
				|| genes.get(randNum2).getType() == "input" || !genes.get(randNum).getActivity()
				|| !genes.get(randNum2).getActivity());
		connections.add(new Connection(randNum,randNum2,Math.floor(Math.random() * weightRandomness) / weightRandomness, connections.size()));
	}
	
	//remove random connection
	public void removeRandomConnection() {
		int randNum;
		int counter = 0;
		do {
			randNum = (int) (Math.floor(Math.random() * genes.size()));
			counter += 1;
		} while ((getOuts(randNum).size() <= 1 || !genes.get(randNum).getActivity())
				&& counter <= 10 * genes.size());
		if (counter <= 10 * genes.size()) {
			int randOut = getOuts(randNum).get((int) Math.floor(Math.random() * getOuts(randNum).size()));
			if (getOuts(randNum).size() > 1) {
				connections.get(randOut).setActivity(false);
			}
		}
	}
	
	//remove a specific connection
	public void removeConnection(int node) {
		for (int i = 0; i < connections.size(); i++) {
			if (connections.get(i).getIn() == node || connections.get(i).getOut() == node) {
				connections.get(i).setActivity(false);
			}
		}
	}
	
	
	//sets a random weight
	public void setRandomWeight() {
		int randC = (int) Math.floor(Math.random()*connections.size());
		double change = connections.get(randC).getWeight() + ((Math.floor(Math.random() * weightRandomness) / weightRandomness)/3);
		connections.get(randC).setWeight(change%1);
	}

	// getter methods
	public ArrayList<Integer> getIns(int node) {
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for (int i = 0; i < connections.size(); i++) {
			if (connections.get(i).getOut() == node) {
				temp.add(i);
			}
		}
		return temp;
	}
	
	public ArrayList<Integer> getOuts(int node) {
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for (int i = 0; i < connections.size(); i++) {
			if (connections.get(i).getIn() == node) {
				temp.add(i);
			}
		}
		return temp;
	}
	
	public int getNodeCount() {
		return genes.size();
	}

	public int getID() {
		return netID;
	}

	public ArrayList<Gene> getGenes() {
		return genes;
	}
	
	public ArrayList<Connection> getConnections() {
		return connections;
	}

	//prints the network
	public void printNodes() {
		// inputs
		for (int i = 0; i < inputs; i++) {
			System.out.println(genes.get(i));
		}
		// hidden
		for (int i = inputs + outputs; i < genes.size(); i++) {
			if (genes.get(i).getActivity()) {
				System.out.println(genes.get(i));
			}
		}
		// outputs
		for (int i = 0; i < outputs; i++) {
			System.out.println(genes.get(i+inputs));
		}
	}

	public String toString() {
		return "Inputs: " + inputs + ", Hidden: " + (genes.size() - (outputs + inputs)) + ", Output: " + outputs;
	}
}
