package repository;

import model.Food;
import model.Slot;
import util.VendingMachineException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SlotRepository {

    private static SlotRepository instance;
    private final List<Slot> slots = new ArrayList<>();
    private static long maxItems = 10;

    private SlotRepository() {}

    public static SlotRepository getInstance() {
        if (instance == null) {
            instance = new SlotRepository();
        }
        return instance;
    }

    public static long getMaxItems() {
        return maxItems;
    }

    public static void setMaxItems(long maxItems) {
        SlotRepository.maxItems = maxItems;
    }

    public boolean hasVacancy(String slotId){
        return findById(slotId).getFoodItemsInSlot().size()<maxItems;
    }

    public void add(Slot slot) {
        if (slot == null) {
            throw new VendingMachineException("Slot cannot be null.");
        }
        slots.add(slot);
    }

    public Slot findById(String slotId) {
        for (Slot slot : slots) {
            if (slot.getSlotId().equals(slotId)) {
                return slot;
            }
        }
        return null;
    }

    public List<Slot> findByVendingMachineId(String vendingMachineId) {
        List<Slot> result = new ArrayList<>();
        for (Slot slot : slots) {
            if (slot.getVendingMachineId().equals(vendingMachineId)) {
                result.add(slot);
            }
        }
        return Collections.unmodifiableList(result);
    }

    public List<Slot> findAll() {
        return Collections.unmodifiableList(slots);
    }

    public void removeById(String slotId) {
        Slot SlotToRemove = findById(slotId);
        if (SlotToRemove != null) {
            slots.remove(SlotToRemove);
        }
    }

    public boolean existsById(String slotId) {
        for(Slot slot: slots){
            if (slot.getSlotId().equals(slotId)){
                return true;
            }
        }
        return false;
    }
}
