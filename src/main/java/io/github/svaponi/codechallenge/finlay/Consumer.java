package io.github.svaponi.codechallenge.finlay;

import java.util.List;

public class Consumer {

    final LoadVerifier loadVerifier = new LoadVerifier();

    public Consumer() {
        loadVerifier.start();
    }

    public void send(final List<PriceUpdate> priceUpdates) {
        priceUpdates.stream().forEach(this::send);
    }

    public void send(final PriceUpdate update) {
        if (loadVerifier.counter.incrementAndGet() > LoadHandler.MAX_UPDATES_PER_CYCLE) {
            throw new IllegalStateException(String.format("consumer overloaded (%d updates in %d ms)", loadVerifier.counter.get(), LoadHandler.CYCLE_MILLIS));
        }
        System.out.printf("consuming '%s' \n", update);
    }
}
