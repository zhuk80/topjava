package ru.javawebinar.topjava.service.DataJPA;


import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.MealServiceTestParent;


@ActiveProfiles({"postgres", "datajpa"})
public class MealServicePgTest extends MealServiceTestParent {
}
