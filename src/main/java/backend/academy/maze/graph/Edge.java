package backend.academy.maze.graph;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents an edge in the maze graph with a destination point and a weight.
 */
@Getter
@AllArgsConstructor
public class Edge {
    private final Point to;      // The destination point
    private final int weight;    // The weight of the edge, based on the cell type
}
