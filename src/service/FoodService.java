package service;

import model.*;
import repository.*;
import util.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class FoodService {

    private static FoodService instance;
    private final FoodRepository foodRepository = FoodRepository.getInstance();
    private final SlotService slotService = SlotService.getInstance();

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
        //Input validation is done inside model class during direct creation
        if (expiryDate.isBefore(LocalDate.now())) {
            throw new VendingMachineException("Cannot register an already-expired food item.");
        }
        Food food = new Food.Builder(productName, brand, description, price,
                manufacturingLocation, manufacturingDate, vegOrNonVeg,
                ingredients, expiryDate, foodType).warning(warning).build();
        foodRepository.add(food);
        return food;
    }

    public Food getFoodById(String foodId) {
        return foodRepository.findById(foodId);
    }

    public Set<Food> getAllFoods() {
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
        slotService.removeFoodTypeFromSlot(foodId);
        foodRepository.removeById(foodId);
    }
}
