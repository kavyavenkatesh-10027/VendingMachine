package repository;

import model.Food;
import util.VendingMachineException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FoodRepository {

    private static FoodRepository instance;
    private final List<Food> foods = new ArrayList<>();

    private FoodRepository() {}

    public static FoodRepository getInstance() {
        if (instance == null) {
            instance = new FoodRepository();
        }
        return instance;
    }

    public void addFood(Food food) {
        if (food == null) {
            throw new VendingMachineException("Food cannot be null.");
        }
        foods.add(food);
    }

    public Food findById(String productId) {
        for(Food food: foods){
            if (food.getProductId().equals(productId)){
                return food;
            }
        }
        return null;
    }

    public List<Food> findAll() {
        return Collections.unmodifiableList(foods);
    }

    public boolean removeById(String productId) {
        Food foodToRemove = findById(productId);
        if(foodToRemove!=null){
            foods.remove(foodToRemove);
            return true;
        }
        return false;
    }

    public boolean existsById(String productId) {
        for(Food food: foods){
            if (food.getProductId().equals(productId)){
                return true;
            }
        }
        return false;
    }
}
