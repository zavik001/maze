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
import lombok.extern.slf4j.Slf4j;

/**
 * The DijkstraPathfinder class implements Dijkstra's algorithm to find the shortest path
 * between two points in a weighted graph (maze). This algorithm is ideal for finding
 * paths in mazes with different types of cells and edge weights, ensuring the minimum
 * path cost is found.
 *
 * Handles weighted graphs and avoids walls or impassable cells.
 * Uses a priority queue to efficiently find the shortest path.
 * Reconstructs the path once the destination is reached.
 *
 * @see Pathfinder
 * @see MazeGraph
 */
@Slf4j
public class DijkstraPathfinder implements Pathfinder {

    // Optimal collection load factor
    public static final double NUM = 0.75;

    /**
     * Finds the shortest path between two points in the maze graph using Dijkstra's algorithm.
     *
     * @param graph the maze graph representing the maze structure
     * @param start the starting point of the path
     * @param end the destination point of the path
     * @return a list of points representing the shortest path from start to end,
     *         or an empty list if no path is found
     */
    @Override
    public List<Point> findPath(MazeGraph graph, Point start, Point end) {
        int estimatedSize = graph.graph().size();

        // Initializing collections with optimal size
        Map<Point, Integer> distances = new HashMap<>((int) (estimatedSize / NUM) + 1);
        Map<Point, Point> previous = new HashMap<>((int) (estimatedSize / NUM) + 1);
        Set<Point> visited = new HashSet<>((int) (estimatedSize / NUM) + 1);

        PriorityQueue<PointDistance> queue = new PriorityQueue<>(
            Math.max(1, estimatedSize), Comparator.comparingInt(PointDistance::distance)
        );

        distances.put(start, 0);
        queue.add(new PointDistance(start, 0));

        while (!queue.isEmpty()) {
            PointDistance current = queue.poll();
            Point currentPoint = current.point();

            if (!visited.add(currentPoint)) {
                log.info("Already visited: {}", currentPoint);
                continue;
            }

            if (currentPoint.equals(end)) {
                return buildPath(previous, start, end); // Reconstructing the path
            }

            List<Edge> edges = graph.graph().get(currentPoint);
            if (edges == null) {
                continue;
            }

            for (Edge edge : edges) {
                Point neighbor = edge.to();
                int newDist = distances.get(currentPoint) + edge.weight();

                if (newDist < distances.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    distances.put(neighbor, newDist);
                    previous.put(neighbor, currentPoint);
                    queue.add(new PointDistance(neighbor, newDist));
                }
            }
        }

        return Collections.emptyList();
    }

    /**
     * Builds the path by backtracking from the destination to the start point.
     *
     * @param previous a map of points leading to each other
     * @param start the starting point of the path
     * @param end the end point of the path
     * @return a list of points representing the path, or an empty list if the path cannot be reconstructed
     */
    private List<Point> buildPath(Map<Point, Point> previous, Point start, Point end) {
        List<Point> path = new ArrayList<>(previous.size());  // Pre-allocate size
        Point current = end;

        while (current != null && !current.equals(start)) {
            path.add(current);
            current = previous.get(current);
        }

        if (current == null) {
            log.error("Failed to reconstruct the path.");
            return Collections.emptyList();
        }

        path.add(start);
        Collections.reverse(path); // Reverse to get the correct order
        return path;
    }
}
