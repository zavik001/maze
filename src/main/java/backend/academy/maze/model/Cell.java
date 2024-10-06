package backend.academy.maze.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representing different types of cells in the maze.
 * Each cell has a value that represents its weight in a graph
 * and a symbol for visual representation in the maze.
 */
@Getter
@AllArgsConstructor
public enum Cell {
    WALL(-1, '#'),             // Represents an impassable wall
    ROAD(2, '.'),        // Represents a normal road cell
    SWAMP(3, '~'),       // Represents a swamp cell, harder to traverse
    ACCELERATED_PATH(1, '>');  // Represents a fast track cell
    // New cell types can be added here without requiring code modifications elsewhere

    private final int value;
    private final char symbol;
}
