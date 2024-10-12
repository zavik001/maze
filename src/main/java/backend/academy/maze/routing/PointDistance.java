package backend.academy.maze.routing;

import backend.academy.maze.graph.Point;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The PointDistance class represents a data structure used to store a point
 * and its associated distance in the Dijkstra algorithm.
 *
 * <p>This is used in the priority queue to track the shortest path during the search.</p>
 *
 * @see DijkstraPathfinder
 */
@Getter
@AllArgsConstructor
public class PointDistance {
    private final Point point;  // The point in the maze graph
    private final int distance; // The distance from the start point
}
