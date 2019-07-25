package neatsnake;

import java.util.ArrayList;

public class SnakeGame {
	//variables
	private Apple apple = new Apple();
	private ArrayList<Tail> snake = new ArrayList<Tail>();

	private int dir, score, cost, ID;
	private boolean playing = true;

	//int new game methods
	public SnakeGame(int ID) {
		this.ID = ID;
		reset();
	}
	
	public SnakeGame() {
		ID = -1;
		reset();
	}

	public int getCost() {
		return cost;
	}
	
	public void setCost(int cost) {
		this.cost = cost;
	}

	//method to determine if dead
	public boolean checkStatus() {
		return playing;
	}
	
	public int getID() {
		return ID;
	}
	
	//getter methods
	public ArrayList<Tail> getSnake() {
		return snake;
	}
	
	public Apple getApple() {
		return apple;
	}

	//make a move affect the game
	public void run(int input) {
		double cDir = 1, wDir = 3;
		playing = true;
		dir = input;
		collision();
		
		for (int i = snake.size()-1; i > 0; i--) {
			snake.get(i).x = snake.get(i - 1).x;
			snake.get(i).y = snake.get(i - 1).y;
		}
		//movement and cost function
		if (dir == 1) {
			snake.get(0).y -= 20;
			
			if (apple.y <= snake.get(0).y) {
				cost += cDir;
			} else {
				cost -= wDir;
			}
		}
		if (dir == 2) {
			snake.get(0).x += 20;
			
			if (apple.x >= snake.get(0).x) {
				cost += cDir;
			} else {
				cost -= wDir;
			}
		}
		if (dir == 3) {
			snake.get(0).y += 20;
			
			if (apple.y >= snake.get(0).y) {
				cost += cDir;
			} else {
				cost -= wDir;
			}
		}
		if (dir == 4) {
			snake.get(0).x -= 20;
			
			if (apple.x <= snake.get(0).x) {
				cost += cDir;
			} else {
				cost -= wDir;
			}
		}
		if (cost > 100000 || cost < -100) {
			playing = false;
		}
	}

	//returns the info that the AI can read and calculate with, it also draws
	public int[] getData() {
		//int[] data = new int[3 + 4 + 1 + 2 + 2];
		int[] data = new int[3];
		
		//preset the values default is forward
		data[0] = 0;
		data[1] = 0;
		data[2] = 0;
		
		//apple direction
		if (dir == 1) {
			if (snake.get(0).y > apple.y) {
				data[0] = 1;
			}
			if (snake.get(0).x > apple.x) {
				data[1] = 1;
			}
			if (snake.get(0).x < apple.x) {
				data[2] = 1;
			}
		}
		else if (dir == 2) {
			if (snake.get(0).x < apple.x) {
				data[0] = 1;
			}
			if (snake.get(0).y > apple.y) {
				data[1] = 1;
			}
			if (snake.get(0).y < apple.y) {
				data[2] = 1;
			}
		}
		else if (dir == 3) {
			if (snake.get(0).y < apple.y) {
				data[0] = 1;
			}
			if (snake.get(0).x < apple.x) {
				data[1] = 1;
			}
			if (snake.get(0).x > apple.x) {
				data[2] = 1;
			}
		}
		else if (dir == 4) {
			if (snake.get(0).x > apple.x) {
				data[0] = 1;
			}
			if (snake.get(0).y < apple.y) {
				data[1] = 1;
			}
			if (snake.get(0).y > apple.y) {
				data[2] = 1;
			}
		}
		
		//calculates the distance to nearest harmful block in every direction
		int[] distance = new int[4];
		boolean breaking = false;
		//right
		for (int i = snake.get(0).x / 20; i < 32; i++) {
			for (int j = 1; j < snake.size(); j++) {
				if (i * 20 == snake.get(j).x && snake.get(j).y == snake.get(0).y) {
					distance[1] = Math.abs((snake.get(0).x / 20) - i);
					breaking = true;
					break;
				}
			}
			if (breaking) {
				breaking = false;
				break;
			}
			if (i * 20 > 580) {
				distance[1] = Math.abs((snake.get(0).x / 20) - i);
				break;
			}
		}
		//left
		for (int i = snake.get(0).x / 20; i >= -1; i--) {
			for (int j = 1; j < snake.size(); j++) {
				if (i * 20 == snake.get(j).x && snake.get(j).y == snake.get(0).y) {
					distance[3] = Math.abs((snake.get(0).x / 20) - i);
					breaking = true;
					break;
				}
			}
			if (breaking) {
				breaking = false;
				break;
			}
			if (i * 20 < 0) {
				distance[3] = Math.abs((snake.get(0).x / 20) - i);
				break;
			}
		}
		//up
		for (int i = snake.get(0).y / 20; i >= -1; i--) {
			for (int j = 1; j < snake.size(); j++) {
				if (i * 20 == snake.get(j).y && snake.get(j).x == snake.get(0).x) {
					distance[0] = Math.abs((snake.get(0).y / 20) - i);
					breaking = true;
					break;
				}
			}
			if (breaking) {
				breaking = false;
				break;
			}
			if (i * 20 < 0) {
				distance[0] = Math.abs((snake.get(0).y / 20) - i);
				break;
			}
		}
		//down
		for (int i = snake.get(0).y / 20; i < 32; i++) {
			for (int j = 1; j < snake.size(); j++) {
				if (i * 20 == snake.get(j).y && snake.get(j).x == snake.get(0).x) {
					distance[2] = Math.abs((snake.get(0).y / 20) - i);
					breaking = true;
					break;
				}
			}
			if (breaking) {
				breaking = false;
				break;
			}
			if (i * 20 > 580) {
				distance[2] = Math.abs((snake.get(0).y / 20) - i);
				break;
			}
		}
		/*
		if (dir == 1) {
			data[3] = distance[0];
			data[4] = distance[1];
			data[5] = distance[2];
			data[6] = distance[3];
		}
		if (dir == 2) {
			data[3] = distance[1];
			data[4] = distance[2];
			data[5] = distance[3];
			data[6] = distance[0];
		}
		if (dir == 3) {
			data[3] = distance[2];
			data[4] = distance[3];
			data[5] = distance[0];
			data[6] = distance[1];
		}
		if (dir == 4) {
			data[3] = distance[3];
			data[4] = distance[0];
			data[5] = distance[1];
			data[6] = distance[2];
		}
		
		//other stuff
		data[data.length - 5] = score;

		data[data.length - 4] = snake.get(0).y/20;
		data[data.length - 3] = snake.get(0).y/20;

		data[data.length - 2] = apple.x/20;
		data[data.length - 1] = apple.y/20;
		*/
		return data;
	}

	//gets the collision data and handles deaths
	void collision() {
		// If eats the apple and cost function
		if (snake.get(0).y == apple.y) {
			if (snake.get(0).x == apple.x) {
				score += 1;
				cost += 20;
				snake.add(new Tail(snake.get(snake.size()-1).x,snake.get(snake.size()-1).y));
				apple = new Apple();
			}
		}

		// If collides with wall then it will reset

		if (snake.get(0).y < 0 || snake.get(0).y > 580 || snake.get(0).x < 0 || snake.get(0).x > 580) {
			playing = false;
			//System.out.println("died by wall");
		}

		// If it runs into its own tail it will reset

		for (int i = snake.size()-1; i > 0; i--) {
			if (snake.get(i).x == snake.get(0).x && snake.get(i).y == snake.get(0).y && snake.size() > 2) {
				playing = false;
				//System.out.println("died by tail");
			}
		}
	}

	//resets the game for new gens
	public void reset() {
		apple = new Apple();
		playing = true;
		cost = 0;
		dir = 2;
		score = 0;
		snake.clear();
		snake.add(new Tail(40, 300));
	}

	//classes to easily store stuff
	class Tail {
		int x, y;

		public Tail(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	class Apple {
		int x, y;

		Apple() {
			x = 20 * ((int) Math.floor(Math.random() * 30));
			y = 20 * ((int) Math.floor(Math.random() * 30));
		}
	}
}
