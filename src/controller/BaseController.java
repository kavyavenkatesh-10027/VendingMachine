package controller;

import model.Food;
import model.Slot;
import model.VendingMachine;
import repository.FoodRepository;
import repository.SlotRepository;
import repository.VendingMachineRepository;
import util.VendingMachineException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseController {

    protected final VendingMachineRepository vmRepository = VendingMachineRepository.getInstance();
    protected final FoodRepository foodRepository = FoodRepository.getInstance();
    protected final SlotRepository slotRepository = SlotRepository.getInstance();

    public VendingMachine viewVendingMachine(String vendingMachineId) {

        if (vendingMachineId == null || vendingMachineId.trim().isEmpty()) {
            throw new VendingMachineException("Vending machine ID cannot be null or empty.");
        }
        VendingMachine vm = vmRepository.findById(vendingMachineId);
        if (vm == null) {
            throw new VendingMachineException("No vending machine found with ID: " + vendingMachineId);
        }
        return vm;
    }

    public List<VendingMachine> viewAllVendingMachines() {
        return vmRepository.findAll();
    }

    public List<Food> viewAvailableProducts(String vendingMachineId) {

        if (vendingMachineId == null || vendingMachineId.trim().isEmpty()) {
            throw new VendingMachineException("Vending machine ID cannot be null or empty.");
        }

        VendingMachine vm = viewVendingMachine(vendingMachineId);
        List<Food> available = new ArrayList<>();

        for (Slot slot : vm.getSlotsInVendingMachine()) {
            for (Map.Entry<String, Integer> entry : slot.getFoodItemsInSlot().entrySet()) {
                if (entry.getValue() > 0) {
                    Food food = foodRepository.findById(entry.getKey());
                    if (food != null && !available.contains(food)) {
                        available.add(food);
                    }
                }
            }
        }

        return available;
    }

    public Map<Food, Integer> viewAvailableQuantityForAllProducts(String vendingMachineId) {

        if (vendingMachineId == null || vendingMachineId.trim().isEmpty()) {
            throw new VendingMachineException("Vending machine ID cannot be null or empty.");
        }

        VendingMachine vm = viewVendingMachine(vendingMachineId);
        Map<Food, Integer> availableQuantity = new HashMap<>();

        for (Slot slot : vm.getSlotsInVendingMachine()) {
            for (Map.Entry<String, Integer> entry : slot.getFoodItemsInSlot().entrySet()) {
                if (entry.getValue() > 0) {
                    Food food = foodRepository.findById(entry.getKey());
                    if (food != null){
                        if(!availableQuantity.containsKey(food)) {
                            availableQuantity.put(food, entry.getValue());
                        }else {
                            availableQuantity.put(food, availableQuantity.get(food)+entry.getValue());
                        }
                    }
                }
            }
        }

        return availableQuantity;
    }


    public int getAvailableQuantityForOneProduct(String vendingMachineId, String foodId) {

        if (vendingMachineId == null || vendingMachineId.trim().isEmpty()) {
            throw new VendingMachineException("Vending machine ID cannot be null or empty.");
        }
        if (foodId == null || foodId.trim().isEmpty()) {
            throw new VendingMachineException("Food ID cannot be null or empty.");
        }

        VendingMachine vm = viewVendingMachine(vendingMachineId);
        int total = 0;

        for (Slot slot : vm.getSlotsInVendingMachine()) {
            Integer qty = slot.getFoodItemsInSlot().get(foodId);
            if (qty != null) {
                total += qty;
            }
        }

        return total;
    }
}