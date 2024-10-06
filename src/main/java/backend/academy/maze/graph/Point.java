package backend.academy.maze.graph;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents a point in the maze with x and y coordinates.
 * This is used to identify vertices in the maze graph.
 */
@Getter
@AllArgsConstructor
public class Point {
    private final int x;  // The x-coordinate of the point
    private final int y;  // The y-coordinate of the point

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Point point = (Point) obj;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);  // Hash code based on x and y coordinates
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";  // String representation of the point
    }
}
