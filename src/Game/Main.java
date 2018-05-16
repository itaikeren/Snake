package Game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * This is the place where the threads are dispatched.
 *
 * @author Itai Keren
 * @version 19.03.2018
 */
public class Main extends Application {

	private static final int WIDTH = 500;
	private static final int HEIGHT = 500;

	private GameLoop loop;
	private Grid grid;
	private GraphicsContext context;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		StackPane root = new StackPane();
		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		context = canvas.getGraphicsContext2D();

		canvas.setFocusTraversable(true);
		canvas.setOnKeyPressed(e -> {
			Snake snake = grid.getSnake();
			if (loop.isKeyPressed()) {
				return;
			}
			switch (e.getCode()) {
			case UP:
				snake.setUp();
				break;
			case DOWN:
				snake.setDown();
				break;
			case LEFT:
				snake.setLeft();
				break;
			case RIGHT:
				snake.setRight();
				break;
			case P:
				if (!loop.isPaused()) {
					loop.pause();
				} else {
					loop.resume();
				}
				break;
			case ENTER:
				if (loop.isPaused()) {
					reset();
					(new Thread(loop)).start();
				}
				break;
			case ESCAPE:
				System.exit(0);
				break;
			default:
				break;
			}
		});

		reset();

		root.getChildren().add(canvas);

		Scene scene = new Scene(root);

		primaryStage.setResizable(false);
		primaryStage.setTitle("Snake");
		primaryStage.setOnCloseRequest(e -> System.exit(0));
		primaryStage.setScene(scene);
		primaryStage.show();

		(new Thread(loop)).start();
	}

	private void reset() {
		Snake.snakeColor = Color.GREEN;
		grid = new Grid(WIDTH, HEIGHT);
		loop = new GameLoop(grid, context);
		Painter.paint(grid, context);
	}
}