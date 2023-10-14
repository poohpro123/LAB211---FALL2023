/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author Quan
 */
public interface I_List {
    //Add product to the list
    void addProduct();
    //Check if a product exists or not
    void checkExist();
    //Search a product information by name
    void searchProduct();
    //Update product (Update or Delete)
    void updateProduct();
    //Write to file
    void writeToFile();
    //print from file
    void printFromFile();
    
    
    
}
