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
        UserStorage userStorage = UserStorage.getInstance();
        AccountsStorage accountsStorage = AccountsStorage.getInstance();

        ArrayList<Account> accounts = accountsStorage.getAccounts();
        if (accounts == null || accounts.isEmpty()) {
            return new Response("No accounts registered", Status.NOT_FOUND);
        }

        // Validar monto
        double amountDouble;
        try {
            amountDouble = Double.parseDouble(amount);
            if (amountDouble <= 0) {
                return new Response("Amount must be positive", Status.BAD_REQUEST);
            }
        } catch (NumberFormatException e) {
            return new Response("Amount must be numeric", Status.BAD_REQUEST);
        }

        // Buscar cuentas origen y destino
        Account sourceAccount = null;
        for (Account account : accounts) {
            if (account.getId().equals(sourceAccountID)) {
                sourceAccount = account;
                break; // Detenemos el bucle al encontrar la cuenta
            }
        }

        Account destinationAccount = null;
        for (Account account : accounts) {
            if (account.getId().equals(destinationAccountID)) {
                destinationAccount = account;
                break; // Detenemos el bucle al encontrar la cuenta
            }
        }

        // Validar existencia de cuentas
        if (sourceAccount == null) {
            return new Response("Source account does not exist", Status.BAD_REQUEST);
        }
        if (destinationAccount == null) {
            return new Response("Destination account does not exist", Status.BAD_REQUEST);
        }

        // Validar saldo suficiente
        if (!sourceAccount.withdraw(amountDouble)) {
            return new Response("Insufficient balance in source account", Status.BAD_REQUEST);
        }

        // Realizar transferencia
        destinationAccount.deposit(amountDouble);

        // Registrar transacción
        TransactionStorage transactionStorage = TransactionStorage.getInstance();
        transactionStorage.addTransaction(
                new Transaction(TransactionType.TRANSFER, sourceAccount, destinationAccount, amountDouble)
        );

        return new Response("Transaction completed successfully", Status.CREATED);
    }

}
