package model;

import util.FoodType;
import util.Location;
import util.VegNonVeg;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Food extends Product {
    private final VegNonVeg vegOrNonVeg;
    private final List<String> ingredients;
    private final LocalDate expiryDate;
    private final FoodType foodType;

    private Food(Builder builder) {
        super(builder);   // passes shared fields up to Product
        this.vegOrNonVeg = builder.vegOrNonVeg;
        this.ingredients = builder.ingredients;
        this.expiryDate = builder.expiryDate;
        this.foodType = builder.foodType;
    }

    public static class Builder extends Product.Builder<Builder> {
        private final VegNonVeg vegOrNonVeg;
        private final List<String> ingredients;
        private final LocalDate expiryDate;
        private final FoodType foodType;

        public Builder(String productName, String brand, String description,
                       BigDecimal price, Location manufacturingLocation,
                       LocalDate manufacturingDate,
                       VegNonVeg vegOrNonVeg, List<String> ingredients,
                       LocalDate expiryDate, FoodType foodType) {

            super(productName, brand, description, price, manufacturingLocation, manufacturingDate);

            if (vegOrNonVeg == null)
                throw new IllegalArgumentException("Must have veg/non-veg mark");
            if (ingredients == null || ingredients.isEmpty())
                throw new IllegalArgumentException("Ingredients must be mentioned");
            if (expiryDate == null)
                throw new IllegalArgumentException("Expiry date cannot be null");
            if (foodType == null)
                throw new IllegalArgumentException("Food variety must always be mentioned");

            this.vegOrNonVeg = vegOrNonVeg;
            this.ingredients = ingredients;
            this.expiryDate = expiryDate;
            this.foodType = foodType;
        }

        @Override
        public Food build() {
            return new Food(this);
        }
    }

    public VegNonVeg getVegOrNonVeg() { return vegOrNonVeg; }

    public List<String> getIngredients() { return Collections.unmodifiableList(ingredients); }

    public FoodType getFoodType() { return foodType; }

    public void addIngredient(String newIngredient) {
        if (newIngredient == null)
            throw new IllegalArgumentException("You cannot add empty ingredient");
        ingredients.add(newIngredient);
    }

    public void removeIngredient(String ingredientToRemove) {
        if (!ingredients.contains(ingredientToRemove))
            throw new IllegalArgumentException("You cannot remove a non-existing ingredient");
        ingredients.remove(ingredientToRemove);
    }

    public LocalDate getExpiryDate() { return expiryDate; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return Objects.equals(getProductId(), food.getProductId());
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