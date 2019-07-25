//@author Erik A Machine Learning Snake AI program that has no learning limit
//@version 1.0
package neatsnake;

import processing.core.PApplet;
import java.util.Scanner;

public class NeatSnake extends PApplet {
	public static void main(String args[]) {
		PApplet.main("neatsnake.NeatSnake");
	}

	private GenerationManager gManager = new GenerationManager();
	private SnakeGame game;
	private Scanner scan = new Scanner(System.in);
	private int stage;
	private int input;
	
	//set window size
	public void settings() {
		size(600, 600);
	}
	//initialize stuff
	public void setup() {
		stage = 0;
		frameRate(20);
	}
	//the main draw loop for the preview and user UI
	public void draw() {
		//menu
		if (stage == 0) {
			System.out.println("How many generations would you like to run? To run the last gen build type 0.");
			input = scan.nextInt();
			if (input == 0) {
				stage = 1;
				game = new SnakeGame();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				gManager.run(input);
			}
		}
		//neural preview
		if (stage == 1) {
			background(51);
			fill(51, 232, 27);
			for (int i = 0; i < game.getSnake().size(); i++) {
				rect(game.getSnake().get(i).x,game.getSnake().get(i).y,20,20);
			}
			fill(255,0,0);
			rect(game.getApple().x, game.getApple().y, 20, 20);
			
			//System.out.println(gManager.getBestGenome().process(game.getData()));
			game.run(gManager.getBestGenome().process(game.getData()));
			
			for (int i = 0; i < game.getData().length; i++) {
				System.out.print(game.getData()[i] + " ");
			}
			System.out.println("");
			
			if (!game.checkStatus()) {
				stage = 0;
				System.out.println("Cost: " + game.getCost());
			}
		}
	}
}