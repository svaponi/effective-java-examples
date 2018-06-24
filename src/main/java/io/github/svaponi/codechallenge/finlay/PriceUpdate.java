package io.github.svaponi.codechallenge.finlay;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = {"companyName", "price"})
public class PriceUpdate {

    private final String companyName;
    private final double price;

    @Override
    public String toString() {
        return companyName + " " + price;
    }
}
