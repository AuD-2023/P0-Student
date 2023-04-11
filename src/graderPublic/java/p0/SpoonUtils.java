package p0;

import org.sourcegrade.jagr.api.testing.Submission;
import spoon.Launcher;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.support.compiler.VirtualFile;

import java.util.Arrays;
import java.util.Set;

public class SpoonUtils {

    private static CtModel modelCache = null;

    public static CtModel buildModelFromSubmission(Submission submission) {
        return buildModelFromSubmission(submission, false);
    }

    public static CtModel buildModelFromSubmission(Submission submission, boolean rebuild) {
        if (!rebuild && modelCache != null) {
            return modelCache;
        }

        Launcher launcher = new Launcher();
        // Submission.getClassNames() is deprecated / experimental but the alternatives don't have the same functionality
        @SuppressWarnings("removal") Set<String> classNames = submission.getClassNames();
        classNames.stream()
            .map(className -> className.replace('.', '/') + ".java")
            // TODO: handle NPE, maybe
            .map(fileName -> new VirtualFile(submission.getSourceFile(fileName).getContent(), fileName))
            .forEach(launcher::addInputResource);
        return modelCache = launcher.buildModel();
    }

    @SuppressWarnings("unchecked")
    public static <T> CtClass<T> getClassFromModel(CtModel model, String className) {
        return (CtClass<T>) model.getElements(element ->
                element instanceof CtClass<?> ctClass && ctClass.getQualifiedName().equals(className))
            .stream()
            .findAny()
            .orElseThrow();
    }

    public static CtMethod<?> getMethodFromClass(CtClass<?> ctClass, String name, Class<?>... parameterTypes) {
        return getMethodFromClass(ctClass, name, Arrays.stream(parameterTypes).map(Class::getCanonicalName).toArray(String[]::new));
    }

    public static CtMethod<?> getMethodFromClass(CtClass<?> ctClass, String name, String... parameterTypes) {
        return (CtMethod<?>) ctClass.getElements(ctElement -> {
                if (ctElement instanceof CtMethod<?> ctMethod && ctMethod.getSimpleName().equals(name)) {
                    String[] currentParameterTypes = ctMethod.getParameters()
                        .stream()
                        .map(ctParameter -> ctParameter.getType().getQualifiedName())
                        .toArray(String[]::new);
                    return Arrays.equals(parameterTypes, currentParameterTypes);
                } else {
                    return false;
                }
            })
            .stream()
            .findAny()
            .orElseThrow();
    }
}
