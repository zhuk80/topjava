package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.mock.InMemoryUserRepositoryImpl;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Evgeniy on 03.04.2017.
 */
public class UsersUtil {
    public static final List<User> USERS = Arrays.asList(
            new User(1, "First", "first@mail.ru", "123", Role.ROLE_ADMIN),
            new User(2, "Second", "second@mail.ru", "123", Role.ROLE_USER),
            new User(3, "B", "B@mail.ru", "123", Role.ROLE_USER),
            new User(4, "Third", "third@mail.ru", "123", Role.ROLE_USER),
            new User(5, "A", "A@mail.ru", "123", Role.ROLE_USER)
            //new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            //new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            //new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            //new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            //new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    );

    public static void main(String[] args) {
        InMemoryUserRepositoryImpl inMemoryUserRepository = new InMemoryUserRepositoryImpl();
        for (User user : inMemoryUserRepository.getAll())
        {
            System.out.println(user.getName());
        }
    }
}
//Integer id, String name, String email, String password, Role role, Role... roles