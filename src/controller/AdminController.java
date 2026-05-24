package controller;

import model.Food;
import model.Slot;
import model.VendingMachine;
import service.FoodService;
import service.SlotService;
import service.VendingMachineService;
import util.FoodType;
import util.Location;
import util.VegNonVeg;
import util.VendingMachineException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class AdminController {

    private final VendingMachineService vendingMachineService = VendingMachineService.getInstance();
    private final SlotService slotService = SlotService.getInstance();
    private final FoodService foodService = FoodService.getInstance();

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
                             String warning, double price, Location manufacturingLocation,
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

    public void editFoodPrice(String foodId, double newPrice) {
        if (foodId == null || foodId.trim().isEmpty()) {
            throw new VendingMachineException("Food ID cannot be null or empty.");
        }
        if (newPrice < 0) {
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
        // warning is optional — null clears it, so no empty-check here
        foodService.editWarning(foodId, newWarning);
    }

    public List<VendingMachine> getAllVendingMachines() {
        return vendingMachineService.getAllVendingMachines();
    }

    public List<Food> getAllFoods() {
        return foodService.getAllFoods();
    }
}