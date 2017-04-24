package ru.javawebinar.topjava.service.DataJPA;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.MealServiceTestParent;


@ActiveProfiles({"hsqldb", "datajpa"})
public class MealServiceTest extends MealServiceTestParent {
}
