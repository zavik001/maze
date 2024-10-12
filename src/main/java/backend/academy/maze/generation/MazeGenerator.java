package backend.academy.maze.generation;

import backend.academy.maze.model.Maze;

/**
 * The MazeGenerator interface defines a contract for maze generation algorithms.
 * Implementing classes are responsible for generating mazes with specified dimensions.
 */
public interface MazeGenerator {

    /**
     * Generates a maze with the specified width and height.
     *
     * @param width the width of the maze
     * @param height the height of the maze
     * @return a Maze object representing the generated maze
     */
    Maze generate(int width, int height);
}
