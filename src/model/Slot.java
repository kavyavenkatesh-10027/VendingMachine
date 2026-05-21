package model;

import util.Generator;

import java.util.Collections;
import java.util.Map;

public class Slot {
    private final String slotId;
    //has-a relationship(Slot has food items)
    private final Map<String, Integer> foodItemsInSlot;
    //This is a map for foodId and quantity that are present in that particular slot

    public Slot(Map<String, Integer> foodItemsInSlot){
        this.slotId = Generator.generateSlotId();
        this.foodItemsInSlot = foodItemsInSlot;
    }

    public String getSlotId() {
        return slotId;
    }

    public Map<String, Integer> getFoodItemsInSlot() {
        return Collections.unmodifiableMap(foodItemsInSlot);
    }

    public void addFoodItemToSlot(String theIdOfFoodToAdd, Integer quantity){
        foodItemsInSlot.put(theIdOfFoodToAdd, quantity);
    }

    public void removeFoodItemFromSlot(String foodToRemove){
        foodItemsInSlot.remove(foodToRemove);
    }

}
