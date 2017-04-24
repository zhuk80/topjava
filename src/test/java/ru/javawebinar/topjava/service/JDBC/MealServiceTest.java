package ru.javawebinar.topjava.service.JDBC;


import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.MealServiceTestParent;


@ActiveProfiles({"postgres", "jdbc"})
public class MealServiceTest extends MealServiceTestParent {
}
