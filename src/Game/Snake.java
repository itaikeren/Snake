package Game;

import javafx.scene.paint.Color;

import java.io.File;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * The class that encapsulates the star of the show. Basically it stores it's current
 * state and stuff like position, velocity, length and so on and so forth.
 *
 * @author Itai Keren
 * @version 19.03.2018
 */
public class Snake {
    public static Color snakeColor = Color.GREEN;
    public static final Color DEAD = Color.PURPLE;
    private Grid grid;
    private int length;
    private boolean safe;
    private List<Point> points;
    private Point head;
    private int xVelocity;
    private int yVelocity;
    private int score;
    private int highScore;
    private Color[] snakeColors = {Color.YELLOW, Color.AQUA, Color.CORAL, Color.ALICEBLUE};

    /**
     * The constructor the snake. It takes the initial point, for the head and the Grid
     * that it lives (and dies) in.
     *
     * @param initialPoint The {@link Point} to the put the snake's head on.
     */
    public Snake(Grid grid, Point initialPoint) {
    	length = 1;
        points = new LinkedList<>();
        points.add(initialPoint);
        head = initialPoint;
        safe = true;
        this.grid = grid;
        xVelocity = 0;
        yVelocity = 0;
        score = 0;
       	setHighScore();
    }
    
    /**
     *
     * @param get the highest score.
     */
    private void setHighScore() {
        try{
        	File hs = new File("HighScore.txt");
        	if(hs.exists()){
        		Scanner s = new Scanner(hs);
        		this.highScore = s.nextInt();
        		s.close();
        	}
        	else{
        		this.highScore = 0;
        	}
        }
        catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }
    
    /**
     * This method is called after food has been consumed. It increases the length of the
     * snake by one and update the high score if needed.
     *
     * @param point The Point where the food was and the new location for the head and update high score.
     */
    private void growTo(Point point, int score) {
        length++;
        checkAndAdd(point);
        this.score += score;
        if(this.score > highScore){
        	try{
            	File hs = new File("HighScore.txt");
            	PrintWriter pw = new PrintWriter(hs);
            	pw.print(this.score);
            	pw.close();
            	}
        	catch (Exception e) {
        		System.out.println(e.getMessage());
			}
        }
        if(this.score % 100 == 0){
        	Random ran = new Random();
        	snakeColor = snakeColors[ran.nextInt(snakeColors.length)];
        }
    }

    /**
     * Called during every update. It gets rid of the oldest point and adds the given point.
     *
     * @param point The new Point to add.
     */
    private void shiftTo(Point point) {
        // The head goes to the new location
        checkAndAdd(point);
        // The last/oldest position is dropped
        points.remove(0);
    }

    /**
     * Checks for an intersection and marks the "safe" flag accordingly.
     *
     * @param point The new Point to move to.
     */
    private void checkAndAdd(Point point) {
        point = grid.wrap(point);
        safe &= !points.contains(point);
        points.add(point);
        head = point;
    }

    /**
     * @return The points occupied by the snake.
     */
    public List<Point> getPoints() {
        return points;
    }
    
    /**
     * @return The high score of the game.
     */
    public int getHighScore() {
        return highScore;
    }
    
    /**
     * @return The score of the current game.
     */
    public int getScore() {
        return score;
    }

    /**
     * @return {@code true} if the Snake hasn't run into itself yet.
     */
    public boolean isSafe() {
        return safe || length == 1;
    }

    /**
     * @return The location of the head of the Snake.
     */
    public Point getHead() {
        return head;
    }

    private boolean isStill() {
        return xVelocity == 0 & yVelocity == 0;
    }

    /**
     * Make the snake move one square in it's current direction.
     */
    public void move() {
        if (!isStill()) {
            shiftTo(head.translate(xVelocity, yVelocity));
        }
    }

    /**
     * Make the snake extend/grow to the square where it's headed.
     */
    public void extend(int score) {
        if (!isStill()) {
            growTo(head.translate(xVelocity, yVelocity), score);
        }
    }

    public void setUp() {
        if (yVelocity == 1 && length > 1) return;
        xVelocity = 0;
        yVelocity = -1;
    }

    public void setDown() {
        if (yVelocity == -1 && length > 1) return;
        xVelocity = 0;
        yVelocity = 1;
    }

    public void setLeft() {
        if (xVelocity == 1 && length > 1) return;
        xVelocity = -1;
        yVelocity = 0;
    }

    public void setRight() {
        if (xVelocity == -1 && length > 1) return;
        xVelocity = 1;
        yVelocity = 0;
    }
}
