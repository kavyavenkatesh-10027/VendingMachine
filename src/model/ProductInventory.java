package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductInventory {
    private static final List<Product> productStorage = new ArrayList<>();
    private static final Map<String, Integer> productStockCount = new HashMap<>();

    public static List<Product> getProductStorage() {
        return productStorage;
    }

    public static Map<String, Integer> getProductStockCount() {
        return productStockCount;
    }

    public void addNewProduct(Product newProduct, Integer stockCount){
        productStorage.add(newProduct);
        productStockCount.put(newProduct.getProductId(), stockCount);
    }

    public boolean addStockToPreExistingProduct(String productId, Integer stocksToAdd){
        if(productStockCount.containsKey(productId)){
            productStockCount.remove(productId);
            return true;
        }
        return false;

    }

    public boolean retrieveStockFromExistingProduct(String productId, Integer quantityBought){
        Product productPurchased = findProductByProductId(productId);
        if(productPurchased!=null){
            productStockCount.put(productId, productStockCount.get(productId)-quantityBought);
            return true;
        }
        return false;
    }

    public Product findProductByProductId(String productId){
        for(Product product : productStorage) {
            if(product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    public boolean deleteProduct(String productId){
        Product productToBeDeleted = findProductByProductId(productId);
        if (productToBeDeleted!=null) {
            productStorage.remove(productToBeDeleted);
            productStockCount.remove(productId);
            return true;
        }
        return false;
    }

}
