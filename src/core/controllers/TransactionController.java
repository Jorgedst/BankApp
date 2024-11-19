/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Account;
import core.models.Transaction;
import core.models.TransactionType;
import core.models.User;
import core.models.storage.AccountsStorage;
import core.models.storage.TransactionStorage;
import core.models.storage.UserStorage;
import java.util.ArrayList;

/**
 *
 * @author Dsilv
 */
public class TransactionController {

    public static Response transfer(String sourceAccountID, String destinationAccountID, String amount) {
        Account sourceAccount = null;
        Account destinationAccount = null;
        UserStorage storage = UserStorage.getInstance();
        AccountsStorage accountsStorage = AccountsStorage.getInstance();
        ArrayList<Account> accounts = accountsStorage.getAccounts();
        ArrayList<User> users = storage.getUsers();
        try {
            Double amountDouble;
            try {
                amountDouble = Double.parseDouble(amount);
                if (amountDouble < 0) {
                    return new Response("amount must be positive", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Amount must be numeric", Status.BAD_REQUEST);
            }
            try {
                for (Account account : accounts) {
                    if (account.getId().equals(sourceAccountID)) {
                        sourceAccount = account;
                    }
                    if (sourceAccount == null) {
                        return new Response("Non-existent source Account", Status.BAD_REQUEST);
                    }
                    if (!sourceAccount.withdraw(amountDouble)) {
                        return new Response("No balance enough in the account", Status.BAD_REQUEST);
                    }
                    for (Account destination : accounts) {
                        if (destination.getId().equals(destinationAccountID)) {
                            destinationAccount = account;
                        }
                    }
                    if (destinationAccount == null) {
                        return new Response("Non-existent destination Account", Status.BAD_REQUEST);
                    }
                }

            } catch (NullPointerException ex) {
                return new Response("No accounts registered", Status.NOT_FOUND);
            }
            TransactionStorage transactionStorage = TransactionStorage.getInstance();
            transactionStorage.addTransaction(new Transaction(TransactionType.TRANSFER, sourceAccount, destinationAccount, amountDouble));
            return new Response("Transaction done successfully", Status.CREATED);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

}
