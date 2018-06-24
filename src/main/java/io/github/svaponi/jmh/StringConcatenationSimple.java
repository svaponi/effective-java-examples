package io.github.svaponi.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;

public class StringConcatenationSimple {

    private static final boolean print = Boolean.getBoolean("print");
    private static final int exp = 4;

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Fork(value = 1)
//    @Warmup(iterations = 1)
    public void testString() {
        final StringConcatenation test = new StringConcatenation(exp, print);
        test.concatString();
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Fork(value = 1)
//    @Warmup(iterations = 1)
    public void testStringBuilder() {
        final StringConcatenation test = new StringConcatenation(exp, print);
        test.concatStringBuilder();
    }
}
