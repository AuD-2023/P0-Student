package p0;

@FunctionalInterface
public interface FibonacciCalculator {

    /**
     * Calculates and returns the {@code n}-th number of the Fibonacci sequence.
     * Valid parameters are any integers greater than 0.
     * If {@code n = 1} or {@code n = 2}, 1 is returned.
     * Otherwise, the sum of the last two numbers in the sequence is returned.
     * @param n index of the number in the Fibonacci sequence
     * @return the Fibonacci number at index {@code n}
     * @throws IllegalArgumentException if {@code n} is non-positive
     */
    int get(int n);
}
