package io.github.svaponi.anonymous;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnonymousBlockTest {

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

    final Integer a;

    {
        stack.add("anonymous block");
        final Integer b = 5;
        a = new Integer(b);
    }

    public AnonymousBlockTest() {
        stack.add("constructor");
        final Integer b = 5;
        // a = new Integer(b); // this won't compile because anonymous block already executed
        System.out.printf("a=%s, b=%s;\n", a, b);
    }

    @Test
    public void test() throws Exception {
        stack.add("test");
    }

    @AfterClass
    public static void check() throws Exception {
        System.out.printf("stack=%s;\n", stack);
        Assert.assertEquals(Arrays.asList(
                "static block",
                "anonymous block",
                "constructor",
                "test"
        ), stack);
    }
}
