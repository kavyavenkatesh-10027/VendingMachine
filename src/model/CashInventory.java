package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CashInventory{
    private static final List<Cash> cashDenomination = new ArrayList<>();
    private static final Map<String, Integer> cashCount = new HashMap<>();
    //This is to keep a count of the money in the bank of the vending machine


    public static List<Cash> getCashDenomination() {
        return cashDenomination;
    }

    public static Map<String, Integer> getCashCount() {
        return cashCount;
    }

    public Cash findCashCurrency(String cashId){
        for (Cash cash: cashDenomination){
            if(cashId.equals(cash.getCashId())){
                return cash;
            }
        }
        return null;
    }

    public void createNewDenomination(Cash newCashType, int numberOfDenominationToAdd){
        cashDenomination.add(newCashType);
        cashCount.put(newCashType.getCashId(), numberOfDenominationToAdd);
    }

    public boolean creditToDenomination(String cashId, Integer numberOfDenominationToAdd){
        if(cashId.contains(cashId)) {
            cashCount.put(cashId, cashCount.get(cashId) + numberOfDenominationToAdd);
            return true;
        }
        return false;
    }

    public boolean debitFromDenomination(String cashId, Integer numberOfDenomination){
        if(cashCount.containsKey(cashId)) {
            cashCount.put(cashId, cashCount.get(cashId) + numberOfDenomination);
            return true;
        }
        return false;

    }

    public boolean deleteDenomination(String cashId){
        Cash cashToBeRemoved = findCashCurrency(cashId);
            if(cashToBeRemoved!=null) {
                cashDenomination.remove(cashToBeRemoved);
                cashCount.remove(cashToBeRemoved.getCashId());
                return true;
            }
            return false;
    }
}
