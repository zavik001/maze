package backend.academy.maze.game.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * A generic class representing a pair of two values.
 * It is commonly used to return two related values together from a method.
 *
 * @param <T> the type of the first value
 * @param <U> the type of the second value
 */
@Getter
@AllArgsConstructor
public class Pair<T, U> {
    private final T first;
    private final U second;
}
