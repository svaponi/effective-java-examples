package io.github.svaponi.codechallenge.finlay;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class LoadHandlerSafeImpl implements LoadHandler {

    private static final long interval = 250;
    private static final long batchSize = 5;

    private final Set<PriceUpdateWrapper> set;

    public LoadHandlerSafeImpl(final Consumer consumer) {
        if (interval > CYCLE_MILLIS) {
            throw new IllegalArgumentException("invalid interval");
        }
        if (batchSize * Math.floorDiv(CYCLE_MILLIS, interval) > MAX_UPDATES_PER_CYCLE) {
            throw new IllegalArgumentException("invalid batchSize");
        }
        this.set = new TreeSet(Comparator.comparing(PriceUpdateWrapper::getInstant).reversed());
        final Runnable runnable = () -> {
            try {
                while (true) {
                    TimeUnit.MILLISECONDS.sleep(interval);
                    final List<PriceUpdate> toBeSent;
                    synchronized (set) {
                        toBeSent = set.stream()
                                .limit(batchSize)
                                .map(PriceUpdateWrapper::getUpdate)
                                .collect(Collectors.toList());
                        set.clear();
                    }
                    Collections.reverse(toBeSent); // after reversing, older comes first
                    consumer.send(toBeSent);
                }
            } catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
                System.exit(1);
            } catch (final Exception e) {
                System.err.println(e);
                System.exit(1);
            }
        };
        new Thread(runnable).start();
    }

    @Override
    public void receive(final PriceUpdate priceUpdate) {
        final PriceUpdateWrapper wrap = new PriceUpdateWrapper(priceUpdate);
        set.add(wrap);
    }

    @Data
    @EqualsAndHashCode(of = {"instant", "update"})
    private static class PriceUpdateWrapper {

        private final Instant instant;
        private final PriceUpdate update;

        public PriceUpdateWrapper(final PriceUpdate update) {
            this.update = update;
            instant = Instant.now();
        }

        @Override
        public String toString() {
            return instant + " - " + update;
        }
    }
}
