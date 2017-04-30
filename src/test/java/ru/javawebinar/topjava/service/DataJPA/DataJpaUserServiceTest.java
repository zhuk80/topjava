package ru.javawebinar.topjava.service.DataJPA;


import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTestParent;


import java.util.Arrays;
import java.util.Objects;

import static ru.javawebinar.topjava.Profiles.ACTIVE_DB;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.MealTestData.*;


@ActiveProfiles({ACTIVE_DB, DATAJPA})
public class DataJpaUserServiceTest extends UserServiceTestParent {

    @Test
    public void testGetWithMeals() throws Exception {
        User user = service.getWithMeals(USER_ID);
        USER.setMeals(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1));
        new ModelMatcher<User>((expected, actual) -> expected == actual ||
                (Objects.equals(expected.getPassword(), actual.getPassword())
                        && Objects.equals(expected.getId(), actual.getId())
                        && Objects.equals(expected.getName(), actual.getName())
                        && Objects.equals(expected.getEmail(), actual.getEmail())
                        && Objects.equals(expected.getCaloriesPerDay(), actual.getCaloriesPerDay())
                        && Objects.equals(expected.isEnabled(), actual.isEnabled())
                        && Objects.equals(expected.getMeals(), actual.getMeals())))
                .assertEquals(USER, user);
    }
}
