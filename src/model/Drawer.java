package model;

import util.IndianCurrency;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class Drawer {

    private final Map<IndianCurrency, Integer> denominations = new EnumMap<>(IndianCurrency.class);

    public Drawer() {
        for (IndianCurrency denomination : IndianCurrency.values()) {
            denominations.put(denomination, 0);
        }
    }

    public int getCount(IndianCurrency denomination) {
        return denominations.get(denomination);
    }

    public void add(IndianCurrency denomination, int count) {
        denominations.put(denomination, denominations.get(denomination) + count);
    }

    public void deduct(IndianCurrency denomination, int count) {
        denominations.put(denomination, denominations.get(denomination) - count);
    }

    public Map<IndianCurrency, Integer> getDenominations() {
        return Collections.unmodifiableMap(denominations);
    }

    public int totalCash() {
        int total = 0;
        for (Map.Entry<IndianCurrency, Integer> entry : denominations.entrySet()) {
            total += entry.getKey().getValue() * entry.getValue();
        }
        return total;
    }
}