package model;

import util.Generator;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

public class Purchase {
    private final String purchaseId;
    private final LocalDateTime purchaseTime;
    private final Map<String, Integer> quantityOfProductsPurchased;
    //The above is a map for Product id -> Quantity bought.
    private final double totalAmount;
    private final double moneyPaidByCustomer;
    private final double moneyToBeReturnedByVendingMachine;

    public Purchase(Map<String, Integer> quantityOfProductsPurchased, double totalAmount, double moneyPaidByCustomer, double moneyToBeReturnedByVendingMachine){
        this.purchaseId = Generator.generatePurchaseId();
        this.purchaseTime = LocalDateTime.now();

        if(quantityOfProductsPurchased == null || quantityOfProductsPurchased.isEmpty()){
            throw new IllegalArgumentException("Purchase isn't logical with no product");
        }

        if (totalAmount<0){
            throw new IllegalArgumentException("Total amount cannot be negative");
        }

        if (moneyPaidByCustomer<0){
            throw new IllegalArgumentException("Money paid by customer cannot be negative");
        }

        if (moneyToBeReturnedByVendingMachine<0){
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

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getMoneyPaidByCustomer() {
        return moneyPaidByCustomer;
    }

    public double getMoneyToBeReturnedByVendingMachine() {
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

    //    public void addProductToThePurchase(String productId, Integer quantity){
//        quantityOfProductsPurchased.put(productId, quantity);
//    }
//
//    public void removeProductToThePurchase(String productId){
//        quantityOfProductsPurchased.remove(productId);
//    }
}
