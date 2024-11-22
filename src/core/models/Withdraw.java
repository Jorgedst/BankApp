/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models;

/**
 *
 * @author rdmp3
 */
public class Withdraw extends Transaction {

    public Withdraw(Account sourceAccount, double amount) {
        super(sourceAccount, null, amount);
    }

    @Override
    public void execute() {
        getSourceAccount().withdraw(getAmount());
    }
    @Override
    public String getType() {
        return "Withdraw";
    }
}
