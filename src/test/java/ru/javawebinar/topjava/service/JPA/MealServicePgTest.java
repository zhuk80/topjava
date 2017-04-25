package ru.javawebinar.topjava.service.JPA;


import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.MealServiceTestParent;


@ActiveProfiles({"postgres", "jpa"})
public class MealServicePgTest extends MealServiceTestParent {
}
