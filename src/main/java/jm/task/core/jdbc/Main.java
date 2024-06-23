package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Егор", "Иванов", (byte) 20);
        userService.saveUser("Дмитрий", "Петров", (byte) 25);
        userService.saveUser("Аркадий", "Сергеев", (byte) 30);
        userService.saveUser("Иван", "Филипов", (byte) 35);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
        }
}
