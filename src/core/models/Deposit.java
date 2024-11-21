/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models;

/**
 *
 * @author rdmp3
 */
public class Deposit {
    public static void deposit(Account account, double amount) {
        account.setBalance(account.getBalance() + amount);
    }
}
