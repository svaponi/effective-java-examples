package io.github.svaponi.codechallenge.finlay;

public class Exercise {

    public static void main(final String[] args) {

        final Consumer consumer = new Consumer();

        final LoadHandler loadHandler = new LoadHandlerSafeImpl(consumer);

        new Producer(loadHandler).start();
    }
}


