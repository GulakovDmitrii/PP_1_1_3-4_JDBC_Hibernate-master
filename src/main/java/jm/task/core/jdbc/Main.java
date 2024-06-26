package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
    UserServiceImpl userService = new UserServiceImpl();
    userService.dropUsersTable();
    userService.createUsersTable();
    userService.saveUser("Егор", "Иванов", (byte) 25);
    userService.saveUser("Сергей", "Петров", (byte) 35);
    userService.saveUser("Максим", "Федоров", (byte) 45);
    userService.saveUser("Иван", "Васильев", (byte) 55);
    userService.getAllUsers().forEach(System.out::println);
    userService.cleanUsersTable();
    userService.dropUsersTable();
        Util.closeConnection();
    }
}
