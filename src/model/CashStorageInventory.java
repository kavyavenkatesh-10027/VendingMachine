package model;

import model.IndianCurrency;

import java.util.EnumMap;
import java.util.Map;

public class CashStorageInventory {

    private static final CashStorageInventory INSTANCE = new CashStorageInventory();

    private final Map<IndianCurrency, Integer> inventory;

    private CashStorageInventory() {
        inventory = new EnumMap<>(IndianCurrency.class);

        for (IndianCurrency currency : IndianCurrency.values()) {
            inventory.put(currency, 0);
        }
    }

    public static CashStorageInventory getInstance() {
        return INSTANCE;
    }

    public void addCash(IndianCurrency currency, int count) {
        inventory.put(currency, inventory.get(currency) + count);
    }

    public boolean removeCash(IndianCurrency currency, int count) {
        int available = inventory.get(currency);

        if (available < count) {
            return false;
        }

        inventory.put(currency, available - count);
        return true;
    }

    public int getCount(IndianCurrency currency) {
        return inventory.get(currency);
    }

    public int getTotalAmount() {
        int total = 0;

        for (Map.Entry<IndianCurrency, Integer> entry : inventory.entrySet()) {
            total += entry.getKey().getValue() * entry.getValue();
        }

        return total;
    }

    public Map<IndianCurrency, Integer> getInventory() {
        return new EnumMap<>(inventory);
    }
}
