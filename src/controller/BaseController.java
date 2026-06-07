package controller;

import model.Food;
import model.VendingMachine;
import service.*;
import util.VendingMachineException;

//todo interface/abstract
//todo naming
//
import java.util.*;

public abstract class BaseController {

    protected final VendingMachineService vendingMachineService = VendingMachineService.getInstance();
    protected final SlotService slotService = SlotService.getInstance();
    protected final FoodService foodService = FoodService.getInstance();
    protected final CurrencyService currencyService = CurrencyService.getInstance();
    protected final PurchaseService purchaseService = PurchaseService.getInstance();

    public VendingMachine viewVendingMachine(String vendingMachineId) {

        if (vendingMachineId == null || vendingMachineId.trim().isEmpty()) {
            throw new VendingMachineException("Vending machine ID cannot be null or empty.");
        }

        return vendingMachineService.getVendingMachineById(vendingMachineId);
    }

    public Set<VendingMachine> viewAllVendingMachines() {
        return vendingMachineService.getAllVendingMachines();
    }

    public Set<Food> viewAvailableProducts(String vendingMachineId) {

        if (vendingMachineId == null || vendingMachineId.trim().isEmpty()) {
            throw new VendingMachineException("Vending machine ID cannot be null or empty.");
        }

        return vendingMachineService.viewAvailableProducts(vendingMachineId);
    }

    public Map<String, Integer> viewAvailableQuantityForAllProducts(String vendingMachineId) {

        if (vendingMachineId == null || vendingMachineId.trim().isEmpty()) {
            throw new VendingMachineException("Vending machine ID cannot be null or empty.");
        }

        return vendingMachineService.viewAvailableQuantityForAllProducts(vendingMachineId);
    }


    public int getAvailableQuantityForOneProduct(String vendingMachineId, String foodId) {

        if (vendingMachineId == null || vendingMachineId.trim().isEmpty()) {
            throw new VendingMachineException("Vending machine ID cannot be null or empty.");
        }
        if (foodId == null || foodId.trim().isEmpty()) {
            throw new VendingMachineException("Food ID cannot be null or empty.");
        }

        return vendingMachineService.getAvailableQuantityForOneProduct(vendingMachineId, foodId);
    }
}