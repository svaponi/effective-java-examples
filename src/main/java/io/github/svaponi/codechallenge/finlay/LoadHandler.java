package io.github.svaponi.codechallenge.finlay;

public interface LoadHandler {

    int MAX_UPDATES_PER_CYCLE = 20;
    int CYCLE_MILLIS = 1000;

    void receive(PriceUpdate priceUpdate);
}
