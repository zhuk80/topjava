package ru.javawebinar.topjava.service.JDBC;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.UserServiceTestParent;


@ActiveProfiles({"hsqldb", "jdbc"})
public class UserServiceHsTest extends UserServiceTestParent {
}
