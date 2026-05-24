package service;

import model.Slot;
import model.VendingMachine;
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
        if (location == null) {
            throw new VendingMachineException("Location cannot be null.");
        }
        if (establishedOn == null) {
            throw new VendingMachineException("Established date cannot be null.");
        }
        if (establishedOn.isAfter(LocalDate.now())) {
            throw new VendingMachineException("Established date cannot be in the future.");
        }
        if (firstSlotFoodItems == null || firstSlotFoodItems.isEmpty()) {
            throw new VendingMachineException(
                    "Cannot create a vending machine without a first slot. Provide at least one food item.");
        }

        String incomingVmId = Generator.peekNextVendingMachineId();

        Slot firstSlot = buildSlotForNewMachine(incomingVmId, firstSlotFoodItems);

        List<Slot> slots = new ArrayList<>();
        slots.add(firstSlot);
        VendingMachine vm = new VendingMachine(location, establishedOn, slots);

        vmRepository.add(vm);
        slotRepository.add(firstSlot);

        return vm;
    }

    public Slot addSlotToVendingMachine(String vendingMachineId, Map<String, Integer> foodItems) {
        if (vendingMachineId == null || vendingMachineId.trim().isEmpty()) {
            throw new VendingMachineException("Vending machine ID cannot be null or empty.");
        }
        if (foodItems == null || foodItems.isEmpty()) {
            throw new VendingMachineException(
                    "Cannot add an empty slot. Provide at least one food item.");
        }

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
        if (!vmRepository.existsById(vendingMachineId)) {
            throw new VendingMachineException(
                    "Cannot build slot, vending machine does not exist: " + vendingMachineId);
        }
        validateFoodItems(foodItems);
        return new Slot(vendingMachineId, foodItems);
    }

    private void validateFoodItems(Map<String, Integer> foodItems) {
        if (foodItems == null || foodItems.isEmpty()) {
            throw new VendingMachineException("A slot must contain at least one food item.");
        }
        for (Map.Entry<String, Integer> entry : foodItems.entrySet()) {
            if (entry.getKey() == null || entry.getKey().trim().isEmpty()) {
                throw new VendingMachineException("Food ID in slot cannot be null or empty.");
            }
            if (entry.getValue() == null || entry.getValue() <= 0) {
                throw new VendingMachineException(
                        "Quantity for food '" + entry.getKey() + "' must be greater than zero.");
            }
        }
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

    public List<VendingMachine> getAllVendingMachines() {
        return vmRepository.findAll();
    }
}