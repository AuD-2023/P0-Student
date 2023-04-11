package p0;

/**
 * Main entry point in executing the program.
 */
public class Main {

    /**
     * Main entry point in executing the program.
     *
     * @param args program arguments, currently ignored
     */
    public static void main(String[] args) {
        FibonacciCalculator iterativeFibonacciCalculator = new IterativeFibonacciCalculator();
        FibonacciCalculator recursiveFibonacciCalculator = new RecursiveFibonacciCalculator();

        System.out.println(iterativeFibonacciCalculator.get(10));
        System.out.println(recursiveFibonacciCalculator.get(10));
    }
}
