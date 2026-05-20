package model;

import util.VegNonVeg;

import java.time.LocalDate;
import java.util.List;

public class Food extends Product{
    private final VegNonVeg vegOrNonVeg;
    private List<String> ingredients;
    private final LocalDate expiryDate;


    public Food(String productId, String productName, String brand, String description, String warning, double price, String manufacturingLocation, LocalDate manufacturingDate, VegNonVeg vegOrNonVeg, List<String> ingredients, LocalDate expiryDate) {
        super(productId, productName, brand, description, warning, price, manufacturingLocation, manufacturingDate);
        this.vegOrNonVeg = vegOrNonVeg;
        this.ingredients = ingredients;
        this.expiryDate=expiryDate;
    }

    @Override
    public double getPrice() {
        return super.getPrice();
    }

    public List<String> getIngredients() {
        return ingredients;
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
