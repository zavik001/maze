package backend.academy.maze.generation;

import backend.academy.maze.model.Cell;
import backend.academy.maze.model.Maze;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The PrimMazeGenerator class implements the {@link MazeGenerator} interface
 * to generate mazes using Prim's algorithm. This algorithm builds the maze by
 * gradually expanding from a random starting point.
 *
 * @see MazeGenerator
 */
public class PrimMazeGenerator implements MazeGenerator {
    private int width;
    private int height;
    private Cell[][] cellTypes;
    private final SecureRandom random = new SecureRandom(); // SecureRandom for better randomness

    /**
     * Generates a maze of the specified width and height using Prim's algorithm.
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

        // Fill the maze with walls
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cellTypes[x][y] = Cell.WALL;
            }
        }

        Set<Position> frontier = new HashSet<>(); // Set of frontier cells

        // Select a random starting cell
        int startX = random.nextInt(width);
        int startY = random.nextInt(height);
        cellTypes[startX][startY] = Cell.ROAD;

        // Add frontier cells around the starting cell
        frontier.addAll(getFrontier(startX, startY));

        // Generate the maze
        while (!frontier.isEmpty()) {
            Position cell = getRandomCell(frontier);
            frontier.remove(cell);
            int cx = cell.x;
            int cy = cell.y;

            Set<Position> neighbors = getNeighbors(cx, cy);
            if (!neighbors.isEmpty()) {
                Position neighbor = getRandomCell(neighbors);
                connect(cx, cy, neighbor.x, neighbor.y);
            }
            frontier.addAll(getFrontier(cx, cy));
        }

        return new Maze(cellTypes, width, height);
    }

    /**
     * Retrieves the frontier cells around a given cell.
     *
     * @param x the x-coordinate of the cell
     * @param y the y-coordinate of the cell
     * @return a set of frontier positions
     */
    private Set<Position> getFrontier(int x, int y) {
        Set<Position> f = new HashSet<>();
        if (x > 1 && cellTypes[x - 2][y] == Cell.WALL) {
            f.add(new Position(x - 2, y));
        }
        if (x + 2 < width && cellTypes[x + 2][y] == Cell.WALL) {
            f.add(new Position(x + 2, y));
        }
        if (y > 1 && cellTypes[x][y - 2] == Cell.WALL) {
            f.add(new Position(x, y - 2));
        }
        if (y + 2 < height && cellTypes[x][y + 2] == Cell.WALL) {
            f.add(new Position(x, y + 2));
        }
        return f;
    }

    /**
     * Retrieves the neighboring cells that are already part of the maze.
     *
     * @param x the x-coordinate of the cell
     * @param y the y-coordinate of the cell
     * @return a set of neighboring positions
     */
    private Set<Position> getNeighbors(int x, int y) {
        Set<Position> neighbors = new HashSet<>();
        if (x > 1 && cellTypes[x - 2][y] != Cell.WALL) {
            neighbors.add(new Position(x - 2, y));
        }
        if (x + 2 < width && cellTypes[x + 2][y] != Cell.WALL) {
            neighbors.add(new Position(x + 2, y));
        }
        if (y > 1 && cellTypes[x][y - 2] != Cell.WALL) {
            neighbors.add(new Position(x, y - 2));
        }
        if (y + 2 < height && cellTypes[x][y + 2] != Cell.WALL) {
            neighbors.add(new Position(x, y + 2));
        }
        return neighbors;
    }

    /**
     * Connects two cells in the maze by creating a path between them.
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
     * Selects a random cell from a set of positions.
     *
     * @param cells the set of positions
     * @return a randomly selected position
     */
    private Position getRandomCell(Set<Position> cells) {
        int index = random.nextInt(cells.size());
        List<Position> cellList = new ArrayList<>(cells); // Convert Set to List for random selection
        return cellList.get(index);
    }

    /**
     * A helper class representing a position (x, y) in the maze.
     */
    private static class Position {
        public final int x;
        public final int y;

        /**
         * Constructs a Position object with the given coordinates.
         *
         * @param x the x-coordinate of the position
         * @param y the y-coordinate of the position
         */
        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Position position = (Position) obj;
            return x == position.x && y == position.y;
        }

        @Override
        public int hashCode() {
            return 31 * x + y;
        }
    }
}
