package util;

public class Generator {
    private static long nextVendingMachineId = 1;
    private static long nextSlotId = 1;
    private static long nextAdminId = 1;
    private static long nextProductId =1;
    private static long nextPurchaseId=1;

    public static String generateVendingMachineId(){
        return "vendingMachine-"+nextVendingMachineId++;
    }

    public static String peekNextVendingMachineId() {
        return "vendingMachine-"+nextVendingMachineId;
    }

    public static String generateSlotId() {
        return "slot-"+nextSlotId++;
    }

    public static String generateAdminId(){
        return "admin-"+nextAdminId++;
    }

    public static String generateProductId(){
        return "product-"+nextProductId++;
    }

    public static String generatePurchaseId(){
        return "purchase-"+nextPurchaseId++;
    }


}
