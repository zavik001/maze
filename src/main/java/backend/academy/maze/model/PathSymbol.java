package backend.academy.maze.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representing symbols used to display pathfinding results in the maze.
 * These symbols include the path itself, the start point, and the end point.
 */
@Getter
@AllArgsConstructor
public enum PathSymbol {
    PATH('o'),    // Symbol used to represent the path
    START('A'),   // Symbol representing the start point of the path
    END('B');     // Symbol representing the end point of the path

    private final char symbol;
}
