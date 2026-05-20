package model;

import java.util.HashMap;
import java.util.Map;

public class Purchase {
    private Map<String, Integer> quantityOfProductsPurchased = new HashMap<>();
    //The above is a map for Product id -> Quantity bought.
    private final double totalAmount;
    private final double moneyPayedByCustomer;
    private final double moneyToBeReturnedByVendingMachine;

    public Purchase(Map<String, Integer> quantityOfProductsPurchased, double totalAmount, double moneyPayedByCustomer, double moneyToBeReturnedByVendingMachine){
        this.quantityOfProductsPurchased = quantityOfProductsPurchased;
        this.totalAmount = totalAmount;
        this.moneyPayedByCustomer =moneyPayedByCustomer;
        this.moneyToBeReturnedByVendingMachine = moneyToBeReturnedByVendingMachine;
    }

    public Map<String, Integer> getQuantityOfProductsPurchased() {
        return quantityOfProductsPurchased;
    }

    public void addProductToThePurchase(String productId, Integer quantity){
        quantityOfProductsPurchased.put(productId, quantity);
    }

    public void removeProductToThePurchase(String productId){
        quantityOfProductsPurchased.remove(productId);
    }
}
