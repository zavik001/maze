# Maze Game

This is a console-based maze generation and pathfinding game, developed as part of a homework assignment for the [Backend Academy 2024 by Tinkoff](https://edu.tinkoff.ru/). The project is implemented in Java using a project template provided by the Academy.

## Project Description

The game generates mazes of various sizes and complexities, and provides several algorithms to find a path from a start point (A) to an endpoint (B). The maze and path are displayed directly in the console.

## Key Features

- **Maze Generation**: The project supports multiple algorithms for maze generation, including:
  - Kruskal's algorithm
  - Prim's algorithm
  - Recursive Backtracker algorithm
- **Pathfinding**: It also implements pathfinding algorithms:
  - A* (A-star)
  - Dijkstra's algorithm
- **Text-based Visualization**: The maze and its solution (if found) are displayed using ASCII characters in the console.

## Design and Architecture

The project adheres to **SOLID principles**, ensuring clear separation of concerns, easy maintainability, and flexibility for future extensions.

### Design Patterns

- **Factory Pattern**: The project uses the Factory design pattern to create instances of maze generators and pathfinders based on the specified algorithm. This makes the code scalable and allows easy swapping or addition of new algorithms.
  - `MazeGeneratorFactory` creates maze generators (e.g., Kruskal, Prim).
  - `PathfinderFactory` creates pathfinding algorithms (e.g., A*, Dijkstra).

## How to Run the Game

   ```bash
   git clone https://github.com/zavik001/maze.git
   cd maze
   ./mvnw clean verify
   mvn clean compile exec:java -Dexec.mainClass="backend.academy.maze.Main"