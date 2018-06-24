package io.github.svaponi.codechallenge.finlay;

import io.github.svaponi.util.RandomUtil;

import java.util.concurrent.TimeUnit;

public class Producer extends Thread {

    private final LoadHandler loadHandler;

    public Producer(final LoadHandler loadHandler) {
        this.loadHandler = loadHandler;
    }

    @Override
    public void run() {
        while (true) {
            final PriceUpdate update = buildUpdate();
            System.out.printf("new update '%s' \n", update);
            loadHandler.receive(update);
            try {
                TimeUnit.MILLISECONDS.sleep(20);
            } catch (final InterruptedException e) {
            }
        }
    }

    private PriceUpdate buildUpdate() {
        final String company = RandomUtil.randomize("Apple", "Google", "Facebook");
        final Double price = Double.valueOf(RandomUtil.randomInt(20000) / 100);
        return new PriceUpdate(company, price);
    }
}
