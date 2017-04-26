package ru.javawebinar.topjava.service.DataJPA;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.MealServiceTestParent;
import static ru.javawebinar.topjava.Profiles.ACTIVE_DB;
import static ru.javawebinar.topjava.Profiles.DATAJPA;


@ActiveProfiles({ACTIVE_DB, DATAJPA})
public class MealServiceTest extends MealServiceTestParent {
}
