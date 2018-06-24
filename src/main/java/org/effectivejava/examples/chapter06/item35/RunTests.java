// Program to process marker annotations - Page 171
package org.effectivejava.examples.chapter06.item35;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RunTests {
    public static void main(final String[] args) throws Exception {
        int tests = 0;
        int passed = 0;
        final String type = args.length > 0 ? args[0] : RunTests.class.getPackage().getName() + ".Sample";
        final Class testClass = Class.forName(type);
        for (final Method m : testClass.getDeclaredMethods()) {
            if (m.isAnnotationPresent(XTest.class)) {
                tests++;
                try {
                    m.invoke(null);
                    passed++;
                } catch (final InvocationTargetException wrappedExc) {
                    final Throwable exc = wrappedExc.getCause();
                    System.out.println(m + " failed: " + exc);
                } catch (final Exception exc) {
                    System.out.println("INVALID @Test: " + m);
                }
            }

            // Array ExceptionTest processing code - Page 174
            if (m.isAnnotationPresent(ExceptionTest.class)) {
                tests++;
                try {
                    m.invoke(null);
                    System.out.printf("Test %s failed: no exception%n", m);
                } catch (final Throwable wrappedExc) {
                    final Throwable exc = wrappedExc.getCause();
                    final Class<? extends Exception>[] excTypes = m.getAnnotation(
                            ExceptionTest.class).value();
                    final int oldPassed = passed;
                    for (final Class<? extends Exception> excType : excTypes) {
                        if (excType.isInstance(exc)) {
                            passed++;
                            break;
                        }
                    }
                    if (passed == oldPassed) {
                        System.out.printf("Test %s failed: %s %n", m, exc);
                    }
                }
            }
        }
        System.out.printf("Passed: %d, Failed: %d%n", passed, tests - passed);
    }
}
