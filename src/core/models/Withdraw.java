/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models;

/**
 *
 * @author rdmp3
 */
public class Withdraw{

    public static boolean withdraw(Account account, double amount) {
        if (amount > account.getBalance()) {
            return false;
        }
        account.setBalance(account.getBalance() - amount);
        return true;
    }
}
