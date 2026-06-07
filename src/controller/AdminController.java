package controller;

import model.*;
import service.*;
import util.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AdminController extends BaseController{

    public VendingMachine createVendingMachine(Location location,
                                               LocalDate establishedOn,
                                               Map<String, Integer> firstSlotFoodItems) {
        if (location == null) {
            throw new VendingMachineException("Location cannot be null.");
        }
        if (establishedOn == null) {
            throw new VendingMachineException("Established date cannot be null.");
        }
        if (firstSlotFoodItems == null || firstSlotFoodItems.isEmpty()) {
            throw new VendingMachineException("First slot must have at least one food item.");
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
                             String warning, BigDecimal price, Location manufacturingLocation,
                             LocalDate manufacturingDate, VegNonVeg vegOrNonVeg,
                             List<String> ingredients, LocalDate expiryDate, FoodType foodType) {


        if (productName == null || productName.trim().isEmpty()) {
            throw new VendingMachineException("Food name cannot be null or empty.");
        }
        if (brand == null || brand.trim().isEmpty()) {
            throw new VendingMachineException("Brand cannot be null or empty.");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new VendingMachineException("Description cannot be null or empty.");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new VendingMachineException("Price cannot be zero or negative.");
        }
        if (manufacturingLocation == null) {
            throw new VendingMachineException("Manufacturing location cannot be null or empty.");
        }
        if (manufacturingDate == null) {
            throw new VendingMachineException("Manufacturing date cannot be null.");
        }
        if (manufacturingDate.isAfter(LocalDate.now())) {
            throw new VendingMachineException("Manufacturing date cannot be in the future.");
        }
        if (vegOrNonVeg == null) {
            throw new VendingMachineException("Veg/Non-veg classification cannot be null.");
        }
        if (ingredients == null || ingredients.isEmpty()) {
            throw new VendingMachineException("At least one ingredient must be provided.");
        }
        if (expiryDate == null) {
            throw new VendingMachineException("Expiry date cannot be null.");
        }
        if (foodType == null) {
            throw new VendingMachineException("Food type cannot be null.");
        }

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
        if (newPrice==null){
            throw new VendingMachineException("Price cannot be null");
        }
        if (newPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new VendingMachineException("Price cannot be zero or negative.");
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

    public Set<VendingMachine> getAllVendingMachines() {
        return vendingMachineService.getAllVendingMachines();
    }

    public void removeVendingMachine(String vendingMachineId) {
        if (vendingMachineId == null || vendingMachineId.trim().isEmpty()) {
            throw new VendingMachineException("Vending machine ID cannot be null or empty.");
        }
        vendingMachineService.removeVendingMachine(vendingMachineId);
    }

    public void removeSlot(String slotId) {
        if (slotId == null || slotId.trim().isEmpty()) {
            throw new VendingMachineException("Slot ID cannot be null or empty.");
        }
        slotService.removeSlot(slotId);
    }

    public void removeFood(String foodId) {
        if (foodId == null || foodId.trim().isEmpty()) {
            throw new VendingMachineException("Food ID cannot be null or empty.");
        }
        foodService.removeFood(foodId);
    }

    public Set<Food> getAllFoods() {
        return foodService.getAllFoods();
    }

    public Food getFoodById(String foodId){
        if(foodId==null || foodId.trim().isEmpty()){
            throw new VendingMachineException("Product ID cannot be null or empty");
        }
        return foodService.getFoodById(foodId);
    }

    public Map<String, Integer> getProductCountForMachine(String vendingMachineId) {
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
        if (vendingMachineId == null || vendingMachineId.trim().isEmpty()) {
            throw new VendingMachineException("Vending machine ID cannot be null or empty.");
        }

        VendingMachine vm = vendingMachineService.getVendingMachineById(vendingMachineId);
        return vm.getDrawer().getDenominations();
    }

    public BigDecimal getTotalCashInMachine(String vendingMachineId) {
        if (vendingMachineId == null || vendingMachineId.trim().isEmpty()) {
            throw new VendingMachineException("Vending machine ID cannot be null or empty.");
        }

        VendingMachine vm = vendingMachineService.getVendingMachineById(vendingMachineId);
        return vm.getDrawer().totalCash();
    }

    public Set<Purchase> getAllPurchases() {
        return purchaseService.getAllPurchases();
    }
}