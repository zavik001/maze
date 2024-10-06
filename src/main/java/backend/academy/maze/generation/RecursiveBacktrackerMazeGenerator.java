package backend.academy.maze.generation;

import backend.academy.maze.model.Cell;
import backend.academy.maze.model.Maze;
import java.security.SecureRandom;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

/**
 * The RecursiveBacktrackerMazeGenerator class implements the {@link MazeGenerator} interface
 * to generate mazes using the recursive backtracking algorithm. The algorithm creates a maze
 * by selecting random unvisited neighbors and backtracking when no unvisited neighbors are found.
 *
 * @see MazeGenerator
 */
public class RecursiveBacktrackerMazeGenerator implements MazeGenerator {
    private static final int STEP_SIZE = 2;
    private int width;
    private int height;
    private Cell[][] cellTypes;
    private final SecureRandom random = new SecureRandom();

    /**
     * Generates a maze of the specified width and height using the recursive backtracking algorithm.
     *
     * @param width the width of the maze
     * @param height the height of the maze
     * @return a Maze object representing the generated maze
     */
    @Override
    public Maze generate(int width, int height) {
        this.width = width;
        this.height = height;
        this.cellTypes = new Cell[width][height];

        // Initialize all cells as walls
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cellTypes[x][y] = Cell.WALL;
            }
        }

        // Stack to store positions, renamed to positionDeque for clarity
        Deque<int[]> position = new ArrayDeque<>();

        // Select a random starting point
        int startX = random.nextInt(width / 2) * STEP_SIZE + 1;
        int startY = random.nextInt(height / 2) * STEP_SIZE + 1;
        position.addFirst(new int[]{startX, startY});
        cellTypes[startX][startY] = Cell.ROAD;

        // Maze generation algorithm
        while (!position.isEmpty()) {
            int[] current = position.getFirst();

            int xIndex = 0;
            int yIndex = 1;
            int x = current[xIndex];
            int y = current[yIndex];

            // Get a random unvisited neighbor
            int[] neighbor = getRandomUnvisitedNeighbor(x, y);

            if (neighbor != null) {
                int nxIndex = 0;
                int nyIndex = 1;
                int nx = neighbor[nxIndex];
                int ny = neighbor[nyIndex];

                // Connect the current cell to the neighbor
                connect(x, y, nx, ny);
                position.addFirst(new int[]{nx, ny});
            } else {
                position.removeFirst();
            }
        }

        return new Maze(cellTypes, width, height);
    }

    /**
     * Retrieves a random unvisited neighbor of the given cell.
     *
     * @param x the x-coordinate of the current cell
     * @param y the y-coordinate of the current cell
     * @return an array containing the x and y coordinates of the unvisited neighbor,
     *         or null if no unvisited neighbor is found
     */
    private int[] getRandomUnvisitedNeighbor(int x, int y) {
        // List of directions to explore
        List<Direction> directions = new ArrayList<>();
        directions.add(new Direction(-STEP_SIZE, 0));  // Left
        directions.add(new Direction(STEP_SIZE, 0));   // Right
        directions.add(new Direction(0, -STEP_SIZE));  // Up
        directions.add(new Direction(0, STEP_SIZE));   // Down

        // Shuffle directions for randomness
        Collections.shuffle(directions, random);

        // Check each direction
        for (Direction dir : directions) {
            int nx = x + dir.dx;
            int ny = y + dir.dy;
            if (nx > 0 && nx < width && ny > 0 && ny < height && cellTypes[nx][ny] == Cell.WALL) {
                return new int[]{nx, ny};
            }
        }
        return null;
    }

    /**
     * Connects two cells by creating a path between them.
     *
     * @param x1 the x-coordinate of the first cell
     * @param y1 the y-coordinate of the first cell
     * @param x2 the x-coordinate of the second cell
     * @param y2 the y-coordinate of the second cell
     */
    private void connect(int x1, int y1, int x2, int y2) {
        int mx = x1 + (x2 - x1) / 2; // Calculate the midpoint x
        int my = y1 + (y2 - y1) / 2; // Calculate the midpoint y
        cellTypes[x1][y1] = Cell.ROAD;
        cellTypes[mx][my] = Cell.ROAD;
        cellTypes[x2][y2] = Cell.ROAD;
    }

    /**
     * The Direction class stores the change in x and y coordinates for a given direction.
     */
    private static class Direction {
        final int dx;
        final int dy;

        /**
         * Constructs a Direction object representing a movement in a certain direction.
         *
         * @param dx the change in x-coordinate
         * @param dy the change in y-coordinate
         */
        Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }
    }
}
