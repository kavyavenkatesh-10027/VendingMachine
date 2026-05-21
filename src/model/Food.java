package model;

import util.FoodType;
import util.VegNonVeg;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class Food extends Product{
    private final VegNonVeg vegOrNonVeg;
    private final List<String> ingredients;
    private final LocalDate expiryDate;
    private final FoodType foodType;

    public Food(String productName, String brand, String description, String warning, double price, String manufacturingLocation, LocalDate manufacturingDate, VegNonVeg vegOrNonVeg, List<String> ingredients, LocalDate expiryDate, FoodType foodType) {
        super(productName, brand, description, warning, price, manufacturingLocation, manufacturingDate);
        this.vegOrNonVeg = vegOrNonVeg;
        this.ingredients = ingredients;
        this.expiryDate = expiryDate;
        this.foodType = foodType;
    }

    public VegNonVeg getVegOrNonVeg() {
        return vegOrNonVeg;
    }

    public List<String> getIngredients() {
        return Collections.unmodifiableList(ingredients);
    }

    public FoodType getFoodType() {
        return foodType;
    }

    public void addIngredient(String newIngredient){
        ingredients.add(newIngredient);
    }

    public void removeIngredient(String ingredientToRemove){
        ingredients.remove(ingredientToRemove);
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }
}
