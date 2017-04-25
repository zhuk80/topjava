package ru.javawebinar.topjava.service.JPA;


import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.UserServiceTestParent;

@ActiveProfiles({"hsqldb", "jpa"})
public class UserServiceHsTest extends UserServiceTestParent {

}
