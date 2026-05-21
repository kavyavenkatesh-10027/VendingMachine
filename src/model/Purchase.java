package model;

import util.Generator;

import java.time.LocalDateTime;
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
        return quantityOfProductsPurchased;
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

    //    public void addProductToThePurchase(String productId, Integer quantity){
//        quantityOfProductsPurchased.put(productId, quantity);
//    }
//
//    public void removeProductToThePurchase(String productId){
//        quantityOfProductsPurchased.remove(productId);
//    }
}
