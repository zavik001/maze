package backend.academy.maze.generation;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The MazeGeneratorType enum represents different types of maze generation algorithms.
 * Each enum constant is associated with a description and a corresponding generator.
 *
 * @see PrimMazeGenerator
 * @see KruskalMazeGenerator
 * @see RecursiveBacktrackerMazeGenerator
 */
@Getter
@AllArgsConstructor
public enum MazeGeneratorType {
    PRIM("Prim's Algorithm", new PrimMazeGenerator()),
    KRUSKAL("Kruskal's Algorithm", new KruskalMazeGenerator()),
    RECURSIVE_BACKTRACKER("Recursive Backtracking", new RecursiveBacktrackerMazeGenerator());

    private final String description; // Description of the generation algorithm
    private final MazeGenerator generator; // The maze generator implementation
}
