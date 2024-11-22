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
import java.util.List;
import java.util.Random;

/**
 *
 * @author Dsilv
 */
public abstract class AccountController {

    public static Response createAccount(String id, String initialBalance) {
        try {
            // Validación del ID
            int idInt;
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

            // Validación del balance inicial
            Double initialBalanceDouble;
            try {
                initialBalanceDouble = Double.parseDouble(initialBalance);
                if (initialBalanceDouble < 0) {
                    return new Response("Initial balance must be positive", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Initial Balance must be numeric", Status.BAD_REQUEST);
            }

            // Verificación de usuario
            UserStorage storage = UserStorage.getInstance();
            AccountsStorage accountsStorage = AccountsStorage.getInstance();
            ArrayList<User> users = storage.getUsers();
            User userSelected = null;

            // Buscar usuario por id
            for (User user : users) {
                if (user.getId() == idInt) {
                    userSelected = user;
                    break;
                }
            }

            if (userSelected == null) {
                return new Response("Non-existent user", Status.BAD_REQUEST);
            }

            // Generación de ID de cuenta
            Random random = new Random();
            int first = random.nextInt(1000); // Primer bloque de 3 dígitos
            int second = random.nextInt(1000000); // Segundo bloque de 6 dígitos
            int third = random.nextInt(100); // Tercer bloque de 2 dígitos

            String accountId = String.format("%03d", first) + "-" + String.format("%06d", second) + "-" + String.format("%02d", third);
            Account newAccount = new Account(accountId, userSelected, initialBalanceDouble);
            accountsStorage.addAccount(newAccount);

            System.out.println("Account created with ID: " + accountId);

            return new Response("Account created successfully", Status.CREATED);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    public static List<Account> getSortedAccounts() {
        AccountsStorage accountStorage = AccountsStorage.getInstance();
        accountStorage.sortAccountsById();  // Llamamos al método que ordena las cuentas
        return accountStorage.getAccounts();
    }

}
