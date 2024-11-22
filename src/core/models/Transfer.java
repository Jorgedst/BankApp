/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models;

/**
 *
 * @author rdmp3
 */
public class Transfer extends Transaction {

    public Transfer(Account sourceAccount, Account destinationAccount, double amount) {
        super(sourceAccount, destinationAccount, amount);
    }

    @Override
    public void execute() {
        if (getSourceAccount().withdraw(getAmount())) {
            getDestinationAccount().deposit(getAmount());
        } else {
            throw new IllegalArgumentException("Insufficient funds for transfer.");
        }
    }
    @Override
    public String getType() {
        return "Transfer";
    }
}
