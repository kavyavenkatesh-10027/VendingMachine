package controller;

import model.*;
import service.*;
import util.*;

import java.math.BigDecimal;
import java.util.Map;

public class ConsumerController extends BaseController {

    private final PurchaseService purchaseService = PurchaseService.getInstance();

    public Purchase buyProducts(String vendingMachineId,
                                Map<String, Integer> cart,
                                Map<IndianCurrency, Integer> inserted) {
        if (vendingMachineId == null || vendingMachineId.trim().isEmpty()) {
            throw new VendingMachineException("Vending machine ID cannot be null or empty.");
        }
        if (cart == null || cart.isEmpty()) {
            throw new VendingMachineException("Cart is empty. Please select at least one product.");
        }
        if (inserted == null || inserted.isEmpty()) {
            throw new VendingMachineException("No money inserted. Please insert payment.");
        }

        VendingMachine vm = vmRepository.findById(vendingMachineId);
        if (vm == null) {
            throw new VendingMachineException("No vending machine found with ID: " + vendingMachineId);
        }

        return purchaseService.processPurchase(vm, cart, inserted);
    }

    public BigDecimal getCartTotal(Map<String, Integer> cart) {
        if (cart == null || cart.isEmpty()) {
            throw new VendingMachineException("Cart is empty.");
        }
        return purchaseService.calculateTotal(cart);
    }

    public int getAvailableStock(String vendingMachineId, String foodId) {
        if (vendingMachineId == null || vendingMachineId.trim().isEmpty()) {
            throw new VendingMachineException("Vending machine ID cannot be null or empty.");
        }
        if (foodId == null || foodId.trim().isEmpty()) {
            throw new VendingMachineException("Food ID cannot be null or empty.");
        }
        return getAvailableQuantityForOneProduct(vendingMachineId, foodId);
    }
}