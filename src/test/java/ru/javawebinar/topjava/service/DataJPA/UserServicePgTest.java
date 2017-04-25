package ru.javawebinar.topjava.service.DataJPA;


import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.UserServiceTestParent;


@ActiveProfiles({"postgres", "datajpa"})
public class UserServicePgTest extends UserServiceTestParent {
}
