package model;

import util.IndianCurrency;
import util.VendingMachineException;

import java.math.BigDecimal;
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
        if(count<=0){
            throw new VendingMachineException("Cannot add zero or negative amount of denomination");
        }
        denominations.put(denomination, denominations.get(denomination) + count);
    }

    public void deduct(IndianCurrency denomination, int count) {
        int current = denominations.get(denomination);
        if (count > current)
            throw new VendingMachineException("Insufficient denomination to deduct.");
        denominations.put(denomination, current - count);
    }

    public Map<IndianCurrency, Integer> getDenominations() {
        return Collections.unmodifiableMap(denominations);
    }

    public BigDecimal totalCash() {
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<IndianCurrency, Integer> entry : denominations.entrySet()) {
            BigDecimal denominationValue = BigDecimal.valueOf(entry.getKey().getValue());
            BigDecimal count = BigDecimal.valueOf(entry.getValue());
            total = total.add(denominationValue.multiply(count));
        }
        return total;
    }
}