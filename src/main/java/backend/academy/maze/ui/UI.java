package backend.academy.maze.ui;

import backend.academy.maze.graph.Point;
import backend.academy.maze.model.Cell;
import java.util.List;

/**
 * Interface for user interface implementations.
 * Defines basic methods for interacting with the user.
 */
public interface UI {

    /** Clears the console window. This method should remove all previous output from the console. */
    void clearWindow();

    /**
     * Reads a Integer value input from the user.
     *
     * @return a numeric value entered by the user.
     */
    int readNumberInput();

    /**
     * Reads a point input for the maze, including X and Y coordinates.
     *
     * @return an array of two numbers representing the coordinates.
     */
    int[] readPointInput();

    /**
     * Displays a message to the user.
     *
     * @param message the message to be displayed.
     */
    void showMessage(CharSequence message);

    /**
     * Displays a list of options or messages to the user.
     *
     * @param options the list of options to be displayed.
     */
    void showListMessage(List<String> options);

    /**
     * Displays the maze in the console.
     *
     * @param maze the array of cells representing the maze.
     * @param path the path to be displayed within the maze.
     * @param startPoint the starting point in the maze.
     * @param endPoint the ending point in the maze.
     */
    void showMaze(Cell[][] maze, List<Point> path, Point startPoint, Point endPoint);

    /**
     * Displays the maze without any additional paths or points.
     *
     * @param maze the array of cells representing the maze.
     */
    void showMaze(Cell[][] maze);

    /**
     * Reads a double value input from the user.
     *
     * @return the double value entered by the user.
     */
    double readDoubleInput();

    /** Displays the legend for the symbols used in the maze.*/
    void showLegend();
}
