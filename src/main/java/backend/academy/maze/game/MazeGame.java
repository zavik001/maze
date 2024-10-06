package backend.academy.maze.game;

import backend.academy.maze.game.utils.ConsoleSizeUtil;
import backend.academy.maze.game.utils.Pair;
import backend.academy.maze.generation.MazeGenerator;
import backend.academy.maze.generation.MazeGeneratorType;
import backend.academy.maze.generation.factory.MazeGeneratorFactory;
import backend.academy.maze.graph.MazeGraph;
import backend.academy.maze.graph.Point;
import backend.academy.maze.model.Cell;
import backend.academy.maze.model.Maze;
import backend.academy.maze.model.utils.MazeUtils;
import backend.academy.maze.routing.Pathfinder;
import backend.academy.maze.routing.PathfinderType;
import backend.academy.maze.routing.factory.PathfinderFactory;
import backend.academy.maze.ui.UI;
import java.util.List;

/**
 * This class represents the main game logic for the maze game.
 * It allows the user to generate a maze, optionally add special cells,
 * select start and end points, and solve the maze using a pathfinding algorithm.
 */
public class MazeGame {
    private final UI ui;

    /**
     * Constructs a new MazeGame instance with a user interface.
     *
     * @param ui the UI interface to handle user interaction and display
     */
    public MazeGame(UI ui) {
        this.ui = ui;
    }

    /**
     * Starts the maze game. The game continues in a loop, allowing
     * the user to play multiple rounds, generate new mazes, solve them,
     * and optionally play again.
     */
    public void start() {
        boolean playAgain = true;

        while (playAgain) {
            ui.clearWindow();

            // Select a valid maze size
            Pair<Integer, Integer> size = selectValidMazeSize();
            int width = size.first();
            int height = size.second();

            // Select maze generation method
            int generatorChoice = getValidChoice(
                    MazeGeneratorFactory.getGeneratorOptions(),
                    MazeGeneratorType.values().length);
            MazeGenerator generator = MazeGeneratorFactory.getGenerator(generatorChoice);
            Maze maze = generator.generate(width, height);
            ui.showMaze(maze.maze());

            // Option to add special cells to the maze
            int specialCellsChoice = getValidChoice(
                    "Do you want to add special cells?\n1. Yes\n2. No", 2);
            if (specialCellsChoice == 1) {
                double percentage = getValidPercentage();
                MazeUtils.addSpecialCells(maze, percentage);
                ui.showMessage("Special cells added.");
                ui.showMaze(maze.maze());
                ui.showLegend();
            }

            // Select start and end points for pathfinding
            Point startPoint = selectValidPoint(maze, "start point");
            Point endPoint = selectValidPoint(maze, "end point");

            // Select pathfinding algorithm
            int pathfinderChoice = getValidChoice(
                    PathfinderFactory.getPathfinderOptions(),
                    PathfinderType.values().length);
            Pathfinder pathfinder = PathfinderFactory.getPathfinder(pathfinderChoice);

            // Find the path
            MazeGraph mazeGraph = new MazeGraph(maze);
            mazeGraph.buildGraph();
            List<Point> path = pathfinder.findPath(mazeGraph, startPoint, endPoint);

            // Check if the path was found
            if (path.isEmpty()) {
                ui.showMessage("Path not found.");
            } else {
                ui.showMessage("Path found: " + path);
                ui.showMaze(maze.maze(), path, startPoint, endPoint);
                ui.showLegend();
            }

            // Ask if the player wants to play again
            int continueChoice = getValidChoice("Do you want to play again?\n1. Yes\n2. No", 2);
            playAgain = continueChoice == 1;
        }

        ui.showMessage("Thanks for playing!");
    }

    /**
     * Prompts the user to enter valid maze dimensions (width and height).
     * It checks the console dimensions to ensure the maze fits within the console window.
     *
     * @return a pair containing the height and width of the maze
     */
    private Pair<Integer, Integer> selectValidMazeSize() {
        int width;
        int height;
        int consoleWidth = ConsoleSizeUtil.WEIGHT;
        int consoleHeight = ConsoleSizeUtil.HEIGHT;

        try {
            consoleWidth = ConsoleSizeUtil.getConsoleWidth();
            consoleHeight = ConsoleSizeUtil.getConsoleHeight();
        } catch (Exception e) {
            ui.showMessage("Failed to get console size. Using default dimensions.");
        }

        ui.showMessage("Console size: " + consoleHeight + " rows and " + consoleWidth + " characters.");

        do {
            ui.showMessage("Enter maze height:");
            height = getValidSize();
            ui.showMessage("Enter maze width:");
            width = getValidSize();

            int requiredWidth = width * 2;
            if (requiredWidth > consoleWidth || height > consoleHeight) {
                ui.showMessage("The maze does not fit in the console. Try again.");
            } else {
                break;
            }
        } while (true);

        return new Pair<>(height, width);
    }

    /**
     * Prompts the user to enter a valid choice based on the provided options.
     * It ensures that the choice falls within the valid range.
     *
     * @param message the message to display with the options
     * @param maxChoice the maximum allowed choice
     * @return the valid user choice
     */
    private int getValidChoice(String message, int maxChoice) {
        int choice;
        ui.showMessage(message);
        do {
            choice = ui.readNumberInput();
            if (choice < 1 || choice > maxChoice) {
                ui.showMessage("Please select a valid option.");
            }
        } while (choice < 1 || choice > maxChoice);
        return choice;
    }

    /**
     * Prompts the user to enter a valid percentage for the special cells.
     * Ensures the input is between 0 and 1.
     *
     * @return the percentage as a double
     */
    private double getValidPercentage() {
        double percentage;
        do {
            ui.showMessage("Enter the percentage for special cells (e.g., 0.1 for 10%):");
            percentage = ui.readDoubleInput();
            if (percentage < 0 || percentage > 1) {
                ui.showMessage("Please enter a value between 0 and 1.");
            }
        } while (percentage < 0 || percentage > 1);
        return percentage;
    }

    /**
     * Prompts the user to enter a valid size for the maze dimensions.
     * Ensures the input is a positive number.
     *
     * @return the valid size as an integer
     */
    private int getValidSize() {
        int input;
        do {
            input = ui.readNumberInput();
            if (input <= 0) {
                ui.showMessage("Please enter a positive number.");
            }
        } while (input <= 0);
        return input;
    }

    /**
     * Prompts the user to select valid coordinates for a point (start or end).
     * Ensures the point is within maze bounds and not a wall.
     *
     * @param maze the maze object
     * @param pointName the name of the point being selected (e.g., "start point")
     * @return the valid Point object
     */
    private Point selectValidPoint(Maze maze, String pointName) {
        int x;
        int y;
        Cell[][] cells = maze.maze();

        do {
            ui.showMessage("Enter coordinates for " + pointName + ":");
            ui.showMessage("Enter X:");
            x = ui.readNumberInput();
            ui.showMessage("Enter Y:");
            y = ui.readNumberInput();

            if (x < 0 || x >= maze.width() || y < 0 || y >= maze.height()) {
                ui.showMessage("Coordinates are out of bounds.");
            } else if (cells[x][y] == Cell.WALL) {
                ui.showMessage("Point is on a wall. Select a different point.");
            } else {
                break;
            }
        } while (true);

        return new Point(x, y);
    }
}
