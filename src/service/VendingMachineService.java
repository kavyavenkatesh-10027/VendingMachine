package service;

import model.Slot;
import model.VendingMachine;
import repository.FoodRepository;
import repository.SlotRepository;
import repository.VendingMachineRepository;
import util.Generator;
import util.Location;
import util.VendingMachineException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VendingMachineService {

    private static VendingMachineService instance;
    private final VendingMachineRepository vmRepository = VendingMachineRepository.getInstance();
    private final SlotRepository slotRepository = SlotRepository.getInstance();
    private final FoodRepository foodRepository = FoodRepository.getInstance();

    private VendingMachineService() {}

    public static VendingMachineService getInstance() {
        if (instance == null) {
            instance = new VendingMachineService();
        }
        return instance;
    }

    public VendingMachine createVendingMachine(Location location,
                                               LocalDate establishedOn,
                                               Map<String, Integer> firstSlotFoodItems) {

        if (establishedOn.isAfter(LocalDate.now())) {
            throw new VendingMachineException("Established date cannot be in the future.");
        }

        String incomingVmId = Generator.peekNextVendingMachineId();

        List<Slot> slots = new ArrayList<>();
        VendingMachine vm = new VendingMachine(location, establishedOn, slots);
        Slot firstSlot = buildSlotForNewMachine(incomingVmId, firstSlotFoodItems);
        slots.add(firstSlot);

        vmRepository.add(vm);
        slotRepository.add(firstSlot);

        return vm;
    }

    public Slot addSlotToVendingMachine(String vendingMachineId, Map<String, Integer> foodItems) {

        // Build the slot, id is passed by us to avoid inconsistency.
        VendingMachine vm = getVendingMachineById(vendingMachineId);

        Slot slot = buildSlotForExistingMachine(vendingMachineId, foodItems);

        vm.addSlotToVendingMachine(slot);
        slotRepository.add(slot);
        return slot;
    }

    private Slot buildSlotForNewMachine(String vendingMachineId, Map<String, Integer> foodItems) {
        validateFoodItems(foodItems);
        return new Slot(vendingMachineId, foodItems);
    }

    private Slot buildSlotForExistingMachine(String vendingMachineId, Map<String, Integer> foodItems) {
        validateFoodItems(foodItems);
        return new Slot(vendingMachineId, foodItems);
    }

    public VendingMachine getVendingMachineById(String vendingMachineId) {
        if (vendingMachineId == null || vendingMachineId.trim().isEmpty()) {
            throw new VendingMachineException("Vending machine ID cannot be null or empty.");
        }

        VendingMachine vm = vmRepository.findById(vendingMachineId);
        if (vm == null) {
            throw new VendingMachineException(
                    "No vending machine found with ID: " + vendingMachineId);
        }
        return vm;
    }

    public void removeVendingMachine(String vendingMachineId) {
        if (!vmRepository.existsById(vendingMachineId)) {
            throw new VendingMachineException("Vending machine with ID " + vendingMachineId + " does not exist");
        }

        List<Slot> slotsToRemove = new ArrayList<>(slotRepository.findByVendingMachineId(vendingMachineId));
        for (Slot slot : slotsToRemove) {
            slotRepository.removeById(slot.getSlotId());
        }

        vmRepository.removeById(vendingMachineId);
    }

    public List<VendingMachine> getAllVendingMachines() {
        return vmRepository.findAll();
    }

    //This had multiple usages, because of which a separate method has been created.
    private void validateFoodItems(Map<String, Integer> foodItems) {
        if (foodItems == null || foodItems.isEmpty()) {
            throw new VendingMachineException("A slot must contain at least one food item.");
        }
        for (Map.Entry<String, Integer> entry : foodItems.entrySet()) {
            if (entry.getKey() == null || entry.getKey().trim().isEmpty()) {
                throw new VendingMachineException("Food ID in slot cannot be null or empty.");
            }
            if (!foodRepository.existsById(entry.getKey())){
                throw new VendingMachineException("No food of ID: "+ entry.getKey() + " has been registered");
            }
            if (entry.getValue() == null || entry.getValue() <= 0) {
                throw new VendingMachineException(
                        "Quantity for food '" + entry.getKey() + "' must be greater than zero.");
            }
        }
    }

}