package model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ProductInventory {

    private static final ProductInventory INSTANCE = new ProductInventory();

    private final Map<String, Product> products = new HashMap<>();

    private final Map<String, Integer> productStockCount = new HashMap<>();

    private ProductInventory(){

    }

    //getter for INSTANCE singleton class
    public static ProductInventory getInstance(){
        return INSTANCE;
    }

    //Basic getters
    public Map<String, Product> getProducts(){
        return Collections.unmodifiableMap(products);
    }

    public Map<String, Integer> getProductStockCount(){
        return Collections.unmodifiableMap(productStockCount);
    }

    //Actions
    public boolean addNewProduct(Product newProduct, Integer stockCount){

        if(newProduct == null || stockCount == null){
            return false;
        }

        if(stockCount < 0){
            return false;
        }

        String productId = newProduct.getProductId();

        if(isSuchAProductGettingSoldAtThisVendingMachine(productId)){
            return false;
        }

        products.put(productId, newProduct);
        productStockCount.put(productId, stockCount);

        return true;
    }

    public boolean addStockToPreExistingProduct(String productId, Integer stocksToAdd){

        if(productId == null || stocksToAdd == null){
            return false;
        }

        if(stocksToAdd <= 0){
            return false;
        }

        if(isSuchAProductGettingSoldAtThisVendingMachine(productId)){

            int updatedStock = productStockCount.get(productId) + stocksToAdd;

            productStockCount.put(productId, updatedStock);

            return true;
        }

        return false;
    }

    public boolean retrieveStockFromExistingProduct(String productId, Integer quantityBought){

        if(productId == null || quantityBought == null){
            return false;
        }

        if(quantityBought <= 0){
            return false;
        }

        if(!isSuchAProductGettingSoldAtThisVendingMachine(productId)){
            return false;
        }

        if(!isStockOfAtLeastThisMuchQuantityAvailableForProduct(productId, quantityBought)){
            return false;
        }

        productStockCount.put(productId, getAvailableStockForProduct(productId)-quantityBought);

        return true;
    }

    public boolean deleteProduct(String productId){

        if(productId == null){
            return false;
        }

        if(isSuchAProductGettingSoldAtThisVendingMachine(productId)){
            products.remove(productId);
            productStockCount.remove(productId);
            return true;
        }

        return false;
    }

    //View only
    public Product findProductByProductId(String productId){
        return products.get(productId);
    }

    public Integer getAvailableStockForProduct(String productId){
        return productStockCount.get(productId);
    }

    public boolean isSuchAProductGettingSoldAtThisVendingMachine(String productId){
        return products.containsKey(productId);
    }

    public boolean isStockOfAtLeastThisMuchQuantityAvailableForProduct(String productId, Integer requiredQuantity){
        //The validation is repeated here for the use case that the product isn't being bought,just the stock quantity is being viewed by admin.
        // In this case the productId etc. need to be validated (which is otherwise getting validated in the retrieveStockByProductId method.
        if(productId == null || requiredQuantity == null){
            return false;
        }

        return getAvailableStockForProduct(productId) >= requiredQuantity;
    }

    public void clearInventory(){

        products.clear();

        productStockCount.clear();
    }
}



//package model;
//
//import java.util.*;
//
//public class ProductInventory {
//    private static final List<Product> productStorage = new ArrayList<>();
//    private static final Map<String, Integer> productStockCount = new HashMap<>();
//
//    private ProductInventory(){}
//
//    public static List<Product> getProductStorage() {
//        return Collections.unmodifiableList(productStorage);
//    }
//
//    public static Map<String, Integer> getProductStockCount() {
//        return Collections.unmodifiableMap(productStockCount);
//    }
//
//    public void addNewProduct(Product newProduct, Integer stockCount){
//        productStorage.add(newProduct);
//        productStockCount.put(newProduct.getProductId(), stockCount);
//    }
//
//    public boolean addStockToPreExistingProduct(String productId, Integer stocksToAdd){
//        if(productStockCount.containsKey(productId)){
//            productStockCount.put(productId, productStockCount.get(productId) + stocksToAdd);
//            return true;
//        }
//        return false;
//
//    }
//
//    public boolean retrieveStockFromExistingProduct(String productId, Integer quantityBought){
//        Product productPurchased = findProductByProductId(productId);
//        if(productPurchased!=null){
//            productStockCount.put(productId, productStockCount.get(productId)-quantityBought);
//            return true;
//        }
//        return false;
//    }
//
//    public Product findProductByProductId(String productId){
//        for(Product product : productStorage) {
//            if(product.getProductId().equals(productId)) {
//                return product;
//            }
//        }
//        return null;
//    }
//
//    public boolean deleteProduct(String productId){
//        Product productToBeDeleted = findProductByProductId(productId);
//        if (productToBeDeleted!=null) {
//            productStorage.remove(productToBeDeleted);
//            productStockCount.remove(productId);
//            return true;
//        }
//        return false;
//    }
//
//}
