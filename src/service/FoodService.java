package service;

import model.Food;
import repository.FoodRepository;
import util.FoodType;
import util.VegNonVeg;
import util.VendingMachineException;

import java.time.LocalDate;
import java.util.List;

public class FoodService {

    private static FoodService instance;
    private final FoodRepository foodRepository = FoodRepository.getInstance();

    private FoodService() {}

    public static FoodService getInstance() {
        if (instance == null) {
            instance = new FoodService();
        }
        return instance;
    }


    public Food registerFood(String productName, String brand, String description,
                             String warning, double price, String manufacturingLocation,
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
        if (price < 0) {
            throw new VendingMachineException("Price cannot be negative.");
        }
        if (manufacturingLocation == null || manufacturingLocation.trim().isEmpty()) {
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
        if (expiryDate.isBefore(LocalDate.now())) {
            throw new VendingMachineException("Cannot register an already-expired food item.");
        }
        if (foodType == null) {
            throw new VendingMachineException("Food type cannot be null.");
        }

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
        if (newDescription == null || newDescription.trim().isEmpty()) {
            throw new VendingMachineException("New description cannot be null or empty.");
        }

        getFoodById(foodId).setDescription(newDescription);
    }

    public void editName(String foodId, String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            throw new VendingMachineException("New name cannot be null or empty.");
        }

        getFoodById(foodId).setProductName(newName);
    }

    public void editBrand(String foodId, String newBrand) {
        if (newBrand == null || newBrand.trim().isEmpty()) {
            throw new VendingMachineException("New brand cannot be null or empty.");
        }

        getFoodById(foodId).setBrand(newBrand);
    }

    public void editPrice(String foodId, double newPrice) {
        if (newPrice < 0) {
            throw new VendingMachineException("Price cannot be negative.");
        }
        getFoodById(foodId).setPrice(newPrice);
    }

    public void editWarning(String foodId, String newWarning) {
        // warning is optional, so null is allowed here
        getFoodById(foodId).setWarning(newWarning);
    }
}
