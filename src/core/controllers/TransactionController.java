/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Account;
import core.models.Deposit;
import core.models.Transaction;
import core.models.TransactionType;
import core.models.Withdraw;
import core.models.storage.AccountsStorage;
import core.models.storage.TransactionStorage;
import core.models.storage.UserStorage;
import java.util.ArrayList;
import java.util.List;

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
        double amountDouble;
        try {
            amountDouble = Double.parseDouble(amount);
            if (amountDouble <= 0) {
                return new Response("Amount must be positive", Status.BAD_REQUEST);
            }
        } catch (NumberFormatException e) {
            return new Response("Amount must be numeric", Status.BAD_REQUEST);
        }

        Account sourceAccount = null;
        for (Account account : accounts) {
            if (account.getId().equals(sourceAccountID)) {
                sourceAccount = account;
                break;
            }
        }

        Account destinationAccount = null;
        for (Account account : accounts) {
            if (account.getId().equals(destinationAccountID)) {
                destinationAccount = account;
                break;
            }
        }

        if (sourceAccount == null) {
            return new Response("Source account does not exist", Status.BAD_REQUEST);
        }
        if (destinationAccount == null) {
            return new Response("Destination account does not exist", Status.BAD_REQUEST);
        }

        if (!Withdraw.withdraw(sourceAccount,amountDouble)) {
            return new Response("Insufficient balance in source account", Status.BAD_REQUEST);
        }

        Deposit.deposit(destinationAccount, amountDouble);

        TransactionStorage transactionStorage = TransactionStorage.getInstance();
        transactionStorage.addTransaction(
                new Transaction(TransactionType.TRANSFER, sourceAccount, destinationAccount, amountDouble)
        );

        return new Response("Transaction completed successfully", Status.CREATED);
    }

    public static Response deposit(String sourceAccountId, String destinationAccountId, String amount) {
        try {
            // Validar que el monto sea numérico y mayor que cero
            double amountDouble;
            try {
                amountDouble = Double.parseDouble(amount);
                if (amountDouble <= 0) {
                    return new Response("Amount must be greater than zero", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Amount must be numeric", Status.BAD_REQUEST);
            }

            // Obtener la instancia de almacenamiento de cuentas
            AccountsStorage accountsStorage = AccountsStorage.getInstance();
            ArrayList<Account> accounts = accountsStorage.getAccounts();

            // Buscar la cuenta origen
            Account sourceAccount = null;
            for (Account account : accounts) {
                if (account.getId().equals(sourceAccountId)) {
                    sourceAccount = account;
                    break;
                }
            }
            if (sourceAccount == null) {
                return new Response("Source account not found", Status.NOT_FOUND);
            }

            // Buscar la cuenta destino
            Account destinationAccount = null;
            for (Account account : accounts) {
                if (account.getId().equals(destinationAccountId)) {
                    destinationAccount = account;
                    break;
                }
            }
            if (destinationAccount == null) {
                return new Response("Destination account not found", Status.NOT_FOUND);
            }

            // Verificar que la cuenta origen tenga saldo suficiente
            if (amountDouble > sourceAccount.getBalance()) {
                return new Response("Insufficient balance in the source account", Status.BAD_REQUEST);
            }

            // Realizar el retiro de la cuenta origen y el depósito en la cuenta destino
            Withdraw.withdraw(sourceAccount,amountDouble);
            Deposit.deposit(destinationAccount, amountDouble);

            // Registrar la transacción
            TransactionStorage transactionStorage = TransactionStorage.getInstance();
            transactionStorage.addTransaction(
                    new Transaction(TransactionType.DEPOSIT, sourceAccount, destinationAccount, amountDouble)
            );

            return new Response("Deposit successful", Status.CREATED);

        } catch (Exception ex) {
            return new Response("Unexpected error: " + ex.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response withdraw(String accountId, String amount) {
        try {
            Double amountDouble;
            try {
                amountDouble = Double.parseDouble(amount);
                if (amountDouble <= 0) {
                    return new Response("Amount must be greater than zero", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Amount must be numeric", Status.BAD_REQUEST);
            }
            AccountsStorage accountsStorage = AccountsStorage.getInstance();
            ArrayList<Account> accounts = accountsStorage.getAccounts();

            Account accountToWithdraw = null;
            for (Account account : accounts) {
                if (account.getId().equals(accountId)) {
                    accountToWithdraw = account;
                    break;
                }
            }

            if (accountToWithdraw == null) {
                return new Response("Account not found", Status.NOT_FOUND);
            }

            if (!Withdraw.withdraw(accountToWithdraw,amountDouble)) {
                return new Response("Insufficient balance", Status.BAD_REQUEST);
            }

            TransactionStorage transactionStorage = TransactionStorage.getInstance();
            transactionStorage.addTransaction(new Transaction(TransactionType.WITHDRAW, accountToWithdraw, null, amountDouble));

            return new Response("Withdrawal successful", Status.CREATED);

        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public List<Transaction> getSortedTransactions() {
        TransactionStorage transactionStorage;
        transactionStorage = TransactionStorage.getInstance();
        return transactionStorage.getSortedTransactions();
    }

}
