package backend.academy.maze.generation.factory;

import backend.academy.maze.generation.MazeGenerator;
import backend.academy.maze.generation.MazeGeneratorType;
import lombok.experimental.UtilityClass;

/**
 * The MazeGeneratorFactory class provides methods to create {@link MazeGenerator}
 * instances based on the user's selection.
 */
@UtilityClass
public class MazeGeneratorFactory {

    /**
     * Returns the appropriate {@link MazeGenerator} based on the user's selection.
     *
     * @param choice the user's choice of maze generation algorithm
     * @return the selected {@link MazeGenerator} instance
     * @throws IllegalArgumentException if the choice is invalid
     */
    public static MazeGenerator getGenerator(int choice) {
        MazeGeneratorType[] types = MazeGeneratorType.values();
        if (choice < 1 || choice > types.length) {
            throw new IllegalArgumentException("Invalid generator selection: " + choice);
        }
        return types[choice - 1].generator();
    }

    /**
     * Returns a formatted menu of available maze generation algorithms.
     *
     * @return a string containing the list of maze generation options
     */
    public static String getGeneratorOptions() {
        StringBuilder options = new StringBuilder("Choose a maze generation method:\n");
        MazeGeneratorType[] types = MazeGeneratorType.values();
        for (int i = 0; i < types.length; i++) {
            options.append(i + 1).append(". ").append(types[i].description()).append('\n');
        }
        return options.toString();
    }
}
