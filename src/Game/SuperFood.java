package Game;

import javafx.scene.paint.Color;

/**
 * A simple class to represent food that takes up only one square.
 *
 * @author Itai Keren
 * @version 19.03.2018
 */
public class SuperFood {
    public static final Color COLOR = Color.PURPLE;
    

    private Point point;

    SuperFood(Point point) {
        this.point = point;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
}
