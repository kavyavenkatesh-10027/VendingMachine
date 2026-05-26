package repository;

import util.IndianCurrency;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;

public class CurrencyRepository {

    private static CurrencyRepository instance;

    private final Map<IndianCurrency, Integer> drawer = new EnumMap<>(IndianCurrency.class);

    private CurrencyRepository() {
        for (IndianCurrency denomination : IndianCurrency.values()) {
            drawer.put(denomination, 0);
        }
    }

    public static CurrencyRepository getInstance() {
        if (instance == null) {
            instance = new CurrencyRepository();
        }
        return instance;
    }

    public int getCount(IndianCurrency denomination) {
        return drawer.get(denomination);
    }

    public void add(IndianCurrency denomination, Integer count) {
        drawer.put(denomination, drawer.get(denomination) + count);
    }

    public void deduct(IndianCurrency denomination, int count) {
        drawer.put(denomination, drawer.get(denomination) - count);
    }

    public Map<IndianCurrency, Integer> getDrawer() {
        return java.util.Collections.unmodifiableMap(drawer);
    }

    public int totalCashInMachine() {
        int total = 0;
        for (Map.Entry<IndianCurrency, Integer> entry : drawer.entrySet()) {
            total += entry.getKey().getValue() * entry.getValue();
        }
        return total;
    }
}
