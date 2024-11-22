/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models;

import core.models.Account;

/**
 *
 * @author edangulo
 */
public abstract class Transaction {
    private Account sourceAccount;
    private Account destinationAccount;
    private double amount;

    public Transaction(Account sourceAccount, Account destinationAccount, double amount) {
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public Account getDestinationAccount() {
        return destinationAccount;
    }

    public double getAmount() {
        return amount;
    }
    public abstract String getType();
    
    public abstract void execute();
}
