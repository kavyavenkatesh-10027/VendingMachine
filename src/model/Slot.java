package model;

import util.Generator;
import java.util.Collections;
import java.util.Map;

public class Slot {
    private final String slotId;
    private final String vendingMachineId;
    //has-a relationship(Slot has food items)
    private final Map<String, Integer> foodItemsInSlot;
    //This is a map for foodId and quantity that are present in that particular slot

    public Slot(String vendingMachineId, Map<String, Integer> foodItemsInSlot){
        this.slotId = Generator.generateSlotId();
        this.vendingMachineId = vendingMachineId;
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

}
