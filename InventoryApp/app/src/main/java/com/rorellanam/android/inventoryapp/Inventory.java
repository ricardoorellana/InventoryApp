package com.rorellanam.android.inventoryapp;

import java.io.Serializable;

/**
 * Created by Rorellanam on 6/28/16.
 */
public class Inventory implements Serializable {
    private int idInventory;
    private String productName;
    private String quantity;
    private String price;
    private byte [] image;


    public int getIdInventory() {
        return idInventory;
    }

    public void setIdInventory(int idInventory) {
        this.idInventory = idInventory;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Inventory(int idInventory, String productName, String quantity, String price, byte [] image) {
        this.idInventory = idInventory;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
    }

    public Inventory(String productName, String quantity, String price, byte [] image) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
    }

    public Inventory(int idInventory) {
        this.idInventory = idInventory;
    }

    public Inventory() {

    }

    @Override
    public String toString() {
        return "Inventory{" +
                "idInventory=" + idInventory +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", price='" + price + '\'' +
                '}';
    }

    public byte [] getImage() {
        return image;
    }

    public void setImage(byte [] image) {
        this.image = image;
    }
}
