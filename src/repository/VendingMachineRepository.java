package repository;

import model.VendingMachine;
import util.VendingMachineException;

public class VendingMachineRepository extends BaseRepository<VendingMachine> {

    private static VendingMachineRepository instance;

    private VendingMachineRepository() {}

    public static VendingMachineRepository getInstance() {
        if (instance == null) instance = new VendingMachineRepository();
        return instance;
    }

    @Override
    protected String getId(VendingMachine vm) {
        return vm.getVendingMachineId();
    }
}
//import model.VendingMachine;
//import util.VendingMachineException;
//
//import java.util.*;
//
//public class VendingMachineRepository {
//
//    private static VendingMachineRepository instance;
//    private final Map<String, VendingMachine> vendingMachines = new HashMap<>();
//
//    private VendingMachineRepository() {}
//
//    public static VendingMachineRepository getInstance() {
//        if (instance == null) {
//            instance = new VendingMachineRepository();
//        }
//        return instance;
//    }
//
//    public void add(VendingMachine vm) {
//        if (vm == null) {
//            throw new VendingMachineException("VendingMachine cannot be null.");
//        }
//        vendingMachines.put(vm.getVendingMachineId(), vm);
//    }
//
//    public VendingMachine findById(String vendingMachineId) {
//        return vendingMachines.get(vendingMachineId);
//    }
//
//    public List<VendingMachine> findAll() {
//        return Collections.unmodifiableList(new ArrayList<>(vendingMachines.values()));
//    }
//
//    public void removeById(String vendingMachineId) {
//        vendingMachines.remove(vendingMachineId);
//    }
//
//    public boolean existsById(String vendingMachineId) {
//        return vendingMachines.containsKey(vendingMachineId);
//    }
//}