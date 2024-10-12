package backend.academy.maze.game.utils;

import java.io.IOException;
import lombok.experimental.UtilityClass;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

/**
 * A utility class for obtaining the console dimensions (height and width).
 * It tries to fetch the size dynamically using the terminal interface but falls back
 * to default values if it cannot retrieve them.
 */
@UtilityClass
public class ConsoleSizeUtil {
    private static Terminal terminal = null;

    /**
     * Default console height.
     */
    public static final int HEIGHT = 24;

    /**
     * Default console width.
     */
    public static final int WEIGHT = 80;

    static {
        try {
            terminal = TerminalBuilder.builder().system(true).streams(System.in, System.out).build();
        } catch (IOException e) {
            System.err.println("Failed to initialize terminal: " + e.getMessage());
        }
    }

    /**
     * Returns the current console height. If the terminal is not initialized,
     * returns the default height.
     *
     * @return the height of the console or the default value
     */
    public static int getConsoleHeight() {
        if (terminal != null) {
            return terminal.getHeight();
        }
        return HEIGHT;
    }

    /**
     * Returns the current console width. If the terminal is not initialized,
     * returns the default width.
     *
     * @return the width of the console or the default value
     */
    public static int getConsoleWidth() {
        if (terminal != null) {
            return terminal.getWidth();
        }
        return WEIGHT;
    }
}
