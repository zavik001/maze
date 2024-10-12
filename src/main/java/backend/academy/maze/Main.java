package backend.academy.maze;

import backend.academy.maze.game.MazeGame;
import backend.academy.maze.ui.ConsoleUI;
import backend.academy.maze.ui.UI;
import lombok.experimental.UtilityClass;

/**
 * The {@code Main} class serves as the entry point for the maze game application.
 * It initializes the user interface and starts the game loop.
 */
@UtilityClass
public class Main {

    /**
     * The main method, which initializes the UI and starts the maze game.
     *
     * @param args command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        // Initialize the user interface (UI) for the game.
        UI ui = new ConsoleUI();

        // Create a new instance of MazeGame and pass the UI to it.
        MazeGame game = new MazeGame(ui);

        // Start the game.
        game.start();
    }
}
