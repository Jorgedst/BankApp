/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.User;
import core.models.storage.UserStorage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC
 */
public class UserController {

    public static Response registerUser(String id, String firstname, String lastname, String age) {
        try {
            int idInt, ageInt;

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

            if (firstname.equals("")) {
                return new Response("Firstname must be not empty", Status.BAD_REQUEST);
            }

            if (lastname.equals("")) {
                return new Response("Lastname must be not empty", Status.BAD_REQUEST);
            }

            try {
                ageInt = Integer.parseInt(age);
                if (ageInt < 0) {
                    return new Response("Age must be positive", Status.BAD_REQUEST);
                }
                if (ageInt < 18) {
                    return new Response("Age must be 18+ ", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Age must be numeric", Status.BAD_REQUEST);
            }

            UserStorage userStorage = UserStorage.getInstance();
            if (!userStorage.addUser(new User(idInt, firstname, lastname, ageInt))) {
                return new Response("A person with that id already exists", Status.BAD_REQUEST);
            }
            return new Response("Person created successfully", Status.CREATED);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static List<User> getSortedUsers() {
        UserStorage userStorage = UserStorage.getInstance();
        userStorage.sortUsersById();
        return userStorage.getUsers();
    }
    
    
    
}
