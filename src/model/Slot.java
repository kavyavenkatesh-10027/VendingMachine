package model;

import util.Generator;
import util.VendingMachineException;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class Slot {
    private final String slotId;
    private final String vendingMachineId;
    private final Map<String, Integer> foodItemsInSlot; //has-a relationship(Slot has food items)
    //This is a map for foodId and quantity that are present in that particular slot

    public Slot(String vendingMachineId, Map<String, Integer> foodItemsInSlot){
        this.slotId = Generator.generateSlotId();
        if (vendingMachineId == null || vendingMachineId.trim().isEmpty())
            throw new IllegalArgumentException("Vending machine ID cannot be null or empty.");

        if (foodItemsInSlot == null || foodItemsInSlot.isEmpty()){
            throw new IllegalArgumentException("Slot shouldn't be added without adding items");
        }
        this.vendingMachineId = vendingMachineId; //here will have to check if it exists, would need repository to be done later
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
        if (theIdOfNewFoodToAdd == null){
            throw new IllegalArgumentException("The id of new food to add cannot be empty");
        }

        if (quantity<=0){
            throw new IllegalArgumentException("Must add at least one item to slot");
        }

        foodItemsInSlot.put(theIdOfNewFoodToAdd, quantity);
    }

    public void addMoreOfFoodItemToSlot(String theIdOfFoodItemToRefill, Integer quantity){
        if(foodItemsInSlot.containsKey(theIdOfFoodItemToRefill)) {
            foodItemsInSlot.put(theIdOfFoodItemToRefill, foodItemsInSlot.get(theIdOfFoodItemToRefill) + quantity);
        }else {
            throw new VendingMachineException("The food item is new to the slot, use add new food item to slot.");
        }
    }

    public void removeFoodItemFromSlot(String theIdOfFoodItemToRemove, Integer quantity) {
        if (theIdOfFoodItemToRemove == null || theIdOfFoodItemToRemove.trim().isEmpty()) {
            throw new IllegalArgumentException("Food ID to remove cannot be null or empty.");
        }

        foodItemsInSlot.put(theIdOfFoodItemToRemove, foodItemsInSlot.get(theIdOfFoodItemToRemove) - quantity);
    }

    public void removeFoodTypeFromSlot(String theIdOfFoodToRemove){
        foodItemsInSlot.remove(theIdOfFoodToRemove);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Slot slot = (Slot) o;
        return Objects.equals(getSlotId(), slot.getSlotId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(slotId);
    }
    @Override
    public String toString() {
        return "Slot ID : " + slotId + "\n" +
                "Vending Machine ID : " + vendingMachineId + "\n" +
                "Food Items In Slot : " + foodItemsInSlot;
    }
}
