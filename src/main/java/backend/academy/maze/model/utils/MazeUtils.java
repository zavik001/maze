package backend.academy.maze.model.utils;

import backend.academy.maze.model.Cell;
import backend.academy.maze.model.Maze;
import java.security.SecureRandom;
import java.util.Arrays;
import lombok.experimental.UtilityClass;

/**
 * Utility class providing methods for manipulating mazes, such as adding special cells.
 * The special cells are distributed across the maze based on a percentage of total road cells.
 */
@UtilityClass
public class MazeUtils {

    /**
     * Adds special cells to the maze based on the specified percentage of road cells.
     *
     * @param maze the maze object to modify
     * @param percentage the percentage of road cells to replace with special cells
     */
    public static void addSpecialCells(Maze maze, double percentage) {
        int width = maze.width();
        int height = maze.height();
        Cell[][] cellTypes = maze.maze();

        int totalRoadCells = 0;

        // Count the number of road cells
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (cellTypes[x][y] == Cell.ROAD) {
                    totalRoadCells++;
                }
            }
        }

        // Calculate the number of special cells to add
        int specialCellsCount = (int) (totalRoadCells * percentage);

        // Get special cell types (excluding WALL and ROAD)
        Cell[] specialCellTypes = getSpecialCellTypes();

        // Distribute special cells evenly among the types
        int countPerType = specialCellsCount / specialCellTypes.length;

        SecureRandom random = new SecureRandom();
        for (Cell cellType : specialCellTypes) {
            for (int i = 0; i < countPerType; i++) {
                replaceRandomCell(cellTypes, width, height, cellType, random);
            }
        }
    }

    /**
     * Replaces a random road cell in the maze with the specified special cell type.
     *
     * @param cellTypes the grid of cells in the maze
     * @param width the width of the maze
     * @param height the height of the maze
     * @param newCellType the type of special cell to add
     * @param random the random number generator to use
     */
    private static void replaceRandomCell(
            Cell[][] cellTypes, int width, int height, Cell newCellType, SecureRandom random) {
        int x;
        int y;
        do {
            x = random.nextInt(width);
            y = random.nextInt(height);
        } while (cellTypes[x][y] != Cell.ROAD);  // Only replace road cells

        cellTypes[x][y] = newCellType;  // Replace with the new cell type
    }

    /**
     * Returns an array of special cell types (excluding WALL and ROAD).
     *
     * @return an array of special cell types
     */
    private static Cell[] getSpecialCellTypes() {
        return Arrays.stream(Cell.values())
                .filter(cell -> cell != Cell.WALL && cell != Cell.ROAD)
                .toArray(Cell[]::new);
    }
}
