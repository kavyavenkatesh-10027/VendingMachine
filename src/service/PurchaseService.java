package service;

import model.Food;
import model.Purchase;
import model.Slot;
import model.VendingMachine;
import repository.FoodRepository;
import repository.PurchaseRepository;
import repository.VendingMachineRepository;
import util.IndianCurrency;
import util.VendingMachineException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class PurchaseService {

    private static PurchaseService instance;

    private final FoodRepository foodRepository = FoodRepository.getInstance();
    private final VendingMachineRepository vmRepository = VendingMachineRepository.getInstance();
    private final PurchaseRepository purchaseRepository = PurchaseRepository.getInstance();
    private final CurrencyService currencyService = CurrencyService.getInstance();

    private PurchaseService() {}

    public static PurchaseService getInstance() {
        if (instance == null) {
            instance = new PurchaseService();
        }
        return instance;
    }


    public Purchase processPurchase(String vendingMachineId,
                                    Map<String, Integer> cart,
                                    Map<IndianCurrency, Integer> inserted) {

        if (vendingMachineId == null || vendingMachineId.trim().isEmpty()) {
            throw new VendingMachineException("Vending machine ID cannot be null or empty.");
        }
        if (cart == null || cart.isEmpty()) {
            throw new VendingMachineException("Cart cannot be empty. Please select at least one item.");
        }
        if (inserted == null || inserted.isEmpty()) {
            throw new VendingMachineException("No money inserted. Please insert payment.");
        }

        VendingMachine vm = vmRepository.findById(vendingMachineId);
        if (vm == null) {
            throw new VendingMachineException("No vending machine found with ID: " + vendingMachineId);
        }


        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            String foodId = entry.getKey();
            int requestedQty = entry.getValue();

            if (foodId == null || foodId.trim().isEmpty()) {
                throw new VendingMachineException("Food ID in cart cannot be null or empty.");
            }
            if (requestedQty <= 0) {
                throw new VendingMachineException(
                        "Quantity for food " + foodId + " must be greater than zero.");
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


        BigDecimal amountPaid = currencyService.acceptPayment(inserted);

        if (amountPaid.intValue() < total.intValue()) {
            // Refunding the inserted amount
            currencyService.refund(inserted);
            System.out.println("Money refunded");
            throw new VendingMachineException(
                    "Insufficient payment. Total: Rs." + total + ", Paid: Rs." + amountPaid);
        }


        BigDecimal changeAmount = amountPaid.subtract(total);
        Map<IndianCurrency, Integer> change;

        try {
            change = currencyService.makeChange(changeAmount);
        } catch (VendingMachineException e) {
            currencyService.refund(inserted);
            throw e;
        }

        deductStockFromSlots(vm, cart);

        //Using the model class for billing
        Purchase purchase = new Purchase(cart, total, amountPaid, changeAmount);
        purchaseRepository.add(purchase);

        return purchase;
    }

     /// These methods felt like util methods for this particular class, that's why I'm writing them down here.
    public BigDecimal calculateTotal(Map<String, Integer> cart) {
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

    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }
}