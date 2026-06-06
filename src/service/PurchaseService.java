package service;

import model.*;
import repository.*;
import util.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PurchaseService {

    private static PurchaseService instance;

    private final FoodRepository foodRepository = FoodRepository.getInstance();
    private final PurchaseRepository purchaseRepository = PurchaseRepository.getInstance();
    private final CurrencyService currencyService = CurrencyService.getInstance();

    private PurchaseService() {}

    public static PurchaseService getInstance() {
        if (instance == null) {
            instance = new PurchaseService();
        }
        return instance;
    }


    public Purchase processPurchase(VendingMachine vm,
                                    Map<String, Integer> cart,
                                    Map<IndianCurrency, Integer> inserted) {

        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            String foodId = entry.getKey();
            int requestedQty = entry.getValue();

            if (foodId == null || foodId.trim().isEmpty()) {
                throw new VendingMachineException("Food ID in cart cannot be null or empty.");
            }
            if (requestedQty <= 0) {
                throw new VendingMachineException("Quantity for food " + foodId + " must be greater than zero.");
            }

            Food food = foodRepository.findById(foodId);
            if (food == null) {
                throw new VendingMachineException("Food not found in system: " + foodId);
            }

            int stockInMachine = getStockInMachine(vm, foodId);
            if (stockInMachine < requestedQty) {
                throw new VendingMachineException("Insufficient stock for '" + food.getProductName() + "'. " + "Available: " + stockInMachine);
            }
        }

        BigDecimal total = calculateTotal(cart);


        BigDecimal amountPaid = currencyService.acceptPayment(vm.getDrawer(), inserted);

        if (amountPaid.compareTo(total) < 0) {
            // Refunding the inserted amount
            currencyService.refund(vm.getDrawer(), inserted);
            throw new VendingMachineException(
                    "Insufficient payment. Total: Rs." + total + ", Paid: Rs." + amountPaid +"\nCollect refund from the inserting plate");
        }


        BigDecimal changeAmount = amountPaid.subtract(total);
        Map<IndianCurrency, Integer> change;//Having this variable for future use-case if we need to show the consumer the exact denominations

        try {
            change = currencyService.makeChange(vm.getDrawer(), changeAmount);
        } catch (VendingMachineException e) {
            currencyService.refund(vm.getDrawer(), inserted);
            throw e;
        }

        deductStockFromSlots(vm, cart);

        //Using the model class for billing
        Purchase purchase = new Purchase(cart, total, amountPaid, changeAmount);
        purchaseRepository.add(purchase);

        return purchase;
    }

     /// These methods felt like util methods for this particular class, that's why I'm writing them down here.
    private BigDecimal calculateTotal(Map<String, Integer> cart) {
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            Food food = foodRepository.findById(entry.getKey());
            if (food == null) {
                throw new VendingMachineException("Food not found: " + entry.getKey());
            }
            total = total.add(food.getPrice().multiply(BigDecimal.valueOf(entry.getValue())));
        }
        return total;
    }

    public BigDecimal getCartTotal(Map<String, Integer> cart){
        return calculateTotal(cart);
    }

    public int getStockInMachine(VendingMachine vm, String foodId) {
        int total = 0;
        for (Slot slot : vm.getSlotsInVendingMachine()) {
            Integer qty = slot.getFoodItemsInSlot().get(foodId);
            if (qty != null) {
                total += qty;
            }
        }
        return total;
    }

    private void deductStockFromSlots(VendingMachine vm, Map<String, Integer> cart) {
        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            String foodId = entry.getKey();
            int remainingItemsNeeded = entry.getValue();

            for (Slot slot : vm.getSlotsInVendingMachine()) {
                if (remainingItemsNeeded <= 0) break;

                Integer inSlot = slot.getFoodItemsInSlot().get(foodId);
                if (inSlot != null && inSlot > 0) {
                    int deductingFromActiveSlot = Math.min(inSlot, remainingItemsNeeded);
                    slot.removeFoodItemFromSlot(foodId, deductingFromActiveSlot);
                    remainingItemsNeeded -= deductingFromActiveSlot;
                }
            }
        }
    }

    public Set<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }
}