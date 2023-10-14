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
public interface I_Menu {
    //Add text to the menu
    void addItem(String s);
    //Get user choice (Let user input their choice)
    int getChoice();
    //Show user the menu to choose the function
    void showMenu();
    //Confirm yes/no (Y/N)
    boolean confirmYesNo(String welcome);
}
