package io.github.svaponi.jmh;

import org.junit.Test;

public class StringConcatenationTest {

    private static final boolean print = Boolean.getBoolean("print");
    private static final int exp = 4;

    @Test
    public void testString() {
        final StringConcatenation test = new StringConcatenation(exp, print);
        test.concatString();
    }

    @Test
    public void testStringBuilder() {
        final StringConcatenation test = new StringConcatenation(exp, print);
        test.concatStringBuilder();
    }
}
