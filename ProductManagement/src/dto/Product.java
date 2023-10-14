/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.io.Serializable;

/**
 *
 * @author Quan
 */


public class Product implements Comparable<Product>,Serializable {
    private String productID;
    private String productName;
    private double unitPrice;
    private double quantity;
    private String status;

    public Product(String productID, String productName, double unitPrice, double quantity, String status) {
        this.productID = productID;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.status = status;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }
    
    public double getQuantity(){
        return quantity;
    }
    
    public String getStatus(){
        return status;
    }

    public String toString(){
        return productID + "-" + productName + "-" + unitPrice + "-" + quantity + "-" + status.toUpperCase();
    }

    @Override
    public int compareTo(Product t) {
      return this.getProductName().compareTo(t.getProductName());
    }
}
