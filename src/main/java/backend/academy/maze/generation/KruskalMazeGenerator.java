package backend.academy.maze.generation;

import backend.academy.maze.model.Cell;
import backend.academy.maze.model.Maze;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The KruskalMazeGenerator class implements the {@link MazeGenerator} interface
 * to generate mazes using Kruskal's algorithm. This algorithm is a minimum spanning tree
 * algorithm adapted for maze generation.
 *
 * @see MazeGenerator
 */
public class KruskalMazeGenerator implements MazeGenerator {
    private static final int STEP_SIZE = 2;
    private Cell[][] cellTypes;

    /**
     * Generates a maze of the specified width and height using Kruskal's algorithm.
     *
     * @param width the width of the maze
     * @param height the height of the maze
     * @return a Maze object representing the generated maze
     */
    @Override
    public Maze generate(int width, int height) {
        this.cellTypes = new Cell[width][height];

        // Fill the maze grid with walls
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cellTypes[x][y] = Cell.WALL;
            }
        }

        List<Edge> edges = new ArrayList<>();
        UnionFind uf = new UnionFind(width * height);

        // Initialize edges for the grid
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x % STEP_SIZE != 0 && y % STEP_SIZE != 0) {
                    if (x + STEP_SIZE < width) {
                        edges.add(new Edge(x, y, x + STEP_SIZE, y));
                    }
                    if (y + STEP_SIZE < height) {
                        edges.add(new Edge(x, y, x, y + STEP_SIZE));
                    }
                }
            }
        }

        // Randomize edge order
        Collections.shuffle(edges);

        // Generate the maze using Kruskal's algorithm
        for (Edge edge : edges) {
            int id1 = edge.y1 * width + edge.x1;
            int id2 = edge.y2 * width + edge.x2;

            if (uf.find(id1) != uf.find(id2)) {
                uf.union(id1, id2);
                connect(edge.x1, edge.y1, edge.x2, edge.y2);
            }
        }

        return new Maze(cellTypes, width, height);
    }

    /**
     * Connects two cells in the maze by clearing a path between them.
     *
     * @param x1 the x-coordinate of the first cell
     * @param y1 the y-coordinate of the first cell
     * @param x2 the x-coordinate of the second cell
     * @param y2 the y-coordinate of the second cell
     */
    private void connect(int x1, int y1, int x2, int y2) {
        // Prevent overflow when calculating the midpoint
        int mx = x1 / 2 + x2 / 2 + (x1 % 2 + x2 % 2) / 2;
        int my = y1 / 2 + y2 / 2 + (y1 % 2 + y2 % 2) / 2;

        cellTypes[x1][y1] = Cell.ROAD;
        cellTypes[mx][my] = Cell.ROAD;
        cellTypes[x2][y2] = Cell.ROAD;
    }

    /**
     * Helper class for union-find operations to manage connected components in the maze.
     */
    private static class UnionFind {
        private final int[] parent;

        /**
         * Initializes a UnionFind data structure with n elements.
         *
         * @param n the number of elements
         */
        UnionFind(int n) {
            parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        /**
         * Finds the root of the set containing the element x.
         *
         * @param x the element to find
         * @return the root of the set containing x
         */
        public int find(int x) {
            if (parent[x] == x) {
                return x;
            }
            int root = find(parent[x]);
            parent[x] = root;
            return root;
        }

        /**
         * Unions two sets containing the elements x and y.
         *
         * @param x the first element
         * @param y the second element
         */
        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            parent[rootX] = rootY;
        }
    }

    /**
     * Represents an edge in the maze. An edge connects two cells in the maze grid.
     */
    private static class Edge {
        public final int x1;
        public final int y1;
        public final int x2;
        public final int y2;

        /**
         * Constructs an Edge object connecting two cells in the maze.
         *
         * @param x1 the x-coordinate of the first cell
         * @param y1 the y-coordinate of the first cell
         * @param x2 the x-coordinate of the second cell
         * @param y2 the y-coordinate of the second cell
         */
        Edge(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
    }
}
