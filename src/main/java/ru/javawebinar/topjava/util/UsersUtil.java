package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Honey on 04.04.2017.
 */
public class UsersUtil {
    public static final List<User> USERS = Arrays.asList(
            new User(1, "Nastya", "n@gmail.com", "pass", Role.ROLE_USER, Role.ROLE_ADMIN),
            new User(2, "Nikolya", "nnn@gmail.com", "pass", Role.ROLE_USER),
//            new User(3, "Mary", "m@gmail.com", "pass", Role.ROLE_USER),
//            new User(4, "Katy", "k@gmail.com", "pass", Role.ROLE_USER),
            new User(6, "Ivan", "i2@gmail.com", "pass", Role.ROLE_ADMIN),
            new User(5, "Ivan", "i@gmail.com", "pass", Role.ROLE_ADMIN)
    );
}
