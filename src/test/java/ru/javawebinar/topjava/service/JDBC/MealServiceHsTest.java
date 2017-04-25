package ru.javawebinar.topjava.service.JDBC;


import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.MealServiceTestParent;


@ActiveProfiles({"hsqldb", "jdbc"})
public class MealServiceHsTest extends MealServiceTestParent {
}
