package model;

import util.FoodType;
import util.Location;
import util.VegNonVeg;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class Food extends Product{
    private final VegNonVeg vegOrNonVeg;
    private final List<String> ingredients;
    private final LocalDate expiryDate;
    private final FoodType foodType;

    public Food(String productName, String brand, String description, String warning, BigDecimal price, Location manufacturingLocation, LocalDate manufacturingDate, VegNonVeg vegOrNonVeg, List<String> ingredients, LocalDate expiryDate, FoodType foodType) {
        super(productName, brand, description, warning, price, manufacturingLocation, manufacturingDate);

        if (vegOrNonVeg == null){
            throw new IllegalArgumentException("Must have veg/non-veg mark");
        }

        if (ingredients == null || ingredients.isEmpty()){
            throw new IllegalArgumentException("Ingredients must be mentioned");
        }

        if (expiryDate == null){
            throw new IllegalArgumentException("Product has already expired");
        }

        if (foodType == null){
            throw new IllegalArgumentException("Food variety must always be mentioned");
        }

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
        if (newIngredient == null){
            throw new IllegalArgumentException("You cannot add empty ingredient");
        }
        ingredients.add(newIngredient);
    }

    public void removeIngredient(String ingredientToRemove){
        if (!ingredients.contains(ingredientToRemove)){
            throw new IllegalArgumentException("You cannot remove a non-existing ingredient");
        }
        ingredients.remove(ingredientToRemove);
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
                "Veg/Non-Veg : " + vegOrNonVeg + "\n" +
                "Ingredients : " + ingredients + "\n" +
                "Expiry Date : " + expiryDate + "\n" +
                "Food Type : " + foodType;
    }
}
