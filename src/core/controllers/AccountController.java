/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Account;
import core.models.User;
import core.models.storage.AccountsStorage;
import core.models.storage.UserStorage;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Dsilv
 */
public class AccountController {

    public static Response createAccount(String id, String initialBalance) {
        try {
            int idInt;
            Double initialBalanceDouble;
            try {
                idInt = Integer.parseInt(id);
                if (idInt < 0) {
                    return new Response("Id must be positive", Status.BAD_REQUEST);
                }
                if (String.valueOf(idInt).length() > 9) {
                    return new Response("Id must not exceed 9 digits", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Id must be numeric", Status.BAD_REQUEST);
            }

            try {
                UserStorage storage = UserStorage.getInstance();
                AccountsStorage accountsStorage = AccountsStorage.getInstance();
                ArrayList<Account> accounts = accountsStorage.getAccounts();
                ArrayList<User> users = storage.getUsers();
                User userSelected = null;
                for (User user : users) {
                    if (user.getId() == idInt) {
                        userSelected = user;
                        Random random = new Random();
                        int first = random.nextInt(1000);
                        int second = random.nextInt(1000000);
                        int third = random.nextInt(100);
                        String accountId = String.format("%03d", first) + "-" + String.format("%06d", second) + "-" + String.format("%02d", third);
                        accountsStorage.addAccount(new Account(accountId, userSelected));
                    }
                }
                if (userSelected == null) {
                    return new Response("Non-existent user", Status.BAD_REQUEST);
                }
            } catch (NullPointerException ex) {
                return new Response("No users registered yet", Status.NOT_FOUND);
            }

            try {
                initialBalanceDouble = Double.parseDouble(initialBalance);
                if (initialBalanceDouble < 0) {
                    return new Response("Initial balance must be positive", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Initial Balance must be numeric", Status.BAD_REQUEST);
            }

            return new Response("Account created succesfully", Status.CREATED);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

}
