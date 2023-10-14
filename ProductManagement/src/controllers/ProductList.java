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
import dto.Product; //use for update
import java.io.BufferedReader; //use for readFromFile
import java.io.File; //use for save to file and read from file
import java.io.FileReader; //use for read from file
import utils.Utils; //use for getting product info
import java.util.ArrayList; //create array list
import java.io.FileWriter; //write to file
import java.io.PrintWriter; //write to file
import java.util.Collections; //use for sort
import java.util.Comparator; // use for sort
import java.util.StringTokenizer; //use for read from file

public class ProductList implements I_List {

    private ArrayList<Product> productList = new ArrayList<>();

    //use to check for exist ProductID
    public int checkProductID(String code) {
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getProductID().equalsIgnoreCase(code)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    //Add a product
    public void addProduct() {
        String productID, productName, status;
        double quantity;
        double unitPrice;
        boolean checkStatus = true, checkDuplicate = true;

        //get user input
        do {
            productID = Utils.getString("Enter product ID:");
            if (checkProductID(productID) >= 0) {
                System.out.println("Code is duplicate");
            } else {
                checkDuplicate = false;
            }
        } while (checkDuplicate);

        productName = Utils.getStringReg("Enter product name:", "^[a-zA-Z0-9]{5,}$", "Input text, name can not be null !!!", "Name is in the wrong format (At least 5 characters and no white spaces)");
        unitPrice = Utils.getDouble("Enter unit price:", "Number must between 0-10000", 0, 10000, 0);
        quantity = Utils.getDouble("Enter quantity for the product:", "Number must be between 0-1000", 0, 1000);

        do {
            status = Utils.getString("Enter the status of the product (Available/Unavailable):");
            if (status.equalsIgnoreCase("available") || status.equalsIgnoreCase("unavailable")) {
                checkStatus = false;
            } else if (status.equalsIgnoreCase("avai") || status.equalsIgnoreCase("unavai")) {
                if (status.equalsIgnoreCase("avai")) {
                    status = "available";
                } else {
                    status = "unavailable";
                }
                checkStatus = false;
            } else {
                System.out.println("Input is in the wrong format!! (Ava ilable/Unvailable)");
                checkStatus = true;
            }
        } while (checkStatus);

        //add the product 
        Product product = new Product(productID, productName, unitPrice, quantity, status);
        productList.add(product);

        System.out.println("Product added!");

    }

    @Override
    //check if a product exist or not
    public void checkExist() {
        ArrayList<Product> listFile = new ArrayList<>();
        boolean checkStatus = true;
        //check if there is a file or not
        try {
            File f = new File("Product.txt");
            if (!f.exists()) {
                return;
            }
            //read information from the file;
            FileReader fr = new FileReader(f); //read character by character
            BufferedReader br = new BufferedReader(fr);
            String details;
            while ((details = br.readLine()) != null) {
                StringTokenizer stk = new StringTokenizer(details, "|");
                String productID = stk.nextToken();
                String productName = stk.nextToken();
                double unitPrice = Double.parseDouble(stk.nextToken());
                double quantity = Double.parseDouble(stk.nextToken());
                String status = stk.nextToken().toUpperCase();
                Product product = new Product(productID, productName, unitPrice, quantity, status);
                listFile.add(product);

            }
            br.close();
            fr.close();

            // search for the product with id from the file
            String searchID = Utils.getString("Enter id to check exist:");
            for (Product x : listFile) {
                if (x.getProductID().equalsIgnoreCase(searchID)) {
                    System.out.println("Exist Product!");
                    System.out.println("CODE-NAME-PRICE-QTY-STATUS");
                    System.out.println("----------------------------------------------------");
                    System.out.println(x.toString());
                    checkStatus = false;
                }
            }
            if (checkStatus) {
                System.out.println("No product found!");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    //Search a product with user input
    public void searchProduct() {
        String searchName = "";
        boolean status = true;
        //let user input the name of the product they want to search
        searchName = Utils.getString("Enter the name of the product you want to search:");
        System.out.println("==============================================");
        for (int i = 0; i < productList.size(); i++) {
            //check if the current product in the arraylist contains the searchName
            if (productList.get(i).getProductName().contains(searchName)) {
                System.out.println(productList.get(i).toString());
                status = false;
            }
        }
        //check if empty
        if (productList.isEmpty()) {
            System.out.println("Have no product");
        } else if (status) {
            //if cant find the product then print the list in an order;
            Collections.sort(productList);
            System.out.println("Sorted:");
            System.out.println("CODE-NAME-PRICE-QTY-STATUS");
            System.out.println("----------------------------------------------------");
            for (Product x : productList) {
                System.out.println(x.toString());
            }
        }
        System.out.println("==============================================");

    }

    @Override
    public void updateProduct() {
        String searchID, productName, status;
        double quantity;
        double unitPrice;
        boolean checkStatus = true;

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
                        quantity = Utils.getDouble("Enter quantity: ", "Number must be between 0-1000", 0, 1000, productList.get(i).getQuantity());

                        do {
                            status = Utils.getString("Enter the status of the product (Available/Unavailable):", productList.get(i).getStatus());
                            if (status.equalsIgnoreCase("available") || status.equalsIgnoreCase("unavailable")) {
                                checkStatus = false;
                            } else if (status.equalsIgnoreCase("avai") || status.equalsIgnoreCase("unavai")) {
                                if (status.equalsIgnoreCase("avai")) {
                                    status = "available";
                                } else {
                                    status = "unavailable";
                                }
                                checkStatus = false;
                            } else {
                                System.out.println("Input is in the wrong format!! (Available/Unvailable)");
                                checkStatus = true;
                            }
                        } while (checkStatus);
                        System.out.println("Do you want to update this product:" + productName + "|" + unitPrice + "|" + quantity + "|" + status.toUpperCase());
                        boolean getChoice = Utils.confirmYesNo("Enter your choice (Y/N):");

                        if (getChoice) {
                            productList.set(i, new Product(productList.get(i).getProductID(), productName, unitPrice, quantity, status));
                            System.out.println("Product update successfully!");
                        } else {
                            System.out.println("Product update fail!");
                        }
                        break;
                    }
                }
                if (checkProduct) {
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
                        cont = Utils.confirmYesNo("Do you want to remove this item " + productList.get(i).toString() + " (Y/N):");
                        if (cont == true) {
                            productList.remove(i);
                            System.out.println("Product delete successfully!");
                            checkProduct = false;
                            break;
                        }
                    }
                }
                if (checkProduct) {
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
                double quantity = Double.parseDouble(stk.nextToken());
                String status = stk.nextToken().toUpperCase();
                Product product = new Product(productID, productName, unitPrice, quantity, status);
                listFile.add(product);

            }
            br.close();
            fr.close();
            Collections.sort(listFile, comparator);
            System.out.println("CODE-NAME-PRICE-QTY-STATUS");
            System.out.println("----------------------------------------------------");
            for (Product x : listFile) {
                System.out.println(x.toString());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    Comparator<Product> comparator = new Comparator<Product>() {
        @Override
        public int compare(Product t, Product t1) {
            double result = t.getQuantity() - t1.getQuantity();
            if (result >= 1) {
                return -1; //reverse 
            } else if (result == 0) {
                return (int) (t.getUnitPrice() - t1.getUnitPrice()); //if qty is equal then compare UnitPrice
            } else {
                return 1;
            }
        }
    };
}
