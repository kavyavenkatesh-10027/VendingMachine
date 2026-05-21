package model;

import util.Generator;
import java.util.Collections;
import java.util.Map;

public class Slot {
    private final String slotId;
    private final String vendingMachineId;
    private final Map<String, Integer> foodItemsInSlot; //has-a relationship(Slot has food items)
    //This is a map for foodId and quantity that are present in that particular slot

    public Slot(String vendingMachineId, Map<String, Integer> foodItemsInSlot){
        this.slotId = Generator.generateSlotId();
        this.vendingMachineId = vendingMachineId; //here will have to check if it exists, would need repository to be done later

        if (foodItemsInSlot == null || foodItemsInSlot.isEmpty()){
            throw new IllegalArgumentException("Slot shouldn't be added without adding items");
        }

        this.foodItemsInSlot = foodItemsInSlot;
    }

    public String getSlotId() {
        return slotId;
    }

    public String getVendingMachineId() {
        return vendingMachineId;
    }

    public Map<String, Integer> getFoodItemsInSlot() {
        return Collections.unmodifiableMap(foodItemsInSlot);
    }

    public void addNewFoodTypeToSlot(String theIdOfNewFoodToAdd, Integer quantity){
        foodItemsInSlot.put(theIdOfNewFoodToAdd, quantity);
    }

    public void addMoreOfFoodItemToSlot(String theIdOfFoodItemToRefill, Integer quantity){
        if(foodItemsInSlot.containsKey(theIdOfFoodItemToRefill)) {
            foodItemsInSlot.put(theIdOfFoodItemToRefill, foodItemsInSlot.get(theIdOfFoodItemToRefill) + quantity);
        }
    }

    public void removeFoodItemFromSlot(String theIdOfFoodItemToRemove, Integer quantity){
        foodItemsInSlot.put(theIdOfFoodItemToRemove, foodItemsInSlot.get(theIdOfFoodItemToRemove) - quantity);
    }

    public void removeFoodTypeFromSlot(String theIdOfFoodToRemove){
        foodItemsInSlot.remove(theIdOfFoodToRemove);
    }

    @Override
    public String toString() {
        return "Slot ID : " + slotId + "\n" +
                "Vending Machine ID : " + vendingMachineId + "\n" +
                "Food Items In Slot : " + foodItemsInSlot;
    }
}
