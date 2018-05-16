package Game;

import javafx.scene.paint.Color;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The positional system for the game. This grid will be rendered in the Canvas.
 *
 * @author Itai Keren
 * @version 19.03.2018
 */
public class Grid {
	/**
	 * The side length of each square point in the grid.
	 */
	public static final int SIZE = 10;
	public static final Color COLOR = new Color(0.1, 0.1, 0.1, 1);

	private final int cols; // The number of columns
	private final int rows; // The number of rows

	private Snake snake;
	private Food food;
	private SuperFood superFood;
	private Timer timer;

	public Grid(final double width, final double height) {
		rows = (int) width / SIZE;
		cols = (int) height / SIZE;

		// initialize the snake at the center of the screen
		snake = new Snake(this, new Point(rows / 2, cols / 2));

		// put the food at a random location
		food = new Food(getRandomPoint());
		
		superFood = null;

		timer = new Timer(true);
				
		timer.schedule(new TimerTask() {
			public void run() {
				if(superFood == null){
					superFood = new SuperFood(getRandomPoint());
				}
				else{
					superFood = null;
				}
			}
		},30000,10000);

	}

	public Point wrap(Point point) {
		int x = point.getX();
		int y = point.getY();
		if (x >= rows)
			x = 0;
		if (y >= cols)
			y = 0;
		if (x < 0)
			x = rows - 1;
		if (y < 0)
			y = cols - 1;
		return new Point(x, y);
	}

	private Point getRandomPoint() {
		Random random = new Random();
		Point point;
		do {
			point = new Point(random.nextInt(rows), random.nextInt(cols));
		} while (point.equals(snake.getHead()));
		return point;
	}

	/**
	 * This method is called in every cycle of execution.
	 */
	public void update() {
		if (food.getPoint().equals(snake.getHead())) {
			snake.extend(10);
			food.setPoint(getRandomPoint());
		} 
		else if(superFood != null && superFood.getPoint().equals(snake.getHead())) {
			snake.extend(20);
			superFood = null;
		}
		else {
			snake.move();
		}
	}

	public double getWidth() {
		return rows * SIZE;
	}

	public double getHeight() {
		return cols * SIZE;
	}

	public Snake getSnake() {
		return snake;
	}

	public Food getFood() {
		return food;
	}

	public SuperFood getSuperFood() {
		return superFood;
	}
}
