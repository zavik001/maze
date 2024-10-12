package backend.academy.maze.routing;

import backend.academy.maze.graph.MazeGraph;
import backend.academy.maze.graph.Point;
import java.util.List;

/**
 * The Pathfinder interface defines a contract for pathfinding algorithms
 * to implement. It is used to find the shortest or optimal path between two
 * points in a maze graph.
 *
 * @see DijkstraPathfinder
 */
public interface Pathfinder {

    /**
     * Finds the path between two points in a maze graph.
     *
     * @param graph the maze graph representing the maze structure
     * @param start the starting point of the path
     * @param end the destination point of the path
     * @return a list of points representing the path from start to end,
     *         or an empty list if no path is found
     */
    List<Point> findPath(MazeGraph graph, Point start, Point end);
}
