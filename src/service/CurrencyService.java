package service;

import repository.CurrencyRepository;
import util.IndianCurrency;
import util.VendingMachineException;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;

public class CurrencyService {

    private static CurrencyService instance;
    private final CurrencyRepository currencyRepository = CurrencyRepository.getInstance();

    private CurrencyService() {}

    public static CurrencyService getInstance() {
        if (instance == null) {
            instance = new CurrencyService();
        }
        return instance;
    }

    public BigDecimal acceptPayment(Map<IndianCurrency, Integer> inserted) {
        if (inserted == null || inserted.isEmpty()) {
            throw new VendingMachineException("No money inserted.");
        }

        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<IndianCurrency, Integer> entry : inserted.entrySet()) {
            Integer count = entry.getValue();
            if (count == null || count < 0) {
                throw new VendingMachineException(
                        "Count for denomination Rs." + entry.getKey().getValue() + " cannot be negative.");
            }
            currencyRepository.add(entry.getKey(), count);

            BigDecimal denomValue = BigDecimal.valueOf(entry.getKey().getValue());
            total = total.add(denomValue.multiply(BigDecimal.valueOf(count)));
        }

        return total;
    }

    public Map<IndianCurrency, Integer> makeChange(BigDecimal changeAmount) {
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


        for (int i = denominations.length - 1; i >= 0 && remaining.compareTo(BigDecimal.ZERO) > 0; i--) {
            IndianCurrency denom = denominations[i];
            BigDecimal denomValue = BigDecimal.valueOf(denom.getValue());
            int available = currencyRepository.getCount(denom);

            int use = remaining.divideToIntegralValue(denomValue).intValue();
            use = Math.min(use, available);

            if (use > 0) {
                change.put(denom, use);
                // Fixed: Use subtract and multiply for BigDecimal
                remaining = remaining.subtract(denomValue.multiply(BigDecimal.valueOf(use)));
            }
        }

        if (remaining.compareTo(BigDecimal.ZERO) != 0) {
            throw new VendingMachineException(
                    "Machine cannot make exact change of Rs." + changeAmount + ". Please insert exact amount or different denominations.");
        }


        for (Map.Entry<IndianCurrency, Integer> entry : change.entrySet()) {
            currencyRepository.deduct(entry.getKey(), entry.getValue());
        }

        return change;
    }


    public void refund(Map<IndianCurrency, Integer> inserted) {
        if (inserted == null || inserted.isEmpty()) {
            return;
        }
        for (Map.Entry<IndianCurrency, Integer> entry : inserted.entrySet()) {
            currencyRepository.deduct(entry.getKey(), entry.getValue());
        }
    }

    public int totalCashInMachine() {
        return currencyRepository.totalCashInMachine();
    }

    public Map<IndianCurrency, Integer> getDrawer() {
        return currencyRepository.getDrawer();
    }
}
