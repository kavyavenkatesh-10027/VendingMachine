package model;

import util.Generator;

import java.time.LocalDate;

public class Product {
    private final String productId;
    private String productName;
    private String brand;
    private String description;
    private String warning;
    private double price;
    private final String manufacturingLocation;
    private final LocalDate manufacturingDate;

    public Product(String productName, String brand, String description, String warning, double price, String manufacturingLocation, LocalDate manufacturingDate){
        this.productId = Generator.generateProductId();
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
        this.productName = productName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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
