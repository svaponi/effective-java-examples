package io.github.svaponi.codechallenge.finlay;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class LoadVerifier extends Thread {

    private static final AtomicInteger instanceId = new AtomicInteger();
    public final AtomicInteger counter = new AtomicInteger();

    public LoadVerifier() {
        this.setDaemon(true);
        this.setName("LoadVerifier-" + instanceId.getAndIncrement());
    }

    @Override
    public void run() {
        try {
            System.out.println(getName() + " started ... ");
            while (true) {
                TimeUnit.MILLISECONDS.sleep(LoadHandler.CYCLE_MILLIS);
                System.out.printf("consumed %d updates in a cycle (%d ms)\n", counter.get(), LoadHandler.CYCLE_MILLIS);
                counter.set(0);
            }
        } catch (final InterruptedException e) {
            System.err.println(getName() + " interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
