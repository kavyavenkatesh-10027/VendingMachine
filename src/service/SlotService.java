package service;

import model.*;
import repository.*;
import util.VendingMachineException;

import java.util.Set;

public class SlotService {

    private static SlotService instance;
    private final SlotRepository slotRepository = SlotRepository.getInstance();
    private final FoodRepository foodRepository = FoodRepository.getInstance();
    private final VendingMachineRepository vmRepository = VendingMachineRepository.getInstance();

    private SlotService() {}

    public static SlotService getInstance() {
        if (instance == null) {
            instance = new SlotService();
        }
        return instance;
    }

    public Slot getSlotById(String slotId) {
         return slotRepository.findById(slotId);
    }

    public void addNewFoodTypeToSlot(String slotId, String foodId, int quantity) {
        Slot slot = getSlotById(slotId);

        if (!foodRepository.existsById(foodId)) {
            throw new VendingMachineException("Food with ID " + foodId + " does not exist. Register the food first.");
        }
        if (slot.getFoodItemsInSlot().containsKey(foodId)) {
            throw new VendingMachineException("Food " + foodId + " is already in slot " + slotId + ". Use refillFoodInSlot instead.");
        }

        slot.addNewFoodTypeToSlot(foodId, quantity);
    }

    public void removeFoodTypeFromSlot(String foodId) {
    //Here the input need not be validated since this method gets called from removeFood in food service that validates the food item
        Set<Slot> allSlots = slotRepository.findAll();
        for (Slot slot : allSlots) {
            if (slot.getFoodItemsInSlot().containsKey(foodId)) {
                slot.removeFoodTypeFromSlot(foodId);
            }
        }
    }

    public void refillFoodInSlot(String slotId, String foodId, int quantity) {
        Slot slot = getSlotById(slotId);

        if (!slot.getFoodItemsInSlot().containsKey(foodId)) {
            throw new VendingMachineException("Food " + foodId + " is not in slot " + slotId + ". Use addNewFoodTypeToSlot instead.");
        }

        slot.addMoreOfFoodItemToSlot(foodId, quantity);
    }

    public void removeSlot(String slotId) {
        Slot slot = getSlotById(slotId);

        VendingMachine vm = vmRepository.findById(slot.getVendingMachineId());
        if (vm != null) {
            vm.removeSlotFromVendingMachine(slot);
        }

        slotRepository.removeById(slotId);
    }
}
