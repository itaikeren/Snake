package Game;

import javafx.scene.canvas.GraphicsContext;

/**
 * @author Itai Keren
 * @version 19.03.2018
 */
public class GameLoop implements Runnable {
	private final Grid grid;
	private final GraphicsContext context;
	private float interval;
	private boolean running;
	private boolean paused;
	private boolean keyIsPressed;

	public GameLoop(final Grid grid, final GraphicsContext context) {
		this.grid = grid;
		this.context = context;
		interval = 1000.0f / 30; // 1000 ms in a second
		running = true;
		paused = false;
		keyIsPressed = false;
	}

	@Override
	public void run() {
		while (running) {
			// Time the update and paint calls
			float time = System.currentTimeMillis();

			if (!paused) {
				keyIsPressed = false;
				grid.update();
				Painter.paint(grid, context);
			}
			if (!grid.getSnake().isSafe()) {
				pause();
				Painter.paintResetMessage(context);
				break;
			}

			time = System.currentTimeMillis() - time;
			// Adjust the timing correctly
			if (time < interval) {
				try {
					Thread.sleep((long) (interval - time));
				} catch (InterruptedException ignore) {
				}
			}
		}
	}

	public void stop() {
		running = false;
	}

	public boolean isKeyPressed() {
		return keyIsPressed;
	}

	public void setKeyPressed() {
		keyIsPressed = true;
	}

	public void resume() {
		paused = false;
	}

	public void pause() {
		paused = true;
	}

	public boolean isPaused() {
		return paused;
	}
}