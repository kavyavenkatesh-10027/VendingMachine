package repository;

import model.Purchase;
import util.VendingMachineException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PurchaseRepository {

    private static PurchaseRepository instance;
    private final List<Purchase> purchases = new ArrayList<>();

    private PurchaseRepository() {}

    public static PurchaseRepository getInstance() {
        if (instance == null) {
            instance = new PurchaseRepository();
        }
        return instance;
    }

    public void add(Purchase purchase) {
        if (purchase == null) {
            throw new VendingMachineException("Purchase cannot be null.");
        }
        purchases.add(purchase);
    }

    public Purchase findById(String purchaseId) {
        if (purchaseId == null || purchaseId.trim().isEmpty()) {
            throw new VendingMachineException("Purchase ID cannot be null or empty.");
        }
        for (Purchase purchase : purchases) {
            if (purchase.getPurchaseId().equals(purchaseId)) {
                return purchase;
            }
        }
        return null;
    }

    public List<Purchase> findAll() {
        return Collections.unmodifiableList(purchases);
    }
}