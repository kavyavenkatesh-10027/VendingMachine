package model;

import util.Generator;

import java.time.LocalDate;
import java.util.Map;

public class Purchase {
    private final String purchaseId;
    private final LocalDate purchaseTime;
    private final Map<String, Integer> quantityOfProductsPurchased;
    //The above is a map for Product id -> Quantity bought.
    private final double totalAmount;
    private final double moneyPayedByCustomer;
    private final double moneyToBeReturnedByVendingMachine;

    public Purchase(Map<String, Integer> quantityOfProductsPurchased, double totalAmount, double moneyPayedByCustomer, double moneyToBeReturnedByVendingMachine){
        this.purchaseId = Generator.generatePurchaseId();
        this.purchaseTime = LocalDate.now();
        this.quantityOfProductsPurchased = quantityOfProductsPurchased;
        this.totalAmount = totalAmount;
        this.moneyPayedByCustomer =moneyPayedByCustomer;
        this.moneyToBeReturnedByVendingMachine = moneyToBeReturnedByVendingMachine;
    }

    public String getPurchaseId() {
        return purchaseId;
    }

    public LocalDate getPurchaseTime() {
        return purchaseTime;
    }

    public Map<String, Integer> getQuantityOfProductsPurchased() {
        return quantityOfProductsPurchased;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getMoneyPayedByCustomer() {
        return moneyPayedByCustomer;
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
