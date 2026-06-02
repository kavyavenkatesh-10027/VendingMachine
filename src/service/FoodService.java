package service;

import model.*;
import repository.*;
import util.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class FoodService {

    private static FoodService instance;
    private final FoodRepository foodRepository = FoodRepository.getInstance();
    private final SlotRepository slotRepository = SlotRepository.getInstance();

    private FoodService() {}

    public static FoodService getInstance() {
        if (instance == null) {
            instance = new FoodService();
        }
        return instance;
    }


    public Food registerFood(String productName, String brand, String description,
                             String warning, BigDecimal price, Location manufacturingLocation,
                             LocalDate manufacturingDate, VegNonVeg vegOrNonVeg,
                             List<String> ingredients, LocalDate expiryDate, FoodType foodType) {

        Food food = new Food(productName, brand, description, warning, price,
                manufacturingLocation, manufacturingDate, vegOrNonVeg,
                ingredients, expiryDate, foodType);
        foodRepository.addFood(food);
        return food;
    }

    public Food getFoodById(String foodId) {
        if (foodId == null || foodId.trim().isEmpty()) {
            throw new VendingMachineException("Food ID cannot be null or empty.");
        }
        Food food = foodRepository.findById(foodId);
        if (food == null) {
            throw new VendingMachineException("No food found with ID: " + foodId);
        }
        return food;
    }

    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    public void editDescription(String foodId, String newDescription) {
        getFoodById(foodId).setDescription(newDescription);
    }

    public void editName(String foodId, String newName) {
        getFoodById(foodId).setProductName(newName);
    }

    public void editBrand(String foodId, String newBrand) {
        getFoodById(foodId).setBrand(newBrand);
    }

    public void editPrice(String foodId, BigDecimal newPrice) {
        getFoodById(foodId).setPrice(newPrice);
    }

    public void editWarning(String foodId, String newWarning) {
        // warning is optional, so null is allowed here
        getFoodById(foodId).setWarning(newWarning);
    }

    public void removeFood(String foodId) {
        getFoodById(foodId); //for verification and preventing program crash

        List<Slot> allSlots = slotRepository.findAll();
        for (Slot slot : allSlots) {
            if (slot.getFoodItemsInSlot().containsKey(foodId)) {
                slot.removeFoodTypeFromSlot(foodId);
            }
        }

        foodRepository.removeById(foodId);
    }
}
