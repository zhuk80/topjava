package ru.javawebinar.topjava.service.JPA;


import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.UserServiceTestParent;

import static ru.javawebinar.topjava.Profiles.ACTIVE_DB;
import static ru.javawebinar.topjava.Profiles.JPA;

@ActiveProfiles({ACTIVE_DB, JPA})
public class UserServiceTest extends UserServiceTestParent {

}
