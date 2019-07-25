package neatsnake;

import java.util.Arrays;
import java.util.Comparator;

public class GenerationManager {
	private Network[] genome;
	private SnakeGame[] game;
	private int currentGen = 0;
	public int inputs = 3, outputs = 3, topSelections = 10, nodePathChance = 70, nodeChance = 300, weightChance = 27;

	//default
	public GenerationManager() {
		this(100);
	}
	
	//initialize
	public GenerationManager(int genSize) {
		genome = new Network[genSize];
		game = new SnakeGame[genSize];
		for (int i = 0; i < genSize; i++) {
			genome[i] = new Network(i, inputs, outputs);
			game[i] = new SnakeGame(i);
		}
	}

	//loops for how many generations user specifies and evolves the snake genomes
	public void run(int gens) {
		for (int j = 0; j < gens; j++) {
			currentGen = j + 1;
			// runs through all of the games
			for (int i = 0; i < game.length; i++) {
				// int gameCount = 0;
				game[i].reset();
				while (game[i].checkStatus()) {
					game[i].run(genome[i].process(game[i].getData()));
					// System.out.println(gameCount);
					// gameCount += 1;
				}
				// System.out.println("Game: " + i);
			}
			// System.out.println("Games Completed");

			// ranking sorts in ascending so biggest value is at 99 and lowest is at 0
			Arrays.sort(game, Comparator.comparing((s) -> s.getCost()));

			// System.out.println("ranked");

			//mutates the snake so that the top ten snakes replace all of the others only the best survive
			Network[] tempList = new Network[10];
			for (int i = 0; i < 10; i++) {
				//System.out.println(game[i].getCost());
				tempList[i] = genome[game[i+90].getID()];
			}
			for (int i = 0; i < 10; i++) {
				for (int g = 0; g < 10; g++) {
					int randNum = (int) (Math.floor(Math.random() * 10));
					if (randNum < 2) {
						genome[i*10+g].setNetwork(tempList[i]);
					}
				}
			}
			for (int i = 0; i < 100; i++) {
				//System.out.println(game[i].getCost());
			}
			// System.out.println("finished initial mutation");

			// changing ins and outs of neural networks
			for (int i = 0; i < genome.length-10; i++) {
				// adding connections
				int randNum = (int) (Math.floor(Math.random() * nodePathChance));
				if (randNum == 0) {
					// System.out.println("adding random Connection");
					genome[i].addRandomConnection();
				}
				// System.out.println(i + ", " + g);
				// removing connections
				randNum = (int) (Math.floor(Math.random() * nodePathChance));
				if (randNum == 0) {
					// System.out.println("removing random Connection");
					genome[i].removeRandomConnection();
				}
			}
			// System.out.println("finished connection mutation");
			
			// chance of adding or removing entire nodes in a network
			for (int i = 0; i < genome.length-10; i++) {
				// adding node
				
				int randNum = (int) (Math.floor(Math.random() * nodeChance));
				if (randNum == 0) {
					// System.out.println("adding node");
					genome[i].addRandomNode();
				}

				// removing node
				
				randNum = (int) (Math.floor(Math.random() * nodeChance));
				if (randNum == 0) {
					// System.out.println("removing node");
					genome[i].removeRandomNode();
				}
			}
			// System.out.println("Finished node addition mutation");

			// chance of changing weight of a node
			for (int i = 0; i < genome.length; i++) {
				for (int g = 0; g < genome[i].getGenes().size(); g++) {
					int randNum = (int) (Math.floor(Math.random() * weightChance));
					if (randNum == 0) {
						genome[i].setRandomWeight();
					}
				}
			}
			// System.out.println("Finished weight mutation");
			System.out.println(currentGen + "/" + gens + " Cost: " + game[99].getCost());
		}
		//display info of best genome
		genome[99].printNodes();
		System.out.println(game[99].getCost());

		System.out.println("Process Completed");
	}

	public Network getBestGenome() {
		return genome[99];
	}
	
	public SnakeGame getBestGame() {
		return game[99];
	}
}
