package backend.academy.maze.routing;

import backend.academy.maze.graph.MazeGraph;
import backend.academy.maze.graph.Point;
import backend.academy.maze.model.Cell;
import backend.academy.maze.model.Maze;
import backend.academy.maze.routing.DijkstraPathfinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DijkstraPathfinderTest {

    private MazeGraph mazeGraph;
    private DijkstraPathfinder pathfinder;
    
    @BeforeEach
    void setUp() {
        Cell[][] cells = {
            {Cell.ROAD, Cell.WALL, Cell.ROAD, Cell.ROAD},
            {Cell.WALL, Cell.WALL, Cell.ROAD, Cell.ROAD},
            {Cell.WALL, Cell.ROAD, Cell.ROAD, Cell.SWAMP},
            {Cell.ACCELERATED_PATH, Cell.WALL, Cell.WALL, Cell.ROAD}
        };
        Maze maze = new Maze(cells, 4, 4);
        
        mazeGraph = new MazeGraph(maze);
        mazeGraph.buildGraph();
        
        pathfinder = new DijkstraPathfinder();
    }

    @Test
    void givenValidPath_whenFindingPath_thenReturnCorrectPath() {
        Point start = new Point(2, 2);
        Point end = new Point(0, 3);

        List<Point> path = pathfinder.findPath(mazeGraph, start, end);

        assertThat(path)
            .hasSize(4)
            .containsExactly(
                new Point(2, 2),
                new Point(1, 2),
                new Point(0, 2),
                new Point(0, 3)
            );
    }

    @Test
    void givenIsolatedRoad_whenFindingPath_thenReturnEmptyList() {
        Point isolatedStart = new Point(0, 0);
        Point end = new Point(2, 1);

        List<Point> path = pathfinder.findPath(mazeGraph, isolatedStart, end);

        assertThat(path).isEmpty();
    }

    @Test
    void givenSameStartAndEnd_whenFindingPath_thenReturnSinglePoint() {
        Point point = new Point(2, 1);

        List<Point> path = pathfinder.findPath(mazeGraph, point, point);

        assertThat(path)
            .hasSize(1)
            .containsExactly(point);
    }

    @Test
    void givenMazeWithUnreachableDestination_whenFindingPath_thenReturnEmptyList() {
        Point start = new Point(3, 3);
        Point blockedEnd = new Point(0, 0);

        List<Point> path = pathfinder.findPath(mazeGraph, start, blockedEnd);

        assertThat(path).isEmpty();
    }

    @Test
    void givenMazeWithOnlyWalls_whenFindingPath_thenReturnEmptyList() {
        Cell[][] wallMazeCells = {
            { Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL },
            { Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL },
            { Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL },
            { Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL }
        };
        Maze wallMaze = new Maze(wallMazeCells, 4, 4);
        MazeGraph wallMazeGraph = new MazeGraph(wallMaze);
        wallMazeGraph.buildGraph();

        Point start = new Point(0, 0);
        Point end = new Point(3, 3);

        List<Point> path = pathfinder.findPath(wallMazeGraph, start, end);

        assertThat(path).isEmpty();
    }
}
