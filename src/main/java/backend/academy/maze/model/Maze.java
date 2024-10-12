package backend.academy.maze.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Class representing a maze consisting of a grid of cells.
 * The maze is defined by its width, height, and the grid of cells.
 */
@Getter
@AllArgsConstructor
public class Maze {
    private final Cell[][] maze;  // A 2D array representing the maze layout
    private final int width;      // The width of the maze
    private final int height;     // The height of the maze
}
