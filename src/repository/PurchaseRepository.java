package repository;

import model.Purchase;
import util.VendingMachineException;

public class PurchaseRepository extends BaseRepository<Purchase> {

    private static PurchaseRepository instance;

    private PurchaseRepository() {}

    public static PurchaseRepository getInstance() {
        if (instance == null) instance = new PurchaseRepository();
        return instance;
    }

    @Override
    protected String getId(Purchase purchase) {
        return purchase.getPurchaseId();
    }
}