package service;

import model.Drawer;
import util.IndianCurrency;
import util.VendingMachineException;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;

public class CurrencyService {

    private static CurrencyService instance;

    private CurrencyService() {}

    public static CurrencyService getInstance() {
        if (instance == null) {
            instance = new CurrencyService();
        }
        return instance;
    }


    public BigDecimal acceptPayment(Drawer drawer, Map<IndianCurrency, Integer> inserted) {
        validateDrawer(drawer);
        if (inserted == null || inserted.isEmpty()) {
            throw new VendingMachineException("No money inserted.");
        }

        BigDecimal total = BigDecimal.ZERO;

        for (Map.Entry<IndianCurrency, Integer> entry : inserted.entrySet()) {
            IndianCurrency denomination = entry.getKey();
            int count = entry.getValue();

            validateDenomination(denomination);
            validateCount(count);

            drawer.add(denomination, count);

            BigDecimal denomValue = BigDecimal.valueOf(denomination.getValue());
            BigDecimal denomTotal = denomValue.multiply(BigDecimal.valueOf(count));
            total = total.add(denomTotal);
        }

        return total;
    }

    public Map<IndianCurrency, Integer> makeChange(Drawer drawer, BigDecimal changeAmount) {
        validateDrawer(drawer);
        if (changeAmount == null) {
            throw new VendingMachineException("Change amount cannot be null.");
        }
        if (changeAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new VendingMachineException("Change amount cannot be negative.");
        }
        if (changeAmount.compareTo(BigDecimal.ZERO) == 0) {
            return new EnumMap<>(IndianCurrency.class);
        }

        Map<IndianCurrency, Integer> change = new EnumMap<>(IndianCurrency.class);
        BigDecimal remaining = changeAmount;
        IndianCurrency[] denominations = IndianCurrency.values();

        for (int i = denominations.length - 1; i >= 0; i--) {
            if (remaining.compareTo(BigDecimal.ZERO) == 0) break;

            IndianCurrency denom = denominations[i];
            BigDecimal denomValue = BigDecimal.valueOf(denom.getValue());
            int available = drawer.getCount(denom);

            int canUse = remaining.divideToIntegralValue(denomValue).intValue();
            int use = Math.min(canUse, available);

            if (use > 0) {
                change.put(denom, use);
                BigDecimal deducted = denomValue.multiply(BigDecimal.valueOf(use));
                remaining = remaining.subtract(deducted);
            }
        }

        if (remaining.compareTo(BigDecimal.ZERO) != 0) {
            throw new VendingMachineException(
                    "Machine cannot make exact change of Rs." + changeAmount + ".");
        }

        for (Map.Entry<IndianCurrency, Integer> entry : change.entrySet()) {
            drawer.deduct(entry.getKey(), entry.getValue());
        }

        return change;
    }

    public void refund(Drawer drawer, Map<IndianCurrency, Integer> inserted) {
        validateDrawer(drawer);
        if (inserted == null || inserted.isEmpty()) {
            return;
        }

        for (Map.Entry<IndianCurrency, Integer> entry : inserted.entrySet()) {
            validateDenomination(entry.getKey());
            drawer.deduct(entry.getKey(), entry.getValue());
        }
    }

    public void addToDrawer(Drawer drawer, IndianCurrency denomination, int count) {
        validateDrawer(drawer);
        validateDenomination(denomination);
        validateCount(count);

        drawer.add(denomination, count);
    }

    // These functionalities seemed to be repetitive hence a separate method

    private void validateDrawer(Drawer drawer) {
        if (drawer == null) {
            throw new VendingMachineException("Drawer cannot be null.");
        }
    }

    private void validateDenomination(IndianCurrency denomination) {
        if (denomination == null) {
            throw new VendingMachineException("Denomination cannot be null.");
        }
    }

    private void validateCount(int count) {
        if (count < 0) {
            throw new VendingMachineException("Count cannot be negative.");
        }
    }
}