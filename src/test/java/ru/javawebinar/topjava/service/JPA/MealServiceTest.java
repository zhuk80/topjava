package ru.javawebinar.topjava.service.JPA;


import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.MealServiceTestParent;


@ActiveProfiles({"hsqldb", "jpa"})
public class MealServiceTest extends MealServiceTestParent {

}
