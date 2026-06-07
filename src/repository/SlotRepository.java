package repository;

import model.Slot;

import java.util.*;

public class SlotRepository extends BaseRepository<Slot> {

    private static SlotRepository instance;
    private final Map<String, List<Slot>> slotsInEveryMachine = new HashMap<>();

    private SlotRepository() {}

    public static SlotRepository getInstance() {
        if (instance == null) instance = new SlotRepository();
        return instance;
    }

    @Override
    protected String getId(Slot slot) {
        return slot.getSlotId();
    }

    @Override
    public void add(Slot slot) {
        super.add(slot);

        List<Slot> slotsInMachine = slotsInEveryMachine.get(slot.getVendingMachineId());
        slotsInMachine.add(slot);

        slotsInEveryMachine.put(slot.getVendingMachineId(), slotsInMachine);
    }

    public List<Slot> findByVendingMachineId(String vendingMachineId) {
        List<Slot> result = new ArrayList<>();
        for (Map.Entry<String, List<Slot>> machineSlotPair : slotsInEveryMachine.entrySet()) {
            if (machineSlotPair.getKey().equals(vendingMachineId)) {
                result = machineSlotPair.getValue();
            }
        }
        return Collections.unmodifiableList(result);
    }
}

//package repository;
//
//import model.Slot;
//import util.VendingMachineException;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//public class SlotRepository {
//
//    private static SlotRepository instance;
//    private final List<Slot> slots = new ArrayList<>();
//    private static long maxItems = 10;
//
//    private SlotRepository() {}
//
//    public static SlotRepository getInstance() {
//        if (instance == null) {
//            instance = new SlotRepository();
//        }
//        return instance;
//    }
//
//    public static long getMaxItems() {
//        return maxItems;
//    }
//
//    public static void setMaxItems(long maxItems) {
//        SlotRepository.maxItems = maxItems;
//    }
//
//    public boolean hasVacancy(String slotId){
//        return findById(slotId).getFoodItemsInSlot().size()<maxItems;
//    }
//
//    public void add(Slot slot) {
//        if (slot == null) {
//            throw new VendingMachineException("Slot cannot be null.");
//        }
//        slots.add(slot);
//    }
//
//    public Slot findById(String slotId) {
//        for (Slot slot : slots) {
//            if (slot.getSlotId().equals(slotId)) {
//                return slot;
//            }
//        }
//        return null;
//    }
//
//    public List<Slot> findByVendingMachineId(String vendingMachineId) {
//        List<Slot> result = new ArrayList<>();
//        for (Slot slot : slots) {
//            if (slot.getVendingMachineId().equals(vendingMachineId)) {
//                result.add(slot);
//            }
//        }
//        return Collections.unmodifiableList(result);
//    }
//
//    public List<Slot> findAll() {
//        return Collections.unmodifiableList(slots);
//    }
//
//    public void removeById(String slotId) {
//        Slot SlotToRemove = findById(slotId);
//        if (SlotToRemove != null) {
//            slots.remove(SlotToRemove);
//        }
//    }
//
//    public boolean existsById(String slotId) {
//        for(Slot slot: slots){
//            if (slot.getSlotId().equals(slotId)){
//                return true;
//            }
//        }
//        return false;
//    }
//}
