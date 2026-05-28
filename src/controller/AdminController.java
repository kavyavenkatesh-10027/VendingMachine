package controller;

import model.*;
import service.*;
import util.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class AdminController extends BaseController{

    private final VendingMachineService vendingMachineService = VendingMachineService.getInstance();
    private final SlotService slotService = SlotService.getInstance();
    private final FoodService foodService = FoodService.getInstance();
    private final CurrencyService currencyService = CurrencyService.getInstance();
    private final PurchaseService purchaseService = PurchaseService.getInstance();

    public VendingMachine createVendingMachine(Location location,
                                               LocalDate establishedOn,
                                               Map<String, Integer> firstSlotFoodItems) {
        if (location == null) {
            throw new VendingMachineException("Location cannot be null.");
        }
        if (establishedOn == null) {
            throw new VendingMachineException("Established date cannot be null.");
        }
        if (establishedOn.isAfter(LocalDate.now())) {
            throw new VendingMachineException("Established date cannot be in the future.");
        }
        if (firstSlotFoodItems == null || firstSlotFoodItems.isEmpty()) {
            throw new VendingMachineException(
                    "First slot must have at least one food item.");
        }

        return vendingMachineService.createVendingMachine(location, establishedOn, firstSlotFoodItems);
    }

    public Slot addSlotToVendingMachine(String vendingMachineId, Map<String, Integer> foodItems) {
        if (vendingMachineId == null || vendingMachineId.trim().isEmpty()) {
            throw new VendingMachineException("Vending machine ID cannot be null or empty.");
        }
        if (foodItems == null || foodItems.isEmpty()) {
            throw new VendingMachineException("Slot must have at least one food item.");
        }

        return vendingMachineService.addSlotToVendingMachine(vendingMachineId, foodItems);
    }

    public Food registerFood(String productName, String brand, String description,
                             String warning, BigDecimal price, String manufacturingLocation,
                             LocalDate manufacturingDate, VegNonVeg vegOrNonVeg,
                             List<String> ingredients, LocalDate expiryDate, FoodType foodType) {
        return foodService.registerFood(productName, brand, description, warning, price,
                manufacturingLocation, manufacturingDate, vegOrNonVeg,
                ingredients, expiryDate, foodType);
    }

    public void addNewFoodTypeToSlot(String slotId, String foodId, int quantity) {
        if (slotId == null || slotId.trim().isEmpty()) {
            throw new VendingMachineException("Slot ID cannot be null or empty.");
        }
        if (foodId == null || foodId.trim().isEmpty()) {
            throw new VendingMachineException("Food ID cannot be null or empty.");
        }
        if (quantity <= 0) {
            throw new VendingMachineException("Quantity must be greater than zero.");
        }
        slotService.addNewFoodTypeToSlot(slotId, foodId, quantity);
    }

    public void refillFoodInSlot(String slotId, String foodId, int quantity) {
        if (slotId == null || slotId.trim().isEmpty()) {
            throw new VendingMachineException("Slot ID cannot be null or empty.");
        }
        if (foodId == null || foodId.trim().isEmpty()) {
            throw new VendingMachineException("Food ID cannot be null or empty.");
        }
        if (quantity <= 0) {
            throw new VendingMachineException("Quantity must be greater than zero.");
        }
        slotService.refillFoodInSlot(slotId, foodId, quantity);
    }

    public void editFoodDescription(String foodId, String newDescription) {
        if (foodId == null || foodId.trim().isEmpty()) {
            throw new VendingMachineException("Food ID cannot be null or empty.");
        }
        if (newDescription == null || newDescription.trim().isEmpty()) {
            throw new VendingMachineException("New description cannot be null or empty.");
        }
        foodService.editDescription(foodId, newDescription);
    }

    public void editFoodName(String foodId, String newName) {
        if (foodId == null || foodId.trim().isEmpty()) {
            throw new VendingMachineException("Food ID cannot be null or empty.");
        }
        if (newName == null || newName.trim().isEmpty()) {
            throw new VendingMachineException("New name cannot be null or empty.");
        }
        foodService.editName(foodId, newName);
    }

    public void editFoodPrice(String foodId, BigDecimal newPrice) {
        if (foodId == null || foodId.trim().isEmpty()) {
            throw new VendingMachineException("Food ID cannot be null or empty.");
        }
        if (newPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new VendingMachineException("Price cannot be negative.");
        }
        foodService.editPrice(foodId, newPrice);
    }

    public void editFoodBrand(String foodId, String newBrand) {
        if (foodId == null || foodId.trim().isEmpty()) {
            throw new VendingMachineException("Food ID cannot be null or empty.");
        }
        if (newBrand == null || newBrand.trim().isEmpty()) {
            throw new VendingMachineException("New brand cannot be null or empty.");
        }
        foodService.editBrand(foodId, newBrand);
    }

    public void editFoodWarning(String foodId, String newWarning) {
        if (foodId == null || foodId.trim().isEmpty()) {
            throw new VendingMachineException("Food ID cannot be null or empty.");
        }
        // warning is optional, null clears it, so no check here
        foodService.editWarning(foodId, newWarning);
    }

    public List<VendingMachine> getAllVendingMachines() {
        return vendingMachineService.getAllVendingMachines();
    }

    public List<Food> getAllFoods() {
        return foodService.getAllFoods();
    }

    public Map<Food, Integer> getProductCountForMachine(String vendingMachineId) {
        if (vendingMachineId == null || vendingMachineId.trim().isEmpty()) {
            throw new VendingMachineException("Vending machine ID cannot be null or empty.");
        }
        return viewAvailableQuantityForAllProducts(vendingMachineId);
    }

    public void addCashToDrawer(String vendingMachineId, Map<IndianCurrency, Integer> denominations) {
        if (vendingMachineId == null || vendingMachineId.trim().isEmpty()) {
            throw new VendingMachineException("Vending machine ID cannot be null or empty.");
        }
        if (denominations == null || denominations.isEmpty()) {
            throw new VendingMachineException("Denomination map cannot be null or empty.");
        }
        for (Map.Entry<IndianCurrency, Integer> entry : denominations.entrySet()) {
            if (entry.getValue() == null || entry.getValue() <= 0) {
                throw new VendingMachineException(
                        "Count for Rs." + entry.getKey().getValue() + " must be greater than zero.");
            }
        }
        VendingMachine vm = vendingMachineService.getVendingMachineById(vendingMachineId);
        for (Map.Entry<IndianCurrency, Integer> entry : denominations.entrySet()) {
            currencyService.addToDrawer(vm.getDrawer(), entry.getKey(), entry.getValue());
        }
    }

    public Map<IndianCurrency, Integer> getDenominationBreakdown(String vendingMachineId) {
        VendingMachine vm = vendingMachineService.getVendingMachineById(vendingMachineId);
        return vm.getDrawer().getDenominations();
    }

    public int getTotalCashInMachine(String vendingMachineId) {
        VendingMachine vm = vendingMachineService.getVendingMachineById(vendingMachineId);
        return vm.getDrawer().totalCash();
    }

    public List<Purchase> getAllPurchases() {
        return purchaseService.getAllPurchases();
    }
}