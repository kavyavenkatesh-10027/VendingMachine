package repository;

import model.Slot;
import model.VendingMachine;
import util.VendingMachineException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VendingMachineRepository {

    private static VendingMachineRepository instance;
    private final List<VendingMachine> vendingMachines = new ArrayList<>();

    private VendingMachineRepository() {}

    public static VendingMachineRepository getInstance() {
        if (instance == null) {
            instance = new VendingMachineRepository();
        }
        return instance;
    }

    public void add(VendingMachine vm) {
        if (vm == null){
            throw new VendingMachineException("VendingMachine cannot be null.");
        }
        vendingMachines.add(vm);
    }

    public VendingMachine findById(String vendingMachineId) {
        for (VendingMachine vendingMachine: vendingMachines){
            if (vendingMachine.getVendingMachineId().equals(vendingMachineId)){
                return vendingMachine;
            }
        }
        return null;
    }

    public List<VendingMachine> findAll() {
        return Collections.unmodifiableList(vendingMachines);
    }

    public boolean removeById(String vendingMachineId) {
        VendingMachine vendingMachineToRemove = findById(vendingMachineId);
        if (vendingMachineToRemove != null) {
            vendingMachines.remove(vendingMachineToRemove);
            return true;
        }
        return false;
    }

    public boolean existsById(String vendingMachineId) {
        for (VendingMachine vendingMachine: vendingMachines){
            if (vendingMachine.getVendingMachineId().equals(vendingMachineId)){
                return true;
            }
        }
        return false;
    }
}
