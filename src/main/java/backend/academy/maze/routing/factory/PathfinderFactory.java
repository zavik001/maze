package backend.academy.maze.routing.factory;

import backend.academy.maze.routing.Pathfinder;
import backend.academy.maze.routing.PathfinderType;
import lombok.experimental.UtilityClass;

/**
 * The PathfinderFactory class provides utility methods for selecting a pathfinding
 * algorithm based on user input. It serves as a factory that allows users to choose
 * between different algorithms at runtime.
 *
 * This class follows the Factory pattern, making it easier to add new pathfinding
 * algorithms in the future and maintain flexibility in the maze-solving logic.
 *
 * @see Pathfinder
 * @see PathfinderType
 */
@UtilityClass
public class PathfinderFactory {

    /**
     * Returns the appropriate Pathfinder implementation based on the user's choice.
     *
     * @param choice the user input (e.g., 1 for Dijkstra, 2 for A*)
     * @return the selected Pathfinder instance
     * @throws IllegalArgumentException if the choice is out of range
     */
    public static Pathfinder getPathfinder(int choice) {
        PathfinderType[] types = PathfinderType.values();
        if (choice < 1 || choice > types.length) {
            throw new IllegalArgumentException("Invalid pathfinder choice: " + choice);
        }
        return types[choice - 1].pathfinder();
    }

    /**
     * Returns a formatted string with the list of available pathfinding algorithms for display in the menu.
     *
     * @return a string listing the available pathfinding algorithms
     */
    public static String getPathfinderOptions() {
        StringBuilder options = new StringBuilder("Choose a pathfinding algorithm:\n");
        PathfinderType[] types = PathfinderType.values();
        for (int i = 0; i < types.length; i++) {
            options.append(i + 1).append(". ").append(types[i].description()).append('\n');
        }
        return options.toString();
    }
}
