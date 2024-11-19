/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

import core.models.Account;
import core.models.Transaction;
import core.models.User;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Dsilv
 */
public class UserStorage {

    // Instancia Singleton
    private static UserStorage instance;

    // Atributos del UserStorage
    private ArrayList<User> users;

    private UserStorage() {
        this.users = new ArrayList<>();

    }

    public static UserStorage getInstance() {
        if (instance == null) {
            instance = new UserStorage();
        }
        return instance;
    }

    public ArrayList<User> getUsers() {
        return new ArrayList<>(users);
    }

    public boolean addUser(User user) {
        for (User u : this.users) {
            if (u.getId() == user.getId()) {
                return false;
            }
        }
        this.users.add(user);
        return true;
    }

    public boolean sortUsersById() {
        if (users.isEmpty()) {
            return false;
        } else {
            this.users.sort((obj1, obj2) -> (obj1.getId() - obj2.getId()));
        }
        return true;
    }

}
