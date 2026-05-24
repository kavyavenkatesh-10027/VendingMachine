package repository;

import model.Slot;
import util.VendingMachineException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SlotRepository {

    private static SlotRepository instance;
    private final List<Slot> slots = new ArrayList<>();

    private SlotRepository() {}

    public static SlotRepository getInstance() {
        if (instance == null) {
            instance = new SlotRepository();
        }
        return instance;
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

    public boolean removeById(String slotId) {
        Slot toRemove = findById(slotId);
        if (toRemove != null) {
            slots.remove(toRemove);
            return true;
        }
        return false;
    }

    public boolean existsById(String slotId) {
        return findById(slotId) != null;
    }
}
