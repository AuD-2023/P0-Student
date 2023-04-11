package p0;

import org.sourcegrade.jagr.api.rubric.*;
import org.sourcegrade.jagr.api.testing.TestCycle;

public class P0_RubricProvider implements RubricProvider {

    private static final Grader NOT_GRADED_BY_PUBLIC_GRADER = (testCycle, criterion) ->
        GradeResult.of(criterion.getMinPoints(), criterion.getMaxPoints(), "Not graded by public grader");

    private static final Criterion FIBONACCI_ITERATIVE_CRITERION = Criterion.builder()
        .shortDescription("Die Fibonacci-Zahlen werden in [[[IterativeFibonacciCalculator]]] korrekt berechnet.")
        .grader(Grader.testAwareBuilder()
            .requirePass(JUnitTestRef.ofMethod(() ->
                IterativeFibonacciCalculatorTests.class.getDeclaredMethod("testFibonacci", int.class, int.class)))
            .pointsFailedMin()
            .pointsPassedMax()
            .build())
        .build();
    private static final Criterion FIBONACCI_LOOPS_ONLY = Criterion.builder()
        .shortDescription("Methode [[[get(int)]]] in [[[IterativeFibonacciCalculator]]] ist rein iterativ implementiert.")
        .grader(Grader.testAwareBuilder()
            .requirePass(JUnitTestRef.ofMethod(() ->
                IterativeFibonacciCalculatorTests.class.getDeclaredMethod("testStrictlyIterative", TestCycle.class)))
            .pointsFailedMin()
            .pointsPassedMax()
            .build())
        .minPoints(-1)
        .maxPoints(0)
        .build();
    private static final Criterion FIBONACCI_ITERATIVE = Criterion.builder()
        .shortDescription("Fibonacci-Zahlen - iterativ")
        .addChildCriteria(FIBONACCI_ITERATIVE_CRITERION, FIBONACCI_LOOPS_ONLY)
        .minPoints(0)
        .maxPoints(1)
        .build();

    private static final Criterion FIBONACCI_RECURSIVE_CRITERION = Criterion.builder()
        .shortDescription("Die Fibonacci-Zahlen werden in [[[RecursiveFibonacciCalculator]]] korrekt berechnet.")
        .grader(NOT_GRADED_BY_PUBLIC_GRADER)
        .build();
    private static final Criterion FIBONACCI_RECURSION_ONLY = Criterion.builder()
        .shortDescription("Methode [[[get(int)]]] in [[[RecursiveFibonacciCalculator]]] ist rein rekursiv implementiert.")
        .grader(NOT_GRADED_BY_PUBLIC_GRADER)
        .minPoints(-1)
        .maxPoints(0)
        .build();
    private static final Criterion FIBONACCI_RECURSIVE = Criterion.builder()
        .shortDescription("Fibonacci-Zahlen - rekursiv")
        .addChildCriteria(FIBONACCI_RECURSIVE_CRITERION, FIBONACCI_RECURSION_ONLY)
        .minPoints(0)
        .maxPoints(1)
        .build();

    public static final Rubric RUBRIC = Rubric.builder()
        .title("P0")
        .addChildCriteria(FIBONACCI_ITERATIVE, FIBONACCI_RECURSIVE)
        .build();

    @Override
    public Rubric getRubric() {
        return RUBRIC;
    }
}
