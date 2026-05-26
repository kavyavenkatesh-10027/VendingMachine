package model;

import util.Generator;
import util.Location;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Product {
    private final String productId;
    private String productName;
    private String brand;
    private String description;
    private String warning;
    private BigDecimal price;
    private final String manufacturingLocation;
    private final LocalDate manufacturingDate;

    public Product(String productName, String brand, String description, String warning, BigDecimal price, String manufacturingLocation, LocalDate manufacturingDate){
        this.productId = Generator.generateProductId();

        if (productName == null || productName.trim().isEmpty()){
            throw new IllegalArgumentException("Product must have a name");
        }

        if (brand == null || brand.trim().isEmpty()){
            throw new IllegalArgumentException("Product must have a brand");
        }

        if (description == null || description.trim().isEmpty()){
            throw new IllegalArgumentException("Product must have a description");
        }

        if (price.intValue()<0){
            throw new IllegalArgumentException("Price of product cannot be negative");
        }

        if (manufacturingLocation == null){
            throw new IllegalArgumentException("Product must have a manufacturing location");
        }

        if (manufacturingDate == null || manufacturingDate.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("Manufacturing date must always be before the current date");
        }

        this.productName = productName;
        this.brand = brand;
        this.description = description;
        this.warning = warning;
        this.price = price;
        this.manufacturingLocation = manufacturingLocation;
        this.manufacturingDate = manufacturingDate;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        if (productName == null || productName.trim().isEmpty()){
            throw new IllegalArgumentException("Product must have a name");
        }

        this.productName = productName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        if (brand == null || brand.trim().isEmpty()){
            throw new IllegalArgumentException("Product must have a brand");
        }

        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty()){
            throw new IllegalArgumentException("Product must have a description");
        }

        this.description = description;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Price of product cannot be negative");
        }

        this.price = price;
    }

    public String getManufacturingLocation() {
        return manufacturingLocation;
    }

    public LocalDate getManufacturingDate() {
        return manufacturingDate;
    }

    @Override
    public String toString() {
        return "Product ID : " + productId + "\n" +
                "Product Name : " + productName + "\n" +
                "Brand : " + brand + "\n" +
                "Description : " + description + "\n" +
                "Warning : " + warning + "\n" +
                "Price : " + price + "\n" +
                "Manufacturing Location : " + manufacturingLocation + "\n" +
                "Manufacturing Date : " + manufacturingDate;
    }
}
