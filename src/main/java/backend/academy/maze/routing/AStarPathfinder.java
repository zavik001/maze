package backend.academy.maze.routing;

import backend.academy.maze.graph.Edge;
import backend.academy.maze.graph.MazeGraph;
import backend.academy.maze.graph.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * The AStarPathfinder class implements the A* algorithm to find the shortest path
 * between two points in a weighted graph (maze). This algorithm uses a heuristic
 * to guide the search, typically improving performance by estimating the distance
 * to the goal.
 *
 * Uses both a cost-so-far (gScore) and an estimated total cost (fScore).
 * Prioritizes paths based on the sum of actual and heuristic costs.
 * Supports weighted graphs, making it suitable for complex mazes with
 * varying edge costs.
 *
 * @see Pathfinder
 * @see MazeGraph
 */
public class AStarPathfinder implements Pathfinder {

    public static final double NUM = 0.75;

    /**
     * Finds the shortest path between two points in the maze graph using the A* algorithm.
     *
     * @param graph the maze graph representing the maze structure
     * @param start the starting point of the path
     * @param end the destination point of the path
     * @return a list of points representing the shortest path from start to end,
     *         or an empty list if no path is found
     */
    @Override
    public List<Point> findPath(MazeGraph graph, Point start, Point end) {
        int estimatedSize = graph.graph().size();  // Estimated graph size

        // Initialize gScore and fScore maps, and other collections
        Map<Point, Integer> gScore = new HashMap<>((int) (estimatedSize / NUM) + 1);
        Map<Point, Integer> fScore = new HashMap<>((int) (estimatedSize / NUM) + 1);
        Map<Point, Point> previous = new HashMap<>((int) (estimatedSize / NUM) + 1);
        Set<Point> closedSet = new HashSet<>((int) (estimatedSize / NUM) + 1);
        PriorityQueue<PointDistance> openQueue = new PriorityQueue<>(
            Math.max(1, estimatedSize), Comparator.comparingInt(PointDistance::distance)
        );

        // Initialize the starting point
        gScore.put(start, 0);
        fScore.put(start, heuristic(start, end));
        openQueue.add(new PointDistance(start, fScore.get(start)));

        // Main A* loop
        while (!openQueue.isEmpty()) {
            Point current = openQueue.poll().point();

            if (current.equals(end)) {
                return buildPath(previous, start, end);
            }

            closedSet.add(current);

            // Get neighbors
            List<Edge> edges = graph.graph().get(current);
            if (edges == null) {
                continue;
            }

            // Process neighbors
            for (Edge edge : edges) {
                Point neighbor = edge.to();

                if (closedSet.contains(neighbor)) {
                    continue;
                }

                int tentativeGScore = gScore.getOrDefault(current, Integer.MAX_VALUE) + edge.weight();

                if (tentativeGScore < gScore.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    previous.put(neighbor, current);
                    gScore.put(neighbor, tentativeGScore);
                    fScore.put(neighbor, tentativeGScore + heuristic(neighbor, end));

                    openQueue.add(new PointDistance(neighbor, fScore.get(neighbor)));
                }
            }
        }

        return Collections.emptyList(); // Return empty list if no path is found
    }

    /**
     * Heuristic function using Manhattan distance. This helps guide the A* search
     * by estimating the cost to the destination.
     *
     * @param a the starting point
     * @param b the destination point
     * @return the estimated cost (heuristic)
     */
    private int heuristic(Point a, Point b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

    /**
     * Builds the path by backtracking from the destination to the start point.
     *
     * @param previous a map of points leading to each other
     * @param start the starting point of the path
     * @param end the destination point of the path
     * @return a list of points representing the path, or an empty list if the path cannot be reconstructed
     */
    private List<Point> buildPath(Map<Point, Point> previous, Point start, Point end) {
        List<Point> path = new ArrayList<>(previous.size());
        Point current = end;

        while (!current.equals(start)) {
            path.add(current);
            current = previous.get(current);
        }
        path.add(start);
        Collections.reverse(path); // Reverse to get the correct order
        return path;
    }
}
