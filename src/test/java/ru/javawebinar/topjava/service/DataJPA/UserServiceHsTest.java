package ru.javawebinar.topjava.service.DataJPA;


import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.UserServiceTestParent;

@ActiveProfiles({"hsqldb", "datajpa"})
public class UserServiceHsTest extends UserServiceTestParent {
}
