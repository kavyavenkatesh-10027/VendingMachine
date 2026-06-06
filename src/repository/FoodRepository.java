package repository;

import model.Food;
import util.VendingMachineException;

public class FoodRepository extends BaseRepository<Food> {

    private static FoodRepository instance;

    private FoodRepository() {}

    public static FoodRepository getInstance() {
        if (instance == null) instance = new FoodRepository();
        return instance;
    }

    @Override
    protected String getId(Food food) {
        return food.getProductId();
    }
}
//import model.Food;
//import util.VendingMachineException;
//
//import java.util.*;
//
//public class FoodRepository {
//
//    private static FoodRepository instance;
//    private final Map<String, Food> foods = new HashMap<>();
//
//    private FoodRepository() {}
//
//    public static FoodRepository getInstance() {
//        if (instance == null) {
//            instance = new FoodRepository();
//        }
//        return instance;
//    }
//
//    public void addFood(Food food) {
//        if (food == null) {
//            throw new VendingMachineException("Food cannot be null.");
//        }
//        if (foods.containsKey(food.getProductId())) {
//            throw new VendingMachineException("Food with ID " + food.getProductId() + " already exists.");
//        }
//        foods.put(food.getProductId(), food);
//    }
//
//    public Food findById(String productId) {
//        if (productId == null) {
//            throw new VendingMachineException("Product ID cannot be null");
//        }
//        if (!foods.containsKey(productId)) {
//            throw new VendingMachineException("Food with ID " + productId + " not found.");
//        }
//        return foods.get(productId);
//    }
//
//    public Set<Food> findAll() {
//        return Collections.unmodifiableSet(new HashSet<>(foods.values()));
//    }
//
//    public void removeById(String productId) {
//        if (productId == null) {
//            throw new VendingMachineException("Product ID cannot be null");
//        }
//        if (!foods.containsKey(productId)) {
//            throw new VendingMachineException("Cannot remove. Food with ID " + productId + " does not exist.");
//        }
//        foods.remove(productId);
//    }
//
//    public boolean existsById(String productId) {
//        if (productId == null) {
//            return false;
//        }
//        return foods.containsKey(productId);
//    }
//}
