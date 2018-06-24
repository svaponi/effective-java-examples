package io.github.svaponi.anonymous;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnonymousBlockComplexTest {

    static final List<String> stack = new ArrayList<String>() {
        @Override
        public boolean add(final String o) {
            System.out.println("" + o);
            return super.add(o);
        }
    };

    static {
        stack.add("static block");
    }

    public AnonymousBlockComplexTest() {
        stack.add("constructor");
    }

    {
        stack.add("anonymous block");
    }

    public class Inner {

        {
            stack.add("Inner anonymous block");
        }

        public Inner() {
            stack.add("Inner constructor");
        }

        public void method() {
            stack.add("Inner method");
        }
    }

    @Test
    public void test() {
        stack.add("test");

        new Inner() {
            {
                method(); // prints "Test method"
            }
        };

        {
            //=========================================================================
            // which means you can even create a List using double brace
            final List<String> list = new ArrayList() {
                // anonymous class extending ArrayList
                {
                    // anonymous block inside anonymous class
                    stack.add("Inner anonymous class extending ArrayList (1)");
                    add("el1");
                    add("el2");
                }
            };
            System.out.println(list); // prints [el1, el2]
        }

        {
            //==========================================================================
            // you can even create your own methods for your anonymous class and use them
            final List<String> list = new ArrayList<String>() {

                private void myCustomMethod(final String s1, final String s2) {
                    stack.add("Inner anonymous class extending ArrayList (2) myCustomMethod");
                    add(s1);
                    add(s2);
                }

                {
                    stack.add("Inner anonymous class extending ArrayList (2)");
                    myCustomMethod("el3", "el4");
                }
            };

            System.out.println(list); // prints [el3, el4]
        }
    }

    @AfterClass
    public static void check() throws Exception {

        System.out.printf("stack=%s;\n", stack);

        Assert.assertEquals(Arrays.asList(
                "static block",
                "anonymous block",
                "constructor",
                "test",
                "Inner anonymous block",
                "Inner constructor",
                "Inner method",
                "Inner anonymous class extending ArrayList (1)",
                "Inner anonymous class extending ArrayList (2)",
                "Inner anonymous class extending ArrayList (2) myCustomMethod"
        ), stack);
    }
}
