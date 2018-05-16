package Game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static Game.Grid.SIZE;

/**
 * @author Itai Keren
 * @version 19.03.2018
 */
public class Painter {


    public static void paint(Grid grid, GraphicsContext gc) {
        gc.setFill(Grid.COLOR);
        gc.fillRect(0, 0, grid.getWidth(), grid.getHeight());

        // Now the Food
        gc.setFill(Food.COLOR);
        paintPoint(grid.getFood().getPoint(), gc);
        
        // Now the SuperFood
        if(grid.getSuperFood() != null){
        	gc.setFill(SuperFood.COLOR);
            paintPoint(grid.getSuperFood().getPoint(), gc);	
        }
        
        // Now the snake
        Snake snake = grid.getSnake();
        gc.setFill(Snake.snakeColor);
        snake.getPoints().forEach(point -> paintPoint(point, gc));
        if (!snake.isSafe()) {
            gc.setFill(Snake.DEAD);
            paintPoint(snake.getHead(), gc);
        }

        // The score
        gc.setFill(Color.BEIGE);
        gc.fillText("Score : " + snake.getScore() + " | High Score : " + snake.getHighScore(), 10, 490);
        
     // Game info
        gc.setFill(Color.BEIGE);
        gc.fillText("Press P to pause/resume", 330, 15);
    }

    private static void paintPoint(Point point, GraphicsContext gc) {
        gc.fillRect(point.getX() * SIZE, point.getY() * SIZE, SIZE, SIZE);
    }

    public static void paintResetMessage(GraphicsContext gc) {
        gc.setFill(Color.AQUAMARINE);
        gc.fillText("Hit ENTER to reset.", 10, 20);
    }
}
