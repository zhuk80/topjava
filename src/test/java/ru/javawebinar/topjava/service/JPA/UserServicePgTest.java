package ru.javawebinar.topjava.service.JPA;


import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.UserServiceTestParent;


@ActiveProfiles({"postgres", "jpa"})
public class UserServicePgTest extends UserServiceTestParent {
}
