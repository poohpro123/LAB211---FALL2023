/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

/**
 *
 * @author Quan
 */
import dto.I_List;
import dto.Product;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import utils.Utils;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

public class ProductList implements I_List {

    private ArrayList<Product> productList = new ArrayList<>();
  //  private ArrayList<Product> listFile = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    public int checkProductID(String code) {
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getProductID().equalsIgnoreCase(code)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void addProduct() {
        String productID, productName, status;
        int quantity;
        double unitPrice;
        boolean checkStatus = true;

        //get user input
        do {
            productID = Utils.getString("Enter product ID:");
            if (checkProductID(productID) >= 0) {
                System.out.println("Code is duplicate");
            } else {
                checkStatus = false;
            }
        } while (checkStatus);

        productName = Utils.getStringReg("Enter product name:", "^[a-zA-Z0-9]{5,}$", "Input text!!!", "Name is in the wrong format (At least 5 characters and no white spaces)");
        unitPrice = Utils.getDouble("Enter unit price:", "Number must between 0-10000", 0, 10000, 0);
        quantity = Utils.getInt("Enter quantity for the product:", "Number must be between 0-1000", 0, 1000);

        do {
            status = Utils.getString("Enter the status of the product (Available/Unavailable):");
            if (status.equalsIgnoreCase("available") || status.equalsIgnoreCase("unavailable")) {
                checkStatus = false;
            } else {
                System.out.println("Input is in the wrong format!! (Available/Unvailable)");
                checkStatus = true;
            }
        } while (checkStatus);

        Product product = new Product(productID, productName, unitPrice, quantity, status);
        productList.add(product);

        System.out.println("Product added!");

    }

    @Override
    public void checkExist() {
        ArrayList<Product> listFile = new ArrayList<>();
        boolean checkStatus = true;
        try {
            File f = new File("Product.txt");
            if (!f.exists()) {
                return;
            }

            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String details;
            while ((details = br.readLine()) != null) {
                StringTokenizer stk = new StringTokenizer(details, "|");
                String productID = stk.nextToken();
                String productName = stk.nextToken();
                double unitPrice = Double.parseDouble(stk.nextToken());
                int quantity = Integer.parseInt(stk.nextToken());
                String status = stk.nextToken().toUpperCase();
                Product product = new Product(productID, productName, unitPrice, quantity, status);
                listFile.add(product);

            }
            br.close();
            fr.close();
            String searchID = Utils.getString("Enter id to check exist:");
            for(Product x : listFile){
                if(x.getProductID().equalsIgnoreCase(searchID)){
                    System.out.println(x.toString());
                    checkStatus = false;
                }
            }
            if(checkStatus) System.out.println("Product does not exist");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void searchProduct() {
        String searchName = "";
        int i = 0;
        boolean status = true;
        searchName = Utils.getString("Enter the name of the product you want to search:");
        for (i = 0; i < productList.size(); i++) {
            if (productList.get(i).getProductName().contains(searchName)) {
                System.out.println(productList.get(i).toString());
                status = false;
            }
        }

        if (productList.isEmpty()) {
            System.out.println("Have no product");
        } else if (status) {
            Collections.sort(productList);
            System.out.println("Sorted:");
            for (Product x : productList) {
                System.out.println(x.toString());
            }
        }

    }

    @Override
    public void updateProduct() {
        String searchID, productName, status;
        int quantity;
        double unitPrice;
        System.out.println("1- Update Product");
        System.out.println("2- Delete Product");
        int choice = Utils.getInt("Enter your choice:", 1, 2);
        boolean checkProduct = true;
        switch (choice) {
            case 1:
                searchID = Utils.getString("Enter the product ID to update:");
                for (int i = 0; i < productList.size(); i++) {
                    if (productList.get(i).getProductID().equalsIgnoreCase(searchID)) {
                        checkProduct = false;
                        productName = Utils.getStringReg("Enter product name:", "^[a-zA-Z0-9]{5,}$", "Input text!!!", "Name is in the wrong format (At least 5 characters and no white spaces)", productList.get(i).getProductName());
                        unitPrice = Utils.getDouble("Enter unit price:", "Number must between 0-10000", 0, 10000, productList.get(i).getUnitPrice());
                        quantity = Utils.getInt("Enter new Quantity:", 0, 1000, productList.get(i).getQuantity());
                        status = Utils.getString("Enter new status (Available/Unavailable):", productList.get(i).getStatus());

                        productList.set(i, new Product(productList.get(i).getProductID(), productName, unitPrice, quantity, status));
                        System.out.println("Product update successfully!");
                        break;
                    } 
                }
                if(checkProduct ){
                    System.out.println("Product does not exist");
                    checkProduct = true;
                    break;
                }
                break;
            case 2:
                boolean cont = true;
                searchID = Utils.getString("Enter the product ID to remove:");
                for (int i = 0; i < productList.size(); i++) {
                    if (productList.get(i).getProductID().equalsIgnoreCase(searchID)) {
                        cont = Utils.confirmYesNo("Do you want to remove this item " + productList.get(i).getProductID() + " (Y/N):");
                        if (cont == true) {
                            productList.remove(i);
                            System.out.println("Product delete successfully!");
                            checkProduct = false;
                            break;
                        }
                    } 
                }
                if(checkProduct){
                    System.out.println("Product does not exist");
                }
                break;
        }
    }

    @Override
    public void writeToFile() {
        boolean checkStatus = true;

        if (productList.isEmpty()) {
            System.out.println("Empty list");
            return;
        }
        try {
            File f = new File("Product.txt");
            if (!f.exists()) {
                f.createNewFile();
            }
            FileWriter fw = new FileWriter(f);
            PrintWriter pw = new PrintWriter(fw);
            
            checkStatus = Utils.confirmYesNo("Do you want to save to file (Y/N): ");
            do {
                if (checkStatus == true) {
                    for (Product x : productList) {
                        pw.println(x.getProductID() + "|" + x.getProductName() + "|" + x.getUnitPrice() + "|" + x.getQuantity() + "|" + x.getStatus().toUpperCase());
                    }
                    System.out.println("Write to file success!");
                    System.out.println(f.getCanonicalPath());
                    checkStatus = false;
                } else {
                    System.out.println("Write fail!!");
                    break;
                }
            } while (checkStatus);
            
            pw.close();
            fw.close();

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @Override
    public void printFromFile() {
       ArrayList<Product> listFile = new ArrayList<>();
        try {
            File f = new File("Product.txt");
            if (!f.exists()) {
                return;
            }

            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String details;
            while ((details = br.readLine()) != null) {
                StringTokenizer stk = new StringTokenizer(details, "|");
                String productID = stk.nextToken();
                String productName = stk.nextToken();
                double unitPrice = Double.parseDouble(stk.nextToken());
                int quantity = Integer.parseInt(stk.nextToken());
                String status = stk.nextToken().toUpperCase();
                Product product = new Product(productID, productName, unitPrice, quantity, status);
                listFile.add(product);

            }
            br.close();
            fr.close();
            Collections.sort(listFile,comparator);
            for (Product x : listFile) {
                System.out.println(x.toString());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    Comparator<Product> comparator = new Comparator<Product>(){
        @Override
        public int compare(Product t, Product t1) {
           int result = t.getQuantity()-t1.getQuantity();
           if(result >= 1) return -1;
           else if (result == 0){
               return (int) (t.getUnitPrice()-t1.getUnitPrice());
           }
           else{
               return 1;
           }
        }      
    };
}
