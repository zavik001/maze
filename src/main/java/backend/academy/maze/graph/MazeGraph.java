package backend.academy.maze.graph;

import backend.academy.maze.model.Cell;
import backend.academy.maze.model.Maze;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents a graph built from a maze where each cell is a vertex and
 * each valid movement between cells represents an edge with a specific weight.
 */
@Getter
@AllArgsConstructor
public class MazeGraph {
    private final Map<Point, List<Edge>> graph = new HashMap<>();  // The graph representing the maze
    private final Maze maze;                                       // The maze used to build the graph

    /**
     * Builds the graph by connecting adjacent cells in the maze
     * that are not walls. Each connection has a weight based on the cell type.
     */
    public void buildGraph() {
        int width = maze.width();
        int height = maze.height();
        Cell[][] cellTypes = maze.maze();

        // Build the graph excluding cells of type WALL
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (cellTypes[x][y] != Cell.WALL) {
                    Point current = new Point(x, y);
                    graph.putIfAbsent(current, new ArrayList<>());

                    // Add neighboring edges (up, down, left, right)
                    addEdgeIfValid(current, x - 1, y, cellTypes); // up
                    addEdgeIfValid(current, x + 1, y, cellTypes); // down
                    addEdgeIfValid(current, x, y - 1, cellTypes); // left
                    addEdgeIfValid(current, x, y + 1, cellTypes); // right
                }
            }
        }
    }

    /**
     * Adds an edge from the current cell to a neighboring cell if it is valid
     * (within bounds and not a wall). The weight is determined by the type of the cell.
     */
    private void addEdgeIfValid(Point current, int nx, int ny, Cell[][] cellTypes) {
        if (nx >= 0 && ny >= 0 && nx < maze.width() && ny < maze.height() && cellTypes[nx][ny] != Cell.WALL) {
            Point neighbor = new Point(nx, ny);
            int weight = cellTypes[nx][ny].value();  // Use the weight from the cell type
            graph.get(current).add(new Edge(neighbor, weight));
        }
    }
}
