package service;

import model.Food;
import model.Slot;
import model.VendingMachine;
import repository.FoodRepository;
import repository.SlotRepository;
import repository.VendingMachineRepository;
import util.Generator;
import util.Location;
import util.VendingMachineException;

import java.time.LocalDate;
import java.util.*;

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

        VendingMachine vm = new VendingMachine(location, establishedOn, new ArrayList<>());
        vmRepository.add(vm);

        Slot firstSlot = buildSlotForMachine(vm.getVendingMachineId(), firstSlotFoodItems);
        vm.addSlotToVendingMachine(firstSlot);
        slotRepository.add(firstSlot);

        return vm;
    }

    public Slot addSlotToVendingMachine(String vendingMachineId, Map<String, Integer> foodItems) {

        // Build the slot, id is passed by us to avoid inconsistency.
        VendingMachine vm = getVendingMachineById(vendingMachineId);

        Slot slot = buildSlotForMachine(vendingMachineId, foodItems);

        vm.addSlotToVendingMachine(slot);
        slotRepository.add(slot);
        return slot;
    }

    private Slot buildSlotForMachine(String vendingMachineId, Map<String, Integer> foodItems) {
        validateFoodItems(foodItems);
        return new Slot(vendingMachineId, foodItems);
    }

    public VendingMachine getVendingMachineById(String vendingMachineId) {
        return vmRepository.findById(vendingMachineId);
    }

    public Set<Food> viewAvailableProducts(String vendingMachineId) {

        VendingMachine vm = getVendingMachineById(vendingMachineId);
        Set<Food> available = new HashSet<>();

        for (Slot slot : vm.getSlotsInVendingMachine()) {
            for (Map.Entry<String, Integer> entry : slot.getFoodItemsInSlot().entrySet()) {
                if (entry.getValue() > 0) {
                    Food food = foodRepository.findById(entry.getKey());
                    if (food != null) {
                        available.add(food);
                    }
                }
            }
        }

        return available;
    }

    public Map<String, Integer> viewAvailableQuantityForAllProducts(String vendingMachineId) {
        VendingMachine vm = getVendingMachineById(vendingMachineId);
        Map<String, Integer> availableQuantity = new HashMap<>();

        for (Slot slot : vm.getSlotsInVendingMachine()) {
            for (Map.Entry<String, Integer> entry : slot.getFoodItemsInSlot().entrySet()) {
                if (entry.getValue() > 0) {
                        if(!availableQuantity.containsKey(entry.getKey())) {
                            availableQuantity.put(entry.getKey(), entry.getValue());
                        }else {
                            availableQuantity.put(entry.getKey(), availableQuantity.get(entry.getKey())+entry.getValue());
                        }
                    }
                }
            }

        return availableQuantity;
    }


    public int getAvailableQuantityForOneProduct(String vendingMachineId, String foodId) {

        if(!foodRepository.existsById(foodId)){
            throw new VendingMachineException("Cannot check quantity for a product that does not exist");
        }

        VendingMachine vm = getVendingMachineById(vendingMachineId);
        int total = 0;

        for (Slot slot : vm.getSlotsInVendingMachine()) {
            Integer qty = slot.getFoodItemsInSlot().get(foodId);
            if (qty != null) {
                total += qty;
            }
        }

        return total;
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

    public Set<VendingMachine> getAllVendingMachines() {
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
                throw new VendingMachineException("Quantity for food '" + entry.getKey() + "' must be greater than zero.");
            }
        }
    }

}