package service;

import model.Slot;
import repository.FoodRepository;
import repository.SlotRepository;
import util.VendingMachineException;

public class SlotService {

    private static SlotService instance;
    private final SlotRepository slotRepository = SlotRepository.getInstance();
    private final FoodRepository foodRepository = FoodRepository.getInstance();

    private SlotService() {}

    public static SlotService getInstance() {
        if (instance == null) {
            instance = new SlotService();
        }
        return instance;
    }

    public Slot getSlotById(String slotId) {
        if (slotId == null || slotId.trim().isEmpty()) {
            throw new VendingMachineException("Slot ID cannot be null or empty.");
        }
        Slot slot = slotRepository.findById(slotId);
        if (slot == null) {
            throw new VendingMachineException("No slot found with ID: " + slotId);
        }
        return slot;
    }

    public void addNewFoodTypeToSlot(String slotId, String foodId, int quantity) {
        if (slotId == null || slotId.trim().isEmpty()) {
            throw new VendingMachineException("Slot ID cannot be null or empty.");
        }
        if (foodId == null || foodId.trim().isEmpty()) {
            throw new VendingMachineException("Food ID cannot be null or empty.");
        }
        if (quantity <= 0) {
            throw new VendingMachineException("Quantity must be greater than zero.");
        }

        Slot slot = getSlotById(slotId);

        if (!foodRepository.existsById(foodId)) {
            throw new VendingMachineException(
                    "Food with ID " + foodId + " does not exist. Register the food first.");
        }
        if (slot.getFoodItemsInSlot().containsKey(foodId)) {
            throw new VendingMachineException(
                    "Food " + foodId + " is already in slot " + slotId + ". Use refillFoodInSlot instead.");
        }

        slot.addNewFoodTypeToSlot(foodId, quantity);
    }

    public void refillFoodInSlot(String slotId, String foodId, int quantity) {
        if (slotId == null || slotId.trim().isEmpty()) {
            throw new VendingMachineException("Slot ID cannot be null or empty.");
        }
        if (foodId == null || foodId.trim().isEmpty()) {
            throw new VendingMachineException("Food ID cannot be null or empty.");
        }
        if (quantity <= 0) {
            throw new VendingMachineException("Quantity must be greater than zero.");
        }

        Slot slot = getSlotById(slotId);

        if (!slot.getFoodItemsInSlot().containsKey(foodId)) {
            throw new VendingMachineException(
                    "Food " + foodId + " is not in slot " + slotId + ". Use addNewFoodTypeToSlot instead.");
        }

        slot.addMoreOfFoodItemToSlot(foodId, quantity);
    }
}
