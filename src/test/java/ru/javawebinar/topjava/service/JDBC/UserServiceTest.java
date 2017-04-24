package ru.javawebinar.topjava.service.JDBC;


import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.UserServiceTestParent;


@ActiveProfiles({"postgres", "jdbc"})
public class UserServiceTest extends UserServiceTestParent {
}
