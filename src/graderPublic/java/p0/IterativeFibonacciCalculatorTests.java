package p0;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.sourcegrade.jagr.api.testing.TestCycle;
import org.sourcegrade.jagr.api.testing.extension.TestCycleResolver;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions4.assertIsNotRecursively;

@TestForSubmission
public class IterativeFibonacciCalculatorTests {

    private static final String CLASS_NAME = "p0.IterativeFibonacciCalculator";
    private static final FibonacciCalculator FIBONACCI_CALCULATOR = new IterativeFibonacciCalculator();

    @ParameterizedTest
    @CsvFileSource(resources = "/fibonacci.csv")
    public void testFibonacci(int n, int expected) {
        assertCallEquals(expected, () -> FIBONACCI_CALCULATOR.get(n), contextBuilder().add("n", n).build(),
            result -> "Returned number did not match the expected Fibonacci number");
    }

    @Test
    @ExtendWith(TestCycleResolver.class)
    @SuppressWarnings("JUnitMalformedDeclaration")
    public void testStrictlyIterative(TestCycle testCycle) {
        CtModel model = SpoonUtils.buildModelFromSubmission(testCycle.getSubmission());
        CtClass<IterativeFibonacciCalculator> iterativeFibonacciCalculatorCtClass = SpoonUtils.getClassFromModel(model, CLASS_NAME);
        CtMethod<?> getCtMethod = SpoonUtils.getMethodFromClass(iterativeFibonacciCalculatorCtClass, "get", int.class);

        assertIsNotRecursively(getCtMethod, emptyContext(), result -> "Recursion detected in method [[[get(int)]]]");
    }
}
