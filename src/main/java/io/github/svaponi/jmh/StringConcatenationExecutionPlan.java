package io.github.svaponi.jmh;

import org.openjdk.jmh.annotations.*;

public class StringConcatenationExecutionPlan {

    @State(Scope.Benchmark)
    public static class ExecutionPlan {

        /*
        the field will be populated with appropriate values from the @Param annotation by the JMH when it is passed to the benchmark method
         */
        @Param({"2", "3", "4", "5"})
        public int exp;

        private StringConcatenation test;

        /*
         @Setup annotated method is invoked before each invocation of the benchmark
         */
        @Setup(Level.Invocation)
        public void setUp() {
            test = new StringConcatenation(exp);
        }
    }

    /*
        By using the @Fork annotation, we can set up how benchmark execution happens: the value parameter controls how many
        times the benchmark will be executed, and the warmup parameter controls how many times a benchmark will dry run
        before results are collected
    */

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Fork(value = 1)
//    @Warmup(iterations = 5)
    public void testString(final ExecutionPlan plan) {
        plan.test.concatString();
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Fork(value = 1)
//    @Warmup(iterations = 5)
    public void testStringBuilder(final ExecutionPlan plan) {
        plan.test.concatStringBuilder();
    }
}

