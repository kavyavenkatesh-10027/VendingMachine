package util;

public class Generator {
    public static long nextAdminId = 1;
    public static long nextProductId =1;

    public static String generateAdminId(){
        return "admin-"+nextAdminId++;
    }

    public static String generateProductId(){
        return "product-"+nextProductId++;
    }
}
