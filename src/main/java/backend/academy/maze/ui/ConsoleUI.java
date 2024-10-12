package backend.academy.maze.ui;

import backend.academy.maze.graph.Point;
import backend.academy.maze.model.Cell;
import backend.academy.maze.model.PathSymbol;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * Console-based implementation of the UI interface.
 * Provides methods for interacting with the user through the console.
 */
@Slf4j
public class ConsoleUI implements UI {

    private final BufferedReader reader;
    private static final String DIGIT_PATTERN = "\\d+";
    private static final char DASH = '-';
    private static final char SPACE = ' ';
    private static final char INDENT = '\n';

    /** Initializes the console reader for reading user input. */
    public ConsoleUI() {
        BufferedReader tempReader = null;
        try {
            tempReader =
                    new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("Failed to initialize BufferedReader. ", e);
        }
        this.reader =
                (tempReader != null)
                        ? tempReader
                        : new BufferedReader(
                                new InputStreamReader(System.in, StandardCharsets.UTF_8));
    }

    /** {@inheritDoc} */
    @Override
    public void clearWindow() {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {
            log.error("Failed to clear console.", e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void showMessage(CharSequence message) {
        log.info("Message: {}", message);
    }

    /** {@inheritDoc} */
    @Override
    public int readNumberInput() {
        log.info("Waiting for number input from the user");
        try {
            String input = reader.readLine();
            if (input == null || input.trim().isEmpty()) {
                return 0;
            }
            input = input.trim();
            log.info("User number input: {}", input);
            if (input.matches(DIGIT_PATTERN) && Integer.parseInt(input) >= 0) {
                return Integer.parseInt(input);
            } else {
                log.warn("Invalid number input: {}", input);
                return -1;
            }
        } catch (IOException e) {
            log.error("Failed to read number input.", e);
            return -1;
        }
    }

    /** {@inheritDoc} */
    @Override
    public int[] readPointInput() {
        log.info("Waiting for point input (two numbers for X and Y)");
        try {
            String input = reader.readLine();
            if (input == null || input.trim().isEmpty()) {
                return new int[] {-1, -1};
            }
            input = input.trim();
            log.info("User point input: {}", input);
            String[] parts = input.split("\\s+");
            if (parts.length == 2
                    && parts[0].matches(DIGIT_PATTERN)
                    && parts[1].matches(DIGIT_PATTERN)) {
                return new int[] {Integer.parseInt(parts[0]), Integer.parseInt(parts[1])};
            } else {
                log.warn("Invalid point input: {}", input);
                return new int[] {-1, -1};
            }
        } catch (IOException e) {
            log.error("Failed to read point input.", e);
            return new int[] {-1, -1};
        }
    }

    /** {@inheritDoc} */
    @Override
    public void showListMessage(List<String> options) {
        if (options == null || options.isEmpty()) {
            log.info("No options available.");
            return;
        }
        String message = String.join(String.valueOf(INDENT), options);
        log.info("Options: \n{}", message);
    }

    /** {@inheritDoc} */
    @Override
    public void showMaze(Cell[][] maze, List<Point> path, Point startPoint, Point endPoint) {
        StringBuilder mazeOutput = new StringBuilder();
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                Point currentPoint = new Point(i, j);

                if (currentPoint.equals(startPoint)) {
                    mazeOutput.append(PathSymbol.START.symbol()).append(SPACE);
                } else if (currentPoint.equals(endPoint)) {
                    mazeOutput.append(PathSymbol.END.symbol()).append(SPACE);
                } else if (path.contains(currentPoint)) {
                    mazeOutput.append(PathSymbol.PATH.symbol()).append(SPACE);
                } else {
                    mazeOutput.append(maze[i][j].symbol()).append(SPACE);
                }
            }
            mazeOutput.append(INDENT);
        }

        log.info("Displaying maze layout with path:\n{}", mazeOutput);
    }

    /** {@inheritDoc} */
    @Override
    public void showMaze(Cell[][] maze) {
        StringBuilder mazeOutput = new StringBuilder();
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                mazeOutput.append(maze[i][j].symbol()).append(SPACE);
            }
            mazeOutput.append(INDENT);
        }

        log.info("Displaying maze:\n{}", mazeOutput);
    }

    /** {@inheritDoc} */
    @Override
    public double readDoubleInput() {
        log.info("Waiting for double input from user");
        double number = -1.0;
        boolean validInput = false;

        while (!validInput) {
            try {
                String input = reader.readLine();
                if (input == null || input.trim().isEmpty()) {
                    log.warn("Empty input, asking again");
                    showMessage("Input cannot be empty.");
                    continue;
                }

                input = input.trim();
                log.info("User input: {}", input);

                number = Double.parseDouble(input);
                validInput = true;
            } catch (NumberFormatException e) {
                log.warn("Invalid double input: {}", e.getMessage());
                showMessage("Invalid input. Please enter a valid number.");
            } catch (IOException e) {
                log.error("Failed to read double input.", e);
            }
        }

        return number;
    }

    /** {@inheritDoc} */
    @Override
    public void showLegend() {
        StringBuilder legendOutput = new StringBuilder();

        // Legend for Cell
        legendOutput.append("Legend:\n");
        legendOutput.append("Cell symbols:\n");
        for (Cell cell : Cell.values()) {
            legendOutput.append(cell.symbol()).append(DASH).append(SPACE).append(cell.name()).append(INDENT);
        }

        // Legend for PathSymbol
        legendOutput.append("Path symbols:\n");
        for (PathSymbol pathSymbol : PathSymbol.values()) {
            legendOutput
                    .append(pathSymbol.symbol())
                    .append(DASH)
                    .append(SPACE)
                    .append(pathSymbol.name())
                    .append(INDENT);
        }

        log.info("Displaying legend:\n{}", legendOutput);
    }
}
