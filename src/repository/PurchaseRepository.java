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
//import model.Purchase;
//import util.VendingMachineException;
//
//import java.util.*;
//
//public class PurchaseRepository {
//
//    private static PurchaseRepository instance;
//    private final Map<String, Purchase> purchases = new HashMap<>();
//
//    private PurchaseRepository() {}
//
//    public static PurchaseRepository getInstance() {
//        if (instance == null) {
//            instance = new PurchaseRepository();
//        }
//        return instance;
//    }
//
//    public void add(Purchase purchase) {
//        if (purchase == null) {
//            throw new VendingMachineException("Purchase cannot be null.");
//        }
//        purchases.put(purchase.getPurchaseId(), purchase);
//    }
//
//    public Purchase findById(String purchaseId) {
//        if (purchaseId == null || purchaseId.trim().isEmpty()) {
//            throw new VendingMachineException("Purchase ID cannot be null or empty.");
//        }
//        return purchases.get(purchaseId);
//    }
//
//    public List<Purchase> findAll() {
//        return Collections.unmodifiableList(new ArrayList<>(purchases.values()));
//    }
//}