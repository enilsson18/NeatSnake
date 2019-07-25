package neatsnake;

import java.util.Arrays;
import java.util.Comparator;

import processing.core.PApplet;

//test file to make sure network and mutations are working
public class Test extends PApplet{

	public static void main(String[] args) {
		GenerationManager gManager = new GenerationManager();
		int[] bestCost = new int[]{0,0,0,0};
		for (int i = 1; i < 500; i+=30) {
			for (int j = 1; j < 200; j+=10) {
					gManager = new GenerationManager();
					gManager.nodeChance = i;
					gManager.nodePathChance = j;
					gManager.weightChance = 30;
					gManager.run(20);
					if (gManager.getBestGame().getCost() > bestCost[0]) {
						bestCost = new int[]{gManager.getBestGame().getCost(),i,j,10};
					}
					if (gManager.getBestGame().getCost() > 100) {
						break;
					}
			}
		}
		System.out.println("bestCost: " + bestCost[0] + ", nodeChance: " + bestCost[1] + ", nodePathChance: " + bestCost[2] + ", weightchance: " + bestCost[3]);
		
		
		/*Network network = new Network(0,3,3);
		
		int[] inputs = {1,1,3};
		for (int i = 0; i < 3; i++) {
			
		}
		
		System.out.println(network.process(inputs));*/
	}
	
	/*
	public static void main(String args[]) {
		PApplet.main("neatsnake.Test");
	}
	
	SnakeGame game = new SnakeGame();
	boolean gameOver = false;
	int dir = 2;
	
	public void settings() {
		size(600,600);
	}
	
	public void setup() {
		frameRate(10);
	}
	
	public void draw() {
		background(51);
		fill(51, 232, 27);
		for (int i = 0; i < game.getSnake().size(); i++) {
			rect(game.getSnake().get(i).x,game.getSnake().get(i).y,20,20);
		}
		fill(255,0,0);
		rect(game.getApple().x, game.getApple().y, 20, 20);
		
		game.run(dir);
		
		if (!game.checkStatus()) {
			System.exit(0);
		}
	}
	
	public void keyPressed()
	{
	  //menu 0 is when you are playing the game
	  //menu 1 is the main menu
	  //menu 2 is the menu when you die
	  //menu 3 is the instructions menu
	  //menu 4 is an easter egg AI Mode
	    if (key == 'w')
	    {
	      dir = 1;
	    }
	    if (key == 'd')
	    {
	      dir = 2;
	    }
	    if (key == 's')
	    {
	      dir = 3;
	    }
	    if (key == 'a')
	    {
	      dir = 4;
	    }
	}
	*/
}
