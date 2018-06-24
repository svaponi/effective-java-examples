package io.github.svaponi.jmh;

public class BenchmarkRunner {

    public static void main(final String[] args) throws Exception {
        org.openjdk.jmh.Main.main(new String[]{".*ExecutionPlan"});
    }
}
