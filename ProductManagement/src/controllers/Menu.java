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
import utils.Utils;
import java.util.ArrayList;
import dto.I_Menu;
import java.util.Scanner;

public class Menu implements I_Menu {

    private ArrayList<String> menu = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    public Menu() {
        super();
    }

    @Override
    public void addItem(String s) {
        menu.add(s);
    }

    @Override
    public int getChoice() {
        int choice = Utils.getInt("Enter your choice:", 1, 7);
        return choice;
    }

    @Override
    public void showMenu() {
        for (String x : menu) {
            System.out.println(x);
        }
    }

    @Override
    public boolean confirmYesNo(String welcome) {
        boolean result = false;
        String confirm = Utils.getString(welcome);
        do {
            if ("Y".equalsIgnoreCase(confirm)) {
                result = false;
                break;
            } 
            else if("N".equalsIgnoreCase(confirm)){
                result = true;
            }
            else {
                System.out.println("Wrong input (Y/N)");
                confirm = Utils.getString(welcome);
            }
        }while(result == false);
        
        
        return result;
    }

}
