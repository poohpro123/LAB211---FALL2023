/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author Quan
 */
import controllers.ProductList;
import dto.I_List;
import controllers.Menu;
import dto.I_Menu;
import utils.Utils;

public class ProductManagement {

    public static void main(String[] args) {
        I_Menu menu = new Menu();
        menu.addItem("Welcome to Product Management - SE182550");
        menu.addItem("======================================");
        menu.addItem("|       Product Management:           |");
        menu.addItem("|       1. Add a new employee.        |");
        menu.addItem("|       2. Check Exist by ID.         |");
        menu.addItem("|       3. Search Product by name.    |");
        menu.addItem("|       4. Update a product.          |");
        menu.addItem("|       5. Save to File.              |");
        menu.addItem("|       6. Print from File.           |");
        menu.addItem("|       7. Quit.                      |");
        menu.addItem("======================================");
       
        int choice;
        boolean cont = true;

        I_List list = new ProductList();
        do {
            menu.showMenu();
            choice = menu.getChoice();
            switch (choice) {
                case 1:
                    System.out.println("ADD A PRODUCT");
                    System.out.println("============================");
                    list.addProduct();
                    cont = Utils.confirmYesNo("Do you want to go back to the menu? (Y/N):");
                    while (cont == false) {
                        System.out.println("ADD A PRODUCT");
                        System.out.println("============================");
                        list.addProduct();
                        cont = Utils.confirmYesNo("Do you want to go back to the menu? (Y/N):");
                    }
                    break;

                case 2:
                    System.out.println("CHECK EXIST");
                    System.out.println("============================");
                    list.checkExist();
                    cont = Utils.confirmYesNo("Do you want to go back to the menu? (Y/N):");
                    while (cont == false) {
                        System.out.println("CHECK EXIST");
                        System.out.println("============================");
                        list.checkExist();
                        cont = Utils.confirmYesNo("Do you want to go back to the menu? (Y/N):");
                    }
                    break;
                case 3:
                    System.out.println("Search Product");
                    System.out.println("============================");
                    list.searchProduct();
                    cont = Utils.confirmYesNo("Do you want to go back to the menu? (Y/N):");
                    while (cont == false) {
                        System.out.println("Search Product");
                        System.out.println("============================");
                        list.searchProduct();
                        cont = Utils.confirmYesNo("Do you want to go back to the menu? (Y/N):");
                    }
                    break;
                case 4:
                    System.out.println("Update Product");
                    System.out.println("============================");
                    list.updateProduct();
                    cont = Utils.confirmYesNo("Do you want to go back to the menu? (Y/N):");
                    while (cont == false) {
                        System.out.println("Update Product");
                        System.out.println("============================");
                        list.updateProduct();
                        cont = Utils.confirmYesNo("Do you want to go back to the menu? (Y/N):");
                    }
                    break;
                case 5:
                    System.out.println("Write to file");
                    System.out.println("============================");
                    list.writeToFile();
                    cont = Utils.confirmYesNo("Do you want to go back to the menu? (Y/N):");
                    while (cont == false) {
                        System.out.println("Write to file");
                        System.out.println("============================");
                        list.writeToFile();
                        cont = Utils.confirmYesNo("Do you want to go back to the menu? (Y/N):");
                    }
                    break;
                case 6:
                    System.out.println("Print from file");
                    System.out.println("============================");
                    list.printFromFile();
                    cont = Utils.confirmYesNo("Do you want to go back to the menu? (Y/N):");
                    while (cont == false) {
                        System.out.println("Print from file");
                        System.out.println("============================");
                        list.printFromFile();
                        cont = Utils.confirmYesNo("Do you want to go back to the menu? (Y/N):");
                    }
                    break;
                case 7:
                   cont = menu.confirmYesNo("Do you want to quit (Y/N):");
            }

        } while (choice >= 1 && choice <= 7 && cont);

    }
}
