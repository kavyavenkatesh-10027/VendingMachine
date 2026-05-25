package model;

import util.Generator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

public class Purchase {
    private final String purchaseId;
    private final LocalDateTime purchaseTime;
    private final Map<String, Integer> quantityOfProductsPurchased;
    // The above is a map for Product id -> Quantity bought.
    private final BigDecimal totalAmount;
    private final BigDecimal moneyPaidByCustomer;
    private final BigDecimal moneyToBeReturnedByVendingMachine;

    public Purchase(Map<String, Integer> quantityOfProductsPurchased,
                    BigDecimal totalAmount,
                    BigDecimal moneyPaidByCustomer,
                    BigDecimal moneyToBeReturnedByVendingMachine) {

        this.purchaseId = Generator.generatePurchaseId();
        this.purchaseTime = LocalDateTime.now();

        if (quantityOfProductsPurchased == null || quantityOfProductsPurchased.isEmpty()) {
            throw new IllegalArgumentException("Purchase isn't logical with no product");
        }
        if (totalAmount == null || totalAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Total amount cannot be negative");
        }
        if (moneyPaidByCustomer == null || moneyPaidByCustomer.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Money paid by customer cannot be negative");
        }
        if (moneyToBeReturnedByVendingMachine == null || moneyToBeReturnedByVendingMachine.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }

        this.quantityOfProductsPurchased = quantityOfProductsPurchased;
        this.totalAmount = totalAmount;
        this.moneyPaidByCustomer = moneyPaidByCustomer;
        this.moneyToBeReturnedByVendingMachine = moneyToBeReturnedByVendingMachine;
    }

    public String getPurchaseId() {
        return purchaseId;
    }

    public LocalDateTime getPurchaseTime() {
        return purchaseTime;
    }

    public Map<String, Integer> getQuantityOfProductsPurchased() {
        return Collections.unmodifiableMap(quantityOfProductsPurchased);
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public BigDecimal getMoneyPaidByCustomer() {
        return moneyPaidByCustomer;
    }

    public BigDecimal getMoneyToBeReturnedByVendingMachine() {
        return moneyToBeReturnedByVendingMachine;
    }

    @Override
    public String toString() {
        return "Purchase ID : " + purchaseId + "\n" +
                "Purchase Time : " + purchaseTime + "\n" +
                "Products Purchased : " + quantityOfProductsPurchased + "\n" +
                "Total Amount : " + totalAmount + "\n" +
                "Money Paid By Customer : " + moneyPaidByCustomer + "\n" +
                "Money To Be Returned : " + moneyToBeReturnedByVendingMachine;
    }
}