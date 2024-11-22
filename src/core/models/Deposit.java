/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models;

/**
 *
 * @author rdmp3
 */
public class Deposit extends Transaction {

    public Deposit(Account destinationAccount, double amount) {
        super(null, destinationAccount, amount);
    }
    @Override
    public void execute() {
        getDestinationAccount().deposit(getAmount());
    }
    @Override
    public String getType() {
        return "Deposit";
    }
}