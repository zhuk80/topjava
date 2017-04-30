package ru.javawebinar.topjava.service.JDBC;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.UserServiceTestParent;

import static ru.javawebinar.topjava.Profiles.ACTIVE_DB;
import static ru.javawebinar.topjava.Profiles.JDBC;


@ActiveProfiles({ACTIVE_DB, JDBC})
public class JdbcUserServiceTest extends UserServiceTestParent {
}
