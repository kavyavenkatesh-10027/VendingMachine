package repository;

import model.VendingMachine;

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