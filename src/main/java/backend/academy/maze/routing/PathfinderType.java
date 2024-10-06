package backend.academy.maze.routing;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The PathfinderType enum represents different pathfinding algorithms available
 * in the maze application. Each enum value includes a description for display
 * purposes and an instance of the corresponding Pathfinder implementation.
 * This enum is used to offer the user a selection of pathfinding algorithms,
 * each tailored for specific maze-solving needs.
 *
 * @see DijkstraPathfinder
 * @see AStarPathfinder
 */
@Getter
@AllArgsConstructor
public enum PathfinderType {
    DIJKSTRA("Dijkstra's Algorithm", new DijkstraPathfinder()),
    ASTAR("A*", new AStarPathfinder());

    private final String description; // Algorithm description for the menu
    private final Pathfinder pathfinder; // The algorithm instance
}
